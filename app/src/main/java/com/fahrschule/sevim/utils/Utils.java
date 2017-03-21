package com.fahrschule.sevim.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import com.fahrschule.sevim.R;
import java.util.List;

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

public static void openUrlInBrowser(Activity activity, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (Utils.isIntentSafe(activity, intent)) {
            activity.startActivity(intent);
        } else {
            Toast.makeText(activity, R.string.browser_app_not_found, Toast.LENGTH_LONG).show();
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
}
