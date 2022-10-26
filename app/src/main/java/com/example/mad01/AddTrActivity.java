package com.example.mad01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddTrActivity extends AppCompatActivity {

    EditText name,price,time,url;
    Button trbtnSave, trbtnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tr);

        name = (EditText)findViewById(R.id.trtxtName);
        price =(EditText)findViewById(R.id.trtxtPrice);
        time =(EditText)findViewById(R.id.trtxtTime);
        url = (EditText)findViewById(R.id.trtxtUrl);

        trbtnSave = (Button) findViewById(R.id.trbtnAdd);
        trbtnBack = (Button) findViewById(R.id.trbtnBack);



        trbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertDataTrainer();
                clearAllTrainer();
            }
        });

        trbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTrActivity.this,TrainersActivity.class);
                startActivity(intent);
            }
        });
    }

    //create a method to add data to DB
    private void insertDataTrainer(){
       Map<String,Object> map= new HashMap<>();
       map.put("name",name.getText().toString());
       map.put("price",price.getText().toString());
       map.put("time",time.getText().toString());
       map.put("url",url.getText().toString());

       FirebaseDatabase.getInstance().getReference().child("Trainers").push()
               .setValue(map)
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void unused) {
                       Toast.makeText(AddTrActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(AddTrActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                   }
               });

    }

    private void clearAllTrainer(){
        name.setText("");
        time.setText("");
        price.setText("");
        url.setText("");

    }
}