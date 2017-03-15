package com.fahrschule.sevim.activities;

import android.support.v4.app.Fragment;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.fragments.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void showDefaultFragment() {
        Fragment fragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
