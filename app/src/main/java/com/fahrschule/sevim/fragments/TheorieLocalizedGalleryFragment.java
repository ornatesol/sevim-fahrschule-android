package com.fahrschule.sevim.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
    private String languagePlan = TheoriezeitenFragment.SUPPORTED_LANGUAGES.DE.name();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TheorieLocalizedGalleryFragment() {
    }

    public static TheorieLocalizedGalleryFragment newInstance(String language) {
        TheorieLocalizedGalleryFragment fragment = new TheorieLocalizedGalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LANGUAGE_PLAN, language);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            languagePlan = getArguments().getString(ARG_LANGUAGE_PLAN);
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
            Observable.just(populateModel(languagePlan))
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

    private TheoriePhotoContent populateModel(@NonNull String language) {
        final TheoriePhotoContent photoContent = new TheoriePhotoContent();
        TheoriezeitenFragment.SUPPORTED_LANGUAGES supportedLanguage =
                TheoriezeitenFragment.SUPPORTED_LANGUAGES.from(language);

        switch (supportedLanguage) {
            case DE:
                for (int i = 0; i < TheorieImageItemSource.getGermanPlanList().size(); i++) {
                    photoContent.createAddItem(TheorieImageItemSource.getGermanPlanList().get(i));
                }
                break;

            case TR:
                for (int i = 0; i < TheorieImageItemSource.getTurkishPlanList().size(); i++) {
                    photoContent.createAddItem(TheorieImageItemSource.getTurkishPlanList().get(i));
                }
                break;

            default:
                break;
        }

        return photoContent;
    }

}
