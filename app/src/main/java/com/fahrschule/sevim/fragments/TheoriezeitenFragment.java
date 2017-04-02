package com.fahrschule.sevim.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import butterknife.BindView;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.utils.Utils;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class TheoriezeitenFragment extends BaseFragment {

    @BindView(R.id.main_plan)
    ImageView mainTheoriePlan;

    public static TheoriezeitenFragment newInstance() {
        return new TheoriezeitenFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_theoriezeiten, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(Utils.isOnline(getActivity())) {
            loadMainPlanImageFromNetwork();
        } else {
            Utils.showConnectionErrorDialog(getActivity(),
                    getString(R.string.error_no_connection_data_is_old));
            loadMainPlanImageFromCache();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isDetached()) {
            // Always cancel the request here, this is safe to call even if the image has been loaded.
            // This ensures that the anonymous callback we have does not prevent the activity from
            // being garbage collected. It also prevents our callback from getting invoked even after the
            // activity has finished.
            Picasso.with(getActivity()).cancelRequest(mainTheoriePlan);
        }
    }

    private void loadMainPlanImageFromNetwork() {
        Picasso.with(getActivity())
                .load(getString(R.string.theorie_main_plan_url))
                .error(R.string.error_generic)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(mainTheoriePlan);
    }

    private void loadMainPlanImageFromCache() {
        Picasso.with(getActivity())
                .load(getString(R.string.theorie_main_plan_url))
                .error(R.string.error_generic)
                .into(mainTheoriePlan);
    }
}
