package com.example.doug.disastermapalert;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static android.app.PendingIntent.getActivity;

class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionGranted) {
            getDeviceLocation();


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
        }

    }


    private static final String TAG = "MapActivity";

    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;


    // variables
    private Boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };


        getLocationPermission();
            Button yesButton = (Button) findViewById(R.id.yesButton);
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage(R.string.OkSafe).setPositiveButton("Thanks", dialogClickListener)
                            .setNegativeButton("Cancel", dialogClickListener).show()
                    ;
                }
            });



            Button noButton = (Button) findViewById(R.id.noButton);
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage(R.string.SignalSent).setPositiveButton("OK", dialogClickListener)
                            .setNegativeButton("Cancel", dialogClickListener).show()
                    ;
                }
            });


    }

    public void openDialogYes() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_demo);
        dialog.setTitle(R.string.dialog_title_yes);
        dialog.show();

//        Button noButton = (Button) findViewById(R.id.dialog_cancel);
////        noButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                openDialogNo();
////            }
////        });
//        Button yesButton = (Button) findViewById(R.id.dialog_ok);
//        noButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openDialogYes();
//            }
//        });


    }

    public void openDialogNo() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_demo);
        dialog.setTitle(R.string.dialog_title_no);
        dialog.show();
//        Button noButton = (Button) findViewById(R.id.dialog_cancel);
//        noButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openDialogNo();
//            }
//        });
//        Button yesButton = (Button) findViewById(R.id.dialog_ok);
//        noButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openDialogYes();
//            }
//        });
    }




    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting device's current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {

            if (mLocationPermissionGranted) {


                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful() && task.getResult() != null) {
                            Log.d(TAG, "onComplete: Location found!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);

                        } else {
                            Log.d(TAG, "onComplete: Current location is null");
                            Toast.makeText(MapActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());

        }

    }

    private void moveCamera(LatLng latlng, float zoom) {
        Log.d(TAG, "moveCamera: moving the camera to\nLat: " + latlng.latitude + ", Lng: " + latlng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));

    }


    private void initMap() {
        Log.d(TAG, "initMap: initializing map");


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions. ");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called");
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: Permission failed.");
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    Log.d(TAG, "onRequestPermissionsResult: Permission granted.");
                    // initialize our map
                    initMap();
                }
            }
        }

    }



}
