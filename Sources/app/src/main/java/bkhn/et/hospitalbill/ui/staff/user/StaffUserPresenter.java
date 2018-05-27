package bkhn.et.hospitalbill.ui.staff.user;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import bkhn.et.hospitalbill.base.BasePresenter;
import bkhn.et.hospitalbill.data.IDataManager;
import bkhn.et.hospitalbill.data.model.UserModel;
import bkhn.et.hospitalbill.ui.staff.IStaffContract;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/27/2018.
 */

public class StaffUserPresenter<V extends IStaffContract.IStaffUserView> extends BasePresenter<V> implements IStaffContract.IStaffUserPresenter<V> {
    private static final String TAG = TAGG + StaffUserPresenter.class.getSimpleName();
    IDataManager mDataManager;

    @Inject
    public StaffUserPresenter(IDataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getUserDetail() {
        FirebaseUser user = mDataManager.getCurrentUser();
        if (isNotNull(user)) {
            String uid = user.getUid();
            mDataManager.getUserDetail(uid, new UserDetailListener());
        }
    }

    class UserDetailListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (isNotNull(dataSnapshot)) {
                UserModel model = dataSnapshot.getValue(UserModel.class);
                if (isNotNull(model) && isNotNull(mView)) {
                    mView.updateUserDetail(model);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
