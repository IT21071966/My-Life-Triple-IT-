package com.example.healthappauth;

import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class BMICalcUtil {

    public static final BMICalcUtil instance = new BMICalcUtil();

    public static BMICalcUtil getInstance() {
        return instance;
    }

    public float calcBMI(float weight, float efeet ,float eInc){


        float height = (float) (efeet*0.3048+ eInc*0.0254);
        return( weight/(height*height)) ;

    }

}

