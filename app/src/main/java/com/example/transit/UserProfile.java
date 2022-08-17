package com.example.transit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    FirebaseUser user;
    DatabaseReference reference;
    String userID;
    Button Save;
    Button Cancel;
    String NAME, EMAIL, CONTACT, pCNIC;
    ImageView back;
    Button Deleteaccount;
    ProgressBar progressBar;

    EditText Name, Email, Contact, CNIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Name = findViewById(R.id.pname);
        Email = findViewById(R.id.pemail);
        Contact = findViewById(R.id.pnumber);
        CNIC = findViewById(R.id.pcnic);
        back = findViewById(R.id.backToMap);
        Cancel = findViewById(R.id.cancel);





        Save = findViewById(R.id.save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNumberChanged()){
                    Toast.makeText(UserProfile.this, "Data has been Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(UserProfile.this, "Data is Same", Toast.LENGTH_SHORT).show();
                }
//                Intent intent = new Intent(UserProfile.this, map.class);
//                startActivity(intent);
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if (userprofile != null)
                {
                    NAME = userprofile.Fullname;
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("userName",userprofile.Fullname);
                    myEdit.commit();
                    EMAIL = userprofile.Email;
                    CONTACT = userprofile.Mobile_number;
                    pCNIC = userprofile.Cnic;

                    Name.setText(NAME);
                    Email.setText(EMAIL);
                    Contact.setText(CONTACT);
                    CNIC.setText(pCNIC);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(UserProfile.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, map.class);
                startActivity(intent);
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, map.class);
                startActivity(intent);
            }
        });
    }


    private boolean isNumberChanged(){
        if (!CONTACT.equals(Contact.getText().toString())){
            reference.child(userID).child("Mobile_number").setValue(Contact.getText().toString());
            return true;
        }
        else {
            return false;
        }

    }

}