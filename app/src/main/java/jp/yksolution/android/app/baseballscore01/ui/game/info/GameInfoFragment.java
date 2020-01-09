package jp.yksolution.android.app.baseballscore01.ui.game.info;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import jp.yksolution.android.app.baseballscore01.R;
import jp.yksolution.android.app.baseballscore01.db.DbHelper;
import jp.yksolution.android.app.baseballscore01.db.dao.GameInfoDao;
import jp.yksolution.android.app.baseballscore01.db.entity.GameInfoEntity;
import jp.yksolution.android.app.baseballscore01.ui.common.PopupMenuOperation;
import jp.yksolution.android.app.baseballscore01.ui.dialogs.ConfirmationDialog;
import jp.yksolution.android.app.baseballscore01.ui.dialogs.GameInfoDialog;

/**
 * 試合情報フラグメント.
 * @author Y.Katou (YKSolution)
 * @since 2020/01/04
 */
public class GameInfoFragment extends Fragment
    implements PopupMenuOperation, GameInfoDialog.NoticeDialogListener,
        ConfirmationDialog.NoticeDialogListener {
    private static final String TAG = GameInfoFragment.class.getSimpleName();

    private GameInfoViewModel gameInfoViewModel;
    private ListView listView;
    private GameInfoListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.gameInfoViewModel =
                ViewModelProviders.of(this).get(GameInfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_game_info, container, false);

        this.listView = (ListView) root.findViewById(R.id.gameInfoList);
        this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            /**
             * ロングタップで試合を選択し、更新画面を開く
             * @param parent
             * @param view
             * @param position 一覧の行インデックス
             * @param id 試合ID
             * @return
             */
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,View view, int position, long id) {
                showUpdateDialog(position);
                return true;
            }
        });
        this.adapter = new GameInfoListAdapter(this.getContext());
        this.gameInfoViewModel.getGameInfos(this).observe(this, new Observer<List<GameInfoDto>>() {
            @Override
            public void onChanged(@Nullable List<GameInfoDto> list) {
                adapter.setGameInfoList(list);
                listView.setAdapter(adapter);
            }
        });
        setHasOptionsMenu(true);        // フラグメントで onOptionsItemSelected を受ける設定
        return root;
    }

    /**
     * for PopupMenuOperation
     * @param menu
     */
    @Override
    public void setupMenu(Menu menu) {
        menu.setGroupVisible(R.id.optMenuGameInfo, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newGameInfo:
                // ゲーム情報登録メニュー選択
                this.addGameInfo();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * ゲーム情報を登録するダイアログを開く
     */
    private void addGameInfo() {
        GameInfoDialog dlg = new GameInfoDialog();
        dlg.show(getActivity().getSupportFragmentManager(), dlg.getTag());
    }

    /**
     * ゲーム情報を更新するダイアログを開く
     * @param rowIndex 一覧の行インデックス
     */
    private void showUpdateDialog(int rowIndex) {
        GameInfoDto dto = (GameInfoDto)this.adapter.getItem(rowIndex);
        Log.d(TAG, dto.toString());
        GameInfoDialog dlg = new GameInfoDialog(dto);
        dlg.show(getActivity().getSupportFragmentManager(), dlg.getTag());
    }

    /**
     * ゲーム情報登録.<br/>
     * for GameInfoDialog.NoticeDialogListener
     * @param gameInfoDto
     */
    public void addGameInfo(GameInfoDto gameInfoDto) {
        Log.d(TAG, "append info : " + gameInfoDto.toString());

        // ＤＢに登録
        GameInfoDao gameInfoDao = DbHelper.getInstance().getDb().gameInfoDao();
        GameInfoEntity entity = this.makeGameInfoEntity(gameInfoDto);
        entity.prepreForInsert();
        String message;
        try {
            gameInfoDao.addGameInfo(entity);
            // 登録後の一覧を更新するため再読み込み
            this.gameInfoViewModel.refreshGameInfos();
            message = this.getResources().getString(R.string.MSG_DB_INS_OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            message = this.getResources().getString(R.string.MSG_DB_INS_NG);
        }
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * ＤＢに登録／更新する試合情報エンティティを編集する.
     * @param gameInfoDto
     * @return
     */
    private GameInfoEntity makeGameInfoEntity(GameInfoDto gameInfoDto) {
        GameInfoEntity entity = new GameInfoEntity();
        entity.setGameId(gameInfoDto.getGameId());
        entity.setGameName(gameInfoDto.getGameName());
        entity.setPlace(gameInfoDto.getPlace());
        entity.setStartTime(gameInfoDto.getStartTime());
        entity.setEndTime(gameInfoDto.getEndTime());
        entity.setTopBottom(gameInfoDto.getTopBottom());
        entity.setGameDate(gameInfoDto.getGameDate());
        entity.setCompetitionTeamName(gameInfoDto.getCompetitionTeamName());
        entity.setUmpire1(gameInfoDto.getUmpire1());
        entity.setUmpire2(gameInfoDto.getUmpire2());
        entity.setUmpire3(gameInfoDto.getUmpire3());
        entity.setUmpire4(gameInfoDto.getUmpire4());
        entity.setUmpire5(gameInfoDto.getUmpire5());
        entity.setUmpire6(gameInfoDto.getUmpire6());
        entity.setNewDateTime(gameInfoDto.getNewDateTime());
        entity.setUpdateDateTime(gameInfoDto.getUpdateDateTime());
        entity.setVersionNo(gameInfoDto.getVersionNo());
        return entity;
    }

    /**
     * ゲーム情報更新.<br/>
     * for GameInfoDialog.NoticeDialogListener
     * @param gameInfoDto
     */
    public void updateGameInfo(GameInfoDto gameInfoDto) {
        Log.d(TAG, "update info : " + gameInfoDto.toString());

        // ＤＢを更新
        GameInfoDao gameInfoDao = DbHelper.getInstance().getDb().gameInfoDao();
        GameInfoEntity entity = this.makeGameInfoEntity(gameInfoDto);
        entity.prepreForUpdate();
        int count = gameInfoDao.updateGameInfo(entity);
        // 更新結果を確認
        String message;
        if (count == 1) {
            // 更新後の一覧を更新するため再読み込み
            this.gameInfoViewModel.refreshGameInfos();
            message = getResources().getString(R.string.MSG_DB_UPD_OK);
        } else {
            message = getResources().getString(R.string.MSG_DB_UPD_NG);
        }
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * ゲーム情報削除.<br/>
     * for GameInfoDialog.NoticeDialogListener
     * @param gameInfoDto
     */
    public void deleteGameInfo(GameInfoDto gameInfoDto) {
        String[] params = new String[]{gameInfoDto.getGameName()};
        ConfirmationDialog dlg = new ConfirmationDialog(R.string.MSG_CONG_002, params, gameInfoDto);
        dlg.show(getActivity().getSupportFragmentManager(), dlg.getTag());
    }

    /**
     * 試合情報の削除処理を行う.<br/>
     * for ConfirmationDialog.NoticeDialogListener
     */
    @Override
    public void forwardProcess(Object obj) {
        Log.d(TAG, "delete Game info : " + obj.toString());
        GameInfoDto dto = (GameInfoDto)obj;

        // ＤＢから削除
        GameInfoDao gameInfoDao = DbHelper.getInstance().getDb().gameInfoDao();
        int count = gameInfoDao.deleteGameInfo(dto.getGameId());
        // 削除結果を確認
        int messageFmtId;
        if (count == 1) {
            // 更新後の一覧を更新するため再読み込み
            this.gameInfoViewModel.refreshGameInfos();
            messageFmtId = R.string.MSG_DB_DLT_OK;
        } else {
            messageFmtId = R.string.MSG_DB_DLT_NG;
        }
        String msgFrm = this.getResources().getString(messageFmtId);
        String message = String.format(msgFrm, dto.getGameName());
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }
}