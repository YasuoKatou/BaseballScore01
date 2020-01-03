package jp.yksolution.android.app.baseballscore01.db;

import android.app.Application;

import androidx.room.Room;

/**
 * データベースの管理<br/>
 * テーブルの追加・削除・変更を行う
 * @author Y.Katou (YKSolution)
 * @since 2019/12/28
 */
public class DbHelper extends Application {
    private static DbHelper mDbHelper = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mDbHelper = this;
    }

    public static DbHelper getInstance() {
        return mDbHelper;
    }

    private MyDB mMyDB = null;

    public MyDB getDb() {
        if (mMyDB == null) {
            mMyDB = Room.databaseBuilder(getApplicationContext(), MyDB.class, MyDB.DB_NAME)
                 .allowMainThreadQueries()
                 .build();
        }
        return mMyDB;
    }
}