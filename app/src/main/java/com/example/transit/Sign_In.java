package com.example.transit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Sign_In extends AppCompatActivity  {
    private FirebaseAuth mAuth;
    EditText Fullname, Cnic, Email, Password,Mobile_number;
    Button save;
    TextView login;
    ImageView back;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Fullname =findViewById(R.id.fullname);
        Cnic = findViewById(R.id.cnic);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Mobile_number = findViewById(R.id.mobile_number);
        login = findViewById(R.id.login);
        back = findViewById(R.id.back_to_home);
        save = findViewById(R.id.save);

        progressBar = findViewById(R.id.progressBar);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             registeruser();


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_In.this, loginaccount.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_In.this, Login.class);
                startActivity(intent);

            }
        });

    }

    private void registeruser() {

        String name = Fullname.getText().toString().trim();
        String cnic = Cnic.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String phone = Mobile_number.getText().toString().trim();

        if (name.isEmpty()){
            Fullname.setError("This is a required field");
            Fullname.requestFocus();
            return;
        }
        if (cnic.isEmpty()){
            Cnic.setError("This is a required field");
            Cnic.requestFocus();
            return;
        }
        if (cnic.length() < 13){
            Cnic.setError("CNIC must be of 13 digits");
            Cnic.requestFocus();
            return;

        }
        if (email.isEmpty()){
            Email.setError("Please enter your EmailID");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("please enter a valid Email");
            Email.requestFocus();
            return;
        }

        if (password.isEmpty()){
            Password.setError("This is a required field");
            Password.requestFocus();
            return;
        }
        if (password.length() < 8){
            Password.setError("Minimum length should be of 8 characters");
            Password.requestFocus();
            return;
        }
        if (phone.isEmpty()){
            Mobile_number.setError("This is a required field");
            Mobile_number.requestFocus();
            return;
        }
        if (phone.length() < 11){
            Mobile_number.setError("Phone number must be of 11 characters");
            Mobile_number.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(name,cnic,email,phone);
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                  if (task.isSuccessful()){

                                      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                      user.sendEmailVerification();
                                      Toast.makeText(Sign_In.this, "Please verify your Email before Logging IN", Toast.LENGTH_LONG).show();
                                      Intent intent = new Intent(Sign_In.this, loginaccount.class);
                                      startActivity(intent);
                                  }else {
                                      Toast.makeText(Sign_In.this, "SignUp Failed", Toast.LENGTH_LONG).show();
                                      progressBar.setVisibility(View.GONE);
                                  }
                                }
                            });
                        }else {
                            Toast.makeText(Sign_In.this, "SignUp Failed", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }


}