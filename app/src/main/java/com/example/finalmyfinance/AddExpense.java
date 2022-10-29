package com.example.finalmyfinance;

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

public class AddExpense extends AppCompatActivity {

    EditText name,amount,note,url;
    Button expbtnSave, expbtnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        name = (EditText)findViewById(R.id.exptxtName);
        amount =(EditText)findViewById(R.id.exptxtAmount);
        note =(EditText)findViewById(R.id.exptxtNotes);
        url = (EditText)findViewById(R.id.exptxtUrl);

        expbtnSave = (Button) findViewById(R.id.expbtnAdd);
        expbtnBack = (Button) findViewById(R.id.expbtnBack);

        expbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertData();
                clearAll();
            }
        });

        expbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddExpense.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }
    //create a method to add data to DB
    private void insertData(){
        Map<String,Object> map = new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("amount",amount.getText().toString());
        map.put("note",note.getText().toString());
        map.put("url",url.getText().toString());


        FirebaseDatabase.getInstance().getReference().child("bugdetexp").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddExpense.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddExpense.this, "Error in Data Insertion", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearAll(){
        name.setText("");
        amount.setText("");
        note.setText("");
        url.setText("");

    }

}
