package com.fahrschule.sevim.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.models.TheoriePhotoContent.TheoriePhotoItem;
import com.fahrschule.sevim.utils.Utils;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.util.List;

public class TheoriePhotoItemRecyclerViewAdapter extends
        RecyclerView.Adapter<TheoriePhotoItemRecyclerViewAdapter.ViewHolder> {

    private final List<TheoriePhotoItem> values;

    public TheoriePhotoItemRecyclerViewAdapter(List<TheoriePhotoItem> items) {
        values = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_theoriephotoitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = values.get(position);
        if(Utils.isOnline(holder.imageView.getContext())) {
            loadLanguagePlanImageFromNetwork(holder.imageView, holder.item.imageUrl);
        } else {
            loadLanguagePlanImageFromCache(holder.imageView, holder.item.imageUrl);
        }
        holder.imageView.setImageDrawable(holder.imageView.getDrawable());
    }

    private void loadLanguagePlanImageFromNetwork(ImageView imageHolder, String url) {
        Picasso.with(imageHolder.getContext())
                .load(url)
                .error(R.string.error_generic)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .noFade()
                .into(imageHolder);
    }

    private void loadLanguagePlanImageFromCache(ImageView imageHolder, String url) {
        Picasso.with(imageHolder.getContext())
                .load(url)
                .error(R.string.error_generic)
                .noFade()
                .into(imageHolder);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.theori_gallery_photo)
        ImageView imageView;

        public TheoriePhotoItem item;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
