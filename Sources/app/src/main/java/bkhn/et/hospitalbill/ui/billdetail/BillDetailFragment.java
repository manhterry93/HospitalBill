package bkhn.et.hospitalbill.ui.billdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.data.model.BillModel;
import bkhn.et.hospitalbill.data.model.ProblemModel;
import bkhn.et.hospitalbill.utils.AppConstants;
import bkhn.et.hospitalbill.utils.Logg;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/29/2018.
 */

public class BillDetailFragment extends BaseFragment implements IBillDetailContract.IBillDetailView {
    private static final String TAG = TAGG + BillDetailFragment.class.getSimpleName();

    /* Views */
    Toolbar mToolbar;
    TextView mStaffName;
    TextView mNote;
    RadioGroup mInsuranceGroup;
    RadioButton mInsuranceAvailable, mInsuranceNotAvailable;
    EditText mInsuranceId;
    TextView mInsuranceDiscount;
    EditText mRecordId;
    RecyclerView mProblemListView;
    TextView mMoneySum;
    LinearLayout mInsuranceLayout;
    /* Parameters */
    private BillModel mBillData = null;
    private ProblemAdapter mAdapter;
    @Inject
    IBillDetailContract.IBillDetailPresenter<IBillDetailContract.IBillDetailView> mPresenter;

    @Override
    public void requestLoadRecord() {
        if (isNotNull(mBillData))
            mPresenter.loadRecordData(mBillData.getRecordId());
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        mStaffName = mView.findViewById(R.id.bill_staff_name);
        mNote = mView.findViewById(R.id.bill_note);
        mInsuranceGroup = mView.findViewById(R.id.bill_insurance_radio_group);
        mInsuranceAvailable = mView.findViewById(R.id.insurance_available);
        mInsuranceNotAvailable = mView.findViewById(R.id.insurance_not_available);
        mInsuranceId = mView.findViewById(R.id.insurance_id);
        mInsuranceDiscount = mView.findViewById(R.id.insurance_percent);
        mRecordId = mView.findViewById(R.id.bill_record_id);
        mProblemListView = mView.findViewById(R.id.problem_list);
        mMoneySum = mView.findViewById(R.id.money_sum);
        mInsuranceLayout = mView.findViewById(R.id.insurance_info_layout);
        setupProblemList();
        setupActionBar();
        mBillData = (BillModel) mActivity.getIntent().getSerializableExtra(AppConstants.BillDetail.EXTRA_BILL_DATA);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isNotNull(mBillData)) {
            updateViews();
            requestLoadRecord();
        }
    }

    private void updateViews() {
        if (isNotNull(mBillData)) {
            mStaffName.setText(mBillData.getStaffName());
            mNote.setText(mBillData.getNote());
            if (mBillData.isUseInsurance()) {
                mInsuranceGroup.check(R.id.insurance_available);
                mInsuranceLayout.setVisibility(View.VISIBLE);
                mInsuranceId.setText(mBillData.getInsuranceId());
                mInsuranceDiscount.setText(String.valueOf(mBillData.getInsuranceDiscount())+"%");
            } else {
                mInsuranceGroup.check(R.id.insurance_not_available);
                mInsuranceLayout.setVisibility(View.GONE);
            }
            mRecordId.setText(mBillData.getRecordId());
            mMoneySum.setText(String.valueOf(mBillData.getTotalCost()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mActivity.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar() {
        mToolbar = mView.findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupProblemList() {
        mAdapter = new ProblemAdapter();
        mProblemListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mProblemListView.setAdapter(mAdapter);
    }

    @Override
    public void updateRecordData(List<ProblemModel> data) {
        Logg.d(TAG,"update Record Data: "+data.size());
        mAdapter.replaceData(data);
    }

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
    protected int getLayoutId() {
        return R.layout.frag_bill_detail;
    }

    class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.ProblemHolder> {
        LayoutInflater mInflater;
        List<ProblemModel> mProblemList;

        public ProblemAdapter() {
            mInflater = LayoutInflater.from(getContext());
            mProblemList = new ArrayList<>();
        }

        List<ProblemModel> getData() {
            return mProblemList;
        }

        @Override
        public ProblemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.newbill_problem_item, parent, false);
            return new ProblemHolder(view);
        }

        void replaceData(List<ProblemModel> data) {
            if (isNotNull(data)) {
                mProblemList.clear();
                mProblemList.addAll(data);
                notifyDataSetChanged();
            }
        }

        @Override
        public void onBindViewHolder(ProblemHolder holder, int position) {
            holder.bind(getItemAt(position));
        }

        ProblemModel getItemAt(int pos) {
            return mProblemList.get(pos);
        }

        @Override
        public int getItemCount() {
            return mProblemList.size();
        }

        class ProblemHolder extends RecyclerView.ViewHolder {
            TextView mId, mName, mCount, mCost;

            public ProblemHolder(View itemView) {
                super(itemView);
                mId = itemView.findViewById(R.id.problem_id);
                mName = itemView.findViewById(R.id.problem_name);
                mCost = itemView.findViewById(R.id.problem_price);
                mCount = itemView.findViewById(R.id.problem_count);
            }

            void bind(ProblemModel model) {
                mId.setText(model.getId());
                mName.setText(model.getName());
                mCount.setText(String.valueOf(model.getAmount()));
                mCost.setText(model.getCostString());
            }
        }
    }
}

