package com.fahrschule.sevim.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MessageContent {

    /**
     * An array of sample (Message) items.
     */
    public static final List<MessageItem> ITEMS = new ArrayList<MessageItem>();

    /**
     * A map of sample (Message) items, by TimeStamp.
     */
    public static final Map<String, MessageItem> ITEM_MAP = new HashMap<String, MessageItem>();

    private static final int COUNT = 15;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(MessageItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.timestamp, item);
    }

    private static MessageItem createDummyItem(int position) {
        return new MessageItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        return "Details about Item: "+ position;
    }

    /**
     * A Message item representing content in a particular Message.
     */
    public static class MessageItem {
        public final String title;
        public final String timestamp;
        public final String details;

        public MessageItem(String title, String timestamp, String details) {
            this.title = title;
            this.timestamp = timestamp;
            this.details = details;
        }

        @Override public String toString() {
            return timestamp;
        }
    }
}
