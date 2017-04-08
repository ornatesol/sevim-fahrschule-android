package com.fahrschule.sevim.dagger;

import com.fahrschule.sevim.AppModule;
import com.fahrschule.sevim.fragments.MessagesListFragment;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component (modules = { AppModule.class, RetroApiModule.class})
public interface ApplicationComponent {
    void inject(MessagesListFragment fragment);
}
