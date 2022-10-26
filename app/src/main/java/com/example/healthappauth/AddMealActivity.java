package com.example.healthappauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddMealActivity extends AppCompatActivity {

    // creating variables for our button, edit text,
    // firebase database, database reference, progress bar.
    private Button addMealBtn;
    private TextInputEditText mealNameEdt, mealDescEdt, mealPriceEdt, mealImgEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String mealID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        // initializing all our variables.
        addMealBtn = findViewById(R.id.idBtnAddMeal);
        mealNameEdt = findViewById(R.id.idEdtMealName);
        mealDescEdt = findViewById(R.id.idEdtMealDescription);
        mealPriceEdt = findViewById(R.id.idEdtMealPrice);
        mealImgEdt = findViewById(R.id.idEdtMealImageLink);

        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("Meals");
        // adding click listener for our add meal button.
        addMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                // getting data from our edit text.
                String mealName = mealNameEdt.getText().toString();
                String mealDesc = mealDescEdt.getText().toString();
                String mealPrice = mealPriceEdt.getText().toString();
                String mealImg = mealImgEdt.getText().toString();
                mealID = mealName;
                // on below line we are passing all data to our modal class.
                MealRVModal mealRVModal = new MealRVModal(mealID, mealName, mealDesc,mealPrice , mealImg);
                // on below line we are calling a add value event
                // to pass data to firebase database.
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // on below line we are setting data in our firebase database.
                        databaseReference.child(mealID).setValue(mealRVModal);
                        // displaying a toast message.
                        Toast.makeText(AddMealActivity.this, "Meal Added..", Toast.LENGTH_SHORT).show();
                        // starting a main activity.
                        startActivity(new Intent(AddMealActivity.this, MainActivity.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on below line.
                        Toast.makeText(AddMealActivity.this, "Fail to add Meal..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}