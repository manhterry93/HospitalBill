package bkhn.et.hospitalbill.utils;

import android.util.Log;

/**
 * Created by PL_itto on 5/3/2018.
 */

public class Logg {
    private static final boolean DEBUGABLE = true;

    private Logg() {
    }

    public static final void d(String tag, String content) {
        if (DEBUGABLE) {
            Log.d(tag, content);
        }
    }

    public static final void i(String tag, String content) {
        if (DEBUGABLE) {
            Log.i(tag, content);
        }
    }

    public static final void e(String tag, String content) {
        if (DEBUGABLE) {
            Log.e(tag, content);
        }
    }

    public static final void w(String tag, String content) {
        if (DEBUGABLE) {
            Log.w(tag, content);
        }
    }
}
