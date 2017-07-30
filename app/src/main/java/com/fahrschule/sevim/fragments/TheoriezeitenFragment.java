package com.fahrschule.sevim.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fahrschule.sevim.R;
import com.fahrschule.sevim.models.TheorieImageItemSource;
import com.fahrschule.sevim.utils.Utils;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;

public class TheoriezeitenFragment extends BaseFragment {

    enum SUPPORTED_LANGUAGES {
        DE, TR;

        static SUPPORTED_LANGUAGES from(String name) {
            for(SUPPORTED_LANGUAGES item: values()) {
                if (item.name().equalsIgnoreCase(name)) {
                    return item;
                }
            }
            return DE; //default as fallback plan
        }
    }

    enum SUPPORTED_LOCATIONS {
        WEDDING, REINICKENDORF;

        static SUPPORTED_LOCATIONS from(String name) {
            for(SUPPORTED_LOCATIONS item: values()) {
                if (item.name().equalsIgnoreCase(name)) {
                    return item;
                }
            }
            return WEDDING; //default as fallback plan
        }
    }

    public static TheoriezeitenFragment newInstance() {
        return new TheoriezeitenFragment();
    }

    @BindView(R.id.imageview_wedding)
    PhotoView weddingTheoryPlanImage;

    @BindView(R.id.imageview_reinickendorf)
    PhotoView reinickendorfTheoryPlanImage;

    private TheorieImageItemSource theorieImageItemSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_theoriezeiten, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        theorieImageItemSource = new TheorieImageItemSource();
        if(Utils.isOnline(getActivity())) {
            loadTheoryPlanImageFromNetwork();
        } else {
            Utils.showConnectionErrorDialog(getActivity(),
                    getString(R.string.error_no_connection_data_is_old));
            loadTheoryPlanImageFromCache();
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
            Picasso.with(getActivity()).cancelRequest(weddingTheoryPlanImage);
        }
    }

    private void loadTheoryPlanImageFromNetwork() {
        Picasso.with(getActivity())
                .load(theorieImageItemSource.getWeddingPlanImageUrl())
                .error(R.string.error_generic)
                .into(weddingTheoryPlanImage);

        Picasso.with(getActivity())
                .load(theorieImageItemSource.getReinickendorfPlanImageUrl())
                .error(R.string.error_generic)
                .into(reinickendorfTheoryPlanImage);
    }

    private void loadTheoryPlanImageFromCache() {
        Picasso.with(getActivity())
                .load(theorieImageItemSource.getWeddingPlanImageUrl())
                .noFade()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .error(R.string.error_generic)
                .into(weddingTheoryPlanImage);

        Picasso.with(getActivity())
                .load(theorieImageItemSource.getReinickendorfPlanImageUrl())
                .noFade()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .error(R.string.error_generic)
                .into(reinickendorfTheoryPlanImage);
    }

    @OnClick(R.id.button_german_wedding)
    protected void showGermanPlanForWedding() {
        launchTheoriPlan(SUPPORTED_LOCATIONS.WEDDING.name(), SUPPORTED_LANGUAGES.DE.name());
    }

    @OnClick(R.id.button_turkish_wedding)
    protected void showTurkishPlanForWedding() {
        launchTheoriPlan(SUPPORTED_LOCATIONS.WEDDING.name(), SUPPORTED_LANGUAGES.TR.name());
    }

    @OnClick(R.id.button_german_reinickendorf)
    protected void showGermanPlanForReinickendorf() {
        launchTheoriPlan(SUPPORTED_LOCATIONS.REINICKENDORF.name(), SUPPORTED_LANGUAGES.DE.name());
    }

    @OnClick(R.id.button_turkish_reinickendorf)
    protected void showTurkishPlanForReinickendorf() {
        launchTheoriPlan(SUPPORTED_LOCATIONS.REINICKENDORF.name(), SUPPORTED_LANGUAGES.TR.name());
    }

    private void launchTheoriPlan(String location, String language) {
        Fragment fragment = TheorieLocalizedGalleryFragment.newInstance(location, language);
        getFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }
}
