package com.team.green;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.team.green.utils.BottomNavigation;
import com.team.green.utils.NetworkConnection;

public class About extends AppCompatActivity {

    LinearLayout connected, disconnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        connected = findViewById(R.id.connected);
        disconnected = findViewById(R.id.disconnected);

        Network();
        setupBottomNav();
    }

    public void setupBottomNav(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        BottomNavigation.enableNavigation(About.this, bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

    }

    //check network
    public void Network(){
        NetworkConnection networkConnection = new NetworkConnection(this);
        networkConnection.observe(this, isConnected -> {
            if (isConnected) {
                disconnected.setVisibility(View.GONE);
                connected.setVisibility(View.VISIBLE);

            } else {
                connected.setVisibility(View.GONE);
                disconnected.setVisibility(View.VISIBLE);
            }
        });
    }
}