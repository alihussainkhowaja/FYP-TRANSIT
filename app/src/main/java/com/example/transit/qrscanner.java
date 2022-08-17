package com.example.transit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

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

public class qrscanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {


    ZXingScannerView scannerView;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    StopModel stopModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        reference = FirebaseDatabase.getInstance().getReference("qrdata");

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void handleResult(Result rawResult) {

        String data = rawResult.getText().toString();
        String data1 = rawResult.getText().toString();
        stopModel = new StopModel();
        stopModel.setArrival(data1);

        reference.push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                stopModel.setDeparture(data);
                String id = reference.push().getKey().toString();
                reference.child(id).setValue(stopModel);
                Toast.makeText(qrscanner.this, "data inserted", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        reference.push().setValue(data1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                stopModel.setArrival(data1);
                String id = reference.push().getKey().toString();
                reference.child(id).setValue(stopModel);
                Toast.makeText(qrscanner.this, "data inserted", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StopModel stopModel1 = snapshot.getValue(StopModel.class);
                if (stopModel1.getDeparture()!=null)
                { stopModel.setArrival(data);
//                    reference.push().setValue(stopModel);
                    onBackPressed();
                }
                else {
//                    reference.push().setValue(stopModel);
                    onBackPressed();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.startCamera();
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}