package com.fahrschule.sevim.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.fragments.MainFragment;

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

    private void showOfficeTimingContent() {
        String timingText = getString(R.string.office_timing_value);
        Fragment fragment = MainFragment.newInstance(timingText);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    private void decideDestination(Intent intent) {
        if(intent.hasExtra(EXTRA_DESTINATION_TYPE)) {
            if(intent.getStringExtra(EXTRA_DESTINATION_TYPE).equals(
                    getString(R.string.office_timing))) {
                showOfficeTimingContent();
            }
        } else {
            //default case
            Fragment fragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
        }
    }
}
