package com.fahrschule.sevim.fragments;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.fahrschule.sevim.R;

//TODO make location text clickable to open Google Maps. Add bubble icon
//TODO make phone and email text label clickable to perform the right action
public class OfficeLocationsFragment extends BaseFragment {

    public static OfficeLocationsFragment newInstance() {
        return new OfficeLocationsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_office_locations, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.location_wedding_address)
    protected void onWeddingAddressClick() {
        String gpsCoordinates[] = getString(R.string.location_wedding_gps).split(",");
        launchGoogleMapsWithMarker(gpsCoordinates[0], gpsCoordinates[1],
                getString(R.string.wedding_title));
    }

    private void launchGoogleMapsWithMarker(String lat, String lon, String label) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + lat
                            + "," + lon
                            + "?q=" + lat
                            + "," + lon
                            + "(" + label + ")"));
            intent.setComponent(new ComponentName(
                    "com.google.android.apps.maps",
                    "com.google.android.maps.MapsActivity"));
            getActivity().startActivity(intent);
        } catch (ActivityNotFoundException e) {

            try {
                getActivity().startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.google.android.apps.maps")));
            } catch (ActivityNotFoundException anfe) {
                getActivity().startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.apps.maps")));
            }
            e.printStackTrace();
        }
    }
}
