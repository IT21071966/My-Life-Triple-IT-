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

import java.util.HashMap;
import java.util.Map;

public class EditMealActivity extends AppCompatActivity {

    // creating variables for our edit text, firebase database,
    // database reference, meal rv modal,progress bar.
    private TextInputEditText mealNameEdt, mealDescEdt, mealPriceEdt, mealImgEdt;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private MealRVModal mealRVModal;
    private ProgressBar loadingPB;
    // creating a string for our meal id.
    private String mealID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meal);
        // initializing all our variables on below line.
        Button addMealBtn = findViewById(R.id.idBtnAddMeal);
        mealNameEdt = findViewById(R.id.idEdtMealName);
        mealDescEdt = findViewById(R.id.idEdtMealDescription);
        mealPriceEdt = findViewById(R.id.idEdtMealPrice);
        mealImgEdt = findViewById(R.id.idEdtMealImageLink);

        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line we are getting our modal class on which we have passed.
        mealRVModal = getIntent().getParcelableExtra("meal");
        Button deleteMealBtn = findViewById(R.id.idBtnDeleteMeal);

        if (mealRVModal != null) {
            // on below line we are setting data to our edit text from our modal class.
            mealNameEdt.setText(mealRVModal.getMealName());
            mealPriceEdt = findViewById(R.id.idEdtMealPrice);
            mealImgEdt.setText(mealRVModal.getMealImg());
            mealDescEdt.setText(mealRVModal.getMealDescription());
            mealID = mealRVModal.getMealId();
        }

        // on below line we are initialing our database reference and we are adding a child as our Meal id.
        databaseReference = firebaseDatabase.getReference("Meals").child(mealID);
        // on below line we are adding click listener for our add Meal button.
        addMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are making our progress bar as visible.
                loadingPB.setVisibility(View.VISIBLE);
                // on below line we are getting data from our edit text.
                String mealName = mealNameEdt.getText().toString();
                String mealDesc = mealDescEdt.getText().toString();
                String mealPrice = mealPriceEdt.getText().toString();
                String mealImg = mealImgEdt.getText().toString();

                // on below line we are creating a map for
                // passing a data using key and value pair.
                Map<String, Object> map = new HashMap<>();
                map.put("mealName", mealName);
                map.put("mealDescription", mealDesc);
                map.put("mealPrice", mealPrice);
                map.put("mealImg", mealImg);
                map.put("mealId", mealID);

                // on below line we are calling a database reference on
                // add value event listener and on data change method
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // making progress bar visibility as gone.
                        loadingPB.setVisibility(View.GONE);
                        // adding a map to our database.
                        databaseReference.updateChildren(map);
                        // on below line we are displaying a toast message.
                        Toast.makeText(EditMealActivity.this, "Meal Updated..", Toast.LENGTH_SHORT).show();
                        // opening a new activity after updating our coarse.
                        startActivity(new Intent(EditMealActivity.this, MyHealth.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on toast.
                        Toast.makeText(EditMealActivity.this, "Fail to update meal..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // adding a click listener for our delete meal button.
        deleteMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete a meal.
                deleteMeal();
            }
        });

    }

    private void deleteMeal() {
        // on below line calling a method to delete the meal.
        databaseReference.removeValue();
        // displaying a toast message on below line.
        Toast.makeText(this, "Meal Deleted..", Toast.LENGTH_SHORT).show();
        // opening a main activity on below line.
        startActivity(new Intent(EditMealActivity.this, MyHealth.class));
    }
}