package bkhn.et.hospitalbill.base;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Calendar;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.IBaseContract.IActionCallback;
import bkhn.et.hospitalbill.utils.NetworkUtils;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/2/2018.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseContract.IBaseView {
    private static final String TAG = TAGG + BaseActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CODE = 100;

    public abstract Fragment getFragment();


    private IActionCallback mIActionCallback = null;

    protected int getLayoutId() {
        return R.layout.activity_default;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setupFragment();
    }

    private void setupFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = getFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(this, getResources().getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void requestPermissions(@NonNull String[] permission, @NonNull IActionCallback callback) {
        mIActionCallback = callback;
        requestPermissions(permission, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // TODO: will be implemented later
            boolean granted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
            if (granted && isNotNull(mIActionCallback)) {
                mIActionCallback.onSuccess();
                mIActionCallback = null;
            }
        }
    }

    @Override
    public boolean isPermissionGranted(String[] permissions) {
        if (Build.VERSION.SDK_INT < 23) return true;
        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    @Override
    public long getCurrentTime() {
        return Calendar.getInstance().getTime().getTime();
    }
}
