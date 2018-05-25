package bkhn.et.hospitalbill.ui.setting;

import javax.inject.Inject;

import bkhn.et.hospitalbill.base.BasePresenter;
import bkhn.et.hospitalbill.data.IDataManager;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;

/**
 * Created by PL_itto on 5/24/2018.
 */

public class SettingPresenter<V extends ISettingContract.ISettingView> extends BasePresenter<V> implements ISettingContract.ISettingPresenter<V> {
    private static final String TAG = TAGG + SettingPresenter.class.getSimpleName();

    IDataManager mDataManager;

    @Inject
    public SettingPresenter(IDataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void logout() {
        mDataManager.logout();
    }
}
