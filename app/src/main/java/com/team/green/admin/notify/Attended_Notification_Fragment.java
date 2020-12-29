package com.team.green.admin.notify;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.green.MyAdapter;
import com.team.green.R;
import com.team.green.models.Request;

import java.util.ArrayList;
import java.util.List;


public class Attended_Notification_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Request> list = new ArrayList<>();

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

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(list);
        recyclerView.setAdapter(mAdapter);



        return view;
    }
}