package jp.yksolution.android.app.baseballscore01.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import jp.yksolution.android.app.baseballscore01.db.MyDB;
import jp.yksolution.android.app.baseballscore01.db.entity.GameStartingMemberEntity;

/**
 * スターティングメンバ―Dao.
 * @author Y.Katou (YKSolution)
 * @since 2020/02/16
 */
@Dao
public interface GameStartingMemberDao {
    /**
     * スターティングメンバーを登録する.
     * @param entity スターティングメンバ―
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addStartingMember(GameStartingMemberEntity entity);

    /**
     * １試合のスターティングメンバーを削除する.
     * @param gameId 試合情報ID
     */
    @Query("DELETE FROM " + MyDB.GAME_STARTING_MEMBER + " WHERE game_id = :gameId")
    void deleteByGame(Long gameId);

    /**
     * スターティングメンバ―を取得する.
     * @param gameId 試合情報ID
     * @return スターティングメンバ―
     */
    @Query("SELECT * FROM " + MyDB.GAME_STARTING_MEMBER + " WHERE game_id = :gameId")
    List<GameStartingMemberEntity> getStartingList(long gameId);
}