package bkhn.et.hospitalbill.ui.splash;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import bkhn.et.hospitalbill.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new SplashFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
