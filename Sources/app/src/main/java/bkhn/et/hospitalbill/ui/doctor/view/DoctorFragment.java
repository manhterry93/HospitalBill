package bkhn.et.hospitalbill.ui.doctor.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract;
import bkhn.et.hospitalbill.ui.doctor.record.view.RecordFragment;
import bkhn.et.hospitalbill.ui.doctor.search.view.SearchFragment;
import bkhn.et.hospitalbill.ui.doctor.setting.view.SettingFragment;
import bkhn.et.hospitalbill.ui.doctor.user.view.DoctorUserFragment;
import bkhn.et.hospitalbill.utils.AppConstants.Doctor;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;

/**
 * Created by PL_itto on 5/8/2018.
 */

public class DoctorFragment extends BaseFragment implements DoctorContract.IDoctorView {
    private static final String TAG = TAGG + DoctorFragment.class.getSimpleName();

    /* View */
    private ViewPager mContentPager;
    private BottomNavigationView mBottomView;

    /* Parameters */
    private ContentPagerAdapter mPagerAdapter;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_main;
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        mContentPager = mView.findViewById(R.id.content_pager);
        mBottomView = mView.findViewById(R.id.main_bottom_menu);
        setupContentPager();
    }

    private void setupContentPager() {
        mPagerAdapter = new ContentPagerAdapter(getChildFragmentManager());
        mContentPager.setAdapter(mPagerAdapter);
        mBottomView.setOnNavigationItemSelectedListener(mTabSelectListener);

    }

    class ContentPagerAdapter extends FragmentStatePagerAdapter {
        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case Doctor.TAB_USER:
                    return new DoctorUserFragment();
                case Doctor.TAB_SEARCH:
                    return new SearchFragment();
                case Doctor.TAB_RECORD:
                    return new RecordFragment();
                case Doctor.TAB_SETTING:
                    return new SettingFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return Doctor.TAB_COUNT;
        }
    }

    BottomNavigationView.OnNavigationItemSelectedListener mTabSelectListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.doctor_user:
                    mContentPager.setCurrentItem(Doctor.TAB_USER);
                    break;
                case R.id.doctor_search:
                    mContentPager.setCurrentItem(Doctor.TAB_SEARCH);
                    break;
                case R.id.doctor_record:
                    mContentPager.setCurrentItem(Doctor.TAB_RECORD);
                    break;
                case R.id.doctor_setting:
                    mContentPager.setCurrentItem(Doctor.TAB_SETTING);
                    break;
            }
            return true;
        }
    };
}
