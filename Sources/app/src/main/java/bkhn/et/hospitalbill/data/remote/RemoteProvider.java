package bkhn.et.hospitalbill.data.remote;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import bkhn.et.hospitalbill.data.model.ProblemModel;
import bkhn.et.hospitalbill.data.model.RecordModel;
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
        reference.addValueEventListener(listener);
    }

    @Override
    public void createRecord(RecordModel model, DatabaseReference.CompletionListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(RECORD_TABLE);
        String newKey = reference.push().getKey();
        model.setRecordId(newKey);
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("doctorId", model.getDoctorId());
        taskMap.put("doctorName", model.getDoctorName());
        taskMap.put("patientId", model.getPatientId());
        taskMap.put("patientName", model.getPatientName());
        taskMap.put("note", model.getNote());
        taskMap.put("time", model.getTime());
        //data
        reference = reference.child(newKey);
        Map<String, Object> taskData = new HashMap<>();
        DatabaseReference data = reference.child("data");
        for (ProblemModel problemItem : model.getProblemList()) {
            Map<String, Object> problem = new HashMap<>();
            problem.put("amount", problemItem.getAmount());
            problem.put("cost", problemItem.getCost());
            problem.put("name", problemItem.getName());
            problem.put("unit", problemItem.getUnit());
//            String id = problemItem.getId();
//            DatabaseReference problem = data.child(id);
//            problem.child("amount").setValue(problemItem.getAmount());
//            problem.child("cost").setValue(problemItem.getCost());
//            problem.child("name").setValue(problemItem.getName());
//            problem.child("unit").setValue(problemItem.getUnit());
            taskData.put(problemItem.getId(), problem);
        }

        taskMap.put("data", taskData);
        reference.updateChildren(taskMap,listener);

    }
}
