package com.fahrschule.sevim.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.fragments.InfoFragment;
import com.fahrschule.sevim.fragments.MainFragment;
import com.fahrschule.sevim.fragments.OfficeLocationsFragment;
import com.fahrschule.sevim.utils.Utils;

public class MainActivity extends BaseActivity {

    private static final String EXTRA_DESTINATION_TYPE = "args_destination_type";

    public static Intent newIntent(final Context context) {
        return new Intent(context, MainActivity.class);
    }

    public static Intent newIntent(final Context context, String destinationType) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_DESTINATION_TYPE, destinationType);
        return intent;
    }

    @Override
    protected void showDefaultFragment() {
        decideDestination(getIntent());
    }

    private void decideDestination(Intent intent) {
        if (intent.hasExtra(EXTRA_DESTINATION_TYPE)) {
            //TODO convert it into Enums
            if (intent.getStringExtra(EXTRA_DESTINATION_TYPE).equals(
                    getString(R.string.office_timing))) {
                showOfficeTimingContent();
            } else if (intent.getStringExtra(EXTRA_DESTINATION_TYPE).equals(
                    getString(R.string.infos))) {
                showInfosContent();
            } else if (intent.getStringExtra(EXTRA_DESTINATION_TYPE).equals(
                    getString(R.string.locations))) {
                showOfficeLocationsContent();
            } else if (intent.getStringExtra(EXTRA_DESTINATION_TYPE).equals(
                    getString(R.string.messages))) {
                showMessagesContent();
            } else if (intent.getStringExtra(EXTRA_DESTINATION_TYPE).equals(
                    getString(R.string.theory_calendar))) {
                showTheoryCalendarContent();
            } else if (intent.getStringExtra(EXTRA_DESTINATION_TYPE).equals(
                    getString(R.string.learning_site))) {
                loadLearningSiteContent();
            }
        } else {
            //default case
            String sampleText = getString(R.string.generic_welcome_message,
                    "Aktuell");
            commitToMainFragment(sampleText);
        }
    }

    private void showTheoryCalendarContent() {
        String sampleText = getString(R.string.generic_welcome_message,
                getString(R.string.theory_calendar));
        commitToMainFragment(sampleText);
    }

    private void showMessagesContent() {
        String sampleText = getString(R.string.generic_welcome_message,
                getString(R.string.messages));
        commitToMainFragment(sampleText);
    }

    private void commitToMainFragment(String sampleText) {
        Fragment fragment = MainFragment.newInstance(sampleText);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    private void loadLearningSiteContent() {
        Utils.openUrlInBrowser(this, getString(R.string.learning_site_url));
    }

    private void showOfficeLocationsContent() {
        Fragment fragment = OfficeLocationsFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    private void showOfficeTimingContent() {
        String timingText = getString(R.string.office_timing_value);
        commitToMainFragment(timingText);
    }

    private void showInfosContent() {
        Fragment fragment = InfoFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
