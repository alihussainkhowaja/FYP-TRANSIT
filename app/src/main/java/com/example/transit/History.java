package com.example.transit;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    ImageView img;
    LinearLayoutManager layoutManager;
    List<ModelClass>userlist;
    Adapter adapter;
    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    private FirebaseUser user;
    private String UserId;


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
        setContentView(R.layout.activity_history);
        img = findViewById(R.id.backToMap);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(History.this,map.class);
                startActivity(intent);


            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserId =user.getUid();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        FirebaseRecyclerOptions<HistoryModel> options =
                new FirebaseRecyclerOptions.Builder<HistoryModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("qrcode").orderByChild("UserId").equalTo(UserId),HistoryModel.class)
                        .build();
        historyAdapter = new HistoryAdapter(options);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(historyAdapter);


    }

    }