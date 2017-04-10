package com.fahrschule.sevim.network;

import android.support.annotation.NonNull;
import com.fahrschule.sevim.models.MessageItem;
import java.util.ArrayList;
import java.util.Arrays;
import javax.inject.Inject;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RetrofitMessagesApi implements MessagesApi {

    private final RetrofitService retrofitService;

    private final static String API_URL = "nachricht/1bda80f2be4d3658e0baa43fbe7ae8c1";

    @Inject
    RetrofitMessagesApi(@NonNull Retrofit retrofit) {
        retrofitService = retrofit.create(RetrofitService.class);
    }

    @Override
    public Observable<ArrayList<MessageItem>> getAllMessages() {
        return retrofitService.getAllMessages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<MessageItem[], ArrayList<MessageItem>>() {
                    @Override
                    public ArrayList<MessageItem> call(MessageItem[] messageItems) {
                        if(messageItems == null) {
                            return new ArrayList<>();
                        }
                        return new ArrayList<>(Arrays.asList(messageItems));
                        //return getMessageItemsFake(messageItems[0]); //TODO Remove it
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @NonNull
    private ArrayList<MessageItem> getMessageItemsFake(MessageItem messageItem) {
        //used this tool http://www.onlineconversion.com/unix_time.htm
        ArrayList<MessageItem> fakeList = new ArrayList<>();
        fakeList.add(messageItem);
        fakeList.add(new MessageItem(1, "Offer " + 1,
                "Details for Offer " + 1,1491624244,1491624244));
        fakeList.add(new MessageItem(2, "Offer " + 2,
                "Details for Offer " + 2,1491567432,1491567432));
        return fakeList;
    }

    interface RetrofitService {
        @GET(API_URL)
        Observable<MessageItem[]> getAllMessages();
    }
}
