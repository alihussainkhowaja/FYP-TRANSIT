package com.example.admintranis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.FirebaseDatabase;

public class complains extends AppCompatActivity {
    RecyclerView recyclerView;
    ComplainAdapter complainAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        complainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        complainAdapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complains);

        recyclerView=(RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        

        FirebaseRecyclerOptions<ComplainModel> options =
                new FirebaseRecyclerOptions.Builder<ComplainModel>().setQuery(FirebaseDatabase.getInstance().getReference().child("Complain"),ComplainModel.class)
                        .build();
        complainAdapter = new ComplainAdapter(options);
        recyclerView.setAdapter(complainAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        return true;

                    case R.id.pHistory:
                        startActivity(new Intent(getApplicationContext(),PassengerHistory.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.complain:
                        startActivity(new Intent(getApplicationContext(),complains.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return true;
            }
        });
        ImageView imageView = findViewById(R.id.backToMap);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(complains.this, dashboard.class);
                startActivity(intent);
            }
        });
    }
}