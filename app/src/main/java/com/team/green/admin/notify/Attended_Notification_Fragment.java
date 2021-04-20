package com.team.green.admin.notify;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.team.green.MyAdapter;
import com.team.green.R;
import com.team.green.admin.NotificationDetails;
import com.team.green.models.Request;
import com.team.green.models.Subscription;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class Attended_Notification_Fragment extends Fragment implements MyAdapter.OnNotificationListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
//    List<Request> list = new ArrayList<>();
    List<Subscription> list = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @NonNull Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attended__notification_, container, false);
        // Inflate the layout for this fragment

        recyclerView = view.findViewById(R.id.recycler_view_attended);

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

        // specify an adapter
        mAdapter = new MyAdapter(list, this);
        recyclerView.setAdapter(mAdapter);


        FirebaseFirestore.getInstance()
                .collection("subscription")
                .get()
                .addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(  QuerySnapshot queryDocumentSnapshots) {

                        for (DocumentSnapshot document :queryDocumentSnapshots.getDocuments()) {

                            Log.d("Requests", "onSuccess: " + document.getData());

                            Subscription subscription = new Subscription(
                                    document.getString("userId"),
                                    document.getDate("startDate"),
                                    document.getDate("endDate"),
                                    document.getString("disabled"),
                                    document.getString("location"),
                                    document.getString("subscription"),
                                    document.getString("status"),
                                    document.getString("name"),
                                    document.getString("phone")
                            );

                            Log.d("TAG", "onSuccess: " + subscription.getUserId());

                            if (subscription.getStatus().equals("1"))
                                list.add(subscription);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });


        return view;
    }

    @Override
    public void onNotificationClick(int position) {

        list.get(position);
        Intent intent = new Intent(getActivity(), NotificationDetails.class);
        Log.d(TAG, "onNotificationClick: " + position);
        intent.putExtra("notify", (Parcelable) list.get(position));
//        intent.putExtra("notify", list.get(position));
        startActivity(intent);
    }
}