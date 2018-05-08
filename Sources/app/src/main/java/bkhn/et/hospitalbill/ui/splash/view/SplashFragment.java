package bkhn.et.hospitalbill.ui.splash.view;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.ui.splash.ISplashContract;

/**
 * Created by PL_itto on 5/3/2018.
 */

public class SplashFragment extends BaseFragment implements ISplashContract.ISplashView {
    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_splash;
    }
}
