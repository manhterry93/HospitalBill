package bkhn.et.hospitalbill.ui.staff.bill;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.data.model.BillModel;
import bkhn.et.hospitalbill.ui.billdetail.BillDetailActivity;
import bkhn.et.hospitalbill.ui.newbill.NewBillActivity;
import bkhn.et.hospitalbill.ui.newbill.NewBillFragment;
import bkhn.et.hospitalbill.ui.staff.IStaffContract;
import bkhn.et.hospitalbill.utils.AppConstants;
import bkhn.et.hospitalbill.utils.Logg;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/24/2018.
 */

public class BillFragment extends BaseFragment implements IStaffContract.IBillView {
    private static final String TAG = TAGG + BillFragment.class.getSimpleName();

    /* VIews */
    EditText mSearchEdit;
    RecyclerView mBillListView;
    FloatingActionButton mNewBillBtn;
    /* Parameters */
    @Inject
    IStaffContract.IBillPresenter<IStaffContract.IBillView> mPresenter;
    private BillAdapter mAdapter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
        mPresenter.onAttach(this);
    }

    @Override
    public void onDestroy() {
        if (isNotNull(mPresenter))
            mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        mNewBillBtn = mView.findViewById(R.id.fab_new);
        mSearchEdit = mView.findViewById(R.id.bill_search);
        mBillListView = mView.findViewById(R.id.bill_list);
        mAdapter = new BillAdapter();
        mBillListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mBillListView.setAdapter(mAdapter);
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mNewBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNewBill();
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.frag_bill;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestLoadBill();
    }

    @Override
    public void requestLoadBill() {
        mPresenter.loadBillList();
    }

    @Override
    public void updateBillList(List<BillModel> data) {
        Logg.d(TAG, "updateBillList: " + data.size());
        mAdapter.replaceData(data);
    }

    @Override
    public void openBillDetail(BillModel model) {
        Intent intent = new Intent(mContext, BillDetailActivity.class);
        intent.putExtra(AppConstants.BillDetail.EXTRA_BILL_DATA, model);
        startActivity(intent);
    }

    void requestFilter() {
        String keyword = mSearchEdit.getText().toString();
        filter(keyword);
    }

    @Override
    public void filter(String keyword) {
        if (isNotNull(mAdapter)) mAdapter.filter(keyword);
    }

    @Override
    public void requestNewBill() {
        Intent intent = new Intent(mContext, NewBillActivity.class);
        startActivity(intent);
    }

    class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillHolder> {
        List<BillModel> mDataList;
        List<BillModel> mDataOrigin;
        LayoutInflater mInflater;

        public BillAdapter() {
            mDataList = new ArrayList<>();
            mDataOrigin = new ArrayList<>();
            mInflater = LayoutInflater.from(getContext());
        }

        void replaceData(List<BillModel> models) {
            if (isNotNull(models)) {
                mDataOrigin.clear();
                mDataOrigin.addAll(models);
                requestFilter();
            }
        }

        void filter(String keyword) {
            keyword = keyword.toLowerCase();
            mDataList.clear();
            for (BillModel model : mDataOrigin) {
                if (model.getId().toLowerCase().contains(keyword)
                        || model.getNote().toLowerCase().contains(keyword)
                        || model.getStaffId().toLowerCase().contains(keyword)
                        ) {
                    mDataList.add(model);
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public BillHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.bill_item, parent, false);
            return new BillHolder(view);
        }

        @Override
        public void onBindViewHolder(BillHolder holder, int position) {
            holder.bind(getItemAt(position));
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        BillModel getItemAt(int pos) {
            return mDataList.get(pos);
        }

        class BillHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            LinearLayout mRootView;
            TextView mId;
            TextView mTime;
            TextView mStaff;
            TextView mNote;

            void bind(BillModel model) {
                mId.setText(model.getId());
                mTime.setText(model.getTimeLiteString());
                mStaff.setText(model.getStaffId());
                mNote.setText(model.getNote());
            }

            public BillHolder(View itemView) {
                super(itemView);
                mRootView = itemView.findViewById(R.id.bill_root_view);
                mId = itemView.findViewById(R.id.bill_id);
                mTime = itemView.findViewById(R.id.bill_time);
                mStaff = itemView.findViewById(R.id.bill_staff);
                mNote = itemView.findViewById(R.id.bill_note);
                mRootView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.bill_root_view) {
                    openBillDetail(getItemAt(getAdapterPosition()));
                }
            }
        }
    }
}
