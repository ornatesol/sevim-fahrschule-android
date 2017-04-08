package com.fahrschule.sevim.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.fragments.InfoFragment;
import com.fahrschule.sevim.fragments.MessageDetailFragment;
import com.fahrschule.sevim.fragments.MessagesListFragment;
import com.fahrschule.sevim.fragments.OfficeLocationsFragment;
import com.fahrschule.sevim.fragments.OfficeTimingsFragment;
import com.fahrschule.sevim.fragments.SplashScreenFragment;
import com.fahrschule.sevim.fragments.TheoriezeitenFragment;
import com.fahrschule.sevim.models.MessageItem;
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
        if(!Utils.isOnline(this)) {
            Utils.showConnectionErrorDialog(this, getString(R.string.error_no_connection));
            selectDefaultNavDrawerItem();
        } else {
            Fragment fragment = MessagesListFragment.newInstance();
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.content, fragment)
                    .commit();
        }
    }

    private void showOfficeTimingContent() {
        Fragment fragment = OfficeTimingsFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    private void showLearningSiteContent() {
        if(!Utils.isOnline(this)) {
            Utils.showConnectionErrorDialog(this, getString(R.string.error_no_connection));
            selectDefaultNavDrawerItem();
        } else {
            showLernsiteDialog();
        }
    }

    private void showOfficeLocationsContent() {
        Fragment fragment = OfficeLocationsFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
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
                    showLearningSiteContent();
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
    public void onListFragmentInteraction(MessageItem item) {
        Fragment fragment = MessageDetailFragment.newInstance(item.getMessageDetail());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void showLernsiteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.lernseite_custom_dialog, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(customView);

        RadioGroup rg = (RadioGroup) customView.findViewById(R.id.radio);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.option_lernseite_website:
                        Utils.openUrlInBrowser(MainActivity.this,
                                getString(R.string.learning_site_url));
                        break;
                    case R.id.option_lernseite_app:
                        String packageName = getString(R.string.learning_app_google_play_package);
                        if(Utils.isApplicationInstalled(packageName, MainActivity.this)) {
                            Intent intent = getPackageManager()
                                    .getLaunchIntentForPackage(packageName);
                            startActivity(intent);
                        } else {
                            Utils.openUrlInBrowser(MainActivity.this,
                                    getString(R.string.learning_app_google_play_url));
                        }
                        break;
                }
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
