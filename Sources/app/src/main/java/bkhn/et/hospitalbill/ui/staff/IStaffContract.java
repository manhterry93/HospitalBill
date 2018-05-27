package bkhn.et.hospitalbill.ui.staff;

import java.util.List;

import bkhn.et.hospitalbill.base.IBaseContract;
import bkhn.et.hospitalbill.data.model.BillModel;
import bkhn.et.hospitalbill.data.model.UserModel;

/**
 * Created by PL_itto on 5/14/2018.
 */

public interface IStaffContract {
    interface IStaffView extends IBaseContract.IBaseView {

    }

    interface IStaffUserView extends IBaseContract.IBaseView {
        void requestGetUserDetail();

        void updateUserDetail(UserModel model);
    }

    interface IStaffUserPresenter<V extends IStaffUserView> extends IBaseContract.IBasePresenter<V> {
        void getUserDetail();
    }

    // User


    // Bill
    interface IBillView extends IBaseContract.IBaseView {
        void requestLoadBill();

        void updateBillList(List<BillModel> data);

        void openBillDetail(BillModel model);

        void filter(String keyword);

        void requestNewBill();
    }

    interface IBillPresenter<V extends IBillView> extends IBaseContract.IBasePresenter<V> {
        void loadBillList();
    }
}
