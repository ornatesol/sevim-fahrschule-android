package com.fahrschule.sevim.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fahrschule.sevim.R;

public class OfficeTimingsFragment extends BaseFragment {

    public static OfficeTimingsFragment newInstance() {
        return new OfficeTimingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_office_timings, container, false);
    }
}
