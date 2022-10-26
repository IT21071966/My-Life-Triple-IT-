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

public class AddExActivity extends AppCompatActivity {

    EditText name,duration,sets,url;
    Button exbtnSave, exbtnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ex);


        name = (EditText)findViewById(R.id.extxtName);
        duration =(EditText)findViewById(R.id.extxtDuration);
        sets =(EditText)findViewById(R.id.extxtSets);
        url = (EditText)findViewById(R.id.extxtUrl);

        exbtnSave = (Button) findViewById(R.id.exbtnAdd);
        exbtnBack = (Button) findViewById(R.id.exbtnBack);

        exbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertData();
                clearAll();
            }
        });

        exbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddExActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }
    //create a method to add data to DB
    private void insertData(){
        Map<String,Object> map = new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("duration",duration.getText().toString());
        map.put("sets",sets.getText().toString());
        map.put("url",url.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Exercise").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddExActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddExActivity.this, "Error in Data Insertion", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearAll(){
        name.setText("");
        duration.setText("");
        sets.setText("");
        url.setText("");

    }

    }

