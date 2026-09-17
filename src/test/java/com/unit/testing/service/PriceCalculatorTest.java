package com.unit.testing.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceCalculatorTest {

    private PriceCalculator priceCalculator = new PriceCalculator();

    @Test
    void shouldApplyDiscountToPrice(){
        //arrange -- initial input
        double price = 1000;
        double discount = 20;

        //action -- action performed
        double actualPrice = priceCalculator.calcuatePrice(price,discount);

        //assertion -- expected output
        assertEquals(800,actualPrice);
    }

    @Test
    void shouldNotBeMoreThanHundred(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> priceCalculator.calcuatePrice(1000,120));

        assertEquals("discount should be within 0 to 100", exception.getMessage());
    }

}
