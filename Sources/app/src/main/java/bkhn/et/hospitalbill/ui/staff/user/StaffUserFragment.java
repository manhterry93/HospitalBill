package bkhn.et.hospitalbill.ui.staff.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.data.model.UserModel;
import bkhn.et.hospitalbill.ui.staff.IStaffContract;
import bkhn.et.hospitalbill.utils.UiUtils;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/24/2018.
 */

public class StaffUserFragment extends BaseFragment implements IStaffContract.IStaffUserView {
    private static final String TAG = TAGG + StaffUserFragment.class.getSimpleName();

    /* Views */
    private ImageView mUserAvatar;
    private TextView mUserId;
    private TextView mUserBirth;
    private TextView mUserName;
    private TextView mJobName;
    private TextView mSession;
    private TextView mPhone;

    /* Parameters */
    @Inject
    IStaffContract.IStaffUserPresenter<IStaffContract.IStaffUserView> mPresenter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
        mPresenter.onAttach(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_staff_user;
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        mUserAvatar = mView.findViewById(R.id.doctor_avatar);
        mUserId = mView.findViewById(R.id.doctor_id);
        mUserBirth = mView.findViewById(R.id.doctor_birth);
        mUserName = mView.findViewById(R.id.doctor_name);
        mJobName = mView.findViewById(R.id.job_name);
        mSession = mView.findViewById(R.id.session);
        mPhone = mView.findViewById(R.id.phone);
    }

    @Override
    public void onDestroy() {
        if (isNotNull(mPresenter))
            mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void requestGetUserDetail() {
        mPresenter.getUserDetail();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestGetUserDetail();
    }

    @Override
    public void updateUserDetail(UserModel model) {
        UiUtils.loadImageLink(mContext, model.getAvatar(), mUserAvatar, R.drawable.ic_avatar_default, true);
        mUserId.setText(model.getId());
        mUserName.setText(model.getName());
        mUserBirth.setText(model.getBirth());
        mPhone.setText(model.getPhone());
        mJobName.setText(model.getJobName());
    }
}
