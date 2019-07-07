package com.fahrschule.sevim.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.OnClick;
import com.fahrschule.sevim.R;

import static com.fahrschule.sevim.utils.Utils.launchGoogleMapsWithMarker;

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

    //Wedding Section Interactions

    @OnClick(R.id.location_wedding_address)
    protected void onWeddingAddressClick() {
        String gpsCoordinates[] = getString(R.string.wedding_gps).split(",");
        launchGoogleMapsWithMarker(getActivity(), gpsCoordinates[0], gpsCoordinates[1],
                getString(R.string.wedding_title));
    }

    @OnClick(R.id.location_wedding_phone_1)
    protected void onWeddingPhone1Click() {
        dialContactPhone(getString(R.string.wedding_phone_1_number));
    }

    @OnClick(R.id.location_wedding_phone_2)
    protected void onWeddingPhone2Click() {
        dialContactPhone(getString(R.string.wedding_phone_2_number));
    }

    @OnClick(R.id.location_wedding_mobile)
    protected void onWeddingMobileClick() {
        dialContactPhone(getString(R.string.wedding_mobile_number));
    }

    @OnClick(R.id.location_wedding_email)
    protected void onWeddingEmailClick() {
        sendEmail(getString(R.string.wedding_email_id));
    }

    //Reinickendorf Section Interactions

    @OnClick(R.id.location_reinickendorf_address)
    protected void onReinickendorfAddressClick() {
        String gpsCoordinates[] = getString(R.string.reinickendorf_gps).split(",");
        launchGoogleMapsWithMarker(getActivity(), gpsCoordinates[0], gpsCoordinates[1],
                getString(R.string.reinickendorf_title));
    }

    @OnClick(R.id.location_reinickendorf_phone_1)
    protected void onReinickendorfPhone1Click() {
        dialContactPhone(getString(R.string.reinickendorf_phone_1_number));
    }

    @OnClick(R.id.location_reinickendorf_mobile)
    protected void onReinickendorfobileClick() {
        dialContactPhone(getString(R.string.reinickendorf_mobile_number));
    }

    @OnClick(R.id.location_reinickendorf_email)
    protected void onReinickendorfEmailClick() {
        sendEmail(getString(R.string.reinickendorf_email_id));
    }

    private void dialContactPhone(@NonNull final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    private void sendEmail(@NonNull final String receiverEmail) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", receiverEmail, null));
        startActivity(Intent.createChooser(emailIntent, getString(R.string.sending_email_message)));
    }
}
