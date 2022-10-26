package com.example.healthappauth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SleepTimeCalculator extends AppCompatActivity {

    EditText start,end;
    CardView cardBtn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_time_calculator);

        start = findViewById(R.id.stdate_time_input);
        end = findViewById(R.id.spdate_time_input);
        cardBtn = findViewById(R.id.sleepcardBtn);
        textView = findViewById(R.id.sleeptextView);

        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Start = start.getText().toString();
                String End = end.getText().toString();

                if (start.length()>0&&end.length()>0){
/*
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    Date date1 = format.parse(Start);
                    Date date2 = format.parse(End);
                    long difference = date2.getTime() - date1.getTime();
*/
                    textView.setText("Your Sleep Time is " );
                }
                else {

                    textView.setText("Please Input All Box");

                }
            }
        });

    }
}