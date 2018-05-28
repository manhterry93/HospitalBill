package bkhn.et.hospitalbill.ui.recordscan;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.base.IBaseContract;
import bkhn.et.hospitalbill.utils.AppConstants;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;


/**
 * Created by PL_itto on 3/4/2018.
 */

public class ScannerFragment extends BaseFragment implements IScanContract.IScanView, ZBarScannerView.ResultHandler {
    private static final String TAG = TAGG + ScannerFragment.class.getSimpleName();
    /*Views */
    Toolbar mToolbar;
    FrameLayout mScannerFrame;

    /* Parameters */
    ZBarScannerView mScannerView;
    String permission[] = {"android.permission.CAMERA"};
    private boolean mGrantedPermisison = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void checkPermission() {
        mGrantedPermisison = isPermissionGranted(permission);
        if (!mGrantedPermisison) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermission(permission, new IBaseContract.IActionCallback() {
                    @Override
                    public void onSuccess() {
                        mGrantedPermisison = true;
                        onScanResume();
                    }

                    @Override
                    public void onFailed() {

                    }
                });
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkPermission();
    }

    @Override
    protected void initInject() {

    }

    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.frag_scanner;
    }


    @Override
    protected void setupViews() {
        setupActionBar();
        mScannerFrame = mView.findViewById(R.id.scanner_frame);
        mScannerView = new ZBarScannerView(mContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mScannerView.setLayoutParams(params);
        mScannerFrame.addView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        onScanResume();

    }

    void onScanResume() {
        if (mGrantedPermisison) {
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
//        Toast.makeText(getActivity(), "Contents = " + result.getContents() +
//                ", Format = " + result.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();

        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        String content = result.getContents();
        Intent intent = new Intent();
        intent.putExtra(AppConstants.Scanner.CODE_RESULT, result.getContents());
        mActivity.setResult(Activity.RESULT_OK, intent);
        mActivity.finish();

//

    }

    @Override
    public void onPause() {
        super.onPause();
        if (isNotNull(mScannerView))
            mScannerView.stopCamera();
    }

    private void setupActionBar() {
        mToolbar = mView.findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mActivity.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
