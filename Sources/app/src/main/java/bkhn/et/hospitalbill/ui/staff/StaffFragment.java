package bkhn.et.hospitalbill.ui.staff;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.ui.setting.SettingFragment;
import bkhn.et.hospitalbill.ui.staff.bill.BillFragment;
import bkhn.et.hospitalbill.ui.staff.user.StaffUserFragment;
import bkhn.et.hospitalbill.utils.AppConstants.Staff;
import bkhn.et.hospitalbill.utils.Logg;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;

/**
 * Created by PL_itto on 5/24/2018.
 */

public class StaffFragment extends BaseFragment {
    private static final String TAG = TAGG + StaffFragment.class.getSimpleName();
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
        mBottomView.inflateMenu(R.menu.staff_menu);
        setupContentPager();
    }

    private void setupContentPager() {
        mPagerAdapter = new ContentPagerAdapter(getChildFragmentManager());
        mContentPager.setAdapter(mPagerAdapter);
        mContentPager.setOffscreenPageLimit(4);
        mBottomView.setOnNavigationItemSelectedListener(mTabSelectListener);
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {
        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case Staff.TAB_USER:
                    return new StaffUserFragment();
                case Staff.TAB_BILL:
                    return new BillFragment();
                case Staff.TAB_SETTING:
                    return new SettingFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return Staff.TAB_COUNT;
        }
    }

    BottomNavigationView.OnNavigationItemSelectedListener mTabSelectListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.staff_user:
                    mContentPager.setCurrentItem(Staff.TAB_USER);
                    break;
                case R.id.staff_bill:
                    mContentPager.setCurrentItem(Staff.TAB_BILL);
                    break;
                case R.id.staff_setting:
                    mContentPager.setCurrentItem(Staff.TAB_SETTING);
                    break;
            }
            return true;
        }
    };
}
