package jp.yksolution.android.app.baseballscore01.db.dao;

import android.database.sqlite.SQLiteDatabase;

import androidx.fragment.app.Fragment;

import java.util.Date;

import jp.yksolution.android.app.baseballscore01.db.DbHelper;
import jp.yksolution.android.app.baseballscore01.db.entity.EntityBase;

/**
 * Dao基底クラス.
 * @author Y.Katou (YKSolution)
 * @since 2019/12/28
 */
public abstract class DaoBase {
    private final Fragment mFragment;
    private DaoBase() { this.mFragment = null; }
    protected DaoBase(Fragment fragment) { this.mFragment = fragment; }

    protected SQLiteDatabase getSQLiteDatabase() {
        DbHelper dbHelper = new DbHelper(this.mFragment.getContext());
        return dbHelper.getWritableDatabase();
    }

    /** 処理開始日時. */
    protected long mStartTime;
    /**
     * 処理開始日時を取得する.
     */
    protected void setStartTime() {
        this.mStartTime = System.currentTimeMillis();
    }
    /**
     * 処理開始日時を取得し、トランザクションを開始する.
     * @param db SQLiteDatabase
     */
    protected void beginTransaction(SQLiteDatabase db) {
        this.setStartTime();
        db.beginTransaction();
    }

    /**
     * 処理時間を取得する.
     * @return 処理時間（単位：msec）
     */
    protected long getProcessTime() {
        return System.currentTimeMillis() - this.mStartTime;
    }

    /**
     * トランザクションを終了し、処理時間（単位：msec）を取得する.
     * @param db SQLiteDatabase
     * @return 処理時間（単位：msec）
     */
    protected long endTransaction(SQLiteDatabase db) {
        db.endTransaction();
        return this.getProcessTime();
    }

    /**
     * 新規登録時の共通情報を設定する
     * @param entity エンティティの基底クラス.
     */
    protected void prepreForInsert(EntityBase entity) {
        Date now = new Date();
        entity.setNewDateTime(now.getTime());
        entity.setUpdateDateTime(now.getTime());
        entity.setVersionNo(1);
    }

    /**
     * 更新時の共通情報を設定する
     * @param entity エンティティの基底クラス.
     */
    protected void prepreForUpdate(EntityBase entity) {
        Date now = new Date();
        entity.setUpdateDateTime(now.getTime());
    }
}