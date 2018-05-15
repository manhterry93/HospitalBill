package bkhn.et.hospitalbill.ui.doctor.search;

import android.support.v4.app.Fragment;

import bkhn.et.hospitalbill.base.BaseActivity;

public class SearchActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new SearchFragment();
    }
}
