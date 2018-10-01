package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    //private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        if (sourceCurrency == null) {
            throw new IllegalArgumentException("Source currency can't be null.");
        } else if (targetCurrency == null) {
            throw new IllegalArgumentException("Target currency can't be null.");
        } else if (sourceAmount == null) {
            throw new IllegalArgumentException("Source amount can't be null.");
        }
        
        BigDecimal result;
        
        try {
            result = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
            
        } catch (UnknownExchangeRateException ex1) {
            Logger.getLogger(CurrencyConvertorImpl.class.getName()).log(Level.SEVERE, null, ex1);
            throw new UnknownExchangeRateException("Given currency is unknown.");
        } catch (ExternalServiceFailureException ex2) {
            throw new UnknownExchangeRateException("Lookup for exchange rate failed.", ex2);
        }
        
        return result.multiply(sourceAmount).setScale(2, RoundingMode.HALF_EVEN);
    }

}
