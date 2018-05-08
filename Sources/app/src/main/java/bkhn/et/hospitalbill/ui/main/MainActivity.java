package bkhn.et.hospitalbill.ui.main;

import android.support.v4.app.Fragment;

import bkhn.et.hospitalbill.base.BaseActivity;
import bkhn.et.hospitalbill.ui.doctor.view.DoctorFragment;

public class MainActivity extends BaseActivity {
    @Override
    public Fragment getFragment() {
        return new DoctorFragment();
    }
}
