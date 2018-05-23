package bkhn.et.hospitalbill.ui.newrecord.problemsselect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.data.model.DepartmentModel;
import bkhn.et.hospitalbill.data.model.ProblemModel;
import bkhn.et.hospitalbill.utils.AppConstants;
import bkhn.et.hospitalbill.utils.Logg;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/22/2018.
 */

public class ProblemSelectDialog extends DialogFragment {
    private static final String TAG = TAGG + ProblemSelectDialog.class.getSimpleName();

    /* Views */
    private EditText mSearchInput;
    private ImageView mClearInput;
    private Spinner mDepartmentSpin;
    private Button mSearchBtn;
    private RecyclerView mResultView;

    /* Parameters */
    private ProblemListAdapter mResultAdapter;
    private DepartmentSpinAdapter mDepartmentAdapter;
    private Context mContext;
    private List<ProblemModel> mProblemModels;
    private List<DepartmentModel> mDepartmentModels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mProblemModels = (List<ProblemModel>) getArguments().getSerializable(AppConstants.Record.EXTRA_PROBLEM_LIST);
        mDepartmentModels = (List<DepartmentModel>) getArguments().getSerializable(AppConstants.Record.EXTRA_DEPARTMENT_LIST);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_search, container, false);
        setupViews(view);
        return view;
    }

    private void setupViews(View rootView) {
        mSearchInput = rootView.findViewById(R.id.search_input);
        mClearInput = rootView.findViewById(R.id.search_clear_input);
        mSearchBtn = rootView.findViewById(R.id.search_btn);
        mDepartmentSpin = rootView.findViewById(R.id.search_department);
        mResultView = rootView.findViewById(R.id.search_result_list);

        mResultAdapter = new ProblemListAdapter();
        mResultView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mResultView.setAdapter(mResultAdapter);
        if (isNotNull(mProblemModels))
            mResultAdapter.replaceData(mProblemModels);
        mDepartmentAdapter = new DepartmentSpinAdapter();
        mDepartmentSpin.setAdapter(mDepartmentAdapter);
        if (isNotNull(mDepartmentModels))
            mDepartmentAdapter.replaceData(mDepartmentModels);
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
                requestSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDepartmentSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                requestSearch();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void requestSearch() {
        String keyword = mSearchInput.getText().toString().toLowerCase();
        String departmentId = ((DepartmentModel) mDepartmentSpin.getSelectedItem()).getId();
        mResultAdapter.filter(keyword, departmentId);
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
                    if (problemModel.getName().toLowerCase().contains(keyword) || keyword.isEmpty()) {
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

        class ProblemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView mId;
            TextView mName;
            TextView mCost;
            FrameLayout mRootLayout;

            public ProblemHolder(View itemView) {
                super(itemView);
                mId = itemView.findViewById(R.id.problem_id);
                mName = itemView.findViewById(R.id.problem_name);
                mCost = itemView.findViewById(R.id.problem_cost);
                mRootLayout = itemView.findViewById(R.id.problem_bgr);
                mRootLayout.setOnClickListener(this);
            }

            void bindItem(ProblemModel model) {
                mId.setText(model.getId());
                mName.setText(model.getName());
                mCost.setText(model.getCostString());
            }

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.problem_bgr) {
                    ProblemModel model = getItemAt(getAdapterPosition());
                    dismiss(model);
                }
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

    private void dismiss(ProblemModel model) {
        Fragment fragment = getTargetFragment();
        if (isNotNull(fragment)) {
            Intent intent = new Intent();
            intent.putExtra(AppConstants.Record.EXTRA_PROBLEM_RETURN, model);
            fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        }
        dismiss();
    }
}
