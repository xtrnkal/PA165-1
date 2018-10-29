package cz.fi.muni.pa165.tasks;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task02 extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Category kitchen = new Category();
    private Category electro = new Category();
    private Product flashlight = new Product();
    private Product kitchenRobot = new Product();
    private Product plate = new Product();

    @BeforeClass
    public void OnlyOnce() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        electro.setName("Electro");
        kitchen.setName("Kitchen");
        em.persist(electro);
        em.persist(kitchen);

        flashlight.setName("Flashlight");
        kitchenRobot.setName("Kitchen robot");
        plate.setName("Plate");

        flashlight.addCategory(electro);
        kitchenRobot.addCategory(kitchen);
        kitchenRobot.addCategory(electro);
        plate.addCategory(kitchen);

        em.persist(flashlight);
        em.persist(kitchenRobot);
        em.persist(plate);

        /*
        em.persist(electro);
        em.persist(kitchen);
         */
        em.getTransaction().commit();
        em.close();
    }

    private void assertContainsCategoryWithName(Set<Category> categories,
            String expectedCategoryName) {
        for (Category cat : categories) {
            if (cat.getName().equals(expectedCategoryName)) {
                return;
            }
        }

        Assert.fail("Couldn't find category " + expectedCategoryName + " in collection " + categories);
    }

    private void assertContainsProductWithName(Set<Product> products,
            String expectedProductName) {

        for (Product prod : products) {
            if (prod.getName().equals(expectedProductName)) {
                return;
            }
        }

        Assert.fail("Couldn't find product " + expectedProductName + " in collection " + products);
    }

    @Test
    private void testCategoryKitchen() {
        EntityManager em = emf.createEntityManager();

        Category found = em.find(Category.class, kitchen.getId());
        Set<Product> foundProducts = found.getProducts();

        Assert.assertEquals(found.getProducts().size(), 2);
        assertContainsProductWithName(foundProducts, "Kitchen robot");
        assertContainsProductWithName(foundProducts, "Plate");

        em.close();
    }

    @Test
    private void testCateoryElectro() {
        EntityManager em = emf.createEntityManager();

        Category found = em.find(Category.class, electro.getId());
        Set<Product> foundProducts = found.getProducts();

        Assert.assertEquals(found.getProducts().size(), 2);
        assertContainsProductWithName(foundProducts, "Kitchen robot");
        assertContainsProductWithName(foundProducts, "Flashlight");

        em.close();
    }

    @Test
    public void testFlashlight() {
        EntityManager em = emf.createEntityManager();

        Product found = em.find(Product.class, flashlight.getId());
        Assert.assertEquals(found.getCategories().size(), 1);
        Assert.assertEquals(found.getCategories().iterator().next().getName(), "Electro");

        em.close();

    }

    @Test
    private void testKitchenRobot() {
        EntityManager em = emf.createEntityManager();

        Product found = em.find(Product.class, kitchenRobot.getId());
        Set<Category> foundCategories = found.getCategories();
        Assert.assertEquals(foundCategories.size(), 2);
        assertContainsCategoryWithName(foundCategories, "Kitchen");
        assertContainsCategoryWithName(foundCategories, "Electro");

        em.close();
    }

    @Test
    private void testPlate() {
        EntityManager em = emf.createEntityManager();

        Product found = em.find(Product.class, plate.getId());
        Assert.assertEquals(found.getCategories().size(), 1);

        Assert.assertEquals(found.getCategories().iterator().next().getName(), "Kitchen");

        em.close();
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testDoesntSaveNullName() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Product product = new Product();
        em.persist(product);

        em.getTransaction().commit();
        em.close();
    }
}
