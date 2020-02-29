package jp.yksolution.android.app.baseballscore01.ui.game.starter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import jp.yksolution.android.app.baseballscore01.R;
import jp.yksolution.android.app.baseballscore01.db.DbHelper;
import jp.yksolution.android.app.baseballscore01.db.dao.GameInfoDao;
import jp.yksolution.android.app.baseballscore01.db.dao.GameStartingMemberDao;
import jp.yksolution.android.app.baseballscore01.db.dao.TeamMemberDao;
import jp.yksolution.android.app.baseballscore01.db.entity.GameInfoEntity;
import jp.yksolution.android.app.baseballscore01.db.entity.GameStartingMemberEntity;
import jp.yksolution.android.app.baseballscore01.db.entity.TeamMemberEntity;
import jp.yksolution.android.app.baseballscore01.ui.game.info.GameInfoDto;
import jp.yksolution.android.app.baseballscore01.ui.member.TeamMemberDto;
import jp.yksolution.android.app.baseballscore01.utils.DateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * スタメン情報データモデル
 * @author Y.Katou (YKSolution)
 * @since 2020/02/24
 */
public class GameStarterViewModel extends ViewModel {

    @Builder
    @ToString
    public static class GamePositionItem {
        /** ポジションカテゴリId. */
        @Getter private int positionCategory;
        /** ポジションId. */
        @Getter private int positionId;
        /** ポジション短縮名. */
        @Getter private String shortName;
        /** ポジション名. */
        @Getter private String value;
    }

    @Builder
    @ToString
    public static class GameStartterDatas {
        /** ポジション情報. */
        @Getter @Setter private List<GamePositionItem> gamePositionList;
        /** 試合情報. */
        @Getter @Setter private List<GameInfoDto> gameInfoList;
        /** チームメンバー情報. */
        @Getter @Setter private List<TeamMemberDto> teamMemberList;
    }

    private MutableLiveData<GameStartterDatas> mGameStartterDatas = null;
    public LiveData<GameStartterDatas> getGameStartterDatas(Fragment fragment) {
        if (this.mGameStartterDatas == null) {
            this.mGameStartterDatas = new MutableLiveData<GameStartterDatas>();
            this.mGameStartterDatas.setValue(GameStartterDatas.builder().build());
            // ポジション情報の取得
            this.getGamePositionList(fragment.getContext());
            // 試合情報の取得
            this.getGameInfo();
            // メンバー情報の取得
            this.getTeamMember();
        }
        return this.mGameStartterDatas;
    }

    /**
     * ポジション情報の取得.
     * @param context
     */
    private void getGamePositionList(Context context) {
        List<GamePositionItem> list = new ArrayList<>();
        String[] strings = context.getResources().getStringArray(R.array.game_position_list);
        for (int index = 0; index < strings.length; ++index) {
            String[] wk = strings[index].split(",");
            list.add(GamePositionItem.builder()
                .positionCategory(Integer.valueOf(wk[0]))
                .shortName(wk[1])
                .value(wk[2])
                .positionId(Integer.valueOf(wk[3]))
                .build());
            this.mGameStartterDatas.getValue().setGamePositionList(list);
        }
    }

    /**
     * 試合情報の取得
     */
    private void getGameInfo() {
        List<GameInfoDto> list = new ArrayList<>();
        GameInfoDao gameInfoDao = DbHelper.getInstance().getDb().gameInfoDao();
        for (GameInfoEntity entity : gameInfoDao.getGameInfoListForStartMember(DateTime.getTodayDate())) {
            list.add(GameInfoDto.builder()
                .gameId(entity.getGameId())
                .gameName(StringUtils.isEmpty(entity.getGameName()) ? "" : entity.getGameName())
                .place(StringUtils.isEmpty(entity.getPlace()) ? "" : entity.getPlace())
                .gameDate(entity.getGameDate())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .topBottom(entity.getTopBottom())
                .competitionTeamName(StringUtils.isEmpty(entity.getCompetitionTeamName()) ? "" : entity.getCompetitionTeamName())
                .newDateTime(entity.getNewDateTime())
                .updateDateTime(entity.getUpdateDateTime())
                .versionNo(entity.getVersionNo())
                .build());
        }
        this.mGameStartterDatas.getValue().setGameInfoList(list);
    }

    private void getTeamMember() {
        List<TeamMemberDto> list = new ArrayList<>();
        TeamMemberDao teamMemberDao = DbHelper.getInstance().getDb().teamMemberDao();
        for (TeamMemberEntity entity : teamMemberDao.getTeamMemberList()) {
            list.add(TeamMemberDto.builder()
                .memberId(entity.getMemberId())
                .name1(entity.getName1())
                .name2(entity.getName2())
                .positionCategory(entity.getPositionCategory())
                .pitching(entity.getPitching())
                .batting(entity.getBatting())
                .status(entity.getStatus())
                .build());
        }
        this.mGameStartterDatas.getValue().setTeamMemberList(list);
    }



    private long gameId;
    private List<GameStartingMemberDto> updateList = null;

    public void initMemberList(long gameId) {
        this.gameId = gameId;
        if (this.updateList == null) {
            this.updateList = new ArrayList<>();
        } else {
            this.updateList.clear();
        }
    }

    public void addStartingMember(GameStartingMemberDto dto) {
        dto.setGameId(this.gameId);
        this.updateList.add(dto);
    }

    public void saveToDb() {
        GameStartingMemberDao dao = DbHelper.getInstance().getDb().gameStartingMemberDao();

        dao.deleteByGame(this.gameId);
        GameStartingMemberEntity entity = new GameStartingMemberEntity();
        for (GameStartingMemberDto dto : this.updateList) {
            entity.setGameId(dto.getGameId());
            entity.setMemberId(dto.getGameId());
            entity.setBattingOrder(dto.getBattingOrder());
            entity.setPosition(dto.getPosition());

            dao.addStartingMember(entity);
        }
    }
}