package com.igm.garbage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.igm.garbage.utils.BottomNavigation;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setupBottomNav();
    }

    public void setupBottomNav(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        BottomNavigation.enableNavigation(About.this, bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
    }
}