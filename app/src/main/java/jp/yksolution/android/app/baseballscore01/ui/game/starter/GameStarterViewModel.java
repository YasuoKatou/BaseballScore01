package jp.yksolution.android.app.baseballscore01.ui.game.starter;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import jp.yksolution.android.app.baseballscore01.db.DbHelper;
import jp.yksolution.android.app.baseballscore01.db.dao.GameStartingMemberDao;
import jp.yksolution.android.app.baseballscore01.db.entity.GameStartingMemberEntity;

/**
 * スタメン情報データモデル
 * @author Y.Katou (YKSolution)
 * @since 2020/02/24
 */
public class GameStarterViewModel extends ViewModel {
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