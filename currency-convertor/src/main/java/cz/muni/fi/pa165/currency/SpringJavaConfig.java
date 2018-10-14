package cz.muni.fi.pa165.currency;

import javax.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 *
 * @author xtrnkal
 */
@Configuration
@ComponentScan("cz.muni.fi.pa165")
@EnableAspectJAutoProxy
public class SpringJavaConfig {

    @Inject
    private ExchangeRateTable exchangeRateTable;

    @Bean
    public CurrencyConvertor currencyConvertorImpl() {
        System.err.println("Creating currencyConvertorImpl");
        return new CurrencyConvertorImpl(exchangeRateTable);
    }
    
}
