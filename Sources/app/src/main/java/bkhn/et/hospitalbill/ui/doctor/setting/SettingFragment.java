package bkhn.et.hospitalbill.ui.doctor.setting;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract;

/**
 * Created by PL_itto on 5/8/2018.
 */

public class SettingFragment extends BaseFragment implements DoctorContract.ISettingView {
    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_setting;
    }
}
