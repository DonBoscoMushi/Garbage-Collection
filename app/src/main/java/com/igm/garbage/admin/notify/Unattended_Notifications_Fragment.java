 package com.igm.garbage.admin.notify;

import android.content.Intent;
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

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.igm.garbage.MyAdapter;
import com.igm.garbage.R;
import com.igm.garbage.admin.NotificationDetails;
import com.igm.garbage.models.Subscription;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class  Unattended_Notifications_Fragment extends Fragment implements MyAdapter.OnNotificationListener  {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
//    List<Request> list = new ArrayList<>();

    List<Subscription> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unatended__notifications_, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_unattended);

        recyclerView.setHasFixedSize(true);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(list, this);
        recyclerView.setAdapter(mAdapter);

        //fetch details from subscription
        FirebaseFirestore.getInstance()
                .collection("subscription")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (DocumentSnapshot document :queryDocumentSnapshots.getDocuments()) {

                        Log.d("Requests", "onSuccess: " + document.getData() + "  " + document.getId());

                        Subscription subscription = new Subscription(
                                document.getId(),
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

                        if (subscription.getStatus().equals("0"))
                            list.add(subscription);
                    }
                    mAdapter.notifyDataSetChanged();
                });

        return view;
    }

    @Override
    public void onNotificationClick(int position) {

        list.get(position);
        Intent intent = new Intent(getActivity(), NotificationDetails.class);
        Log.d(TAG, "onNotificationClick: " + position);
        intent.putExtra("notify", list.get(position));
        startActivity(intent);
    }
}