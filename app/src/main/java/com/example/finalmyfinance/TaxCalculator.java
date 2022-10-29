package com.example.finalmyfinance;

public class TaxCalculator {
    public static final TaxCalculator instance = new TaxCalculator();

    public static TaxCalculator getInstance() {
        return instance;
    }

    float tax;

    public float calTax(float inc){
        if(inc<=100000){
            tax = 0;
        }
        if(inc>100000){
            tax = (float) (inc*5/100);
        }
        if(inc>200000){
            tax = (float) (inc*10/100);
        }
        if(inc>300000){
            tax = (float) (inc*15/100);
        }
        if(inc>400000){
            tax = (float) (inc*20/100);
        }

        return(tax) ;

    }



}
