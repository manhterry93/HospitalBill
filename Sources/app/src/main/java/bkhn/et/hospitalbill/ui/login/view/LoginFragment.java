package bkhn.et.hospitalbill.ui.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.ui.main.MainActivity;
import bkhn.et.hospitalbill.ui.login.ILoginContract;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;

/**
 * Created by PL_itto on 5/8/2018.
 */

public class LoginFragment extends BaseFragment implements ILoginContract.ILoginView {
    private static final String TAG = TAGG + LoginFragment.class.getSimpleName();

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_login;
    }

    @Override
    public void openMainScreen(int type) {
        Intent i = new Intent(mContext, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                openMainScreen(1);
            }
        },5000);
    }
}
