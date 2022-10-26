package com.example.healthappauth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.collection.LLRBNode;

public class BMIcalculator extends AppCompatActivity {


    EditText edKg,edFeet,edIns;
    CardView cardBtn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);
        edFeet = findViewById(R.id.edFeet);
        edKg = findViewById(R.id.edKg);
        cardBtn = findViewById(R.id.cardBtn);
        textView = findViewById(R.id.textView);
        edIns = findViewById(R.id.edIns);


        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String kg = edKg.getText().toString();
                String feet = edFeet.getText().toString();
                String inc = edIns.getText().toString();

                if (kg.length()>0&&feet.length()>0&&inc.length()>0)
                {
                    float weight = Float.parseFloat(kg);
                    float efeet = Float.parseFloat(feet);
                    float eInc = Float.parseFloat(inc);

                    float bmi = BMICalcUtil.getInstance().calcBMI(weight,efeet,eInc);
                    displayBMI(bmi);

                }
                else {

                    textView.setText("Please Input All Box");
                }
            }
        });

    }
    private void displayBMI(float bmi) {

        if (bmi>24){

            textView.setText("Overweight : "+bmi);

        }else if (bmi>18){

            textView.setText("Normal weight : "+bmi);

        }else {

            textView.setText("Underweight : "+bmi);
        }
    }


}