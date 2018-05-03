package bkhn.et.hospitalbill.di.component;

import javax.inject.Singleton;

import bkhn.et.hospitalbill.MainApp;
import bkhn.et.hospitalbill.di.module.AppModule;
import dagger.Component;

/**
 * Created by PL_itto on 5/3/2018.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MainApp app);
}
