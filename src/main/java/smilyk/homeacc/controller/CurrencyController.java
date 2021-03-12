package smilyk.homeacc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smilyk.homeacc.enums.Currency;

import java.util.Arrays;
import java.util.List;

@RestController
@Api( value = "home-accounting" , description = "Operations pertaining to currency in HomeAccounting" )

@RequestMapping("v1/currency")
public class CurrencyController {

    @ApiOperation(value = "Search all currency by user uuid")
    @GetMapping()
    public List<Currency> getAllCurrency() {
        List<Currency> currencies = Arrays.asList(Currency.values());
        System.err.println(currencies);
        return currencies;
    }
}
