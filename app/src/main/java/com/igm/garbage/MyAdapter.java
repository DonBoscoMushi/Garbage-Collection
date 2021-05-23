package com.igm.garbage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.igm.garbage.models.Subscription;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
//  List<Request> list;
    List<Subscription> list;

    private OnNotificationListener mOnNotificationListener;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnNotificationListener onNotificationListener;


        // each data item is just a string in this case
//        TextView  = (TextView) holder.
//        textView.getViewById(R.id.location);
        public TextView nameTextView;
        public TextView locationTextView;
        public TextView phoneTextView;

//        public TextView nameTextView;
//        public TextView phoneTextView;
//        public TextView subscription;

        public MyViewHolder(View v, OnNotificationListener onNotificationListener) {
            super(v);
            nameTextView = (TextView) v.findViewById(R.id.name);
            locationTextView = (TextView) v.findViewById(R.id.location);
            phoneTextView = (TextView) v.findViewById(R.id.phone);
            this.onNotificationListener = onNotificationListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNotificationListener.onNotificationClick(getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Subscription> list, OnNotificationListener onNotificationListener){
        this.list = list;
        this.mOnNotificationListener = onNotificationListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                     int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.requestlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v, mOnNotificationListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your data set at this position
        // - replace the contents of the view with that element
//        Request request = list.get(position);
        Subscription subscription = list.get(position);

//        holder.phoneTextView.setText(request.getSubscription());-git

        holder.phoneTextView.setText(subscription.getPhone());
        holder.locationTextView.setText(subscription.getStartDate().toString());
        holder.nameTextView.setText(subscription.getName());

    }


    //interface for notification list
    public interface OnNotificationListener {
        void onNotificationClick(int position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}