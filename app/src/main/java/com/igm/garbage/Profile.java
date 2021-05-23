package com.igm.garbage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        setupBottomNav();
    }

//    public void setupBottomNav(){
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
//        BottomNavigation.enableNavigation(Profile.this, bottomNavigationView);
//
//        Menu menu = bottomNavigationView.getMenu();
//        MenuItem menuItem = menu.getItem(1);
//        menuItem.setChecked(true);
//    }
}