package bkhn.et.hospitalbill.ui.doctor.record;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import javax.inject.Inject;

import bkhn.et.hospitalbill.R;
import bkhn.et.hospitalbill.base.BaseFragment;
import bkhn.et.hospitalbill.data.model.RecordModel;
import bkhn.et.hospitalbill.ui.doctor.DoctorContract;
import bkhn.et.hospitalbill.ui.newrecord.NewRecordActivity;
import bkhn.et.hospitalbill.ui.recorddetail.RecordDetailActivity;
import bkhn.et.hospitalbill.utils.AppConstants;
import bkhn.et.hospitalbill.utils.AppConstants.Record;
import bkhn.et.hospitalbill.utils.Logg;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;
import static bkhn.et.hospitalbill.utils.AppUtils.isNotNull;

/**
 * Created by PL_itto on 5/8/2018.
 */

public class RecordFragment extends BaseFragment implements DoctorContract.IRecordView {
    private static final String TAG = TAGG + RecordFragment.class.getSimpleName();

    /* Views */
    private Spinner mTypeSpin;
    private EditText mFilterEdit;
    private Button mFilterBtn;
    private RecyclerView mRecordListView;
    private ImageView mClearInput;
    private FloatingActionButton mNewRecordBtn;
    /* Parameters */
    private RecordAdapter mAdapter;
    @Inject
    DoctorContract.IRecordPresenter<DoctorContract.IRecordView> mPresenter;

    @Override
    protected void setupViews() {
        super.setupViews();
        mTypeSpin = mView.findViewById(R.id.record_filter_spin);
        mFilterEdit = mView.findViewById(R.id.record_filter_input);
        mFilterBtn = mView.findViewById(R.id.record_filter_btn);
        mRecordListView = mView.findViewById(R.id.record_result_list);
        mClearInput = mView.findViewById(R.id.search_clear_input);
        mNewRecordBtn = mView.findViewById(R.id.record_new);
        mAdapter = new RecordAdapter();
        mRecordListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecordListView.setAdapter(mAdapter);
        mFilterEdit.addTextChangedListener(new TextWatcher() {
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
        mClearInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterEdit.setText("");
            }
        });
        mNewRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewRecord();
            }
        });
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
        return R.layout.frag_record;
    }

    @Override
    public void requestLoadRecord() {
        mPresenter.loadRecords();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestLoadRecord();
    }

    private void requestSearch() {
        String keyword = mFilterEdit.getText().toString().toLowerCase();
        Record.TYPE type = Record.TYPE.setValue(mTypeSpin.getSelectedItemPosition());
        Logg.d(TAG, "search: " + keyword + " " + type);
        mAdapter.filter(keyword, type);
    }

    @Override
    public void openRecordDetail(RecordModel model) {
        Intent intent = new Intent(mContext, RecordDetailActivity.class);
        intent.putExtra(Record.EXTRA_RECORD, model);
        startActivity(intent);
    }

    @Override
    public void openNewRecord() {
        Intent intent = new Intent(mContext, NewRecordActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateRecordList(List<RecordModel> data) {
        mAdapter.replaceData(data);
    }

    class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordHolder> {
        List<RecordModel> mRecordList;
        List<RecordModel> mOriginList;
        LayoutInflater mInflater;

        public RecordAdapter() {
            mInflater = LayoutInflater.from(mContext);
            mRecordList = new ArrayList<>();
            mOriginList = new ArrayList<>();
        }

        public void replaceData(List<RecordModel> data) {
            Logg.d(TAG, "replaceData: " + data.size());
            if (isNotNull(data)) {
                mOriginList.clear();
                mOriginList.addAll(data);
                requestSearch();
            }
        }

        public void filter(String keyword, Record.TYPE type) {
            mRecordList.clear();
            for (RecordModel model : mOriginList) {
                String id = model.getRecordId().toLowerCase();
                String doctor = model.getDoctorId().toLowerCase();
                String note = model.getNote().toLowerCase();
                if (isNotNull(keyword)) {
                    switch (type) {
                        case ALL:
                            if (id.contains(keyword) || doctor.contains(keyword) || note.contains(keyword))
                                mRecordList.add(model);
                            break;
                        case ID:
                            if (id.contains(keyword))
                                mRecordList.add(model);
                            break;
                        case DOCTOR:
                            if (doctor.contains(keyword))
                                mRecordList.add(model);
                            break;
                    }
                } else {
                    mRecordList.add(model);
                }
            }
            Logg.d(TAG, "Filter : " + mRecordList.size());
            notifyDataSetChanged();
        }

        @Override
        public RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.record_item, parent, false);
            return new RecordHolder(view);
        }

        @Override
        public void onBindViewHolder(RecordHolder holder, int position) {
            holder.bindITem(getItemAt(position));
        }

        RecordModel getItemAt(int pos) {
            return mRecordList.get(pos);
        }

        @Override
        public int getItemCount() {
            return mRecordList.size();
        }

        class RecordHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView mId, mDoctor, mTime, mNote;
            LinearLayout mRootLayout;

            public RecordHolder(View itemView) {
                super(itemView);
                mRootLayout = itemView.findViewById(R.id.record_root_view);
                mId = itemView.findViewById(R.id.record_id);
                mDoctor = itemView.findViewById(R.id.record_doctor);
                mTime = itemView.findViewById(R.id.record_time);
                mNote = itemView.findViewById(R.id.record_note);
                mRootLayout.setOnClickListener(this);
            }

            void bindITem(RecordModel model) {
                mId.setText(model.getRecordId());
                mDoctor.setText(model.getDoctorId());
                mTime.setText(model.getTimeLiteString());
                mNote.setText(model.getNote());
            }

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.record_root_view) {
                    openRecordDetail(getItemAt(getAdapterPosition()));
                }
            }
        }

    }


}
