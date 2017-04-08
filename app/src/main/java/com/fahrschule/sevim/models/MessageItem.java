package com.fahrschule.sevim.models;

import com.google.gson.annotations.SerializedName;

public class MessageItem {

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

    public long getCreatedAtDate() {
        return created_at;
    }

    public long getUpdatedAtDate() {
        return updated_at;
    }
}
