package jp.yksolution.android.app.baseballscore01.utils;

import android.icu.text.SimpleDateFormat;
import android.text.format.DateFormat;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;

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
            Log.i(TAG, "Date Fromat Error (" + date + ")");
            return false;
        }
    }
}