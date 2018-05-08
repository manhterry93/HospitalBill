package bkhn.et.hospitalbill.ui.doctor.record.view;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;

/**
 * Created by PL_itto on 5/8/2018.
 */

public class RecordFragment extends BaseFragment implements DoctorContract.IRecordView {
    private static final String TAG = TAGG + RecordFragment.class.getSimpleName();

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_record;
    }
}
