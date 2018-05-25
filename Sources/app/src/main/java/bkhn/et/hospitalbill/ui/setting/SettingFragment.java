package bkhn.et.hospitalbill.ui.setting;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract;
import bkhn.et.hospitalbill.ui.login.LoginActivity;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;

/**
 * Created by PL_itto on 5/8/2018.
 */

public class SettingFragment extends BaseFragment implements ISettingContract.ISettingView {
    private static final String TAG = TAGG + SettingFragment.class.getSimpleName();

    private TextView mLogoutBtn;
    @Inject
    ISettingContract.ISettingPresenter<ISettingContract.ISettingView> mPresenter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        mLogoutBtn = mView.findViewById(R.id.logout);
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLogout();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_setting;
    }

    @Override
    public void openLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void requestLogout() {
        mPresenter.logout();
        openLogin();
        mActivity.finish();
    }


}
