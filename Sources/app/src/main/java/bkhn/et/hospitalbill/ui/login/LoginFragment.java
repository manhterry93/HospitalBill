package bkhn.et.hospitalbill.ui.login;

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
import bkhn.et.hospitalbill.ui.login.ILoginContract.ILoginPresenter;
import bkhn.et.hospitalbill.ui.login.ILoginContract.ILoginView;
import bkhn.et.hospitalbill.ui.main.MainActivity;
import bkhn.et.hospitalbill.utils.AppConstants;
import bkhn.et.hospitalbill.utils.Logg;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/8/2018.
 */

public class LoginFragment extends BaseFragment implements ILoginView {
    private static final String TAG = TAGG + LoginFragment.class.getSimpleName();

    /* VIews */
    private EditText mUserInput, mPasswordInput;
    private Button mLoginBtn;
    private ProgressBar mLoginProgress;

    /* Paramters */
    @Inject
    ILoginPresenter<ILoginView> mPresenter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
        mPresenter.onAttach(this);
    }

    @Override
    public void onDestroy() {
        Logg.d(TAG, "onDestroy");
        if (isNotNull(mPresenter))
            mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        mLoginProgress = mView.findViewById(R.id.login_progress_bar);
        mUserInput = mView.findViewById(R.id.login_username_input);
        mPasswordInput = mView.findViewById(R.id.login_password_input);
        mLoginBtn = mView.findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLogin();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_login;
    }

    @Override
    public void openMainScreen(int type) {
        Intent i = new Intent(mContext, MainActivity.class);
        i.putExtra(AppConstants.Main.EXTRA_JOB_TYPE, type);
        startActivity(i);
        mActivity.finish();
    }

    @Override
    public void requestLogin() {
        String user = mUserInput.getText().toString();
        if (user.isEmpty()) {
            showMessage(R.string.login_user_empty);
            return;
        }

        String password = mPasswordInput.getText().toString();
        if (password.isEmpty()) {
            showMessage(R.string.login_pass_empty);
            return;
        }
        mLoginProgress.setVisibility(View.VISIBLE);
        mPresenter.login(user, password);
    }

    @Override
    public void onLoginResult(boolean success) {
        mLoginProgress.setVisibility(View.INVISIBLE);
        if (success)
            showMessage(R.string.login_success);
        else
            showMessage(R.string.login_fail);
    }

}
