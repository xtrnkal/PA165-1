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
        flashlight.setName("Kitchen robot");
        flashlight.setName("Plate");

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
    private void testCategory() {
        
    }

    @Test
    public void testFlashlight() {
        EntityManager em = emf.createEntityManager();

        Product found = em.find(Product.class, flashlight.getId());
        Assert.assertEquals(found.getCategories().size(), 1);
        Assert.assertEquals(found.getCategories().iterator().next().getName(), "Electro");

        em.close();

    }
}

