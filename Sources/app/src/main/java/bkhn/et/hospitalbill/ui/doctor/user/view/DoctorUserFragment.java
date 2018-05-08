package bkhn.et.hospitalbill.ui.doctor.user.view;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract;

/**
 * Created by PL_itto on 5/8/2018.
 */

public class DoctorUserFragment extends BaseFragment implements DoctorContract.IDoctorView {
    @Override
    protected void initInject() {
        
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_doctor_user;
    }
}
