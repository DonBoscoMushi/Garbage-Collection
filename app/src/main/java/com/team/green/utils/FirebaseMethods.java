package com.team.green.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.green.Home;
import com.team.green.admin.Admin;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethod";
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference zeroDatabaseRef;

    private String userID;

    public void checkRole(final Context context, String uid){

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        //root access
        mDatabaseReference = mFirebaseDatabase.getReference();

        //zero access
        zeroDatabaseRef = mDatabaseReference.child("Users").child(uid);

        user = mAuth.getCurrentUser();



        zeroDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String role = dataSnapshot.child("role").getValue(String.class);

                Log.d(TAG, "onDataChange: " + role);
                if (role.equals("customer")){
                    context.startActivity(new Intent(context, Home.class));
                }else
                    context.startActivity(new Intent(context, Admin.class));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

}
