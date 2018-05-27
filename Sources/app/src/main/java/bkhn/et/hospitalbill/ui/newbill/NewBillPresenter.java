package bkhn.et.hospitalbill.ui.newbill;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bkhn.et.hospitalbill.base.BasePresenter;
import bkhn.et.hospitalbill.data.IDataManager;
import bkhn.et.hospitalbill.data.model.BillModel;
import bkhn.et.hospitalbill.data.model.InsuranceItem;
import bkhn.et.hospitalbill.data.model.ProblemModel;
import bkhn.et.hospitalbill.data.model.UserModel;
import bkhn.et.hospitalbill.utils.Logg;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/27/2018.
 */

public class NewBillPresenter<V extends INewBillContract.INewBillView> extends BasePresenter<V> implements INewBillContract.INewBillPresenter<V> {
    private static final String TAG = TAGG + NewBillPresenter.class.getSimpleName();

    IDataManager mDataManager;

    @Inject
    public NewBillPresenter(IDataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void completeBill(BillModel model) {
        mDataManager.completeBill(model, new BillCompleteListener());
    }

    @Override
    public void getStaffInfo() {
        FirebaseUser user = mDataManager.getCurrentUser();
        if (isNotNull(user)) {
            String uid = user.getUid();
            mDataManager.getUserDetail(uid, new UserDetailListener());
        }

    }

    @Override
    public void checkInsuranceInfo(String id) {
        mDataManager.checkInsuranceInfo(id, new InsuranceInfoListener(id));
    }

    @Override
    public void loadRecord(String recordId) {
        Logg.d(TAG,"loadRecord: "+recordId);
        mDataManager.loadRecordDetail(recordId, new RecordDetailListener());
    }

    class RecordDetailListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (isNotNull(dataSnapshot)) {
                List<ProblemModel> data = new ArrayList<>();
                DataSnapshot problemList = dataSnapshot.child("data");
                for (DataSnapshot problem : problemList.getChildren()) {
                    ProblemModel model = problem.getValue(ProblemModel.class);
                    model.setId(problem.getKey());
                    data.add(model);
                }
                mView.updateProblemList(data);
            }else{

            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            mView.updateProblemList(null);
        }
    }

    class InsuranceInfoListener implements ValueEventListener {
        String mId;

        public InsuranceInfoListener(String id) {
            mId = id;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            InsuranceItem item = new InsuranceItem();
            if (isNotNull(dataSnapshot)) {
                item.setId(dataSnapshot.getKey());
                item.setPercent(dataSnapshot.getValue(Double.class));
                mView.updateInsuranceInfo(item, true);

            } else {
                item.setId(mId);
                item.setPercent(0);
                mView.updateInsuranceInfo(item, false);
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    class UserDetailListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Logg.d(TAG, "onDataChange: " + dataSnapshot.getKey());
            if (isNotNull(dataSnapshot)) {
                UserModel model = dataSnapshot.getValue(UserModel.class);
                if (isNotNull(model) && isNotNull(mView)) {
                    mView.updateStaffInfo(model);
                    Logg.d(TAG, "updateStaffInfo: " + model.getName());
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    class BillCompleteListener implements DatabaseReference.CompletionListener {

        @Override
        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
            mView.onCreateBillResult(true);
        }
    }
}
