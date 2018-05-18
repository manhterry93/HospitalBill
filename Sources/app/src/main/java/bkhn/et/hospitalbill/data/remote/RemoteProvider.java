package bkhn.et.hospitalbill.data.remote;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;
import javax.inject.Singleton;

import bkhn.et.hospitalbill.utils.Logg;

import static bkhn.et.hospitalbill.utils.AppConstants.TAGG;

/**
 * Created by PL_itto on 5/10/2018.
 */
@Singleton
public class RemoteProvider implements IRemoteProvider {
    private static final String TAG = TAGG + RemoteProvider.class.getSimpleName();
    FirebaseAuth mFirebaseAuth;
    private static final String DEPARTMENT_TABLE = "Department";
    private static final String PROBLEM_TABLE = "Data";
    private static final String RECORD_TABLE = "Record";

    @Inject
    public RemoteProvider() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return mFirebaseAuth.getCurrentUser();
    }

    @Override
    public void getUserDetail(String userId, ValueEventListener listener) {
        Logg.d(TAG, "getUserDetail: " + userId);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users/" + userId);
        reference.addListenerForSingleValueEvent(listener);
    }

    @Override
    public void login(String email, String password, OnCompleteListener listener) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    @Override
    public void logout() {

    }

    @Override
    public void loadDepartmentList(ValueEventListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(DEPARTMENT_TABLE);
        reference.addListenerForSingleValueEvent(listener);
    }

    @Override
    public void loadProblemList(ValueEventListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(PROBLEM_TABLE);
        reference.addListenerForSingleValueEvent(listener);
    }

    @Override
    public void loadRecordList(ValueEventListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(RECORD_TABLE);
        reference.addListenerForSingleValueEvent(listener);
    }
}
