package bkhn.et.hospitalbill.ui.main;

import android.support.v4.app.Fragment;

import bkhn.et.hospitalbill.base.BaseActivity;
import bkhn.et.hospitalbill.ui.doctor.DoctorFragment;
import bkhn.et.hospitalbill.ui.staff.StaffFragment;
import bkhn.et.hospitalbill.utils.AppConstants;
import bkhn.et.hospitalbill.utils.Logg;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;

public class MainActivity extends BaseActivity {
    private static final String TAG=TAGG+MainActivity.class.getSimpleName();
    @Override
    public Fragment getFragment() {
        long jobType = getIntent().getLongExtra(AppConstants.Main.EXTRA_JOB_TYPE, -1);
        Logg.d(TAG,"jobType: "+jobType);
        if (jobType == AppConstants.Job.TYPE_DOCTOR)
            return new DoctorFragment();
        else if (jobType == AppConstants.Job.TYPE_STAFF) return new StaffFragment();
        else throw new IllegalArgumentException("Job type must be TYPE_DOCTOR or TYPE_STAFF");
    }
}
