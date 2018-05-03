package bkhn.et.hospitalbill;

import android.app.Application;

import bkhn.et.hospitalbill.di.component.AppComponent;
import bkhn.et.hospitalbill.di.component.DaggerAppComponent;
import bkhn.et.hospitalbill.di.module.AppModule;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;

/**
 * Created by PL_itto on 5/3/2018.
 */

public class MainApp extends Application {
    private static final String TAG = TAGG + MainApp.class.getSimpleName();
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initInject();
    }

    private void initInject() {
        getAppComponent().inject(this);
    }

    public AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return mAppComponent;
    }


}
