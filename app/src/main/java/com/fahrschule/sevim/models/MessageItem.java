package com.fahrschule.sevim.models;

import com.fahrschule.sevim.utils.Utils;
import com.google.gson.annotations.SerializedName;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MessageItem {

    public MessageItem(int id, String title, String detail, long created_at, long updated_at) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("detail")
    private String detail;

    @SerializedName("created_at")
    private long created_at;

    @SerializedName("updated_at")
    private long updated_at;

    public int getId() {
        return id;
    }

    public String getMessageTitle() {
        return title;
    }

    public String getMessageDetail() {
        return detail;
    }

    public Date getUpdatedDate() {
        final Calendar createdAtDate = Calendar.getInstance(Locale.getDefault());
        createdAtDate.setTimeInMillis(updated_at * 1000L);
        return createdAtDate.getTime();
    }

    public String getUpdatedDateToDisplay() {
        return Utils.convertShortDateToString(getUpdatedDate());
    }
}
