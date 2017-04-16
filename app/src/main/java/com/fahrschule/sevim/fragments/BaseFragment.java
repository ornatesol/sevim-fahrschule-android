package com.fahrschule.sevim.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.fahrschule.sevim.R;

public abstract class BaseFragment extends Fragment {

    private Unbinder unBinder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unBinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        unBinder.unbind();
        super.onDestroyView();
    }

    public void showDefaultContent() {
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
           actionBar.setTitle(R.string.services);
        }

        Fragment fragment = ServicesFragment.newInstance();
        getFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
