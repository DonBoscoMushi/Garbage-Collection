package com.team.green.utils;

import android.app.Activity;
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
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.team.green.Home;
import com.team.green.admin.Admin;
import com.team.green.models.User;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethod";
//    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference zeroDatabaseRef;
    private FirebaseFirestore db;

    public void checkRole(final Context context, String uid){

        /** We changed to firestore **/
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        //root access
//        mDatabaseReference = mFirebaseDatabase.getReference();
//
//        //zero access
//        zeroDatabaseRef = mDatabaseReference.child("Users").child(uid);
//
//        zeroDatabaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String role = dataSnapshot.child("role").getValue(String.class);
//
//                Log.d(TAG, "onDataChange: " + role);
//                assert role != null;
//                if (role.equals("customer")){
//                    context.startActivity(new Intent(context, Home.class));
//                    ((Activity)context).finish();
//                }else {
//                    context.startActivity(new Intent(context, Admin.class));
//                    ((Activity)context).finish();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });

        db  = FirebaseFirestore.getInstance();

        final DocumentReference docRef = db.collection("users").document(uid);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {

                    User user = snapshot.toObject(User.class);
                    if(user.getRole().equals("customer")){
                        context.startActivity(new Intent(context, Home.class));
                        ((Activity)context).finish();
                    }else {
                        context.startActivity(new Intent(context, Admin.class));
                        ((Activity)context).finish();
                    }

                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

}
