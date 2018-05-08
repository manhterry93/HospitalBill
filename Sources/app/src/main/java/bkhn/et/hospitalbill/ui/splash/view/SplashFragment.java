package bkhn.et.hospitalbill.ui.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.ui.login.view.LoginActivity;
import bkhn.et.hospitalbill.ui.splash.ISplashContract;

/**
 * Created by PL_itto on 5/3/2018.
 */

public class SplashFragment extends BaseFragment implements ISplashContract.ISplashView {

    @Override
    protected void initInject() {

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       getView().postDelayed(new Runnable() {
           @Override
           public void run() {
               openLoginActivity();
           }
       },2000);
    }
}
