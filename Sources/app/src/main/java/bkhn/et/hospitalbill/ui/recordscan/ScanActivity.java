package bkhn.et.hospitalbill.ui.recordscan;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bkhn.et.hospitalbill.base.BaseActivity;

public class ScanActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new ScannerFragment();
    }

}
