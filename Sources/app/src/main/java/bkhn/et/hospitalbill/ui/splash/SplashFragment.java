package bkhn.et.hospitalbill.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import javax.inject.Inject;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.data.model.UserModel;
import bkhn.et.hospitalbill.ui.login.LoginActivity;
import bkhn.et.hospitalbill.ui.main.MainActivity;
import bkhn.et.hospitalbill.ui.splash.ISplashContract.ISplashPresenter;
import bkhn.et.hospitalbill.utils.AppConstants;

import static bkhn.et.hospitalbill.ui.splash.ISplashContract.ISplashView;
import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/3/2018.
 */

public class SplashFragment extends BaseFragment implements ISplashView {
    private static final String TAG = TAGG + SplashFragment.class.getSimpleName();

    @Inject
    ISplashPresenter<ISplashContract.ISplashView> mPresenter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
        mPresenter.onAttach(this);
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_splash;
    }

    @Override
    public void openLoginActivity() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void openMainActivity(UserModel model) {
        if (isNotNull(model)) {
            Intent intent;
            intent = new Intent(mContext, MainActivity.class);
            intent.putExtra(AppConstants.Main.EXTRA_JOB_TYPE, model.getJobId());
            startActivity(intent);

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.checkLogin();
    }
}
