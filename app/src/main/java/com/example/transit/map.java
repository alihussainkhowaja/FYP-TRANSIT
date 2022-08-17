package com.example.transit;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Map;

public class map extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnMapReadyCallback {

    TextView textView1;
    TextView name;
    DrawerLayout drawerLayout;
    ImageView imageView;
    TextView textView ,textAdd;
    private ResultReceiver resultReceiver;
    TextView Delteaccount;
    ProgressBar ProgressBar;
    private LocationCallback locationCallback;
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient client;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private int REQUEST_CODE = 111;
    private GoogleMap googleMap;
    String userID;
    FirebaseUser user;
    DatabaseReference reference;
    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();


    LatLng NumiashChowrangi = new LatLng(24.876956028379634, 67.03490890299491);
    LatLng GuruMandirChowrangi = new LatLng(24.88056461104264, 67.03888527745033);
    LatLng LasbelaChowk = new LatLng(24.88621452216924, 67.03398756376848);
    LatLng Gulbahar = new LatLng(24.89555162945301, 67.03074857284923);
    LatLng NazimabadChowrangi = new LatLng(24.916057424141684, 67.03119516064696);
    LatLng EidGahGround = new LatLng(24.916104251016645, 67.03117739101);
    LatLng NorthNazimabad = new LatLng(24.91862922671536, 67.03119348426762);
    LatLng Tower = new LatLng(24.924831982244186, 67.03172992606214);
    LatLng KDAChowrangi = new LatLng(24.95908595465811, 67.06188261505113);
    LatLng SharahEJehanghir = new LatLng(24.9468343611729, 67.05087482922796);
    LatLng SakhiHassanCircle = new LatLng(24.93702345311224, 67.04198598856598);
    LatLng NaganChowrangi = new LatLng(24.967231667306294, 67.06729923535781);
    LatLng UP = new LatLng(24.971394418742506, 67.06614052115606);
    LatLng PowerHouseRoundabout = new LatLng(24.98652697325205, 67.06558262163041);
    LatLng Chowrangi4K = new LatLng(25.00706381022713, 67.0645097379893);
    LatLng SurjaniTown = new LatLng(25.027713870444956, 67.06444536443709);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        drawerLayout = findViewById(R.id.drawer_layout);
        imageView = findViewById(R.id.scan);
        textView = findViewById(R.id.viewprofile);
        textAdd=findViewById(R.id.textAdd);
        textView1 = findViewById(R.id.viewprofile);
        Delteaccount = findViewById(R.id.delteaccount);
        ProgressBar = findViewById(R.id.progressBar);
        name = findViewById(R.id.pname);
        resultReceiver = new AddressResultReceiver(new Handler());

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        client = LocationServices.getFusedLocationProviderClient(map.this);

        Delteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(map.this);
                dialog.setTitle("Are you sure you want to delete your account?");
                dialog.setMessage("Deleting this account will completely delete this account and you will not" +
                        "not be able to access this account on this app.");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProgressBar.setVisibility(View.VISIBLE);
                      user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                              ProgressBar.setVisibility(View.GONE);
                         if (task.isSuccessful()){
                             Toast.makeText(map.this, "Account Deleted", Toast.LENGTH_LONG).show();
                             Intent intent = new Intent(map.this, Login.class);
                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                             startActivity(intent);
                         } else{
                             Toast.makeText(map.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                         }
                          }
                      });

                    }
                });
                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                     dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(map.this,UserProfile.class);
                startActivity(intent);
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
                    String NAME = userprofile.Fullname;

                    name.setText(NAME);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(map.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        mapFragment.getMapAsync(this);
        arrayList.add(NumiashChowrangi);
        arrayList.add(GuruMandirChowrangi);
        arrayList.add(LasbelaChowk);
        arrayList.add(Gulbahar);
        arrayList.add(NazimabadChowrangi);
        arrayList.add(EidGahGround);
        arrayList.add(NorthNazimabad);
        arrayList.add(Tower);
        arrayList.add(KDAChowrangi);
        arrayList.add(SharahEJehanghir);
        arrayList.add(SakhiHassanCircle);
        arrayList.add(NaganChowrangi);
        arrayList.add(UP);
        arrayList.add(PowerHouseRoundabout);
        arrayList.add(Chowrangi4K);
        arrayList.add(SurjaniTown);


        findViewById(R.id.buttonGetCurrentLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            map.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION
                    );

                } else {
                    getCurrentLocation();
                }
            }
        });

        if (ActivityCompat.checkSelfPermission(map.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
           getLocation();
        }
        else {
            ActivityCompat.requestPermissions(map.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
        }

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                IntentIntegrator intentIntegrator = new IntentIntegrator(
//                        map.this
//                );
//                intentIntegrator.setPrompt("For flash use volume up key");
//                intentIntegrator.setBeepEnabled(true);
//                intentIntegrator.setOrientationLocked(true);
//                intentIntegrator.setCaptureActivity(scanner.class);
//                intentIntegrator.initiateScan();
//            }
//        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(map.this,scanoptions.class);
                startActivity(intent);
            }
        });



    }

    private void getCurrentLocation() {
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(10000)
                .setFastestInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(1)
                .setMaxWaitTime(100);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(map.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(map.this)
                                .removeLocationUpdates(this);
                        if(locationRequest != null && locationResult.getLocations().size() > 0){
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();

                            Location location = new Location("providerNA");
                            location.setLatitude(latitude);
                            location.setLongitude(longitude);
                            fetchAddressFromLatLong(location);
                        }
                        else {
                            Toast.makeText(map.this, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, Looper.getMainLooper());
                }
                private  void  fetchAddressFromLatLong(Location location){
                Intent intent = new Intent(this,FetchAddressIntentServices.class);
                intent.putExtra(Constants.RECEIVER,resultReceiver);
                intent.putExtra(Constants.LOCATION_DATA_EXTRA,location);
                startService(intent);

                }
                private class AddressResultReceiver extends ResultReceiver{
                AddressResultReceiver(Handler handler) {
                        super(handler);
                    }

                    @Override
                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                        super.onReceiveResult(resultCode, resultData);
                        if (resultCode != Constants.SUCCESS_RESULT) {
                            Toast.makeText(map.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
                        }
                        textAdd.setText(resultData.getString(Constants.RESULT_DATA_KEY));
                    }
                }


    private void getLocation() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();


        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null){
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Current Location");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                            googleMap.addMarker(markerOptions);
                        }
                    });
                }
            }
        });
 }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }


    public void ClickMenu(View view) {

        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void Click(View view) {
        closeDrawer(drawerLayout);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHistory(View view) {
        redirectActivity(this,History.class);
    }

    public void ClickWallet(View view) {
        redirectActivity(this, Wallet.class);
    }

    public void ClickComplains(View view) {
        redirectActivity(this, Complains.class);
    }

    public void Clicksignout(View view) {
        signout(this);
    }

    public void ClickViewProfile(View view){redirectActivity(this,UserProfile.class);}

    private void signout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(map.this,loginaccount.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        closeDrawer(drawerLayout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );
        if (intentResult.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    map.this
            );
            builder.setTitle("Result");

            builder.setMessage(intentResult.getContents());

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.show();
        } else {
            Toast.makeText(getApplicationContext()
                    , "OOPS... You did not Scan anything", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_LONG);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap Map) {
        googleMap = Map;
        for (int i=0;i<arrayList.size();i++){
            googleMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("STOP").icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.stops)));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }
             double bottomBoundry =24.746 - 0.1f;
             double leftBoundry =66.878 - 0.1f;
             double topBoundry =25.092 + 0.1;
             double rightBoundry =67.405 + 0.1;

            LatLngBounds karachi = new LatLngBounds(
                    new LatLng(bottomBoundry,leftBoundry),
                    new LatLng(topBoundry,rightBoundry)
            );
            googleMap.setLatLngBoundsForCameraTarget(karachi);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(karachi.getCenter(),12));


        }
        private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId){
            Drawable vectorDrawable = ContextCompat.getDrawable(context,vectorResId);
            vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight());
            Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.draw(canvas);
            return BitmapDescriptorFactory.fromBitmap(bitmap);
        }

    private void showMarker(LatLng latLng){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        googleMap.addMarker(markerOptions);
   }
}