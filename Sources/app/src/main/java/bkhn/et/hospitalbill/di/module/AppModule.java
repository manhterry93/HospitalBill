package bkhn.et.hospitalbill.di.module;

import android.app.Application;

import bkhn.et.hospitalbill.data.DataManager;
import bkhn.et.hospitalbill.data.IDataManager;
import bkhn.et.hospitalbill.data.remote.IRemoteProvider;
import bkhn.et.hospitalbill.data.remote.RemoteProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by PL_itto on 5/3/2018.
 */
@Module
public class AppModule {
    Application mApp;

    public AppModule(Application app) {
        mApp = app;
    }

    @Provides
    IRemoteProvider provideRemoteProvider(RemoteProvider remoteProvider) {
        return remoteProvider;
    }

    @Provides
    IDataManager provideDataManager(DataManager manager) {
        return manager;
    }


}
