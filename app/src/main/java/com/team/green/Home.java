package com.team.green;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.team.green.admin.notify.Notification;
import com.team.green.utils.BottomNavigation;
import com.team.green.utils.InternetCheck;

public class Home extends AppCompatActivity {

    RelativeLayout collectionBox, collectionR;
    InternetCheck internetCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupBottomNav();

//        cb = findViewById(R.id.cleaningbox);
        collectionBox = findViewById(R.id.collectionbox);
        collectionR = findViewById(R.id.collectionRoutineBox);

        //check internet
        internetCheck = new InternetCheck();

        if(!internetCheck.isConnected(this)){
            Toast.makeText(this, "Connect na internet we mbwa", Toast.LENGTH_SHORT).show();
        }

        collectionBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Subscription.class));
            }
        });

        collectionR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Notification.class));
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

}