package bkhn.et.hospitalbill.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PL_itto on 5/2/2018.
 */

public class AppUtils {
    public static <V> boolean isNotNull(V object) {
        return object != null;
    }

    public static final SimpleDateFormat TIME_FORMAT_LITE = new SimpleDateFormat("dd/MM/yy - HH:mm");

    /**
     * Convert from TimeStamp long to String
     *
     * @param timeStamp
     * @return
     */
    public static final String getTimeLite(long timeStamp) {
        Date date = new Date(timeStamp);
        return TIME_FORMAT_LITE.format(date);
    }
}
