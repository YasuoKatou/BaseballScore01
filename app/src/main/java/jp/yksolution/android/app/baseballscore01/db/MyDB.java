package jp.yksolution.android.app.baseballscore01.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import jp.yksolution.android.app.baseballscore01.db.dao.TeamMemberDao;
import jp.yksolution.android.app.baseballscore01.db.entity.TeamMemberEntity;

@Database(entities = {TeamMemberEntity.class}, version = 1)
public abstract class MyDB extends RoomDatabase {
    /** データベース名 : score_db */
    public static final String DB_NAME = "score_db";
    /** チームメンバーテーブル名称：team_member */
    public static final String TNAME_TEAM_MEMBER = "team_member";

    /** チームメンバーテーブル. */
    public abstract TeamMemberDao teamMemberDao();
}
