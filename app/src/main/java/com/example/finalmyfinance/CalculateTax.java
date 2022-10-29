package com.example.finalmyfinance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class CalculateTax extends AppCompatActivity {

    EditText income;
    CardView cardBtn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        income = findViewById(R.id.income_input);
        cardBtn = findViewById(R.id.cardBtn);
        textView = findViewById(R.id.textView);

        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Income = income.getText().toString();

                if (income.length()>0)
                {
                    float inc = Float.parseFloat(Income);
                    float incT = Float.parseFloat(Income);

                    float tax = TaxCalculator.getInstance().calTax(inc);

                    displayBMI(tax);


                }
                else {

                    textView.setText("Please Input All Box");
                }
            }
        });

    }

    private void displayBMI(float tax) {
        textView.setText("Your monthly tax is : " + tax);

    }

}
