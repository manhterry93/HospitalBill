package bkhn.et.hospitalbill.ui.doctor.search;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bkhn.et.hospitalbill.base.BasePresenter;
import bkhn.et.hospitalbill.data.IDataManager;
import bkhn.et.hospitalbill.data.model.DepartmentModel;
import bkhn.et.hospitalbill.data.model.ProblemModel;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract.IIllnessSearchPresenter;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract.IIllnessSearchView;
import bkhn.et.hospitalbill.utils.Logg;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/15/2018.
 */

public class SearchPresenter<V extends IIllnessSearchView> extends BasePresenter<V>
        implements IIllnessSearchPresenter<V> {
    private static final String TAG = TAGG + SearchPresenter.class.getSimpleName();

    IDataManager mDataManager;

    @Inject
    public SearchPresenter(IDataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void loadProblemList() {
        mDataManager.loadProblemList(new ProblemListener());
    }

    @Override
    public void loadDepartments() {
        mDataManager.loadDepartmentList(new DepartmentListener());
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
