package bkhn.et.hospitalbill.ui.newrecord;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import bkhn.et.hospitalbill.base.BasePresenter;
import bkhn.et.hospitalbill.data.IDataManager;
import bkhn.et.hospitalbill.data.model.RecordModel;
import bkhn.et.hospitalbill.data.model.UserModel;
import bkhn.et.hospitalbill.ui.newrecord.INewRecordContract.INewRecordPresenter;
import bkhn.et.hospitalbill.ui.newrecord.INewRecordContract.INewRecordView;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/18/2018.
 */

public class NewRecordPresenter<V extends INewRecordView> extends BasePresenter<V>
        implements INewRecordPresenter<V> {
    private static final String TAG = TAGG + NewRecordPresenter.class.getSimpleName();
    IDataManager mDataManager;

    @Inject
    public NewRecordPresenter(IDataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void completeRecord(RecordModel model) {

    }

    @Override
    public void loadProblemList() {

    }

    @Override
    public void loadDoctorInfo() {
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
                if (isNotNull(model)&& isNotNull(mView)) {
                    mView.updateDoctorInfo(model);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
