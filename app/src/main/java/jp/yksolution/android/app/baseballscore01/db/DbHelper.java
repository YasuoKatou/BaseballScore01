package jp.yksolution.android.app.baseballscore01.db;

import android.app.Application;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // スターティングメンバ―テーブルの作成
            database.execSQL("CREATE TABLE IF NOT EXISTS `" + MyDB.GAME_STARTING_MEMBER
                + "` (`game_id` INTEGER NOT NULL, `batting_order` INTEGER NOT NULL, `member_id` INTEGER NOT NULL, `position` INTEGER, PRIMARY KEY(`game_id`, `batting_order`))");
        }
    };

    public MyDB getDb() {
        if (mMyDB == null) {
            mMyDB = Room.databaseBuilder(getApplicationContext(), MyDB.class, MyDB.DB_NAME)
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .build();
        }
        return mMyDB;
    }
}