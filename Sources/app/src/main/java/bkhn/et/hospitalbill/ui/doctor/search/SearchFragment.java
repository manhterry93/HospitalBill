package bkhn.et.hospitalbill.ui.doctor.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.data.model.DepartmentModel;
import bkhn.et.hospitalbill.data.model.ProblemModel;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract.IIllnessSearchPresenter;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract.IIllnessSearchView;
import bkhn.et.hospitalbill.utils.Logg;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/8/2018.
 */

public class SearchFragment extends BaseFragment implements IIllnessSearchView {
    private static final String TAG = TAGG + SearchFragment.class.getSimpleName();

    /* Views */
    private EditText mSearchInput;
    private ImageView mClearInput;
    private Spinner mDepartmentSpin;
    private Button mSearchBtn;
    private RecyclerView mResultView;


    /* Parameters */
    private ProblemListAdapter mResultAdapter;
    private DepartmentSpinAdapter mDepartmentAdapter;
    @Inject
    IIllnessSearchPresenter<IIllnessSearchView> mPresenter;

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
        return R.layout.frag_search;
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        mSearchInput = mView.findViewById(R.id.search_input);
        mClearInput = mView.findViewById(R.id.search_clear_input);
        mSearchBtn = mView.findViewById(R.id.search_btn);
        mDepartmentSpin = mView.findViewById(R.id.search_department);
        mResultView = mView.findViewById(R.id.search_result_list);

        mResultAdapter = new ProblemListAdapter();
        mResultView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mResultView.setAdapter(mResultAdapter);

        mDepartmentAdapter = new DepartmentSpinAdapter();
        mDepartmentSpin.setAdapter(mDepartmentAdapter);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSearch();
            }
        });

        mClearInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchInput.setText("");
            }
        });

        mSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    mClearInput.setVisibility(View.VISIBLE);
                else
                    mClearInput.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void toggleSearchMode() {

    }

    @Override
    public void requestSearch() {
        String keyword = mSearchInput.getText().toString();
        String departmentId = ((DepartmentModel) mDepartmentSpin.getSelectedItem()).getId();
        mResultAdapter.filter(keyword, departmentId);
    }

    @Override
    public void requestLoadDepartment() {

    }

    @Override
    public void updateDepartments(List<DepartmentModel> data) {
        if (isNotNull(data)) {
            mDepartmentAdapter.replaceData(data);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.loadDepartments();
        mPresenter.loadProblemList();
    }

    @Override
    public void updateProblemList(@NonNull List<ProblemModel> data) {
        mResultAdapter.replaceData(data);
        requestSearch();
    }

    class ProblemListAdapter extends RecyclerView.Adapter<ProblemListAdapter.ProblemHolder> {
        LayoutInflater mInflater;
        List<ProblemModel> mProblemList;
        List<ProblemModel> mOriginList;

        public ProblemListAdapter() {
            mInflater = LayoutInflater.from(mContext);
            mOriginList = new ArrayList<>();
            mProblemList = new ArrayList<>();
        }

        public void replaceData(List<ProblemModel> data) {
            if (isNotNull(data)) {
                mOriginList.clear();
                mOriginList.addAll(data);
            }
        }

        void filter(String keyword, String departmentId) {
            mProblemList.clear();
            for (ProblemModel problemModel : mOriginList) {
                if (problemModel.getDepartmentId().equals(departmentId) || departmentId.equals("-1")) {
                    if (problemModel.getName().contains(keyword) || keyword.isEmpty()) {
                        mProblemList.add(problemModel);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public ProblemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.problem_item, parent, false);
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

        class ProblemHolder extends RecyclerView.ViewHolder {
            TextView mId;
            TextView mName;
            TextView mCost;

            public ProblemHolder(View itemView) {
                super(itemView);
                mId = itemView.findViewById(R.id.problem_id);
                mName = itemView.findViewById(R.id.problem_name);
                mCost = itemView.findViewById(R.id.problem_cost);
            }

            void bindItem(ProblemModel model) {
                mId.setText(model.getId());
                mName.setText(model.getName());
                mCost.setText(model.getCostString());
            }
        }
    }

    class DepartmentSpinAdapter extends BaseAdapter {
        List<DepartmentModel> mDepartmentList;
        LayoutInflater mInflater;

        public DepartmentSpinAdapter() {
            mDepartmentList = new ArrayList<>();
            mInflater = LayoutInflater.from(mContext);
            mDepartmentList.add(new DepartmentModel(mContext));
        }

        void replaceData(List<DepartmentModel> data) {
            Logg.d(TAG, "update Department: " + data.size());
            mDepartmentList.clear();
            mDepartmentList.add(new DepartmentModel(mContext));
            mDepartmentList.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mDepartmentList.size();
        }

        @Override
        public DepartmentModel getItem(int position) {
            return mDepartmentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.department_spin_item, null);
                holder = new ItemHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ItemHolder) convertView.getTag();
            }
            holder.mTitle.setText(getItem(position).getName());
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return super.getDropDownView(position, convertView, parent);
        }

        class ItemHolder {
            TextView mTitle;

            public ItemHolder(View view) {
                mTitle = view.findViewById(R.id.title);
            }

        }

    }
}
