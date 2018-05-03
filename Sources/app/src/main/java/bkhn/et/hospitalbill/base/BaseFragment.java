package bkhn.et.hospitalbill.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/2/2018.
 */

public abstract class BaseFragment extends Fragment implements IBaseContract.IBaseView {
    private static final String TAG = TAGG + BaseFragment.class.getSimpleName();
    protected Context mContext;
    protected View mView;
    protected BaseActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mActivity = (BaseActivity) getActivity();
        initInject();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        setupViews();
        return mView;
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(mContext, getResources().getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    protected abstract void initInject();

    protected abstract int getLayoutId();

    protected void setupViews() {
        if (!isNotNull(mView)) return;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermission(String[] permissions, @Nullable IBaseContract.IActionCallback callback) {
        mActivity.requestPermissions(permissions, callback);
    }

    @Override
    public boolean isPermissionGranted(String[] permissions) {
        return mActivity.isPermissionGranted(permissions);
    }
}
