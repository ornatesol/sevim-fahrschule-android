package com.fahrschule.sevim.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.models.TheoriePhotoContent;
import com.fahrschule.sevim.models.TheoriePhotoContent.TheoriePhotoItem;
import com.fahrschule.sevim.models.TheorieImageItemSource;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TheorieLocalizedGalleryFragment extends BaseFragment {

    private static final String ARG_LANGUAGE_PLAN = "language_plan";
    private static final int columnCount = 2;
    private String languagePlan = TheoriezeitenFragment.SUPPORTED_LANGUAGES.DE.name();
    private OnListFragmentInteractionListener listener;

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
            recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            Observable.just(populateModel(languagePlan))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<TheoriePhotoContent>() {
                        @Override public void call(TheoriePhotoContent dummyContent) {
                            recyclerView.setAdapter(
                                    new TheoriePhotoItemRecyclerViewAdapter(dummyContent.getItems(),
                                            listener, languagePlan));
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
        final TheoriePhotoContent dummyContent = new TheoriePhotoContent();
        TheoriezeitenFragment.SUPPORTED_LANGUAGES supportedLanguage =
                TheoriezeitenFragment.SUPPORTED_LANGUAGES.from(language);

        switch (supportedLanguage) {
            case DE:
                for (int i = 0; i < TheorieImageItemSource.getGermanPlanList().size(); i++) {
                    dummyContent.createAddItem(TheorieImageItemSource.getGermanPlanList().get(i));
                }
                break;

            case EN:
                for (int i = 0; i < TheorieImageItemSource.getEnglishPlanList().size(); i++) {
                    dummyContent.createAddItem(TheorieImageItemSource.getEnglishPlanList().get(i));
                }
                break;

            case TR:
                for (int i = 0; i < TheorieImageItemSource.getTurkishPlanList().size(); i++) {
                    dummyContent.createAddItem(TheorieImageItemSource.getTurkishPlanList().get(i));
                }
                break;

            default:
                break;
        }

        return dummyContent;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            listener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(TheoriePhotoItem item);
    }
}
