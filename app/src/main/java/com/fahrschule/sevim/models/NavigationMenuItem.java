package com.fahrschule.sevim.models;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import com.fahrschule.sevim.R;

public enum NavigationMenuItem {

    LEARNINGSITE(R.string.learning_site),
    MESSAGES(R.string.messages),
    INFOS(R.string.infos),
    THEORYCALENDAR(R.string.theory_calendar),
    LOCATIONS(R.string.locations),
    OFFICETIMING(R.string.office_timing);

    @StringRes
    public final int title;

    NavigationMenuItem(int itemTitle) {
        this.title = itemTitle;
    }

    @Nullable
    public static NavigationMenuItem getNavMenuItem(Context context, String itemName) {
        if(context != null) {
            for (NavigationMenuItem item : values()) {
                if (context.getString(item.title).equals(itemName)) {
                    return item;
                }
            }
        }
        return null;
    }
}
