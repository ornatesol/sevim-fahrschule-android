package com.fahrschule.sevim;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class AppModule {

    private final MainApplication application;

    public AppModule(MainApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    MainApplication provideApplication() {
        return application;
    }

    @Singleton
    @Provides Context provideApplicationContext() {
        return application;
    }
}
