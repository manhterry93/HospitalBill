package bkhn.et.hospitalbill.ui.doctor;

import bkhn.et.hospitalbill.base.IBaseContract.IBasePresenter;
import bkhn.et.hospitalbill.base.IBaseContract.IBaseView;

/**
 * Created by PL_itto on 5/8/2018.
 */

public interface DoctorContract {
    interface IDoctorView extends IBaseView {

    }

    //    Doctor User Screen
    interface IDoctorUserView extends IBaseView {

    }

    interface IDoctorUserPresenter<V extends IDoctorUserView> extends IBasePresenter<V> {

    }

    //    Search screen
    interface IIllnessSearchView extends IBaseView {

    }

    interface IIllnessSearchPresenter<V extends IIllnessSearchView> extends IBasePresenter<V> {

    }

    //    Record
    interface IRecordView extends IBaseView {

    }

    interface IRecordPresenter<V extends IRecordView> extends IBasePresenter<V> {

    }

    //  SettingActivity
    interface ISettingView extends IBaseView {

    }

    interface ISettingPresenter<V extends ISettingView> extends IBasePresenter<V> {

    }

}
