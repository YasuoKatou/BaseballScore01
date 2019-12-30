package jp.yksolution.android.app.baseballscore01.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import jp.yksolution.android.app.baseballscore01.db.entity.TeamMemberEntity;

/**
 * チームメンバーテーブルDao.
 * @author Y.Katou (YKSolution)
 * @since 2019/12/28
 */
public class TeamMemberDao extends DaoBase {
    private static final String TAG = TeamMemberDao.class.getSimpleName();

    private final String mSQL_Insert;
    public TeamMemberDao(Fragment fragment) {
        super(fragment);

        StringBuffer sb = new StringBuffer("insert into team_member");
        sb.append(" (name1,name2,sex,birthday,position_category,pitching,batting")
          .append(",new_date_time,upd_date_time,verno) values")
          .append(" (?,?,?,?,?,?,?,?,?,?)");
        this.mSQL_Insert = sb.toString();
    }

    /**
     * チームの選手情報を登録する.
     * @param entity
     * @return
     */
    public int addTeamMember(TeamMemberEntity entity) {
        int ret = 0;
        SQLiteDatabase db = super.getSQLiteDatabase();
        super.beginTransaction(db);
        try {
             try (SQLiteStatement statement = db.compileStatement(this.mSQL_Insert)) {
                  statement.bindString(1, entity.getName1());
                  statement.bindString(2, entity.getName2());
                  statement.bindLong(3, entity.getSex());
                  statement.bindLong(4, entity.getBirthday());
                  statement.bindLong(5, entity.getPositionCategory());
                  statement.bindLong(6, entity.getPitching());
                  statement.bindLong(7, entity.getBatting());
                  statement.bindLong(8, entity.getNewDateTime());
                  statement.bindLong(9, entity.getUpdateDateTime());
                  statement.bindLong(10, entity.getVersionNo());
                  statement.executeInsert();
                  db.setTransactionSuccessful();
                  ret = 1;
             }
        } finally {
            long time = super.endTransaction(db);
            Log.d(TAG,"insert member information time : " + time + "ms");
            return ret;
        }
    }

    /**
     * 登録選手の一覧を取得する.
     * @return
     */
    public List<TeamMemberEntity> getTeamMemberList() {
        super.setStartTime();
        boolean distinct = false;
        String table = "team_member";
        String[] columns = new String[]{"member_id", "name1", "name2", "sex", "birthday",
            "position_category", "pitching", "batting"};
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = "birthday";
        String limit = null;
        List<TeamMemberEntity> list = new ArrayList<>();
        SQLiteDatabase db = super.getSQLiteDatabase();
        try (Cursor cursor = db.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit)) {
            while (cursor.moveToNext()) {
                list.add(TeamMemberEntity.builder()
                    .memberId(cursor.getLong(0))
                    .name1(cursor.getString(1))
                    .name2(cursor.getString(2))
                    .sex(cursor.getInt(3))
                    .birthday(cursor.getLong(4))
                    .positionCategory(cursor.getInt(5))
                    .pitching(cursor.getInt(6))
                    .batting(cursor.getInt(7))
                    .build());
            }
        }
        long time = super.getProcessTime();
        Log.d(TAG,"select team member list time : " + time + "ms");
        return list;
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
           .append(",sex int not null")
           .append(",birthday int not null")
           .append(",position_category int")
           .append(",pitching int")
           .append(",batting int")
           .append(",new_date_time int not null")
           .append(",upd_date_time int not null")
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