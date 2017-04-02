package com.fahrschule.sevim.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.fragments.InfoFragment;
import com.fahrschule.sevim.fragments.MainFragment;
import com.fahrschule.sevim.fragments.MessageDetailFragment;
import com.fahrschule.sevim.fragments.MessagesListFragment;
import com.fahrschule.sevim.fragments.OfficeLocationsFragment;
import com.fahrschule.sevim.fragments.SplashScreenFragment;
import com.fahrschule.sevim.fragments.TheoriezeitenFragment;
import com.fahrschule.sevim.models.MessageContent;
import com.fahrschule.sevim.models.NavigationMenuItem;
import com.fahrschule.sevim.utils.Utils;

public class MainActivity extends BaseActivity implements BaseActivity.NavItemActionTargetListener,
        MessagesListFragment.OnListFragmentInteractionListener {

    public static Intent newIntent(final Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void showDefaultFragment() {
        showSplashScreenContent();
    }

    private void showSplashScreenContent() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Fragment fragment = SplashScreenFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    private void showDefaultContent() {
        showInfosContent();
    }

    private void showTheoryCalendarContent() {
        Fragment fragment = TheoriezeitenFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    private void showMessagesContent() {
        Fragment fragment = MessagesListFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    private void commitToMainFragment(String sampleText) {
        Fragment fragment = MainFragment.newInstance(sampleText);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    private void loadLearningSiteContent() {
        if(!Utils.isOnline(this)) {
            showDefaultContent();
            Utils.showConnectionErrorDialog(this, getString(R.string.error_no_connection));
        } else {
            Utils.openUrlInBrowser(this, getString(R.string.learning_site_url));
        }
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

    @Override
    public void handleSelection(String selectedMenu) {

        NavigationMenuItem menuItem = NavigationMenuItem.getNavMenuItem(this, selectedMenu);
        if (menuItem != null) {
            switch (menuItem) {
                case LEARNINGSITE:
                    loadLearningSiteContent();
                    toolbar.setTitle(R.string.learning_site);
                    break;
                case MESSAGES:
                    showMessagesContent();
                    toolbar.setTitle(R.string.messages);
                    break;
                case INFOS:
                    showInfosContent();
                    toolbar.setTitle(R.string.infos);
                    break;
                case THEORYCALENDAR:
                    showTheoryCalendarContent();
                    toolbar.setTitle(R.string.theory_calendar);
                    break;
                case LOCATIONS:
                    showOfficeLocationsContent();
                    toolbar.setTitle(R.string.locations);
                    break;
                case OFFICETIMING:
                    showOfficeTimingContent();
                    toolbar.setTitle(R.string.office_timing);
                    break;
                default:
                    showDefaultContent();
                    break;
            }
        }
    }

    @Override
    public void onListFragmentInteraction(MessageContent.MessageItem item) {
        Fragment fragment = MessageDetailFragment.newInstance(item.details);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }
}
