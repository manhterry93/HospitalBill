package bkhn.et.hospitalbill.ui.splash;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import bkhn.et.hospitalbill.base.BasePresenter;
import bkhn.et.hospitalbill.data.DataManager;
import bkhn.et.hospitalbill.data.IDataManager;
import bkhn.et.hospitalbill.data.model.UserModel;
import bkhn.et.hospitalbill.utils.Logg;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/10/2018.
 */

public class SplashPresenter<V extends ISplashContract.ISplashView> extends BasePresenter<V> implements ISplashContract.ISplashPresenter<V> {
    private static final String TAG = TAGG + SplashPresenter.class.getSimpleName();

    IDataManager mDataManager;

    @Inject
    public SplashPresenter(IDataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void checkLogin() {
        FirebaseUser user = mDataManager.getCurrentUser();
        if (isNotNull(user)) {
            String uid = user.getUid();
            mDataManager.getUserDetail(uid, new UserDetailListener());
        } else {
            Logg.d(TAG,"currentUser is null");
            mView.openLoginActivity();
        }
    }

    class UserDetailListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (isNotNull(dataSnapshot)) {
                UserModel model = dataSnapshot.getValue(UserModel.class);
                if (isNotNull(model)) {
                    mView.openMainActivity(model);
                }
            } else {
                mDataManager.logout();
                mView.openLoginActivity();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
