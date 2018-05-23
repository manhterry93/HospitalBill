package bkhn.et.hospitalbill.data;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;
import javax.inject.Singleton;

import bkhn.et.hospitalbill.data.model.RecordModel;
import bkhn.et.hospitalbill.data.remote.IRemoteProvider;

/**
 * Created by PL_itto on 5/10/2018.
 */
@Singleton
public class DataManager implements IDataManager {
    IRemoteProvider mRemoteProvider;

    @Inject
    public DataManager(IRemoteProvider remoteProvider) {
        mRemoteProvider = remoteProvider;
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return mRemoteProvider.getCurrentUser();
    }

    @Override
    public void getUserDetail(String userId, ValueEventListener listener) {
        mRemoteProvider.getUserDetail(userId, listener);
    }

    @Override
    public void login(String email, String password, OnCompleteListener listener) {
        mRemoteProvider.login(email, password, listener);
    }

    @Override
    public void logout() {

    }

    @Override
    public void loadDepartmentList(ValueEventListener listener) {
        mRemoteProvider.loadDepartmentList(listener);
    }

    @Override
    public void loadProblemList(ValueEventListener listener) {
        mRemoteProvider.loadProblemList(listener);
    }

    @Override
    public void loadRecordList(ValueEventListener listener) {
        mRemoteProvider.loadRecordList(listener);
    }

    @Override
    public void createRecord(RecordModel model, DatabaseReference.CompletionListener listener) {
        mRemoteProvider.createRecord(model,listener);
    }
}
