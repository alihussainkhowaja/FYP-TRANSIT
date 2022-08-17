package com.example.transit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class loginaccount extends AppCompatActivity {
    Button button;
    TextView signup, forgot, Email, Password;
    ImageView back;
    CheckBox loginState;
    FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginaccount);

        button = findViewById(R.id.button);
        signup = findViewById(R.id.signup);
        forgot = findViewById(R.id.textView2);
        back = findViewById(R.id.back);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        loginState = findViewById(R.id.checkbox);
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
                                Intent intent = new Intent (loginaccount.this,map.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(loginaccount.this, "Please Verify Your Account", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }else{
                          Toast.makeText(loginaccount.this, "Incorrect Password/Email", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginaccount.this, Login.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginaccount.this, Sign_In.class);
                startActivity(intent);
                finish();

            }
        });


        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(loginaccount.this, ForgetPassword.class);
              startActivity(intent);
            }
        });

    }

    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();;
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

}