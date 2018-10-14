package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author xtrnkal
 */
public class MainXml {

    public static void main(String[] args) {
        ApplicationContext applicationContext
                = new ClassPathXmlApplicationContext("applicationContext.xml");

        CurrencyConvertor currencyConvertor 
                = (CurrencyConvertor) applicationContext.getBean("CurrencyConvertor");

        BigDecimal amount = new BigDecimal(1);
        BigDecimal result = currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), amount);

        System.out.println(result.longValue());
    }
}
