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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.FirebaseDatabase;

public class PassengerHistory extends AppCompatActivity {
    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;


    @Override
    protected void onStart() {
        super.onStart();
        historyAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        historyAdapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_history);

        ImageView imageView = findViewById(R.id.backToMap);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PassengerHistory.this, dashboard.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        recyclerView=(RecyclerView)findViewById(R.id.rv);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        FirebaseRecyclerOptions<HistoryModel> options =
        new FirebaseRecyclerOptions.Builder<HistoryModel>().setQuery(FirebaseDatabase.getInstance().getReference().child("qrcode"),HistoryModel.class)
                .build();
                historyAdapter = new HistoryAdapter(options);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(historyAdapter);




        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),dashboard.class));
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
                return false;
            }
        });

    }
}