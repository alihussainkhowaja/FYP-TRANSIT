package com.example.admintranis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Summary extends AppCompatActivity {
    TextView stop,arrival,departure;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        stop=findViewById(R.id.stop);
        arrival=findViewById(R.id.arrival);
        departure=findViewById(R.id.departure);
        databaseReference = FirebaseDatabase.getInstance().getReference();


        Query query = databaseReference.orderByChild("Arrival").equalTo("North Nazimabad");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.i(log, "M case count: "+snapshot.getChildrenCount());
                Log.i("arrival",""+snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException(); // Never ignore errors
            }
        });
    }
}