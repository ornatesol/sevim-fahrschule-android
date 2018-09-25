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

    public MessageItem(int id, String title, String detail, long created_at, long updated_at,
                       String day, Integer location, Integer language,
                       Integer startDateTime, Integer endDateTime) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.day = day;
        this.location = location;
        this.language = language;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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

    @SerializedName("day")
    private String day;

    @SerializedName("location")
    private Integer location;

    @SerializedName("language")
    private Integer language;

    @SerializedName("start_date_time")
    private Integer startDateTime;

    @SerializedName("end_date_time")
    private Integer endDateTime;


    public int getId() {
        return id;
    }

    public String getMessageTitle() {
        return title;
    }

    public String getMessageDetail() {
        return detail;
    }


    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public long getCreated_at() {
        return created_at;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public String getDay() {
        return day;
    }

    public Integer getLocation() {
        return location;
    }

    public Integer getLanguage() {
        return language;
    }

    public Integer getStartDateTime() {
        return startDateTime;
    }

    public Integer getEndDateTime() {
        return endDateTime;
    }

    public Date getUpdatedDate() {
        final Calendar createdAtDate = Calendar.getInstance(Locale.getDefault());
        createdAtDate.setTimeInMillis(updated_at * 1000L);
        return createdAtDate.getTime();
    }

    public Date getStartDate(){
        final Calendar createdAtDate = Calendar.getInstance(Locale.getDefault());
        createdAtDate.setTimeInMillis(startDateTime * 1000L);
        return createdAtDate.getTime();
    }

    public Date getEndDate(){
        final Calendar createdAtDate = Calendar.getInstance(Locale.getDefault());
        createdAtDate.setTimeInMillis(endDateTime * 1000L);
        return createdAtDate.getTime();
    }


    public String getUpdatedDateToDisplay() {
        return Utils.convertShortDateToString(getUpdatedDate());
    }
}
