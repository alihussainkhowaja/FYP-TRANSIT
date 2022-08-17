package com.example.admintranis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    Button button;
    TextView forgot, Email, Password;
    ImageView back;
    FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.button);
        forgot = findViewById(R.id.textView2);
        back = findViewById(R.id.back);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }

            private void userLogin() {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (email.isEmpty()){
                    Email.setError("Enter your Email");
                    Email.requestFocus();
                    return;

                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Email.setError("Enter a valid Email");
                    Email.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    Password.setError("Password is required");
                    Password.requestFocus();
                    return;
                }
                if (password.length() < 8){
                    Password.setError("Minimum password length should be of 8 characters");
                    Password.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            if (user.isEmailVerified()){
                                Intent intent = new Intent (Login.this,dashboard.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(Login.this, "Please Verify Your Account", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }else{
                            Toast.makeText(Login.this, "Incorrect Password/Email", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        ImageView imageView = findViewById(R.id.code);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, code.class);
                startActivity(intent);
            }
        });

       TextView textView= findViewById(R.id.textView2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, forget.class);
                startActivity(intent);
            }
        });
    }
}