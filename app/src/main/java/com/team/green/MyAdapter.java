package com.team.green;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.team.green.models.Request;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Request> list;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
//        TextView  = (TextView) holder.textView.getViewById(R.id.location);
        public TextView nameTextView;
        public TextView locationTextView;
        public TextView phoneTextView;


        public MyViewHolder(View v) {
            super(v);
            nameTextView = (TextView) v.findViewById(R.id.name);
            locationTextView = (TextView) v.findViewById(R.id.location);
            phoneTextView = (TextView) v.findViewById(R.id.phone);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Request> list){
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                     int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.requestlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Request request = list.get(position);

//        holder.phoneTextView.setText(request.getSubscription());-git

        holder.phoneTextView.setText(request.getSubscription());
        holder.locationTextView.setText(request.getLocation());
        holder.nameTextView.setText(request.getTime().toString());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}