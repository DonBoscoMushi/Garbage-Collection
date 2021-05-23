package com.igm.garbage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.igm.garbage.utils.NetworkConnection;

public class About extends AppCompatActivity {

    LinearLayout connected, disconnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        connected = findViewById(R.id.connected);
        disconnected = findViewById(R.id.disconnected);

    }

    //This is no longer needed
//    public void setupBottomNav(){
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
//        BottomNavigation.enableNavigation(About.this, bottomNavigationView);
//
//        Menu menu = bottomNavigationView.getMenu();
//        MenuItem menuItem = menu.getItem(3);
//        menuItem.setChecked(true);
//
//    }

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