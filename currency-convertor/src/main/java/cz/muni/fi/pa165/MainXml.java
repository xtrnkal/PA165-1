/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165;

//import cz.muni.fi.pa165.currency.CurrencyConvertor;
//import cz.muni.fi.pa165.currency.ExchangeRateTable;
import java.math.BigDecimal;
import java.util.Currency;
//import javax.inject.Inject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author xtrnkal
 */
/*
@Configuration
@ComponentScan("cz.muni.fi.pa165")
@EnableAspectJAutoProxy
@ImportResource({"cz.muni.fi.pa165.currency-convertor.resources:applicationContext.xml"})
*/
public class MainXml {
    
    //@Inject
    //private ProductDao productDao;
    //@Bean
    public static void main(String ... args) {
        /*
        ExchangeRateTable exchangeRateTable = new ExchangeRateTableImpl();
        CurrencyConvertor convertor = new CurrencyConvertorImpl(exchangeRateTable);
        Currency CZK = Currency.getInstance("CZK");
        Currency EUR = Currency.getInstance("EUR");
        if (convertor.convert(EUR, CZK, BigDecimal.ONE) == BigDecimal.valueOf(27)) {
            System.out.println("OK");
        }
        */
        springXmlContext();
        //springJavaConfigContext();
    }
    
    private static void springXmlContext() {

        ApplicationContext applicationContext 
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        ExchangeRateTable exchangeRateTable = applicationContext.getBean(exchangeRateTable.class);
        
        //CurrencyConvertor currencyConvertor = applicationContext.getBean(exchangeRateTable, CurrencyConvertor.class);
        CurrencyConvertor currencyConvertor = applicationContext.getBean(CurrencyConvertorImpl.class);
        System.err.println(currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), BigDecimal.ONE));

    }
    
    /*
    private static void springJavaConfigContext() {

        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(SpringJavaConfig.class);

        ExchangeRateTable exchangeRateTable = applicationContext.getBean(ExchangeRateTable.class);
        
        
        //CurrencyConvertor currencyConvertor = applicationContext.getBean(exchangeRateTable, CurrencyConvertor.class);
        CurrencyConvertor currencyConvertor = applicationContext.getBean(CurrencyConvertor.class);
        System.err.println(currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), BigDecimal.ONE));
        
    }*/
    
}