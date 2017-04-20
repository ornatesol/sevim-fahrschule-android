package com.fahrschule.sevim.network;

import android.support.annotation.NonNull;
import com.fahrschule.sevim.models.MessageItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import rx.Observable;
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
                .observeOn(Schedulers.io())
                .timeout(10, TimeUnit.SECONDS)
                .map(new Func1<MessageItem[], ArrayList<MessageItem>>() {
                    @Override
                    public ArrayList<MessageItem> call(MessageItem[] messageItems) {
                        if(messageItems == null) {
                            return new ArrayList<>();
                        }
                        return new ArrayList<>(Arrays.asList(messageItems));
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    interface RetrofitService {
        @GET(API_URL)
        Observable<MessageItem[]> getAllMessages();
    }
}
