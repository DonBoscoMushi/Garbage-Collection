package com.team.green.admin.notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.team.green.R;

public class Notification extends AppCompatActivity {

    private static final String CHANNEL_ID = "Green";
    private static final String CHANNEL_NAME = "Green";
    private static final String  CHANNEL_DESCRIPTION = "Clean City";

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        setTopTabs();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

//        textView = findViewById(R.id.textView);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {

                        if (task.isSuccessful()){
                            String token = task.getResult().getToken();
//                            textView.setText(token);
                        }else {
//                            textView.setText(task.getException().getMessage());
                        }
                    }
                });

    }

    //Display new notifications
    private void displayNotification(){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("Its working ...")
                        .setContentText("1st Notification")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, mBuilder.build());


    }

    private void setTopTabs(){
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager(), 0);
        sectionPagerAdapter.addFragment(new Unattended_Notifications_Fragment());
        sectionPagerAdapter.addFragment(new Attended_Notification_Fragment());

        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(sectionPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.topTabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("New");
        tabLayout.getTabAt(1).setText("Attended");
    }

}