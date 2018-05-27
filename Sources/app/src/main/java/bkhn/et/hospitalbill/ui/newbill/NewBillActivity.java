package bkhn.et.hospitalbill.ui.newbill;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseActivity;

public class NewBillActivity extends BaseActivity {

    @Override
    public Fragment getFragment() {
        return new NewBillFragment();
    }

}
