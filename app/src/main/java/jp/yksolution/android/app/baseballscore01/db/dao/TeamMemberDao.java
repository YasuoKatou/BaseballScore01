package jp.yksolution.android.app.baseballscore01.db.dao;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import jp.yksolution.android.app.baseballscore01.db.entity.EntityBase;

/**
 * チームメンバーテーブルDao.
 * @author Y.Katou (YKSolution)
 * @since 2019/12/28
 */
public class TeamMemberDao extends DaoBase {
    public void execute(SQLiteDatabase db, EntityBase entty) {
        // TODO ここにクエリーを実装すること
    }

    /**
     * チームメンバーテーブルを作成する.
     * @param db SQLiteDatabase
     */
    public static final void createTable(SQLiteDatabase db) {
        final String tag = TeamMemberDao.class.getSimpleName();

        StringBuilder ddl;
        // テーブルを作成
        ddl = new StringBuilder("CREATE TABLE IF NOT EXISTS team_member (");
        ddl.append("member_id integer primary key")     // integer for auto increment
           .append(",name1 text not null")
           .append(",name2 text not null")
           .append(",sex integer not null")
           .append(",birthday text not null")
           .append(",new_date_time text not null")
           .append(",upd_date_time text not null")
           .append(",verno int not null")                // int for self increment
           .append(");");
        Log.d(tag, "CREATE TABLE : " + ddl.toString());
        db.execSQL(ddl.toString());
        Log.d(tag, "CREATE TABLE passed");

        // インデックスを作成
        ddl = new StringBuilder("create index team_member_idx1 on team_member(birthday, name1, name2);");
        Log.d(tag, "CREATE INDEX : " + ddl.toString());
        db.execSQL(ddl.toString());
        Log.d(tag, "CREATE INDEX passed");    }
}