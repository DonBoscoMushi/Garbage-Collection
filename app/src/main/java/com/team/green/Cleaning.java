package com.team.green;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.team.green.utils.BottomNavigation;

public class Cleaning extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning);

//        setupBottomNav();
    }

//    public void setupBottomNav(){
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
//        BottomNavigation.enableNavigation(Cleaning.this, bottomNavigationView);
//
//        Menu menu = bottomNavigationView.getMenu();
//        MenuItem menuItem = menu.getItem(0);
//        menuItem.setChecked(true);
//    }
}