package com.team.green.admin.notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.team.green.MyAdapter;
import com.team.green.R;
import com.team.green.models.Request;
import com.team.green.models.Subscription;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Notification extends AppCompatActivity {

    private static final String CHANNEL_ID = "Green";
    private static final String CHANNEL_NAME = "Green";
    private static final String  CHANNEL_DESCRIPTION = "Clean City";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    TextView textView;
    List<Subscription> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setTopTabs();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        //specify an adapter
        mAdapter = new MyAdapter(list);
        recyclerView.setAdapter(mAdapter);

//        final DocumentReference docRef = FirebaseFirestore.getInstance()
//                .collection("subscription").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        FirebaseFirestore.getInstance()
                .collection("requests")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (DocumentSnapshot document :queryDocumentSnapshots.getDocuments()) {
//
                            Log.d("Requests", "onSuccess: " + document.getData());


                            Subscription subscription = new Subscription(
                                    document.getString("userID"),
                                    document.getDate("startDate"),
                                    document.getDate("endDate"),
                                    document.getString("disabled"),
                                    document.getString("location"),
                                    document.getString("subscription")
                            );

                            Log.d("TAG", "onSuccess: " + subscription.getLocation());

                            list.add(subscription);
                        }
                        mAdapter.notifyDataSetChanged();
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