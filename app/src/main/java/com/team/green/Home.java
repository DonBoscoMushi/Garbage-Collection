package com.team.green;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.team.green.utils.BottomNavigation;

public class Home extends AppCompatActivity {//implements  NavigationView.OnNavigationItemSelectedListener{

    //Firebase instances
    private FirebaseAuth mAuth;

    RelativeLayout collectionBox, cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupBottomNav();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        cb = findViewById(R.id.cleaningbox);
        collectionBox = findViewById(R.id.collectionbox);

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Cleaning.class);
                startActivity(intent);
            }
        });

        collectionBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Subscription.class));
            }
        });

    }

    public void setupBottomNav(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        BottomNavigation.enableNavigation(Home.this, bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    //Send the user to login Screen when not signed in
    private void updateUI(FirebaseUser currentUser) {
        if(currentUser == null){
            startActivity(new Intent(this, Login.class));
            finish();
        }
    }


//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        drawerLayout.closeDrawer(GravityCompat.START);
//
//        switch(item.getItemId()){
//
//            case R.id.home:
//                fragmentManager = getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container_fragment, new Main());
//                fragmentTransaction.commit();
//                break;
//
//            case R.id.profile:
//                fragmentManager = getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container_fragment, new Profile());
//                fragmentTransaction.commit();
//                break;
//
//            case R.id.settings:
//
//                fragmentManager = getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container_fragment, new Settings());
//                fragmentTransaction.commit();
//                break;
//
//            case R.id.about:
//                fragmentManager = getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container_fragment, new About());
//                fragmentTransaction.commit();
//                break;
//        }
//
//        return false;
//    }
}