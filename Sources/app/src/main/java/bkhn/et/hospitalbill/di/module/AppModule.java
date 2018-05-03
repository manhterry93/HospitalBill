package bkhn.et.hospitalbill.di.module;

import android.app.Application;

import dagger.Module;

/**
 * Created by PL_itto on 5/3/2018.
 */
@Module
public class AppModule {
    Application mApp;

    public AppModule(Application app) {
        mApp = app;
    }
}
