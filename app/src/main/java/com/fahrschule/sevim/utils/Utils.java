package com.fahrschule.sevim.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import com.fahrschule.sevim.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void verifyArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Opens the url passed as parameter in a browser
     * @param context
     * @param url
     */
    public static void openUrlInBrowser(@NonNull final Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (Utils.isIntentSafe(context, intent)) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, R.string.browser_app_not_found, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * If there is an activity available that can respond to the intent
     * http://developer.android.com/training/basics/intents/sending.html#StartActivity
     */
    private static boolean isIntentSafe(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
    }

    /**
     *
     * @return true if the internet connection currently in use is functional,
     * otherwise false
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    /**
     * Displaying Connection Error Dialog
     * @param context
     * @param cause
     * @return
     */
    public static Dialog showConnectionErrorDialog(Context context,
            String cause) {
        return new AlertDialog.Builder(context)
                .setTitle(R.string.connection_error_dialog_title)
                .setMessage(cause)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do nothing
                    }
                })
                .show();
    }

    /**
     * Displaying Progress Dialog
     * @param context
     * @param message
     * @return
     */
    public static Dialog showProgressUpdateDialog(Context context, String message) {
        return ProgressDialog.show(context, "", message, true, false);
    }

    /**
     * Opens a particular coordinate on Google Maps app or in Browser and
     * displays marker on that spot along with label
     * @param context Context
     * @param lat Latitude
     * @param lon Longitude
     * @param label Name of the location displayed on footer
     */
    public static void launchGoogleMapsWithMarker(Context context, @NonNull final String lat,
            @NonNull final String lon, @NonNull final String label) {
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
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {

            try {
                context.startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.google.android.apps.maps")));
            } catch (ActivityNotFoundException anfe) {
                context.startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.apps.maps")));
            }
            e.printStackTrace();
        }
    }

    /**
     * Checks if app is already installed or not
     * @param packageName
     * @param context
     * @return
     */
    public static boolean isApplicationInstalled(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * Convert Date to String and a particular format
     * @param date
     * @return
     */
    public static String convertShortDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return sdf.format(date); // Get Date String according to date format
    }
}
