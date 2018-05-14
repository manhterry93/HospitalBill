package bkhn.et.hospitalbill.ui.splash;

import bkhn.et.hospitalbill.base.IBaseContract.IBasePresenter;
import bkhn.et.hospitalbill.base.IBaseContract.IBaseView;
import bkhn.et.hospitalbill.data.model.UserModel;

/**
 * Created by PL_itto on 5/3/2018.
 */

public interface ISplashContract {
    interface ISplashView extends IBaseView {
        void openLoginActivity();

        void openMainActivity(UserModel model);
    }

    interface ISplashPresenter<V extends ISplashView> extends IBasePresenter<V> {
        void checkLogin();
    }
}
