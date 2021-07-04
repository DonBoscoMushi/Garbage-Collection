package com.igm.garbage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.igm.garbage.utils.BottomNavigation;

public class Subscription extends AppCompatActivity {

    LinearLayout cleaningServiceBox, mSubscription, wSubscription, pSubscription;

    final static String mSub = "Monthly Subscription";
    final static String wSub = "Weekly Subscription";
    final static String payPerService = "Pay Per Service";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);


        mSubscription = findViewById(R.id.monthlySubscription);
        wSubscription = findViewById(R.id.weeklySubscription);
        pSubscription = findViewById(R.id.payPerServiceSubscription);


        mSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Collection.class);
                intent.putExtra("Subscription", mSub);
                startActivity(intent);
//                startActivity(new Intent(getApplicationContext(), Collection.class));

            }
        });

        wSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Collection.class);
                intent.putExtra("Subscription", wSub);
                startActivity(intent);
//                startActivity(new Intent(getApplicationContext(), Collection.class));
            }
        });

        pSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Subscription.this, Collection.class);
                intent.putExtra("Subscription", payPerService);
                startActivity(intent);
//                startActivity(new Intent(getApplicationContext(), Collection.class));
            }
        });

    }

}