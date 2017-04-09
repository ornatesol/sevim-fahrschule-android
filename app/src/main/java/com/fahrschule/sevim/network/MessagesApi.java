package com.fahrschule.sevim.network;

import com.fahrschule.sevim.models.MessageItem;
import java.util.ArrayList;
import rx.Observable;

public interface MessagesApi {

    Observable<ArrayList<MessageItem>> getAllMessages();

}
