package bkhn.et.hospitalbill.ui.newbill;

import java.util.List;

import bkhn.et.hospitalbill.base.IBaseContract;
import bkhn.et.hospitalbill.data.model.BillModel;
import bkhn.et.hospitalbill.data.model.InsuranceItem;
import bkhn.et.hospitalbill.data.model.ProblemModel;
import bkhn.et.hospitalbill.data.model.UserModel;

/**
 * Created by PL_itto on 5/27/2018.
 */

public interface INewBillContract {
    interface INewBillView extends IBaseContract.IBaseView {
        void requestCompleteBill();

        void onCreateBillResult(boolean success);

        void requestStaffInfo();

        void updateStaffInfo(UserModel model);

        void requestCheckInsurance();

        void updateInsuranceInfo(InsuranceItem data, boolean canUse);

        void requestLoadRecord();

        void updateProblemList(List<ProblemModel> data);

        void requestScanRecordId();

        void onRecordIdScanResult(String id);
    }

    interface INewBillPresenter<V extends INewBillView> extends IBaseContract.IBasePresenter<V> {
        void completeBill(BillModel model);

        void getStaffInfo();

        void checkInsuranceInfo(String id);

        void loadRecord(String recordId);
    }
}
