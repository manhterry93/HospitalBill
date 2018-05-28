package bkhn.et.hospitalbill.ui.newrecord;

import android.app.Activity;
import android.content.Intent;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.data.model.DepartmentModel;
import bkhn.et.hospitalbill.data.model.ProblemModel;
import bkhn.et.hospitalbill.data.model.RecordModel;
import bkhn.et.hospitalbill.data.model.UserModel;
import bkhn.et.hospitalbill.ui.newrecord.problemsselect.ProblemSelectDialog;
import bkhn.et.hospitalbill.utils.AppConstants;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/18/2018.
 */

public class NewRecordFragment extends BaseFragment implements INewRecordContract.INewRecordView {
    private static final String TAG = TAGG + NewRecordFragment.class.getSimpleName();
    private static final int SELECT_PROBLEM_REQUEST_CODE = 200;
    /* Views */
    private Toolbar mToolbar;
    private TextView mDoctorId, mDoctorName;
    private EditText mPatientId, mPatientName;
    private RecyclerView mProblemView;
    private ImageView mAddProblemBtn;
    private FloatingActionButton mDoneBtn;
    private EditText mNoteEdit;
    /* Parameters */
    private ProblemListAdapter mProblemListAdapter;
    private List<ProblemModel> mProblemList;
    private List<DepartmentModel> mDepartmentList;

    @Inject
    INewRecordContract.INewRecordPresenter<INewRecordContract.INewRecordView> mPresenter;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            mActivity.finish();
        return super.onOptionsItemSelected(item);
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
        mAddProblemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProblemSelect();
            }
        });
        mNoteEdit = mView.findViewById(R.id.new_record_note);
        // Problem List View
        mProblemView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mProblemListAdapter = new ProblemListAdapter();
        mProblemView.setAdapter(mProblemListAdapter);
        mDoneBtn = mView.findViewById(R.id.fab_done);
        mDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCompleteRecord();
            }
        });

    }

    private void setupActionBar() {
        mToolbar = mView.findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_new_record;
    }

    @Override
    public void addProblem(ProblemModel model) {
        if (isNotNull(model)) mProblemListAdapter.addProblem(model);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestDoctorInfo();
        mPresenter.loadDepartmentList();
        mPresenter.loadProblemList();
    }


    @Override
    public void openProblemSelect() {
        if (isNotNull(mProblemList) && mProblemList.size() > 0
                && isNotNull(mDepartmentList) && mDepartmentList.size() > 0) {
            ProblemSelectDialog dialog = new ProblemSelectDialog();
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstants.Record.EXTRA_PROBLEM_LIST, (Serializable) mProblemList);
            bundle.putSerializable(AppConstants.Record.EXTRA_DEPARTMENT_LIST, (Serializable) mDepartmentList);
            dialog.setTargetFragment(this, SELECT_PROBLEM_REQUEST_CODE);
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), null);
        }
    }

    @Override
    public void requestCompleteRecord() {
        if (mProblemListAdapter.getItemCount() > 0) {
            RecordModel model = new RecordModel();
            model.setTime(getCurrentTime());
            model.setDoctorId(mDoctorId.getText().toString());
            model.setDoctorName(mDoctorName.getText().toString());
            model.setPatientId(mPatientId.getText().toString());
            model.setPatientName(mPatientName.getText().toString());
            model.setProblemList(mProblemListAdapter.getData());
            model.setNote(mNoteEdit.getText().toString());
            mPresenter.completeRecord(model);
        }
    }

    @Override
    public void updateProblemList(List<ProblemModel> data) {
        if (isNotNull(data)) {
            mProblemList = data;
        }
    }

    @Override
    public void updateDepartments(List<DepartmentModel> data) {
        if (isNotNull(data)) {
            mDepartmentList = data;
        }
    }

    @Override
    public void requestDoctorInfo() {
        mPresenter.loadDoctorInfo();
    }

    @Override
    public void updateDoctorInfo(UserModel model) {
        mDoctorId.setText(model.getId());
        mDoctorName.setText(model.getName());
    }

    @Override
    public void onAddRecordResult(boolean success, String recordId) {
        if (success) {
            RecordQRDialog dialog = new RecordQRDialog(recordId);
            dialog.setTargetFragment(this, 0);
            dialog.show(getFragmentManager(), null);
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SELECT_PROBLEM_REQUEST_CODE:
                    if (isNotNull(data)) {
                        ProblemModel model = (ProblemModel) data.getSerializableExtra(AppConstants.Record.EXTRA_PROBLEM_RETURN);
                        if (isNotNull(model)) {
                            addProblem(model.clone());
                        }

                    }

                    break;
            }
        }
    }
}
