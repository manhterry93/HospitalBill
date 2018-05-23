package bkhn.et.hospitalbill.ui.recorddetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.data.model.ProblemModel;
import bkhn.et.hospitalbill.data.model.RecordModel;
import bkhn.et.hospitalbill.ui.newrecord.NewRecordFragment;
import bkhn.et.hospitalbill.utils.AppConstants;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/23/2018.
 */

public class RecordDetailFragment extends BaseFragment {
    private static final String TAG = TAGG + RecordDetailFragment.class.getSimpleName();

    /* Views */
    private Toolbar mToolbar;
    private TextView mDoctorId, mDoctorName;
    private EditText mPatientId, mPatientName;
    private RecyclerView mProblemView;
    private ImageView mAddProblemBtn;
    private FloatingActionButton mDoneBtn;
    private EditText mNoteEdit;

    /* Parameters */
    private RecordModel mRecordModel;
    private ProblemListAdapter mProblemListAdapter;

    @Override
    protected void initInject() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRecordModel = (RecordModel) getActivity().getIntent().getSerializableExtra(AppConstants.Record.EXTRA_RECORD);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_new_record;
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        setupActionBar();
        mDoctorId = mView.findViewById(R.id.new_record_doctor_id);
        mDoctorName = mView.findViewById(R.id.new_record_doctor_name);
        mPatientId = mView.findViewById(R.id.new_record_patient_id);
        mPatientName = mView.findViewById(R.id.new_record_patient_name);
        mProblemView = mView.findViewById(R.id.new_record_problem_list);
        mAddProblemBtn = mView.findViewById(R.id.header_add_problem);
        mNoteEdit = mView.findViewById(R.id.new_record_note);
        mProblemView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mProblemListAdapter = new ProblemListAdapter();
        mProblemView.setAdapter(mProblemListAdapter);
        mDoneBtn = mView.findViewById(R.id.fab_done);
        mDoneBtn.setVisibility(View.GONE);
        mAddProblemBtn.setVisibility(View.GONE);
        mPatientName.setEnabled(false);
        mPatientId.setEnabled(false);
    }

    private void setupActionBar() {
        mToolbar = mView.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.record_detail_title);
        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDoctorId.setText(mRecordModel.getDoctorId());
        mDoctorName.setText(mRecordModel.getDoctorName());
        mPatientId.setText(mRecordModel.getPatientId());
        mPatientName.setText(mRecordModel.getPatientName());
        mNoteEdit.setText(mRecordModel.getNote());
        if (isNotNull(mRecordModel))
            mProblemListAdapter.replaceData(mRecordModel.getProblemList());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            mActivity.finish();
        return super.onOptionsItemSelected(item);
    }

    class ProblemListAdapter extends RecyclerView.Adapter<ProblemListAdapter.ProblemHolder> {
        LayoutInflater mInflater;
        List<ProblemModel> mProblemList;

        public ProblemListAdapter() {
            mInflater = LayoutInflater.from(mContext);
            mProblemList = new ArrayList<>();
        }

        List<ProblemModel> getData() {
            return mProblemList;
        }

        public void replaceData(List<ProblemModel> data) {
            if (isNotNull(data)) {
                mProblemList.clear();
                mProblemList.addAll(data);
                notifyDataSetChanged();
            }
        }

        public void addProblem(ProblemModel data) {
            for (int i = 0; i < getItemCount(); i++) {
                ProblemModel model = getItemAt(i);
                if (model.getId() == data.getId()) return;
            }
            mProblemList.add(data);
            notifyDataSetChanged();
        }

        @Override
        public ProblemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.problem_record_item, parent, false);
            return new ProblemHolder(view);
        }

        @Override
        public void onBindViewHolder(ProblemHolder holder, int position) {
            holder.bindItem(getItemAt(position));
        }

        ProblemModel getItemAt(int pos) {
            return mProblemList.get(pos);
        }

        @Override
        public int getItemCount() {
            return mProblemList.size();
        }

        class ProblemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView mId;
            TextView mName;
            TextView mAmount;
            ImageView mDecrease, mIncrease;

            public ProblemHolder(View itemView) {
                super(itemView);
                mId = itemView.findViewById(R.id.problem_id);
                mName = itemView.findViewById(R.id.problem_name);
                mAmount = itemView.findViewById(R.id.problem_amount);
                mDecrease = itemView.findViewById(R.id.problem_amount_decrease);
                mIncrease = itemView.findViewById(R.id.problem_amount_increase);
                mIncrease.setOnClickListener(this);
                mDecrease.setOnClickListener(this);
            }

            void bindItem(ProblemModel model) {
                mId.setText(model.getId());
                mName.setText(model.getName());
                updateProblemAmount(model);
            }

            void updateProblemAmount(ProblemModel model) {
                mAmount.setText(String.valueOf(model.getAmount()));
            }

            @Override
            public void onClick(View v) {
                ProblemModel model = getItemAt(getAdapterPosition());
                switch (v.getId()) {
                    case R.id.problem_amount_decrease:
                        if (model.getAmount() > 1) model.setAmount(model.getAmount() - 1);
                        break;
                    case R.id.problem_amount_increase:
                        model.setAmount(model.getAmount() + 1);
                        break;
                }
                updateProblemAmount(model);
            }
        }
    }
}
