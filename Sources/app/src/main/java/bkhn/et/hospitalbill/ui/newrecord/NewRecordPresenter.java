package bkhn.et.hospitalbill.ui.newrecord;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bkhn.et.hospitalbill.base.BasePresenter;
import bkhn.et.hospitalbill.data.IDataManager;
import bkhn.et.hospitalbill.data.model.DepartmentModel;
import bkhn.et.hospitalbill.data.model.ProblemModel;
import bkhn.et.hospitalbill.data.model.RecordModel;
import bkhn.et.hospitalbill.data.model.UserModel;
import bkhn.et.hospitalbill.ui.newrecord.INewRecordContract.INewRecordPresenter;
import bkhn.et.hospitalbill.ui.newrecord.INewRecordContract.INewRecordView;
import bkhn.et.hospitalbill.utils.Logg;

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
        mDataManager.createRecord(model, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (isNotNull(databaseReference)) {
                    Logg.d(TAG, databaseReference.getKey().toString());
                    mView.onAddRecordResult(true, databaseReference.getKey());
                } else {
                    mView.onAddRecordResult(false, null);
                }

            }
        });
    }

    @Override
    public void loadProblemList() {
        mDataManager.loadProblemList(new ProblemListener());
    }

    @Override
    public void loadDepartmentList() {
        mDataManager.loadDepartmentList(new DepartmentListener());
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
                if (isNotNull(model) && isNotNull(mView)) {
                    mView.updateDoctorInfo(model);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    class DepartmentListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (isNotNull(dataSnapshot)) {
                Logg.d(TAG, "deparmentSize:" + dataSnapshot.getChildrenCount());
                List<DepartmentModel> data = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DepartmentModel model = new DepartmentModel();
                    model.setId(snapshot.getKey());
                    model.setName(snapshot.getValue(String.class));
                    data.add(model);
                }
                if (isNotNull(mView)) mView.updateDepartments(data);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    class ProblemListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (isNotNull(dataSnapshot)) {
                List<ProblemModel> modelList = new ArrayList<>();
                //List of DepartmentId
                for (DataSnapshot department : dataSnapshot.getChildren()) {
                    String departmentId = department.getKey();
                    Logg.d(TAG, "departmentID: " + departmentId);
                    for (DataSnapshot problem : department.getChildren()) {
                        ProblemModel model = problem.getValue(ProblemModel.class);
                        model.setId(problem.getKey());
                        model.setDepartmentId(departmentId);
                        modelList.add(model);
                    }
                }
                if (isNotNull(mView)) mView.updateProblemList(modelList);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
