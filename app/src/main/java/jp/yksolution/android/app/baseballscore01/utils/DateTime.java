package jp.yksolution.android.app.baseballscore01.utils;

import android.icu.text.SimpleDateFormat;
import android.text.format.DateFormat;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 日時に関するユーティリティ.
 * @author Y.Katou (YKSolution)
 * @since 2019/12/29
 */
public class DateTime {
    private static final String TAG = DateTime.class.getSimpleName();

    /**
     * 現在日時を文字列で取得する.
     * @return 現在日時
     */
    public static String now() {
        return DateFormat.format("yyyy/MM/dd kk:mm:ss", Calendar.getInstance()).toString();
    }

    /**
     * 日付を文字列で取得する.
     * @return 日付文字列
     */
    public static String getDate(long date) {
        return DateFormat.format("yyyy/M/d", date).toString();
    }

    public static String getDateTime(long dateTime) {
        return DateFormat.format("yyyy/M/d kk:mm", dateTime).toString();
    }

    public static String getTime(Long time) {
        if (time == null) return "";
        return String.format("%d:%02d", time / 100, time % 100);
    }

    /**
     * 日付の書式をチェックする.
     * @param date
     */
    public static boolean isDateFormat(String date) {
        if (StringUtils.isEmpty(date)) return false;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
            sdf.parse(date);
            return true;
        } catch (Exception ex) {
            Log.i(TAG, "Date Fromat Error at isDateFormat method (" + date + ")");
            return false;
        }
    }

    /**
     * 時刻の書式(h:mm)をチェックする.
     * @param hourMinute
     * @return
     */
    public static boolean isTimeFormat(String hourMinute) {
        if (StringUtils.isEmpty(hourMinute)) return false;
        int pos = hourMinute.indexOf(":");
        if (pos == -1) return false;
        try {
            int hour = Integer.parseInt(hourMinute.substring(0, pos));
            if ((hour < 0) || (hour > 23)) return false;
            int minute = Integer.parseInt(hourMinute.substring(pos + 1));
            if ((minute < 0) || (minute > 59)) return false;
            String val = String.format("%d:%02d", hour, minute);
            return val.equals(hourMinute);
        } catch (Exception ex) {
            Log.i(TAG, "Time Fromat Error at isTimeFormat (" + hourMinute + ")");
            return false;
        }
    }

    /**
     * 日付変換（文字列 → long値）
     * @param strDate
     * @return
     */
    public static long convertStringDateToLong(String strDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
            Date date = sdf.parse(strDate);
            return date.getTime();
        } catch (Exception ex) {
            Log.i(TAG, "Date Fromat Error at convertStringDateToLong method (" + strDate + ")");
            return 0L;
        }
    }

    /**
     * 時刻変換（文字列 → long値）
     * @param hourMinute
     * @return
     */
    public static long convertStringTimeToLong(String hourMinute) {
        int pos = hourMinute.indexOf(":");
        try {
            long hour = Long.parseLong(hourMinute.substring(0, pos));
            long minute = Long.parseLong(hourMinute.substring(pos + 1));
            return ((hour * 100) + minute);
        } catch (Exception ex) {
            Log.i(TAG, "Time Fromat Error at isTimeFormat (" + hourMinute + ")");
            return 0;
        }
    }
}