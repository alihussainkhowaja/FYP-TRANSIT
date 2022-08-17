package com.example.transit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class scanoptions extends AppCompatActivity {

    private Button arrival_btn,depart_btn;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanoptions);

        arrival_btn=(Button)findViewById(R.id.arrival_btn);
        depart_btn=(Button)findViewById(R.id.depart_btn);
        arrival_btn.setEnabled(false);


        depart_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO

                Intent intent = new Intent(getApplicationContext(), departurescanning.class);
                startActivity(intent);
                arrival_btn.setEnabled(true);
               /* Toast.makeText(
                        getApplicationContext(),
                        "Button Clicked",
                        Toast.LENGTH_SHORT
                ).show();*/

            }});


        arrival_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO

                Intent intent = new Intent(getApplicationContext(), arrivalscanning.class);
                startActivity(intent);
                /*Toast.makeText(
                        getApplicationContext(),
                        "Child Clicked",
                        Toast.LENGTH_SHORT
                ).show();*/

            }});

    }
}