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

public class MainActivity extends AppCompatActivity {
    //creating recycler view object
    RecyclerView recyclerView;
    //creating object from  object
    MainAdapter mainAdapter;

    //floating actin button
    FloatingActionButton floatingActionButton;

    Button btnTrainers,btnStepcount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //giving the layout id to catch the recycler view (rv <- recycler view ID)
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //firebase connect getting form the Firebase source codes (Exercise <- DB name)
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Exercise"), MainModel.class)
                        .build();


        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);


        //floating action button and using it to open the add new exercise
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddExActivity.class));
            }
        });

        //To direct to trainers
        btnTrainers = (Button)findViewById(R.id.btnTrainers);
        btnTrainers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TrainersActivity.class));
            }
        });

       //To direct to S
       btnStepcount = (Button)findViewById(R.id.btnStepcount);
        btnStepcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StepCounter.class));
            }
       });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //passing adapter to start the method
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //passing adapter to stop the method
        mainAdapter.stopListening();
    }
//for the search from the DB
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_ex,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //calling the txtSearch method
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //calling the txtSearch method
                txtSearch(query);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    //creating a search menthod
    private  void txtSearch(String str1){
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Exercise").orderByChild("name").startAt(str1).endAt(str1+"~"), MainModel.class)
                        .build();


        mainAdapter = new MainAdapter(options);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }
}