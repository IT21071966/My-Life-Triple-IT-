package com.example.mad01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class TrainersActivity extends AppCompatActivity {
    //creating recycler view object
    RecyclerView recyclerView2;
    //creating object from  object
    TrainersAdapter trainersAdapter;

    //back button
    Button trbtnBack,trbtnStep;

    //create a object from the  floating action button
    FloatingActionButton floatingActionButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainers);

        //giving the layout id to catch the recycler view (rv <- recycler view ID)
        recyclerView2 = (RecyclerView)findViewById(R.id.rv2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<TrainersModel> options =
                new FirebaseRecyclerOptions.Builder<TrainersModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Trainers"), TrainersModel.class)
                        .build();


        trainersAdapter = new TrainersAdapter(options);
        recyclerView2.setAdapter(trainersAdapter);


        //register floating action button
        floatingActionButton2 =(FloatingActionButton)findViewById(R.id.floatingActionButton2);
        //set onclick listener to floating action
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddTrActivity.class));
            }
        });

        //register back button
        trbtnBack = (Button)findViewById(R.id.trbtnBack);
        trbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(TrainersActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //register Stepcounter button
        trbtnStep = (Button)findViewById(R.id.trbtnStep);
        trbtnStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(TrainersActivity.this,StepCounter.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        trainersAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        trainersAdapter.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_ex,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                txtSearch(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    //creating a search menthod
    private  void txtSearch(String str1){
        FirebaseRecyclerOptions<TrainersModel> options =
                new FirebaseRecyclerOptions.Builder<TrainersModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Trainers").orderByChild("name").startAt(str1).endAt(str1+"~"), TrainersModel.class)
                        .build();


        trainersAdapter = new TrainersAdapter(options);
        trainersAdapter.startListening();
        recyclerView2.setAdapter(trainersAdapter);

    }
}