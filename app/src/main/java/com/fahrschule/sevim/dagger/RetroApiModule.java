package com.fahrschule.sevim.dagger;

import com.fahrschule.sevim.network.MessagesApi;
import com.fahrschule.sevim.network.RetrofitMessagesApi;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module (includes = NetworkModule.class)
public class RetroApiModule {

    @Singleton
    @Provides
    MessagesApi provideMessagesApi(RetrofitMessagesApi impl) {
        return impl;
    }
}
