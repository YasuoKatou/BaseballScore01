package jp.yksolution.android.app.baseballscore01.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import jp.yksolution.android.app.baseballscore01.db.MyDB;
import jp.yksolution.android.app.baseballscore01.db.entity.TeamMemberEntity;

/**
 * チームメンバーテーブルDao.
 * @author Y.Katou (YKSolution)
 * @since 2019/12/28
 */
@Dao
public interface TeamMemberDao {
    @Insert(entity = TeamMemberEntity.class)
    void addTeamMember(TeamMemberEntity entity);

    @Update(entity = TeamMemberEntity.class)
    int updateTeamMember(TeamMemberEntity entity);

    @Query("DELETE FROM " + MyDB.TNAME_TEAM_MEMBER + " WHERE member_id = :memberId")
    int deleteTeamMember(long memberId);

    @Query("SELECT * FROM " + MyDB.TNAME_TEAM_MEMBER + " ORDER BY birthday")
    List<TeamMemberEntity> getTeamMemberList();
}