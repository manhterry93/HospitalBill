package bkhn.et.hospitalbill.ui.staff.bill;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bkhn.et.hospitalbill.base.BasePresenter;
import bkhn.et.hospitalbill.data.IDataManager;
import bkhn.et.hospitalbill.data.model.BillModel;
import bkhn.et.hospitalbill.ui.staff.IStaffContract;

import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/27/2018.
 */

public class BillPresenter<V extends IStaffContract.IBillView> extends BasePresenter<V> implements IStaffContract.IBillPresenter<V> {

    IDataManager mDataManager;

    @Inject
    public BillPresenter(IDataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void loadBillList() {
        mDataManager.loadBillList(new BillListener());
    }

    class BillListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (isNotNull(dataSnapshot)) {
                List<BillModel> billList = new ArrayList<>();
                for (DataSnapshot billResponse : dataSnapshot.getChildren()) {
                    BillModel model = billResponse.getValue(BillModel.class);
                    model.setId(billResponse.getKey());
                    billList.add(model);
                }

                mView.updateBillList(billList);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
