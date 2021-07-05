package com.igm.garbage.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.igm.garbage.MyAdapter;
import com.igm.garbage.R;
import com.igm.garbage.models.Feedback;
import com.igm.garbage.models.Subscription;

import java.util.ArrayList;
import java.util.List;

public class FeedBackView extends AppCompatActivity implements FeedbackAdapter.OnNotificationListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    //    List<Request> list = new ArrayList<>();
    List<Feedback> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_view);

        recyclerView = findViewById(R.id.feedback_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new FeedbackAdapter(list, this);
        recyclerView.setAdapter(mAdapter);

        FirebaseFirestore.getInstance()
                .collection("feedback")
                .get()
                .addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(  QuerySnapshot queryDocumentSnapshots) {

                        for (DocumentSnapshot document :queryDocumentSnapshots.getDocuments()) {

                            Log.d("Requests", "onSuccess: " + document.getData());

                            Feedback fd = new Feedback(
                                    document.getString("message"));
                            list.add(fd);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });


    }

    @Override
    public void onNotificationClick(int position) {

    }
}