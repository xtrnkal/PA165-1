package cz.muni.fi.pa165.currency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.math.BigDecimal;
import java.util.Currency;

/**
 *
 * @author xtrnkal
 */
public class MainJavaConfig {

    public static void main(String[] arguments) {
        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(SpringJavaConfig.class);

        CurrencyConvertor convertor
                = applicationContext.getBean("currencyConvertorImpl", CurrencyConvertor.class);

        BigDecimal result = convertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), new BigDecimal(1));
        System.out.println(result);
    }
}
