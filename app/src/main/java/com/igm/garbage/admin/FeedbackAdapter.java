package com.igm.garbage.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.igm.garbage.R;
import com.igm.garbage.models.Feedback;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {
    //  List<Request> list;
    List<Feedback> list;

    private FeedbackAdapter.OnNotificationListener mOnNotificationListener;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        FeedbackAdapter.OnNotificationListener onNotificationListener;

        public TextView mFeedback;

        public MyViewHolder(View v, FeedbackAdapter.OnNotificationListener onNotificationListener) {
            super(v);
            mFeedback = (TextView) v.findViewById(R.id.message);

            this.onNotificationListener = onNotificationListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNotificationListener.onNotificationClick(getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FeedbackAdapter(List<Feedback> list, FeedbackAdapter.OnNotificationListener onNotificationListener){
        this.list = list;
        this.mOnNotificationListener = onNotificationListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FeedbackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                     int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedback_view, parent, false);
        FeedbackAdapter.MyViewHolder vh = new FeedbackAdapter.MyViewHolder(v, mOnNotificationListener);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.MyViewHolder holder, int position) {
        Feedback fb = list.get(position);

        holder.mFeedback.setText(fb.getMessage());

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
