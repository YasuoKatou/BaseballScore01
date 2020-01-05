package jp.yksolution.android.app.baseballscore01.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import jp.yksolution.android.app.baseballscore01.db.MyDB;
import jp.yksolution.android.app.baseballscore01.db.entity.GameInfoEntity;

/**
 * 試合情報テーブルDao.
 * @author Y.Katou (YKSolution)
 * @since 2020/01/04
 */
@Dao
public interface GameInfoDao {
    @Insert(entity = GameInfoEntity.class)
    void addGameInfo(GameInfoEntity entity);

    @Update(entity = GameInfoEntity.class)
    int updateGameInfo(GameInfoEntity entity);

    @Query("DELETE FROM " + MyDB.TNAME_GAME_INFO + " WHERE game_id = :gameId")
    int deleteGameInfo(long gameId);

    @Query("SELECT * FROM " + MyDB.TNAME_GAME_INFO + " ORDER BY game_date desc, start_time desc")
    List<GameInfoEntity> getGameInfoList();
}