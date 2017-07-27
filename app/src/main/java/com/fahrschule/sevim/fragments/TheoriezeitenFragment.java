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
        DE, TR; //TODO make it dynamic based on urls in text file

        static SUPPORTED_LANGUAGES from(String name) {
            for(SUPPORTED_LANGUAGES item: values()) {
                if (item.name().equalsIgnoreCase(name)) {
                    return item;
                }
            }
            return DE; //default as fallback plan
        }
    }

    @BindView(R.id.imageview_wedding)
    PhotoView weddingTheoryPlanImage;

    @BindView(R.id.imageview_reinickendorf)
    PhotoView reinickendorfTheoryPlanImage;

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
                .load(TheorieImageItemSource.getPlanForWedding())
                .error(R.string.error_generic)
                .noFade()
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(weddingTheoryPlanImage);

        Picasso.with(getActivity())
                .load(TheorieImageItemSource.getPlanForReinickendorf())
                .error(R.string.error_generic)
                .noFade()
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(reinickendorfTheoryPlanImage);
    }

    private void loadTheoryPlanImageFromCache() {
        Picasso.with(getActivity())
                .load(TheorieImageItemSource.getPlanForWedding())
                .noFade()
                .error(R.string.error_generic)
                .into(weddingTheoryPlanImage);

        Picasso.with(getActivity())
                .load(TheorieImageItemSource.getPlanForReinickendorf())
                .noFade()
                .error(R.string.error_generic)
                .into(reinickendorfTheoryPlanImage);
    }

    @OnClick(R.id.show_german_plan)
    protected void showGermanPlan() {
        launchTheoriPlanPerLanguage(SUPPORTED_LANGUAGES.DE.name());
    }

    @OnClick(R.id.show_turkish_plan)
    protected void showTurkishPlan() {
        launchTheoriPlanPerLanguage(SUPPORTED_LANGUAGES.TR.name());
    }

    private void launchTheoriPlanPerLanguage(String language) {
        Fragment fragment = TheorieLocalizedGalleryFragment.newInstance(language);
        getFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }
}
