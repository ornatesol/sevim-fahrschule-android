package com.fahrschule.sevim.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.fragments.MessagesListFragment.OnListFragmentInteractionListener;

import com.fahrschule.sevim.models.MessageItem;
import java.util.List;

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter
        .ViewHolder> {

    private final List<MessageItem> values;
    private final OnListFragmentInteractionListener listener;

    MessageRecyclerViewAdapter(List<MessageItem> items, OnListFragmentInteractionListener listener) {
        values = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final MessageItem messageItem = values.get(position);
        holder.title.setText(messageItem.getMessageTitle());
        holder.date.setText(
                String.valueOf(messageItem.getUpdatedAtDate()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onListFragmentInteraction(messageItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void swapItems(List<MessageItem> newItems) {
        values.clear();
        values.addAll(newItems);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;

        @BindView(R.id.message_title)
        TextView title;

        @BindView(R.id.message_date)
        TextView date;

        public MessageItem item;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }

        @Override public String toString() {
            return super.toString() + " '" + date.getText() + "'";
        }
    }
}
