package com.fahrschule.sevim.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fahrschule.sevim.R;
import com.fahrschule.sevim.activities.CalendarActivity;
import com.fahrschule.sevim.models.TheorieImageItemSource;
import com.fahrschule.sevim.utils.Utils;

import butterknife.OnClick;

public class TheoriezeitenFragment extends BaseFragment {

    enum SUPPORTED_LANGUAGES {
        DE, TR;

        static SUPPORTED_LANGUAGES from(String name) {
            for (SUPPORTED_LANGUAGES item : values()) {
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
            for (SUPPORTED_LOCATIONS item : values()) {
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_theoriezeiten, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @OnClick({R.id.ll_1, R.id.ll_2, R.id.ll_3, R.id.ll_4, R.id.ll_5, R.id.ll_6, R.id.ll_7,
            R.id.ll_8, R.id.ll_9, R.id.ll_10, R.id.ll_11, R.id.ll_12, R.id.ll_13, R.id.ll_14})
    public void onCalenderItemWeddingClicked(View view) {
        if (view.getTag().toString().contains(getString(R.string.english))) return;
        lanuchCalenderActivity(view.getTag().toString(), true);
    }

    @OnClick({R.id.ll_15, R.id.ll_16, R.id.ll_17, R.id.ll_18, R.id.ll_19, R.id.ll_20, R.id.ll_21,
            R.id.ll_22, R.id.ll_23, R.id.ll_24})
    public void onCalenderItemReinClicked(View view) {
        lanuchCalenderActivity(view.getTag().toString(), false);
    }

    private void lanuchCalenderActivity(String type, boolean isWedding) {
        Intent intent = new Intent(getActivity(), CalendarActivity.class);
        intent.putExtra(CalendarActivity.CALENDAR_TYPE, type);
        intent.putExtra(CalendarActivity.IS_WEDDING, isWedding);
        startActivity(intent);
    }
}
