package com.example.transit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class verification extends AppCompatActivity {
    ImageView img;
    Button butt;
    Button but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        img =findViewById(R.id.back_to_home);
        butt = findViewById(R.id.button);
        but = findViewById(R.id.button2);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(verification.this,ForgetPassword.class);
                startActivity(intent);
            }
        });
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(verification.this,ResetPassword.class);
                startActivity(intent);
            }
        });
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(verification.this, ForgetPassword.class);
                startActivity(intent);
            }
        });
    }
}