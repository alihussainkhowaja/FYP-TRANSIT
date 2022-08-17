package com.example.transit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    ImageView bac;
    Button butt;
    Button can;
    EditText email;

    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        bac = findViewById(R.id.back_to_home);
        butt = findViewById(R.id.button);
        can = findViewById(R.id.button2);
        email =findViewById(R.id.EmailAddress);

        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPassword.this,loginaccount.class);
                startActivity(intent);
            }
        });

        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button();

//                Intent intent = new Intent(ForgetPassword.this,verification.class);
//                startActivity(intent);
            }
        });

        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPassword.this,loginaccount.class);
                startActivity(intent);
            }
        });

    }
    private void button(){
        String EmailAddress = email.getText().toString().trim();

        if(EmailAddress.isEmpty()){
            email.setError("Email Required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(EmailAddress).matches()){
            email.setError("please provide valid email");
            email.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(EmailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this, "check your email to reset your password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(ForgetPassword.this, "Try again! some thing went wrong.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}