package com.team.green.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.team.green.Home;
import com.team.green.MyAdapter;
import com.team.green.ProfileDialogFragment;
import com.team.green.R;
import com.team.green.admin.notify.Notification;
import com.team.green.models.Request;
import com.team.green.models.User;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Request> list = new ArrayList<>();

    private static final String CHANNEL_ID = "Green";
    private static final String CHANNEL_NAME = "Green";
    private static final String  CHANNEL_DESCRIPTION = "Clean City";

    ImageView profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
//        recyclerView = findViewById(R.id.recycler_view);
        profileImage = findViewById(R.id.profile_icon);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        //Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();



        final DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection("requests").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {

                    Request request = snapshot.toObject(Request.class);
                    list.add(request);
                    mAdapter.notifyDataSetChanged();

                } else {
                    Log.d("TAG", "Current data: null");
                }
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProfileDialogFragment profileDialogFragment = new ProfileDialogFragment();
                profileDialogFragment.show(getSupportFragmentManager(), "Green");
            }
        });

        findViewById(R.id.regAdmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterAdmin.class));
            }
        });

        findViewById(R.id.notify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Notification.class));
            }
        });

    }

    //Display new notifications
    private void displayNotification( ){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("Its working ...")
                        .setContentText("1st Notification")
                        .setWhen(System.currentTimeMillis())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, mBuilder.build());


    }
}