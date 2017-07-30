package com.fahrschule.sevim.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fahrschule.sevim.R;
import com.fahrschule.sevim.models.TheorieImageItemSource;
import com.fahrschule.sevim.models.TheoriePhotoContent;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TheorieLocalizedGalleryFragment extends BaseFragment {

    private static final String ARG_LANGUAGE_PLAN = "language_plan";

    private static final String ARG_LOCATION_PLAN = "location_plan";

    private String languagePlan;

    private String locationPlan;

    private TheorieImageItemSource theorieImageItemSource;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TheorieLocalizedGalleryFragment() {
    }

    public static TheorieLocalizedGalleryFragment newInstance(String location, String language) {
        TheorieLocalizedGalleryFragment fragment = new TheorieLocalizedGalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LANGUAGE_PLAN, language);
        args.putString(ARG_LOCATION_PLAN, location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            languagePlan = getArguments().getString(ARG_LANGUAGE_PLAN);
            locationPlan = getArguments().getString(ARG_LOCATION_PLAN);

            theorieImageItemSource = new TheorieImageItemSource(languagePlan);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theory_photoitem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            Observable.just(populateModel())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<TheoriePhotoContent>() {
                        @Override public void call(TheoriePhotoContent photoContent) {
                            recyclerView.setAdapter(
                                    new TheoriePhotoItemRecyclerViewAdapter(
                                            photoContent.getItems()));
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    });
        }
        return view;
    }

    private TheoriePhotoContent populateModel() {
        final TheoriePhotoContent photoContent = new TheoriePhotoContent();

        TheoriezeitenFragment.SUPPORTED_LANGUAGES supportedLanguage =
                TheoriezeitenFragment.SUPPORTED_LANGUAGES.from(languagePlan);
        TheoriezeitenFragment.SUPPORTED_LOCATIONS supportedLocations =
                TheoriezeitenFragment.SUPPORTED_LOCATIONS.from(locationPlan);

        if(supportedLocations.equals(TheoriezeitenFragment.SUPPORTED_LOCATIONS.REINICKENDORF)) {
            if(supportedLanguage.equals(TheoriezeitenFragment.SUPPORTED_LANGUAGES.DE)) {
                for (int i = 0;
                     i < theorieImageItemSource.getDeReinickendorfGalleryPagesList().size(); i++) {
                    photoContent.createAddItem(
                            theorieImageItemSource.getDeReinickendorfGalleryPagesList().get(i));
                }
            } else if(supportedLanguage.equals(TheoriezeitenFragment.SUPPORTED_LANGUAGES.TR)) {
                for (int i = 0;
                     i < theorieImageItemSource.getTrReinickendorfGalleryPagesList().size(); i++) {
                    photoContent.createAddItem(
                            theorieImageItemSource.getTrReinickendorfGalleryPagesList().get(i));
                }
            }

        } else if(supportedLocations.equals(TheoriezeitenFragment.SUPPORTED_LOCATIONS.WEDDING)) {
            if(supportedLanguage.equals(TheoriezeitenFragment.SUPPORTED_LANGUAGES.DE)) {
                for (int i = 0;
                     i < theorieImageItemSource.getDeWeddingGalleryPagesList().size(); i++) {
                    photoContent.createAddItem(
                            theorieImageItemSource.getDeWeddingGalleryPagesList().get(i));
                }
            } else if(supportedLanguage.equals(TheoriezeitenFragment.SUPPORTED_LANGUAGES.TR)) {
                for (int i = 0;
                     i < theorieImageItemSource.getTrWeddingGalleryPagesList().size(); i++) {
                    photoContent.createAddItem(
                            theorieImageItemSource.getTrWeddingGalleryPagesList().get(i));
                }
            }
        }

        return photoContent;
    }

}
