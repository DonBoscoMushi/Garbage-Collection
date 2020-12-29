package com.team.green.admin.notify;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.team.green.MyAdapter;
import com.team.green.R;
import com.team.green.models.Request;

import java.util.ArrayList;
import java.util.List;

public class Unattended_Notifications_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Request> list = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unatended__notifications_, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_unattended);

//        list.addAll(Arrays.asList("elephant","hyena","chicken",
//                "girrafe","penguin","blackbird","crown","dove","lion",
//                "quora","bird","snake","milk","hawky","turkey"));
        recyclerView.setHasFixedSize(true);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

//        findViewById(R.id.filldata).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), com.team.green.Request.class));
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

        //fetch details from requesrts
        FirebaseFirestore.getInstance()
                .collection("requests")
                .get()
                .addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
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

        //fetch name from user
//        FirebaseFirestore.getInstance()

        return view;
    }
}