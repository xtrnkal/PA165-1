package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;
import org.junit.Test;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.*;
//import org.junit.runner.RunWith;

//@RunWith(MockingJUnitRunner.class)
public class CurrencyConvertorImplTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency INR = Currency.getInstance("INR");
    
    private final ExchangeRateTable exchangeRateTable = mock(ExchangeRateTable.class);
    private final CurrencyConvertor currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    
    /*
    @Mock
    private ExchangeRateTable exchangeRateTable;
    private CurrencyConvertor currencyConvertor;
    
    @Before
    public void SetUp() {
        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }
    */
    
    //TODO doplnit jeste to logovani
    
    @Test
    public void testConvert() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(USD, INR)).thenReturn(new BigDecimal("73.152"));
        assertThat(currencyConvertor.convert(USD, INR, new BigDecimal("1.50"))).isEqualTo(new BigDecimal("109.73"));

        // doplnit jeste 3-4 cases na ten rounding, ze se treba nechova jako jiny rounding
        
        // Don't forget to test border values and proper rounding.
    }

    @Test
    public void testConvertWithNullSourceCurrency() throws ExternalServiceFailureException {
        
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
        
        when(exchangeRateTable.getExchangeRate(null, USD)).thenThrow(illegalArgumentException);
        
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> currencyConvertor.convert(null, USD, BigDecimal.ONE))
                .withMessage("Source currency can't be null.");
    }

    @Test
    public void testConvertWithNullTargetCurrency() throws ExternalServiceFailureException {
        
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
        
        when(exchangeRateTable.getExchangeRate(USD, null)).thenThrow(illegalArgumentException);
        
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> currencyConvertor.convert(USD, null, BigDecimal.ONE))
                .withMessage("Target currency can't be null.");
    }

    @Test
    public void testConvertWithNullSourceAmount() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(USD, INR)).thenReturn(new BigDecimal("73.152"));
        
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> currencyConvertor.convert(USD, INR, null))
                .withMessage("Source amount can't be null.");
    }

    @Test
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        UnknownExchangeRateException unknownExchangeRateException = new UnknownExchangeRateException("Given currency is unknown.");

        Currency CZK = Currency.getInstance("CZK");
        when(exchangeRateTable.getExchangeRate(USD, CZK)).thenThrow(unknownExchangeRateException);

        assertThatExceptionOfType(UnknownExchangeRateException.class)
                .isThrownBy(() -> currencyConvertor.convert(USD, CZK, BigDecimal.ONE))
                .withMessage("Given currency is unknown.");
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        ExternalServiceFailureException externalServiceFailureException = new ExternalServiceFailureException("External error.");
        
        when(exchangeRateTable.getExchangeRate(USD, INR)).thenThrow(externalServiceFailureException);
        
        assertThatExceptionOfType(UnknownExchangeRateException.class)
                .isThrownBy(() -> currencyConvertor.convert(USD, INR, BigDecimal.ONE))
                .withMessage("Lookup for exchange rate failed.")
                .withCause(externalServiceFailureException);
    }

}
