package jp.yksolution.android.app.baseballscore01.db.dao;

import android.database.sqlite.SQLiteDatabase;

import jp.yksolution.android.app.baseballscore01.db.entity.EntityBase;

/**
 * Dao基底クラス.
 * @author Y.Katou (YKSolution)
 * @since 2019/12/28
 */
public abstract class DaoBase {
    abstract void execute(SQLiteDatabase db, EntityBase entty);

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
}