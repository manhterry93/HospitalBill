package bkhn.et.hospitalbill.ui.setting;

import bkhn.et.hospitalbill.base.IBaseContract;

/**
 * Created by PL_itto on 5/24/2018.
 */

public interface ISettingContract {
    //  SettingActivity
    interface ISettingView extends IBaseContract.IBaseView {
        void openLogin();

        void requestLogout();
    }

    interface ISettingPresenter<V extends ISettingView> extends IBaseContract.IBasePresenter<V> {
        void logout();
    }
}
