package bkhn.et.hospitalbill.ui.newbill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
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

public class NewBillFragment extends BaseFragment implements INewBillContract.INewBillView {
    private static final String TAG = TAGG + NewBillFragment.class.getSimpleName();

    /* Views */
    Toolbar mToolbar;
    TextView mStaffName;
    EditText mNote;
    EditText mRecordInput;
    ImageView mQrBtn;
    TextView mRecordConfirmBtn;
    RecyclerView mProblemListView;
    RadioGroup mInsuranceGroup;
    RadioButton mInsuranceAvailable;
    RadioButton mInsuranceNotAvailable;
    EditText mInsuranceId;
    TextView mDiscountPercent;
    TextView mInsuranceCheckBtn;
    LinearLayout mInsuranceInfoLayout;
    TextView mMoneySum;
    /* Parameters */
    ProblemAdapter mAdapter;
    @Inject
    INewBillContract.INewBillPresenter<INewBillContract.INewBillView> mPresenter;
    UserModel mUserModel;
    InsuranceItem mInsuranceItem;
    private boolean mUseInsurance = false;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.newbill_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bill_done:
                requestCompleteBill();
                return true;
            case android.R.id.home:
                mActivity.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        mInsuranceId = mView.findViewById(R.id.insurance_id);
        mDiscountPercent = mView.findViewById(R.id.insurance_percent);
        mStaffName = mView.findViewById(R.id.newbill_staff_name);
        mNote = mView.findViewById(R.id.newbill_note);
        mRecordInput = mView.findViewById(R.id.newbill_record_id);
        mQrBtn = mView.findViewById(R.id.newbill_scan_btn);
        mRecordConfirmBtn = mView.findViewById(R.id.record_confirm_btn);
        mMoneySum = mView.findViewById(R.id.money_sum);
        mProblemListView = mView.findViewById(R.id.problem_list);
        mAdapter = new ProblemAdapter();
        mProblemListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mProblemListView.setAdapter(mAdapter);
        setupActionBar();
        setupInsuranceInfoLayout();
        mRecordConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLoadRecord();
            }
        });
    }

    private void setupInsuranceInfoLayout() {
        mInsuranceInfoLayout = mView.findViewById(R.id.insurance_info_layout);
        mInsuranceGroup = mView.findViewById(R.id.newbill_insurance_radio_group);
        mInsuranceAvailable = mView.findViewById(R.id.insurance_available);
        mInsuranceNotAvailable = mView.findViewById(R.id.insurance_not_available);
        mInsuranceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.insurance_available:
                        updateInsuranceVisible(true);
                        break;
                    case R.id.insurance_not_available:
                        updateInsuranceVisible(false);
                        break;
                }
            }
        });
        mInsuranceCheckBtn = mView.findViewById(R.id.insurance_check_btn);
        mInsuranceCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCheckInsurance();
            }
        });
    }

    private void updateInsuranceVisible(boolean visible) {
        if (visible)
            mInsuranceInfoLayout.setVisibility(View.VISIBLE);
        else {
            mInsuranceId.setText("");
            mDiscountPercent.setText("");
            mUseInsurance = false;
            mInsuranceInfoLayout.setVisibility(View.GONE);
        }
    }

    private void setupActionBar() {
        mToolbar = mView.findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_new_bill;
    }

    @Override
    public void requestCompleteBill() {
        if (!isNotNull(mUserModel))
            return;
        BillModel model = new BillModel();
        model.setUseInsurance(mUseInsurance);
        if (mUseInsurance)
            model.setInsuranceId(mInsuranceItem.getId());
        else model.setInsuranceId(null);
        model.setStaffId(mUserModel.getId());
        model.setNote(mNote.getText().toString());
        model.setPaid(true);
        model.setTime(Calendar.getInstance().getTime().getTime());
        model.setStaffName(mUserModel.getName());
        model.setRecordId(mRecordInput.getText().toString());
        mPresenter.completeBill(model);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestStaffInfo();
    }

    @Override
    public void onCreateBillResult(boolean success) {
        if (success) {
            showMessage(R.string.newbill_done);
            mActivity.finish();
        } else {
            showMessage(R.string.newbill_failed);
        }
    }

    @Override
    public void requestStaffInfo() {
        Logg.d(TAG, "requestStaffInfo");
        mPresenter.getStaffInfo();
    }

    @Override
    public void updateStaffInfo(UserModel model) {
        mUserModel = model;
        mStaffName.setText(model.getName());
    }

    @Override
    public void requestCheckInsurance() {
        String id = mInsuranceId.getText().toString();
        if (!id.isEmpty())
            mPresenter.checkInsuranceInfo(id);
    }

    @Override
    public void updateInsuranceInfo(InsuranceItem data, boolean canUse) {
        mInsuranceItem = data;
        mDiscountPercent.setText(data.getPercent() + "%");
        mUseInsurance = canUse;
        calculateMoneySum();
    }

    @Override
    public void requestLoadRecord() {
        String text = mRecordInput.getText().toString();
        if (text.isEmpty()) {
            showMessage(R.string.newbill_record_empty);
            return;
        }
        mRecordConfirmBtn.setEnabled(false);
        mPresenter.loadRecord(text);
    }

    @Override
    public void updateProblemList(List<ProblemModel> data) {
        mRecordConfirmBtn.setEnabled(true);
        if (isNotNull(data)) {
            mAdapter.replaceData(data);
        }
        calculateMoneySum();
    }

    private void calculateMoneySum() {
        List<ProblemModel> problemList = mAdapter.getData();
        double totalCost = 0;
        for (ProblemModel model : problemList) {
            totalCost += model.getCost() * model.getAmount();
        }
        if (mUseInsurance && isNotNull(mInsuranceItem)) {
            totalCost = totalCost * (mInsuranceItem.getPercent() / 100);
        }

        mMoneySum.setText(String.format("%.2f", totalCost));
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
