package com.fahrschule.sevim;

import android.app.Application;
import android.content.Context;
import com.fahrschule.sevim.dagger.ApplicationComponent;
import com.fahrschule.sevim.dagger.DaggerApplicationComponent;

public class MainApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ApplicationComponent component() {
        return component;
    }

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }
}
