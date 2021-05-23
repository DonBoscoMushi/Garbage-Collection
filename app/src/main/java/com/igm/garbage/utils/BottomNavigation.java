package com.igm.garbage.utils;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.igm.garbage.About;
import com.igm.garbage.Home;
import com.igm.garbage.Profile;
import com.igm.garbage.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.igm.garbage.Settings;

public class BottomNavigation {

    public static void enableNavigation(final Context context, BottomNavigationView bottomNavigationView){

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        Intent intent1 = new Intent(context, Home.class);
                        context.startActivity(intent1);
                        break;

                    case R.id.profile:
                        Intent intent2 = new Intent(context, Profile.class);
                        context.startActivity(intent2);
                        break;
//
                    case R.id.about:
                        Intent intent3 = new Intent(context, About.class);
                        context.startActivity(intent3);
                        break;
//
                    case R.id.settings:
                        Intent intent4 = new Intent(context, Settings.class);
                        context.startActivity(intent4);
                        break;
                }

                return false;
            }
        });
    }
}

