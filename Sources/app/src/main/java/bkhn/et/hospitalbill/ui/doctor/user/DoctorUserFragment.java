package bkhn.et.hospitalbill.ui.doctor.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.data.model.UserModel;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract;
import bkhn.et.hospitalbill.utils.UiUtils;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/8/2018.
 */

public class DoctorUserFragment extends BaseFragment implements DoctorContract.IDoctorUserView {
    private static final String TAG = TAGG + DoctorUserFragment.class.getSimpleName();

    /* Views */
    private ImageView mUserAvatar;
    private TextView mUserId;
    private TextView mUserBirth;
    private TextView mUserName;
    private TextView mJobName;
    private TextView mDepartment;
    private TextView mSession;
    private TextView mPhone;

    /* Parameters */
    @Inject
    DoctorContract.IDoctorUserPresenter<DoctorContract.IDoctorUserView> mPresenter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
        mPresenter.onAttach(this);
    }

    @Override
    public void onDestroy() {
        if (isNotNull(mPresenter))
            mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        mUserAvatar = mView.findViewById(R.id.doctor_avatar);
        mUserId = mView.findViewById(R.id.doctor_id);
        mUserBirth = mView.findViewById(R.id.doctor_birth);
        mUserName = mView.findViewById(R.id.doctor_name);
        mJobName = mView.findViewById(R.id.job_name);
        mDepartment = mView.findViewById(R.id.department);
        mSession = mView.findViewById(R.id.session);
        mPhone = mView.findViewById(R.id.phone);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_doctor_user;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        requestGetUserDetail();
    }

    @Override
    public void requestGetUserDetail() {
        mPresenter.getUserDetail();
    }

    @Override
    public void updateUserDetail(UserModel model) {
        UiUtils.loadImageLink(mContext, model.getAvatar(), mUserAvatar, R.drawable.ic_avatar_default, true);
        mUserId.setText(model.getId());
        mUserName.setText(model.getName());
        mUserBirth.setText(model.getBirth());
        mPhone.setText(model.getPhone());
        mDepartment.setText(model.getDepartmentName());
        mJobName.setText(model.getDoctorPosition());
    }
}
