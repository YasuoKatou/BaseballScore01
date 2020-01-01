package jp.yksolution.android.app.baseballscore01.utils;

import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * 日時に関するユーティリティ.
 * @author Y.Katou (YKSolution)
 * @since 2019/12/29
 */
public class DateTime {
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
}