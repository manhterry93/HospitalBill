package bkhn.et.hospitalbill.ui.billdetail;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bkhn.et.hospitalbill.base.BasePresenter;
import bkhn.et.hospitalbill.data.IDataManager;
import bkhn.et.hospitalbill.data.model.ProblemModel;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/29/2018.
 */

public class BillDetailPresenter<V extends IBillDetailContract.IBillDetailView> extends BasePresenter<V> implements IBillDetailContract.IBillDetailPresenter<V> {
    private static final String TAG = TAGG + BillDetailPresenter.class.getSimpleName();

    IDataManager mDataManager;

    @Inject
    public BillDetailPresenter(IDataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void loadRecordData(String recordId) {
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
                mView.updateRecordData(data);
            } else {
                mView.updateRecordData(null);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            mView.updateRecordData(null);
        }
    }
}
