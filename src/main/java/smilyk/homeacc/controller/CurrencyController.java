package smilyk.homeacc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smilyk.homeacc.enums.Currency;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("v1/currency")
public class CurrencyController {

    @GetMapping()
    public List<Currency> getAllCurrency() {
        List<Currency> currencies = Arrays.asList(Currency.values());
        System.err.println(currencies);
        return currencies;
    }
}
