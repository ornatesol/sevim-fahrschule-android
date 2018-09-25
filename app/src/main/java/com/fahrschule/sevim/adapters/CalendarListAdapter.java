package com.fahrschule.sevim.adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fahrschule.sevim.R;
import com.fahrschule.sevim.models.MessageItem;
import com.fahrschule.sevim.utils.Utils;

import java.text.DateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarListAdapter extends RecyclerView.Adapter<CalendarListAdapter.ViewHolder> {

    private Context context;

    private final List<MessageItem> values;

    private OnCalendarItemClicked onCalendarItemClicked;

    public CalendarListAdapter(Context context, OnCalendarItemClicked calendarItemClicked
            , List<MessageItem> values) {
        this.context = context;
        this.onCalendarItemClicked = calendarItemClicked;
        this.values = values;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.calendar_item, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.day.setText(values.get(position).getDay());
        holder.date.setText(Utils.convertShortDateToString(values.get(position).getStartDate()));
        holder.details.setText(values.get(position).getDetail());

        String timeStart = DateUtils.formatDateTime(context,
                values.get(position).getStartDate().getTime(), DateUtils.FORMAT_SHOW_TIME);
        String timeEnd = DateUtils.formatDateTime(context,
                values.get(position).getEndDate().getTime(), DateUtils.FORMAT_SHOW_TIME);

        holder.duration.setText(timeStart + " - " + timeEnd);


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCalendarItemClicked.selectedItem(values.get(position));
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


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_day)
        TextView day;
        @BindView(R.id.tv_date)
        TextView date;
        @BindView(R.id.tv_details)
        TextView details;
        @BindView(R.id.tv_duration)
        TextView duration;
        @BindView(R.id.item)
        LinearLayout item;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface OnCalendarItemClicked {
        void selectedItem(MessageItem messageItem);
    }
}
