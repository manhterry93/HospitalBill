package bkhn.et.hospitalbill.base;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * Created by PL_itto on 5/2/2018.
 */

public interface IBaseContract {
    interface IBasePresenter<T extends IBaseView> {
        void onAttach(T view);

        void onDetach();
    }

    interface IBaseView {
        void showMessage(String message);

        void showMessage(@StringRes int resId);

        boolean isNetworkConnected();

        boolean isPermissionGranted(String[] permisisons);
    }

    interface IActionCallback<V> {
        void onSuccess();

        void onFailed();
    }
}
