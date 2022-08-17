package com.example.transit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class arrivalscanning extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    DatabaseReference reference1;
    Task<Void> reference;
    String scanvalue2;
    FirebaseUser user;
    String userID;
    String NAME;
    int AmountDeducted;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrivalscanning);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference1 = FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        Date = simpleDateFormat.format(calendar.getTime());

        reference1.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if (userprofile != null)
                {
                    NAME = userprofile.Fullname;
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(arrivalscanning.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

        /**Scanner Code Start **/

        CodeScannerView scannerView2 = findViewById(R.id.scanner_view2);
        mCodeScanner = new CodeScanner(this, scannerView2);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                            Toast.makeText(arrivalscanning.this, result.getText(), Toast.LENGTH_LONG).show();
                            scanvalue2 = result.getText();

                            
                            SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            String s2 = sh.getString("depart", "");
//                            String s3 = sh.getString("userName", "");
                            //reference = FirebaseDatabase.getInstance().getReference().child("qrdata").child("departure").setValue(s2);
                            user = FirebaseAuth.getInstance().getCurrentUser();


                            int s6=sh.getInt("Credit",0);
                            if(scanvalue2.equals("North Nazimabad"))
                            {
                                int total=s6-10;
                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putInt("Credit", total);
                                myEdit.commit();
                                AmountDeducted = 10;

                            }
                            else if(scanvalue2.equals("Nagan"))
                            {
                                int total=s6-20;
                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putInt("Credit", total);
                                myEdit.commit();
                                AmountDeducted = 20;

                            }
                            else if(scanvalue2.equals("UP More"))
                            {
                                int total=s6-30;
                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putInt("Credit", total);
                                myEdit.commit();
                                AmountDeducted = 30;
                            }
                            else if(scanvalue2.equals("Power House"))
                            {
                                int total=s6-40;
                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putInt("Credit", total);
                                myEdit.commit();
                                AmountDeducted = 40;

                            }

                        int s7 = sh.getInt("Credit", 0);
                        Toast.makeText(arrivalscanning.this, "Your Remaining Balance Is:"+s7, Toast.LENGTH_SHORT).show();


//                        new CountDownTimer(30000, 1000) {
//                            public void onTick(long millisUntilFinished) {
//
//                            }
//
//                            public void onFinish() {
//                               // mTextField.setText("done!");
//                                int total=s6-90;
//                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
//                                myEdit.putInt("Credit", total);
//                                myEdit.commit();
//                                Toast.makeText(arrivalscanning.this, "Money Detected At Full:90PKR", Toast.LENGTH_SHORT).show();
//                                AmountDeducted = 90;
//                            }
//                        }
//                        .start();


                            /**Sending Data To Firebase **/

                            HashMap<String, Object> map = new HashMap<>();
                            map.put("Name", NAME);
                            map.put("Departure", s2.toString());
                            map.put("Arrival", scanvalue2.toString());
                            map.put("Amount",AmountDeducted);
                            map.put("UserId",userID);
                            map.put("Date",Date);

                            FirebaseDatabase.getInstance().getReference("qrcode").push().updateChildren(map);





                    }
                });
            }
        });
        scannerView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

/** Scanner Code End **/





        Toast.makeText(arrivalscanning.this, "Arrival Done:"+scanvalue2, Toast.LENGTH_SHORT).show();

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
