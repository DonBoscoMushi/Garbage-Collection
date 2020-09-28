package com.team.green;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.zip.Inflater;

public class Main extends Fragment {

    RelativeLayout collectionBox, cb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        cb = view.findViewById(R.id.cleaningbox);
        collectionBox = view.findViewById(R.id.collectionbox);

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Cleaning.class);
                startActivity(intent);
            }
        });

        collectionBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Subscription.class));
            }
        });

        return view;


    }

}

