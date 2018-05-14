package bkhn.et.hospitalbill.ui.login;

import bkhn.et.hospitalbill.base.IBaseContract;

/**
 * Created by PL_itto on 5/8/2018.
 */

public interface ILoginContract {
    interface ILoginView extends IBaseContract.IBaseView {
        void openMainScreen(int type);

        void requestLogin();

        void onLoginResult(boolean success);
    }

    interface ILoginPresenter<V extends ILoginView> extends IBaseContract.IBasePresenter<V> {
        void login(String email, String password);

        void getUserDetail();
    }


}
