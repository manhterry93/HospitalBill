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

    public static final class Record {
        public enum TYPE {
            ALL(-1), ID(0), DOCTOR(1);
            private int val;

            TYPE(int value) {
                this.val = value;
            }

            public int getVal() {
                return val;
            }

            static public TYPE setValue(int val) {
                switch (val) {
                    case -1:
                        return TYPE.ALL;
                    case 0:
                        return TYPE.ID;
                    case 1:
                        return TYPE.DOCTOR;
                }
                return ALL;
            }


        }
    }
}
