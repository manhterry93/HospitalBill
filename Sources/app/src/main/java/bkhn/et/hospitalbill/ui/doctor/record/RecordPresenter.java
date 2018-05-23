package bkhn.et.hospitalbill.ui.doctor.record;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bkhn.et.hospitalbill.base.BasePresenter;
import bkhn.et.hospitalbill.data.IDataManager;
import bkhn.et.hospitalbill.data.model.ProblemModel;
import bkhn.et.hospitalbill.data.model.RecordModel;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract;
import bkhn.et.hospitalbill.utils.Logg;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/15/2018.
 */

public class RecordPresenter<V extends DoctorContract.IRecordView> extends BasePresenter<V> implements DoctorContract.IRecordPresenter<V> {
    private static final String TAG = TAGG + RecordPresenter.class.getSimpleName();
    IDataManager mDataManager;

    @Inject
    public RecordPresenter(IDataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void loadRecords() {
        mDataManager.loadRecordList(new RecordListener());
    }

    class RecordListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Logg.d(TAG,"recordSize: "+dataSnapshot.getChildrenCount());
            List<RecordModel> data = processRecordResponse(dataSnapshot);
            mView.updateRecordList(data);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    private static final List<RecordModel> processRecordResponse(DataSnapshot dataSnapshot) {
        List<RecordModel> result = new ArrayList<>();
        if (isNotNull(dataSnapshot))
            for (DataSnapshot record : dataSnapshot.getChildren()) {
                RecordModel item = record.getValue(RecordModel.class);
                item.setRecordId(record.getKey());

                //get ProblemList
                DataSnapshot problemResponse = record.child("data");
                item.setProblemList(retrieveProblemList(problemResponse));
                result.add(item);
                Logg.d(TAG,"data: "+item.getDoctorId()+" "+item.getDoctorName());
            }
        return result;
    }

    private static final List<ProblemModel> retrieveProblemList(DataSnapshot response) {
        List<ProblemModel> result = new ArrayList<>();
        if (isNotNull(response)) {
            for (DataSnapshot problem : response.getChildren()) {
                ProblemModel model = problem.getValue(ProblemModel.class);
                model.setId(problem.getKey());
                result.add(model);
            }
        }
        return result;
    }
}
