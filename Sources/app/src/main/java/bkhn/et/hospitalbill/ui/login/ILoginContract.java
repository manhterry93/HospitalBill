package bkhn.et.hospitalbill.ui.login;

import bkhn.et.hospitalbill.base.IBaseContract;

/**
 * Created by PL_itto on 5/8/2018.
 */

public interface ILoginContract {
    interface ILoginView extends IBaseContract.IBaseView {
        void openMainScreen(int type);

    }

    interface ILoginPresenter<V extends ILoginView> extends IBaseContract.IBasePresenter<V> {

    }


}
