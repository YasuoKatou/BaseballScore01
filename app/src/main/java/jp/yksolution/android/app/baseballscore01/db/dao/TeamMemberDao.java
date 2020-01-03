package jp.yksolution.android.app.baseballscore01.db.dao;

import android.content.ContentValues;
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

    private static final String TABLE_NAME = "team_member";

    private final String mSQL_Insert;
    public TeamMemberDao(Fragment fragment) {
        super(fragment);

        StringBuffer sb = new StringBuffer("insert into ");
        sb.append(TABLE_NAME)
          .append(" (name1, name2, sex, birthday, position_category, pitching, batting")
          .append(",status,new_date_time, upd_date_time, verno) values")
          .append(" (?,?,?,?,?,?,?,?,?,?,?)");
        this.mSQL_Insert = sb.toString();
    }

    /**
     * チームの選手情報を登録する.
     * @param entity
     * @return
     */
    public int addTeamMember(TeamMemberEntity entity) {
        Log.d(TAG, "insert by " + entity.toString());
        int ret = 0;
        entity.prepareInsert();
        SQLiteDatabase db = super.getSQLiteDatabase();
        super.beginTransaction(db);
        try {
             try (SQLiteStatement statement = db.compileStatement(this.mSQL_Insert)) {
                  statement.bindString(1, entity.getName1());
                  statement.bindString(2, entity.getName2());
                  statement.bindLong(3, entity.getSex());
                  statement.bindLong(4, entity.getBirthday());
                  statement.bindLong(5,
                      (entity.getPositionCategory() == null) ? null : entity.getPositionCategory().intValue());
                  statement.bindLong(6,
                      (entity.getPitching() == null) ? null : entity.getPitching().intValue());
                  statement.bindLong(7,
                      (entity.getBatting() == null) ? null :entity.getBatting().intValue());
                  statement.bindLong(8, entity.getStatus());
                  statement.bindLong(9, entity.getNewDateTime());
                  statement.bindLong(10, entity.getUpdateDateTime());
                  statement.bindLong(11, entity.getVersionNo());
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
     * チームの選手情報を更新する.
     * @param entity
     * @return
     */
    public int updateTeamMember(TeamMemberEntity entity) {
        Log.d(TAG, "update by " + entity.toString());
        int count = 0;
        String table = TABLE_NAME;
        // 更新内容
        ContentValues values = new ContentValues();
        values.put("name1", entity.getName1());
        values.put("name2", entity.getName2());
        values.put("sex", entity.getSex());
        values.put("birthday", entity.getBirthday());
        values.put("position_category", entity.getPositionCategory());
        values.put("pitching", entity.getPitching());
        values.put("batting", entity.getBatting());
        values.put("status", entity.getStatus());
        values.put("upd_date_time", entity.getUpdateDateTime());
        values.put("verno", entity.getVersionNo());
        // 更新条件
        String[] whereArgs = new String[] {Long.toString(entity.getMemberId())};
        entity.prepareUpdate();

        SQLiteDatabase db = super.getSQLiteDatabase();
        super.beginTransaction(db);
        try {
            count = db.update(table, values, "member_id = ?", whereArgs);
            db.setTransactionSuccessful();
        } finally {
            long time = super.endTransaction(db);
            Log.d(TAG,"updated : " + time + "ms (update num :" + count + ")");
        }
        return count;
    }

    /**
     * チームの選手情報を削除する.
     * @param memberId
     * @return
     */
    public int deleteTeamMember(long memberId) {
        Log.d(TAG, "delete by " + Long.toString(memberId));
        int count = 0;
        String table = TABLE_NAME;
        // 削除条件
        String[] whereArgs = new String[] {Long.toString(memberId)};

        SQLiteDatabase db = super.getSQLiteDatabase();
        super.beginTransaction(db);
        try {
            count = db.delete(table, "member_id = ?", whereArgs);
            db.setTransactionSuccessful();
        } finally {
            long time = super.endTransaction(db);
            Log.d(TAG,"deleted : " + time + "ms (delete num : " + count + ")");
        }
        return count;
    }

    /**
     * 登録選手の一覧を取得する.
     * @return
     */
    public List<TeamMemberEntity> getTeamMemberList() {
        super.setStartTime();
        boolean distinct = false;
        String table = TABLE_NAME;
        String[] columns = new String[]{"member_id", "name1", "name2", "sex", "birthday",
            "position_category", "pitching", "batting", "status", "new_date_time", "upd_date_time", "verno"};
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
                TeamMemberEntity entity = TeamMemberEntity.builder()
                    .memberId(cursor.getLong(0))
                    .name1(cursor.getString(1))
                    .name2(cursor.getString(2))
                    .sex(cursor.getInt(3))
                    .birthday(cursor.getLong(4))
                    .positionCategory(cursor.getInt(5))
                    .pitching(cursor.getInt(6))
                    .batting(cursor.getInt(7))
                    .status(cursor.getInt(8))
                    .build();
                entity.setNewDateTime(cursor.getInt(9));
                entity.setUpdateDateTime(cursor.getInt(10));
                entity.setVersionNo(cursor.getInt(11));
                list.add(entity);
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
        ddl = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        ddl.append(TABLE_NAME)
           .append(" (member_id integer primary key")     // integer for auto increment
           .append(",name1 text not null")
           .append(",name2 text not null")
           .append(",sex int not null")
           .append(",birthday int not null")
           .append(",position_category int")
           .append(",pitching int")
           .append(",batting int")
           .append(",status int")
           .append(",new_date_time int not null")
           .append(",upd_date_time int not null")
           .append(",verno int not null")                // int for self increment
           .append(");");
        Log.d(tag, "CREATE TABLE : " + ddl.toString());
        db.execSQL(ddl.toString());
        Log.d(tag, "CREATE TABLE passed");

        // インデックスを作成
        ddl = new StringBuilder("create index team_member_idx1 on ");
        ddl.append(TABLE_NAME).append("(status, birthday, name1, name2)");
        Log.d(tag, "CREATE INDEX : " + ddl.toString());
        db.execSQL(ddl.toString());
        Log.d(tag, "CREATE INDEX passed");
    }
}