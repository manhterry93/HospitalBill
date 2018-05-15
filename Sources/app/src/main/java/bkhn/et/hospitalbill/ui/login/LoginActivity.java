package bkhn.et.hospitalbill.ui.login;

import android.support.v4.app.Fragment;

import bkhn.et.hospitalbill.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new LoginFragment();
    }
}
