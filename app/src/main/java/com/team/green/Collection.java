package com.team.green;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.team.green.utils.BottomNavigation;
import com.team.green.utils.CheckNetworkGps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Collection extends AppCompatActivity implements OnMapReadyCallback {

    CheckNetworkGps checkNetworkGps;

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String TAG = "Collection";

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

        setupBottomNav();

        if (checkService()) {
            getLocationPermision();

            initialize();

        } else {
            ////Funga activity and desplay an errror
        }

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
                Toast.makeText(Collection.this, "Upo hapa " + obtainedLocation.toString(), Toast.LENGTH_LONG).show();
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

    public void setupBottomNav() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        BottomNavigation.enableNavigation(Collection.this, bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }

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
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(Collection.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map ready function ipo hapa");

        mMap = googleMap;

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
                            Toast.makeText(Collection.this, "Location tumepata", Toast.LENGTH_SHORT).show();

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

    //Auto complete ya location

//    private AdapterView.OnItemClickListener mAutoCompleteListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            hideSoftKeyboard();
//
//            Place place;
//
//            final AutocompletePrediction item = .getItem(i);
//        }
//    };
}