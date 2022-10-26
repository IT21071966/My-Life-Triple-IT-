package com.example.healthappauth;

import junit.framework.TestCase;

public class BMICalcUtilTest extends TestCase {

    public void testCalcBMI() {
        float wei = 65;
        float ft = 5;
        float output;
        float inch = 11;
        float expected = 20;
        double delta = 1;

        BMICalcUtil bmiCalcUtil = new BMICalcUtil();
        output = bmiCalcUtil.calcBMI(wei,ft,inch);

        assertEquals(expected,output,delta);

    }
}