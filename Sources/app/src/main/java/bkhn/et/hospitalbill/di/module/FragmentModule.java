package bkhn.et.hospitalbill.di.module;

import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract.IDoctorUserPresenter;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract.IDoctorUserView;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract.IIllnessSearchPresenter;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract.IIllnessSearchView;
import bkhn.et.hospitalbill.ui.doctor.record.RecordPresenter;
import bkhn.et.hospitalbill.ui.doctor.search.SearchPresenter;
import bkhn.et.hospitalbill.ui.doctor.user.DoctorUserPresenter;
import bkhn.et.hospitalbill.ui.login.ILoginContract;
import bkhn.et.hospitalbill.ui.login.ILoginContract.ILoginView;
import bkhn.et.hospitalbill.ui.login.LoginPresenter;
import bkhn.et.hospitalbill.ui.newrecord.INewRecordContract;
import bkhn.et.hospitalbill.ui.newrecord.NewRecordPresenter;
import bkhn.et.hospitalbill.ui.setting.ISettingContract;
import bkhn.et.hospitalbill.ui.setting.SettingPresenter;
import bkhn.et.hospitalbill.ui.splash.ISplashContract.ISplashPresenter;
import bkhn.et.hospitalbill.ui.splash.ISplashContract.ISplashView;
import bkhn.et.hospitalbill.ui.splash.SplashPresenter;
import dagger.Module;
import dagger.Provides;


/**
 * Created by PL_itto on 5/3/2018.
 */
@Module
public class FragmentModule {
    private BaseFragment mFragment;

    public FragmentModule(BaseFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    ISplashPresenter<ISplashView> provideSplashPresenter(SplashPresenter<ISplashView> presenter) {
        return presenter;
    }

    @Provides
    ILoginContract.ILoginPresenter<ILoginView> provideLoginPresenter(LoginPresenter<ILoginView> presenter) {
        return presenter;
    }

    @Provides
    IDoctorUserPresenter<IDoctorUserView> provideDoctorUserPresenter(DoctorUserPresenter<IDoctorUserView> presenter) {
        return presenter;
    }

    @Provides
    IIllnessSearchPresenter<IIllnessSearchView> provideSearchPresenter(SearchPresenter<IIllnessSearchView> presenter) {
        return presenter;
    }

    @Provides
    DoctorContract.IRecordPresenter<DoctorContract.IRecordView> provideRecordPresenter(RecordPresenter<DoctorContract.IRecordView> presenter) {
        return presenter;
    }

    @Provides
    INewRecordContract.INewRecordPresenter<INewRecordContract.INewRecordView> provideNewRecordPresenter(NewRecordPresenter<INewRecordContract.INewRecordView> presenter) {
        return presenter;
    }

    @Provides
    ISettingContract.ISettingPresenter<ISettingContract.ISettingView> provideSettingPresenter(SettingPresenter<ISettingContract.ISettingView> presenter) {
        return presenter;
    }
}
