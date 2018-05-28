package bkhn.et.hospitalbill.ui.billdetail;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bkhn.et.hospitalbill.base.BaseActivity;

public class BillDetailActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new BillDetailFragment();
    }
}
