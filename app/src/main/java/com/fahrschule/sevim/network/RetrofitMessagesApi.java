package com.fahrschule.sevim.network;

import android.support.annotation.NonNull;

import com.fahrschule.sevim.models.MessageItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RetrofitMessagesApi implements MessagesApi {

    private final RetrofitService retrofitService;

    public final static String API_URL = "nachricht/1bda80f2be4d3658e0baa43fbe7ae8c1";
    public final static String API_URL_WEDDING_DE = "theory/6a992d5529f459a44fee58c733255e86";
    public final static String API_URL_WEDDING_TR = "theory/92777ffd7251181036eb61be3f184ccd";
    public final static String API_URL_REICKENDORF_DE = "theory/7d486008a8b76429b68adaee46b89c2c";
    public final static String API_URL_REICKENDORF_TR = "theory/f7efc8358cc6f07c97025d7b72b6da4";

    @Inject
    RetrofitMessagesApi(@NonNull Retrofit retrofit) {
        retrofitService = retrofit.create(RetrofitService.class);
    }

    @Override
    public Observable<ArrayList<MessageItem>> getAllMessages(String url) {
        return retrofitService.getAllMessages(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .timeout(30, TimeUnit.SECONDS)
                .map(new Func1<MessageItem[], ArrayList<MessageItem>>() {
                    @Override
                    public ArrayList<MessageItem> call(MessageItem[] messageItems) {
                        if (messageItems == null) {
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
        @GET
        Observable<MessageItem[]> getAllMessages(@Url String url);
    }
}
