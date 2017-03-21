package com.fahrschule.sevim.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import butterknife.BindView;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.adapters.InfoListAdapter;
import com.fahrschule.sevim.models.InfoItemsSource;
import java.util.List;

public class InfoFragment extends BaseFragment {

    @BindView(R.id.info_sources_list_view)
    ListView infoSourcesListView;

    private InfoListAdapter adapter;

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupItemSources();
    }

    private void setupItemSources() {
        List<InfoItemsSource> contactSources = InfoItemsSource.getInfoItems();
        adapter = new InfoListAdapter(contactSources, getActivity());
        infoSourcesListView.setAdapter(adapter);
    }
}
