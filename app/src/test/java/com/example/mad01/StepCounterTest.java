package com.example.mad01;

import junit.framework.TestCase;

import java.util.Arrays;

import kotlin.jvm.internal.Intrinsics;

public class StepCounterTest extends TestCase {

    public void testGetDistanceCovered() {

        int input = 50;
        String output;
        String ExValue= 125+ " meter";
        double delta = 1;

       StepCounter s = new StepCounter();
       output = s.getDistanceCovered(input);


       assertEquals(ExValue,output,delta);
    }
}