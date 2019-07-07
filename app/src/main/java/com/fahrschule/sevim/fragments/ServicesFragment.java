package com.fahrschule.sevim.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.BindView;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.adapters.ServicesListAdapter;
import com.fahrschule.sevim.models.ServicesItemsSource;
import java.util.List;

public class ServicesFragment extends BaseFragment {

    @BindView(R.id.info_sources_list_view)
    ListView servicesSourcesListView;

    public static ServicesFragment newInstance() {
        return new ServicesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_services, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupItemSources();
    }

    private void setupItemSources() {
        List<ServicesItemsSource> contactSources = ServicesItemsSource.getInfoItems();
        ServicesListAdapter adapter = new ServicesListAdapter(contactSources, getActivity());
        servicesSourcesListView.setAdapter(adapter);
    }
}
