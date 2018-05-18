package bkhn.et.hospitalbill.ui.doctor;

import android.support.annotation.NonNull;

import java.util.List;

import bkhn.et.hospitalbill.base.IBaseContract.IBasePresenter;
import bkhn.et.hospitalbill.base.IBaseContract.IBaseView;
import bkhn.et.hospitalbill.data.model.DepartmentModel;
import bkhn.et.hospitalbill.data.model.ProblemModel;
import bkhn.et.hospitalbill.data.model.RecordModel;
import bkhn.et.hospitalbill.data.model.UserModel;

/**
 * Created by PL_itto on 5/8/2018.
 */

public interface DoctorContract {
    interface IDoctorView extends IBaseView {

    }

    //    Doctor User Screen
    interface IDoctorUserView extends IBaseView {
        void requestGetUserDetail();

        void updateUserDetail(UserModel model);
    }

    interface IDoctorUserPresenter<V extends IDoctorUserView> extends IBasePresenter<V> {
        void getUserDetail();
    }

    //    Search screen
    interface IIllnessSearchView extends IBaseView {
        void toggleSearchMode();

        void requestSearch();

        void requestLoadDepartment();

        void updateDepartments(List<DepartmentModel> data);

        void updateProblemList(@NonNull List<ProblemModel> data);
    }

    interface IIllnessSearchPresenter<V extends IIllnessSearchView> extends IBasePresenter<V> {
        void loadProblemList();

        void loadDepartments();
    }

    //    Record
    interface IRecordView extends IBaseView {
        void requestLoadRecord();

        void openRecordDetail(RecordModel model);

        void openNewRecord();

        void updateRecordList(List<RecordModel> data);

    }

    interface IRecordPresenter<V extends IRecordView> extends IBasePresenter<V> {
        void loadRecords();
    }

    //  SettingActivity
    interface ISettingView extends IBaseView {

    }

    interface ISettingPresenter<V extends ISettingView> extends IBasePresenter<V> {

    }

}
