package com.fahrschule.sevim.models;

import java.util.ArrayList;
import java.util.List;

public class TheoriePhotoContent {

    private final List<TheoriePhotoItem> ITEMS = new ArrayList<>();

    private void addItem(TheoriePhotoItem item) {
        ITEMS.add(item);
    }

    public void createAddItem(String path) {
        addItem(new TheoriePhotoItem(path));
    }

    public List<TheoriePhotoItem> getItems() {
        return ITEMS;
    }

    public static class TheoriePhotoItem {
        public final String imageUrl;

        TheoriePhotoItem(String url) {
            this.imageUrl = url;
        }

        @Override
        public String toString() {
            return imageUrl;
        }
    }
}
