package bkhn.et.hospitalbill.di.component;

import javax.inject.Singleton;

import bkhn.et.hospitalbill.di.PerActivity;
import bkhn.et.hospitalbill.di.module.FragmentModule;
import bkhn.et.hospitalbill.ui.splash.view.SplashFragment;
import dagger.Component;

/**
 * Created by PL_itto on 5/3/2018.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(SplashFragment fragment);
}
