package jp.yksolution.android.app.baseballscore01.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import jp.yksolution.android.app.baseballscore01.db.dao.GameInfoDao;
import jp.yksolution.android.app.baseballscore01.db.dao.GameStartingMemberDao;
import jp.yksolution.android.app.baseballscore01.db.dao.TeamMemberDao;
import jp.yksolution.android.app.baseballscore01.db.entity.GameInfoEntity;
import jp.yksolution.android.app.baseballscore01.db.entity.GameStartingMemberEntity;
import jp.yksolution.android.app.baseballscore01.db.entity.TeamMemberEntity;

/**
 * スコアＤＢ.
 * @author Y.Katou (YKSolution)
 */
@Database(entities = {TeamMemberEntity.class, GameInfoEntity.class, GameStartingMemberEntity.class}, version = 2)
public abstract class MyDB extends RoomDatabase {
    /** データベース名 : score_db */
    public static final String DB_NAME = "score_db";
    /** テーブル名：チームメンバー（team_member） */
    public static final String TNAME_TEAM_MEMBER = "team_member";
    /** テーブル名：試合情報（game_info） */
    public static final String TNAME_GAME_INFO = "game_info";
    /** テーブル名：スターティングメンバ―（game_starting_member） */
    public static final String GAME_STARTING_MEMBER = "game_starting_member";

    /** チームメンバーテーブルDao. */
    public abstract TeamMemberDao teamMemberDao();
    /** 試合情報テーブルDao. */
    public abstract GameInfoDao gameInfoDao();
    /** スターティングメンバ―Dao. */
    public abstract GameStartingMemberDao gameStartingMemberDao();
}