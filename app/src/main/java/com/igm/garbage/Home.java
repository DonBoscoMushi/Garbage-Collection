package com.igm.garbage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.igm.garbage.utils.DatabaseHelper;
import com.igm.garbage.utils.InternetCheck;

public class Home extends AppCompatActivity {

    private static final String TAG = "Home";
    RelativeLayout collectionBox, collectionR, cleaningServiceBox;
    InternetCheck internetCheck;
    ImageView profileImage;
    DatabaseHelper db;

    ProfileDialogFragment profileDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Network();

//        setupBottomNav();

//        cb = findViewById(R.id.cleaningbox);
        collectionBox = findViewById(R.id.collectionbox);
        collectionR = findViewById(R.id.collectionRoutineBox);
        profileImage = findViewById(R.id.profile_icon);
        cleaningServiceBox = findViewById(R.id.cleaningServiceBox);

        db = new DatabaseHelper(Home.this);
        db.checkRole();

        //check internet
        internetCheck = new InternetCheck();

        if(!internetCheck.isConnected(this)){
            Toast.makeText(this, "Connect with internet", Toast.LENGTH_SHORT).show();
        }

        collectionBox.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Subscription.class)));



        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProfileDialogFragment profileDialogFragment = ProfileDialogFragment.getInstance();
                profileDialogFragment.show(getSupportFragmentManager(), "Green");
            }
        });

        collectionR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sorry! We have no any route for now.", Snackbar.LENGTH_LONG).show();
            }
        });
    }


}