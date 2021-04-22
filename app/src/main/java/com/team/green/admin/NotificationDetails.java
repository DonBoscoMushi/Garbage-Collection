  package com.team.green.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.team.green.R;
import com.team.green.admin.notify.Notification;
import com.team.green.models.Subscription;

import java.util.Date;

 public class NotificationDetails extends AppCompatActivity {

     TextView name_txt, phone_txt, start_date, subscription_txt;
     Button goTo, attended;
     private String TAG = "Notification";

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