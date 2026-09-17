package com.unit.testing.service;

import org.springframework.stereotype.Service;

@Service
public class PriceCalculator {

    public double calcuatePrice(double price, double discount){

        if (price < 0){
            throw new IllegalArgumentException("price cannot be negative");
        }

        if (discount < 0 || discount > 100){
            throw new IllegalArgumentException("discount should be within 0 to 100");
        }

        return price - ((price * discount) / 100);

    }

}
