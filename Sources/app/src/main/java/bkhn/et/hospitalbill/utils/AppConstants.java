package bkhn.et.hospitalbill.utils;

/**
 * Created by PL_itto on 5/2/2018.
 */

public class AppConstants {
    public static final String TAGG = "PL_itto";

    public static final class Job {
        public static final int TYPE_DOCTOR = 101;
        public static final int TYPE_STAFF = 102;
    }

    public static final class User {
        public static final String AVATAR = "avatar";
        public static final String BIRTH = "birth";
        public static final String DEPARTMENT_ID = "departmentId";
        public static final String DEPARTMENT_NAME = "departmentName";
        public static final String EMAIL = "email";
        public static final String NAME = "name";
        public static final String PHONE = "phone";
        public static final String JOB_ID = "jobId";
    }

    public static final class Doctor {
        public static final int TAB_COUNT = 4;
        public static final int TAB_USER = 0;
        public static final int TAB_SEARCH = 1;
        public static final int TAB_RECORD = 2;
        public static final int TAB_SETTING = 3;
    }

    public static final class Main {
        public static final String EXTRA_JOB_TYPE = "extra_job_type";
    }
}
