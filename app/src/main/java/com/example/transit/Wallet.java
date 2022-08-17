package com.example.transit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Wallet extends AppCompatActivity {

    ImageView back;
    Button top;
    TextView paisy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        back = findViewById(R.id.backToMap);
        top= findViewById(R.id.topup);
        paisy=findViewById(R.id.amount);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int s4 = sh.getInt("Credit", 0);
        paisy.setText(String.valueOf(s4));



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wallet.this, map.class);
                startActivity(intent);
            }
        });

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wallet.this, TopupScreen.class);
                startActivity(intent);
            }
        });


    }
}