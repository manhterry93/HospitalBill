package bkhn.et.hospitalbill.ui.billdetail;

import java.util.List;

import bkhn.et.hospitalbill.base.IBaseContract;
import bkhn.et.hospitalbill.data.model.ProblemModel;

/**
 * Created by PL_itto on 5/29/2018.
 */

public interface IBillDetailContract {
    interface IBillDetailView extends IBaseContract.IBaseView {
        void requestLoadRecord();

        void updateRecordData(List<ProblemModel> data);
    }

    interface IBillDetailPresenter<V extends IBillDetailView> extends IBaseContract.IBasePresenter<V> {
        void loadRecordData(String recordId);
    }
}
