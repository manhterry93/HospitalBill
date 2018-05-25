package bkhn.et.hospitalbill.di.component;

import bkhn.et.hospitalbill.di.PerActivity;
import bkhn.et.hospitalbill.di.module.FragmentModule;
import bkhn.et.hospitalbill.ui.doctor.record.RecordFragment;
import bkhn.et.hospitalbill.ui.doctor.search.SearchFragment;
import bkhn.et.hospitalbill.ui.doctor.user.DoctorUserFragment;
import bkhn.et.hospitalbill.ui.login.LoginFragment;
import bkhn.et.hospitalbill.ui.newrecord.NewRecordFragment;
import bkhn.et.hospitalbill.ui.setting.SettingFragment;
import bkhn.et.hospitalbill.ui.splash.SplashFragment;
import dagger.Component;

/**
 * Created by PL_itto on 5/3/2018.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(SplashFragment fragment);

    void inject(LoginFragment fragment);

    void inject(DoctorUserFragment fragment);

    void inject(SearchFragment fragment);

    void inject(RecordFragment fragment);

    void inject(NewRecordFragment fragment);

    void inject(SettingFragment fragment);
}
