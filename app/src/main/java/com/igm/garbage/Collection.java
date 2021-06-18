package com.igm.garbage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.igm.garbage.models.Request;
import com.igm.garbage.models.Subscription;
import com.igm.garbage.utils.CheckNetworkGps;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Collection extends AppCompatActivity implements OnMapReadyCallback {

    CheckNetworkGps checkNetworkGps;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String UserId, name, phone;

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String TAG = "Collection";

    String subscription;
    Date currentTime;

    private boolean permissionGranted = false;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private EditText inputSearch;
    private Button btnMap;
    private LatLng obtainedLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        inputSearch = findViewById(R.id.inputSearch);
        btnMap = findViewById(R.id.btnMap);

        checkNetworkGps = new CheckNetworkGps();
        checkNetworkGps.checkNetworkGps(Collection.this);
        currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        Log.d(TAG, "onCreate: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
        UserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Intent intent = getIntent();
        subscription = intent.getStringExtra("Subscription");


        if (checkService()) {
            getLocationPermision();

            initialize();

        } else {
            ////Funga activity and display an error
        }

    }

    //send a request when a location is obtained
    private void sendRequest(LatLng location, String mSubscription){

        DocumentReference getUser = FirebaseFirestore.getInstance().collection("users")
                .document(UserId);
        getUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        name = document.getString("fullname");
                        phone = document.getString("phone_no");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        Subscription subscription = new Subscription(
                UserId,
                currentTime,
                currentTime,
                "0",
                location.toString(),
                mSubscription,
                "0",
                name,
                phone
        );

//        DocumentReference mDocumentReference = db.collection("requests");
        CollectionReference toSubscription = db.collection("subscription");

        toSubscription.add(subscription)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        addToRequests(documentReference.getId());
                        Log.d(TAG, "Check document Id: " + documentReference.getId());
                        Toast.makeText(Collection.this, "Your request already sent.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Collection.this, "Request failed", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }
                });


        //An alert for payments
        new AlertDialog.Builder(this)
                .setTitle("Payment")
                .setMessage("Complete payments through 55773 With the name Garbage Collection Company and wait for collection")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }


    //requests
    private void addToRequests(String transactionId){

        Request request = new Request(
                UserId,
                "0",
                transactionId
        );

        CollectionReference toRequests = db.collection("requests");
        toRequests.add(request)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "toRequest: Requests sents" );
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "toRequest: failed");
                    }
                });

    }

    private void initialize(){
        Log.d(TAG, "initialize: Initializing");

        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                    geoLocate();
                }

                return false;
            }
        });

        hideSoftKeyboard();

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                getDeviceLocation();

                sendRequest(obtainedLocation, subscription);
                Log.d(TAG, "onClick: " + obtainedLocation.toString());
//                Toast.makeText(Collection.this, "Upo hapa " + obtainedLocation.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void geoLocate(){
        String searchString = inputSearch.getText().toString();

        Geocoder geocoder = new Geocoder(Collection.this);
        List<Address> list = new ArrayList<>();

        try {
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.d(TAG, "geoLocate: " + e.getMessage());
        }

        if (list.size() > 0){
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: Found a location " + address.toString());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), 15f, address.getAddressLine(0));

        }
    }

    //check if a map can be shown in this device
    public boolean checkService() {

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Collection.this);

        if (available == ConnectionResult.SUCCESS) {
            //Service zipo poa keep on
            return true;

        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //Kuna error but ina solvika.. fix it

            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Collection.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else
            Toast.makeText(this, "Map cannot be shown with this device", Toast.LENGTH_LONG).show();

        return false;
    }

    private void getLocationPermision() {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

        if (ContextCompat.checkSelfPermission(Collection.this, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(Collection.this, COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                permissionGranted = true;
            }

        } else {
            ActivityCompat.requestPermissions(Collection.this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            permissionGranted = false;
                            return;
                        }
                    }
                    permissionGranted = true;
                    //Initialize the map
                    initializeMap();
                }
            }

        }
    }

    private void initializeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(Collection.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map ready function ipo hapa");

        mMap = googleMap;

        // Create a LatLngBounds that includes the city of Dar es Salaam
        LatLngBounds darEsSalaam = new LatLngBounds(
                new LatLng(-7.12000, 38.89400), // SW bounds
                new LatLng(-6.50200, 39.66100)  // NE bounds
        );

        // Constrain the camera target to the Adelaide bounds.
        mMap.setLatLngBoundsForCameraTarget(darEsSalaam);


        if (permissionGranted) {

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

            getDeviceLocation();
            Log.d(TAG, "onMapReady: permission granted level 1");

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            Log.d(TAG, "onMapReady: permission granted level 1 enabled location icon");
        }

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                checkNetworkGps.checkNetworkGps(Collection.this);

                return false;
            }
        });
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: imefika hapa");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (permissionGranted) {

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

                Task<Location> location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Location currentLocation =  (Location) task.getResult();
//                            Toast.makeText(Collection.this, "Location tumepata", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onComplete: location is found");
                            try {
                                hideSoftKeyboard();
                                //This brings an error when location is turned off.. weka try catch na uombe awashe location
                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 15f, "Current Location");

                            }catch (Exception e){
                                Log.d(TAG, "onComplete: " + e.getMessage());
                            }
                        }else {
                            //iyo cm tupa tu
                            Toast.makeText(Collection.this, "Amna kitu apa", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }


        }catch (SecurityException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        obtainedLocation = latLng;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("Current Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);

            mMap.addMarker(options);
        }

        hideSoftKeyboard();

    }

    @Override
    protected void onResume() {
        super.onResume();

        checkNetworkGps.checkNetworkGps(Collection.this);
        Log.d("MyMap", "onResume");
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        if (mMap == null) {

            Log.d("MyMap", "setUpMapIfNeeded");
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMapAsync(this);
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}