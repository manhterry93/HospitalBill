package bkhn.et.hospitalbill.ui.newrecord;

import java.util.List;

import bkhn.et.hospitalbill.base.IBaseContract;
import bkhn.et.hospitalbill.data.model.DepartmentModel;
import bkhn.et.hospitalbill.data.model.ProblemModel;
import bkhn.et.hospitalbill.data.model.RecordModel;
import bkhn.et.hospitalbill.data.model.UserModel;

/**
 * Created by PL_itto on 5/18/2018.
 */

public interface INewRecordContract {
    interface INewRecordView extends IBaseContract.IBaseView {
        void addProblem(ProblemModel model);

        void openProblemSelect();

        void requestCompleteRecord();

        void updateProblemList(List<ProblemModel> data);

        void updateDepartments(List<DepartmentModel> data);

        void requestDoctorInfo();

        void updateDoctorInfo(UserModel model);

        void onAddRecordResult(boolean success, String recordId);
    }

    interface INewRecordPresenter<V extends INewRecordView> extends IBaseContract.IBasePresenter<V> {
        void completeRecord(RecordModel model);

        void loadProblemList();

        void loadDepartmentList();

        void loadDoctorInfo();


    }
}
