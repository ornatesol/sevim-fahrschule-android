package com.fahrschule.sevim.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.models.ServicesItemsSource;
import java.util.List;

public class ServicesListAdapter extends BaseAdapter {

    private final List<ServicesItemsSource> items;

    private Context context;

    private final LayoutInflater inflater;

    public ServicesListAdapter(List<ServicesItemsSource> items, Context context) {
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override public int getCount() {
        return items.size();
    }

    @Override public Object getItem(int position) {
        return items.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_services_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ServicesItemsSource servicesItem = items.get(position);
        holder.icon.setBackground(ContextCompat.getDrawable(context, servicesItem.drawableResId));
        holder.title.setText(context.getString(servicesItem.textResId));

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.list_item_services_icon)
        ImageView icon;

        @BindView(R.id.list_item_services_text)
        TextView title;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
