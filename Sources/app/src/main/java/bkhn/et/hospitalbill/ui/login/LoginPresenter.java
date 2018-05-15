package bkhn.et.hospitalbill.ui.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import bkhn.et.hospitalbill.base.BasePresenter;
import bkhn.et.hospitalbill.data.IDataManager;
import bkhn.et.hospitalbill.ui.login.ILoginContract.ILoginPresenter;
import bkhn.et.hospitalbill.ui.login.ILoginContract.ILoginView;
import bkhn.et.hospitalbill.utils.Logg;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/14/2018.
 */

public class LoginPresenter<V extends ILoginView> extends BasePresenter<V> implements ILoginPresenter<V> {
    private static final String TAG = TAGG + LoginPresenter.class.getSimpleName();
    IDataManager mDataManager;

    @Inject
    public LoginPresenter(IDataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void login(String email, String password) {
        mDataManager.login(email, password, new LoginListener());
    }

    @Override
    public void getUserDetail() {
        FirebaseUser user = mDataManager.getCurrentUser();
        if (isNotNull(user))
            mDataManager.getUserDetail(user.getUid(), new UserDetailListener(user.getUid()));
    }

    class LoginListener implements OnCompleteListener<AuthResult> {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            Logg.d(TAG, "oncomplete: " + task.isSuccessful());
            if (task.isSuccessful()) {
                getUserDetail();
            } else {
                mView.onLoginResult(false);
            }

        }
    }

    class UserDetailListener implements ValueEventListener {
        String userId;

        public UserDetailListener(String userId) {
            this.userId = userId;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (isNotNull(dataSnapshot)) {
                Logg.d(TAG, "userDetail: " + dataSnapshot.getValue().toString());
                long mode = (long) dataSnapshot.child("jobId").getValue();
                mView.onLoginResult(true);
                mView.openMainScreen((int) mode);
            } else {
                Logg.d(TAG,"userDetail null");
                mView.onLoginResult(false);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
