package com.igm.garbage.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.igm.garbage.MyAdapter;
import com.igm.garbage.R;
import com.igm.garbage.admin.notify.Notification;
import com.igm.garbage.models.Request;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Request> list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        recyclerView = findViewById(R.id.recycler_view);

        //Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

//        list.addAll(Arrays.asList("elephant","hyena","chicken",
//                "girrafe","penguin","blackbird","crown","dove","lion",
//                "quora","bird","snake","milk","hawky","turkey"));
        recyclerView.setHasFixedSize(true);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        findViewById(R.id.filldata).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), com.igm.garbage.Request.class));
//            }
//        });

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(list);
        recyclerView.setAdapter(mAdapter);

//        mFirebaseFirestore  = FirebaseFirestore.getInstance();

//        final DocumentReference docRef = FirebaseFirestore.getInstance()
//                .collection("requests").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot snapshot,
//                                @Nullable FirebaseFirestoreException e) {
//                if (e != null) {
//                    Log.w("TAG", "Listen failed.", e);
//                    return;
//                }
//
//                if (snapshot != null && snapshot.exists()) {
//
//                    Request request = snapshot.toObject(Request.class);
//                    list.add(request);
//                    mAdapter.notifyDataSetChanged();
//
//                } else {
//                    Log.d("TAG", "Current data: null");
//                }
//            }
//        });
//        final DocumentReference docRef = FirebaseFirestore.getInstance()
//                .collection("requests").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

//        DocumentReference docRef = db.collection("requests").document(mAuth.getCurrentUser().getUid());
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Request request = documentSnapshot.toObject(Request.class);
//                Log.d("TAG", "onSuccess: " + request);
//                list.add(request);
//                mAdapter.notifyDataSetChanged();
//            }
//        });

        /**
        FirebaseFirestore.getInstance()
                .collection("requests")
                .get()
                .addOnSuccessListener(this, new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(  QuerySnapshot queryDocumentSnapshots) {

                        for (DocumentSnapshot document :queryDocumentSnapshots.getDocuments()) {

                            Log.d("Requests", "onSuccess: " + document.getData());

                            Request request = new Request(document.getString("subscription"), document.getString("location"),
                                    document.getDate("time"), document.getString("userId"));

                            Log.d("TAG", "onSuccess: " + request.getLocation());

                            list.add(request);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });

        **/

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
}