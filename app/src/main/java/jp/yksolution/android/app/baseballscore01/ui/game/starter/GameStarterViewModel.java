package jp.yksolution.android.app.baseballscore01.ui.game.starter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import jp.yksolution.android.app.baseballscore01.R;
import jp.yksolution.android.app.baseballscore01.db.DbHelper;
import jp.yksolution.android.app.baseballscore01.db.dao.GameStartingMemberDao;
import jp.yksolution.android.app.baseballscore01.db.entity.GameStartingMemberEntity;
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
        @Getter @Setter List<GamePositionItem> gamePositionList;
    }

    private MutableLiveData<GameStartterDatas> mGameStartterDatas = null;
    public LiveData<GameStartterDatas> getGameStartterDatas(Fragment fragment) {
        if (this.mGameStartterDatas == null) {
            this.mGameStartterDatas = new MutableLiveData<GameStartterDatas>();
            this.mGameStartterDatas.setValue(GameStartterDatas.builder().build());
            // ポジション情報の取得
            this.getGamePositionList(fragment);
        }
        return this.mGameStartterDatas;
    }

    /**
     * ポジション情報んぼ取得.
     * @param fragment
     */
    private void getGamePositionList(Fragment fragment) {
        Context context = fragment.getContext();
        List<GamePositionItem> list = new ArrayList<>();

        // 守備のポジションを初期設定
        String[] strings = context.getResources().getStringArray(R.array.game_position_list);
        for (int index = 0; index < strings.length; ++index) {
            String[] wk = strings[index].split(",");
            list.add(GamePositionItem.builder()
                .positionCategory(Integer.valueOf(wk[0]))
                .shortName(wk[1])
                .value(wk[2])
                .positionId(Integer.valueOf(wk[3]))
                .build());
        }
        this.mGameStartterDatas.getValue().setGamePositionList(list);
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