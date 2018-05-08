package bkhn.et.hospitalbill.ui.splash;

import bkhn.et.hospitalbill.base.IBaseContract.IBasePresenter;
import bkhn.et.hospitalbill.base.IBaseContract.IBaseView;

/**
 * Created by PL_itto on 5/3/2018.
 */

public interface ISplashContract {
    interface ISplashView extends IBaseView {

    }

    interface ISplashPresenter<V extends ISplashView> extends IBasePresenter<V> {

    }
}
