package com.example.transit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Complains extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ImageView img;
    Button button;
    Button cancel;
    Spinner reason_spinner,issue_spinner;
    TextView reason,issue;
    DatabaseReference databaseReference;
    String item,item1,description,name,email;
    Member member;
    EditText editText;

    FirebaseUser user;
    DatabaseReference reference;
    String userID;
    String NAME, EMAIL;
    EditText Name, Email;


    String[] reasons ={"Choose Reason","Delayed in reaching destination","Health and hygiene issue","poor quality services","wrongly charged","Inappropriate QR Codes"};
    String[] issues={"Choose Issue","Bus was late","Over Crowded bus","No cleanliness","Too Slow","Improper management"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complains);

        editText=findViewById(R.id.edittext);
        img = findViewById(R.id.backToMap);
        reason=findViewById(R.id.reason);
        issue=findViewById(R.id.issue);
        button = findViewById(R.id.button);
        cancel = findViewById(R.id.cancel);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        databaseReference=database.getReference("Complain");
        reason_spinner=findViewById(R.id.reason_spinner);
        reason_spinner.setOnItemSelectedListener(this);
        issue_spinner=findViewById(R.id.issue_spinner);
        issue_spinner.setOnItemSelectedListener(this);

        Name = findViewById(R.id.pname);
        Email = findViewById(R.id.pemail);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();
        String id = databaseReference.push().getKey();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if (userprofile != null)
                {
                    NAME = userprofile.Fullname;
                    EMAIL = userprofile.Email;

                    Name.setText(NAME);
                    Email.setText(EMAIL);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Complains.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

        member = new Member();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,reasons);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reason_spinner.setAdapter(arrayAdapter);
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,issues);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        issue_spinner.setAdapter(arrayAdapter1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEmail(email);
                saveName(name);
//                SaveDescription(description);
                description = editText.getText().toString();
                member.setDescription(description);

                SaveReason(item);
                SaveIssue(item1);

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Complains.this, map.class);
                startActivity(intent);
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Complains.this,map.class);
                startActivity(intent);
            }
        });
    }


    void saveEmail(String email) {
        member.setEmail(email);
//        String id = databaseReference.push().getKey();
//        databaseReference.child(id).setValue(member);

    }

     void saveName(String name) {
        member.setName(name);
//        String id = databaseReference.push().getKey();
//        databaseReference.child(id).setValue(member);
    }
        void SaveIssue(String item1) {
        if (item1 == "Choose Issue"){
            Toast.makeText(this, "Please Select a Issue", Toast.LENGTH_SHORT).show();
        }
        else{
            member.setIssue(item1);
            String id = databaseReference.push().getKey();
            databaseReference.child(id).setValue(member);
            Toast.makeText(this, "Complain Saved", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Complains.this,map.class);
            startActivity(intent);

        }
    }


    void SaveReason(String item) {
      if (item == "Choose Reason"){
          Toast.makeText(this, "Please Select a Reason", Toast.LENGTH_SHORT).show();
      }
      else{
          member.setReason(item);
//          String id = databaseReference.push().getKey();
//          databaseReference.child(id).setValue(member);
          Toast.makeText(this, "Reason Saved", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Complains.this,map.class);
             startActivity(intent);
      }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    item = reason_spinner.getSelectedItem().toString();
    reason.setText(item);
    item1 = issue_spinner.getSelectedItem().toString();
    issue.setText(item1);
    name= Name.getText().toString();
    Name.setText(name);
    email = Email.getText().toString();
    Email.setText(email);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}