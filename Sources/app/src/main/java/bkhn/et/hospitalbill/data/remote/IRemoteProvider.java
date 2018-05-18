package bkhn.et.hospitalbill.data.remote;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by PL_itto on 5/10/2018.
 */

public interface IRemoteProvider {
    FirebaseUser getCurrentUser();

    void getUserDetail(String userId, ValueEventListener listener);

    void login(String email, String password, OnCompleteListener listener);

    void logout();

    void loadDepartmentList(ValueEventListener listener);

    void loadProblemList(ValueEventListener listener);

    void loadRecordList(ValueEventListener listener);
}
