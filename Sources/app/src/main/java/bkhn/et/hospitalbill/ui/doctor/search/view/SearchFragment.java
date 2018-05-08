package bkhn.et.hospitalbill.ui.doctor.search.view;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;

/**
 * Created by PL_itto on 5/8/2018.
 */

public class SearchFragment extends BaseFragment implements DoctorContract.IIllnessSearchView {
    private static final String TAG = TAGG + SearchFragment.class.getSimpleName();

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_search;
    }
}
