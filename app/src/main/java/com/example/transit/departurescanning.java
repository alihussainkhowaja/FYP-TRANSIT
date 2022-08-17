package com.example.transit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class departurescanning extends AppCompatActivity  {
    //Task<Void> reference;
    private CodeScanner mCodeScanner;
    String scanvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departurescanning);

/** Scanner Code Start **/

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        SharedPreferences shh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        int blnc = shh.getInt("Credit", 0);


                        if (blnc >= 50) {

                            Toast.makeText(departurescanning.this, result.getText(), Toast.LENGTH_LONG).show();
                            scanvalue = result.getText();

                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("depart", scanvalue);
                            myEdit.commit();
                            //SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            //String s1 = sh.getString("depart", "");
                            // reference = FirebaseDatabase.getInstance().getReference().child("qrdata").child("departure").setValue(s1);
                        }
                        else{
                            Toast.makeText(departurescanning.this, "Insufficient Credit.", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

/** Scanner Code End **/




        Toast.makeText(this, "Your Departure Is:"+scanvalue , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

}
