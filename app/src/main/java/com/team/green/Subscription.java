package com.team.green;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Subscription extends AppCompatActivity {

    LinearLayout cleaningServiceBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        cleaningServiceBox = findViewById(R.id.payPerServiceBox);

        cleaningServiceBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Subscription.this, SignUp.class);
                startActivity(intent);

            }
        });

    }
}