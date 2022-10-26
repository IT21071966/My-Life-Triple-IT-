package com.example.healthappauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MyHealth extends AppCompatActivity implements View.OnClickListener {

    private CardView mh1,mh2,mh3;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_health);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mh1 = (CardView) findViewById(R.id.myH1);
        mh2 = (CardView) findViewById(R.id.myH2);
        mh3 = (CardView) findViewById(R.id.myH3);

        mh1.setOnClickListener(this);
        mh2.setOnClickListener(this);
        mh3.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.myH1:
                i = new Intent(this,CalorieCalculator.class);
                startActivity(i);
                break;

            case  R.id.myH2:
                i = new Intent(this,SleepTimeCalculator.class);
                startActivity(i);
                break;

            case  R.id.myH3:
                i = new Intent(this,BMIcalculator.class);
                startActivity(i);
            break;
        }

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // adding a click listener for option selected on below line.
        int id = item.getItemId();
        switch (id) {
            case R.id.idLogOut:
                // displaying a toast message on user logged out inside on click.
                Toast.makeText(getApplicationContext(), "User Logged Out", Toast.LENGTH_LONG).show();
                // on below line we are signing out our user.
                mAuth.signOut();
                // on below line we are opening our login activity.
                Intent i = new Intent( MyHealth.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // on below line we are inflating our menu
        // file for displaying our menu options.
        getMenuInflater().inflate(R.menu.main_main, menu);
        return true;
    }
}