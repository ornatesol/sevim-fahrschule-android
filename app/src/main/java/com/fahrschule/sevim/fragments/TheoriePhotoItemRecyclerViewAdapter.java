package com.fahrschule.sevim.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.fragments.TheorieLocalizedGalleryFragment.OnListFragmentInteractionListener;
import com.fahrschule.sevim.models.TheoriePhotoContent.TheoriePhotoItem;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.util.List;

public class TheoriePhotoItemRecyclerViewAdapter extends
        RecyclerView.Adapter<TheoriePhotoItemRecyclerViewAdapter.ViewHolder> {

    private final List<TheoriePhotoItem> values;
    private final OnListFragmentInteractionListener listener;
    private final String planType;

    public TheoriePhotoItemRecyclerViewAdapter(List<TheoriePhotoItem> items,
            OnListFragmentInteractionListener listener, String planType) {
        values = items;
        this.listener = listener;
        this.planType = planType;
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
        loadLanguagePlanImageFromNetwork(holder.imageView, holder.item.imageUrl);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onListFragmentInteraction(holder.item);
                }
            }
        });
    }

    private void loadLanguagePlanImageFromNetwork(ImageView imageHolder, String url) {
        Picasso.with(imageHolder.getContext())
                .load(url)
                .error(R.string.error_generic)
                .networkPolicy(NetworkPolicy.NO_CACHE)
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
