package jp.yksolution.android.app.baseballscore01.ui.game.info;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import jp.yksolution.android.app.baseballscore01.db.DbHelper;
import jp.yksolution.android.app.baseballscore01.db.dao.GameInfoDao;
import jp.yksolution.android.app.baseballscore01.db.entity.GameInfoEntity;

/**
 * 試合情報ビューのデータモデル.
 * @author Y.Katou (YKSolution)
 * @since 2020/01/04
 */
public class GameInfoViewModel extends ViewModel {
    private MutableLiveData<List<GameInfoDto>> mGameInfos =null;
    private Fragment mFragment = null;
    public LiveData<List<GameInfoDto>> getTeamMembers(Fragment fragment) {
        this.mFragment = fragment;
        if (this.mGameInfos == null) {
            this.mGameInfos = new MutableLiveData<List<GameInfoDto>>();
            this.loadGameInfos();
        }
        return this.mGameInfos;
    }

    public void refreshTeamMembers() {
        this.loadGameInfos();
    }

    private void loadGameInfos() {
        List<GameInfoDto> memberList = new ArrayList<>();
        GameInfoDao gameInfoDao = DbHelper.getInstance().getDb().gameInfoDao();
        for (GameInfoEntity entity : gameInfoDao.getGameInfoList()) {
            memberList.add(GameInfoDto.builder()
                .gameId(entity.getGameId())
                .gameName(entity.getGameName())
                .place(entity.getPlace())
                .gameDate(entity.getGameDate())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .topBottom(entity.getTopBottom())
                .competitionTeamName(entity.getCompetitionTeamName())
                .umpire1(entity.getUmpire1())
                .umpire2(entity.getUmpire2())
                .umpire3(entity.getUmpire3())
                .umpire4(entity.getUmpire4())
                .umpire5(entity.getUmpire5())
                .umpire6(entity.getUmpire6())
                .newDateTime(entity.getNewDateTime())
                .updateDateTime(entity.getUpdateDateTime())
                .versionNo(entity.getVersionNo())
                .build());
        }

        this.mGameInfos.setValue(memberList);
    }
}