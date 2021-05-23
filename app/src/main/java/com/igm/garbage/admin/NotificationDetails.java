  package com.igm.garbage.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.igm.garbage.R;
import com.igm.garbage.admin.notify.Notification;
import com.igm.garbage.models.Subscription;

import java.util.Date;

 public class NotificationDetails extends AppCompatActivity {

     TextView name_txt, phone_txt, start_date, subscription_txt;
     Button goTo, attended;
     private String TAG = "Notification";
     private LottieAnimationView animationView;
     private LinearLayout greyedLinearLayout;

     //Firestore
     FirebaseFirestore db = FirebaseFirestore.getInstance();
     FirebaseAuth mAuth = FirebaseAuth.getInstance();

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);

        name_txt = findViewById(R.id.name_txt);
        phone_txt = findViewById(R.id.phone_txt);
        start_date = findViewById(R.id.start_date);
        subscription_txt = findViewById(R.id.subscription_type_txt);
        goTo = findViewById(R.id.to_location);
        attended = findViewById(R.id.attended);

        greyedLinearLayout = findViewById(R.id.greyedBg);
        animationView = findViewById(R.id.animatedDialog);
        animationView.setVisibility(View.GONE);

        Intent intent = getIntent();

        Subscription subDetails = intent.getParcelableExtra("notify");
        Log.d("TAG", "onCreate: " + subDetails);

        String subscriptionId = subDetails.getSubscriptionId();
        Date startDate = subDetails.getStartDate();
        Date endDate = subDetails.getEndDate();
        String disabled = subDetails.getDisabled();
        String location = subDetails.getLocation();
        String subscription = subDetails.getSubscription();
        String status = subDetails.getStatus();
        String name = subDetails.getName();
        String phone = subDetails.getPhone();
        String uId = subDetails.getUserId();

        Log.d("TAG", "onCreate: " + startDate + name + phone + subscription);

        name_txt.setText(name);
        phone_txt.setText(phone);
        start_date.setText(startDate.toString());
        subscription_txt.setText(subscription);


        attended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                greyedLinearLayout.setVisibility(View.VISIBLE);
                animationView.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                Log.d(TAG, "onClick Attended: " + uId);
                DocumentReference updateStatus = db.collection("subscription").document(subscriptionId);

                updateStatus
                        .update("status", "1")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully updated!");

                                Toast.makeText(NotificationDetails.this, "Attended", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(NotificationDetails.this, Notification.class));
                                finish();
                                animationView.setVisibility(View.GONE);
                                greyedLinearLayout.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });

            }
        });



        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse("google.streetview:cbll=" + location);

                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    // Attempt to start an activity that can handle the Intent
                    startActivity(mapIntent);
                }


            }
        });

    }
}