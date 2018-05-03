package bkhn.et.hospitalbill.di.component;

import bkhn.et.hospitalbill.di.module.AppModule;
import bkhn.et.hospitalbill.di.module.FragmentModule;
import dagger.Component;

/**
 * Created by PL_itto on 5/3/2018.
 */
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
}
