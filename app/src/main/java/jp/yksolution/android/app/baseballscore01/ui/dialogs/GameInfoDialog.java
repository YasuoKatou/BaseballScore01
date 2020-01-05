package jp.yksolution.android.app.baseballscore01.ui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import jp.yksolution.android.app.baseballscore01.R;
import jp.yksolution.android.app.baseballscore01.ui.game.info.GameInfoDto;

/**
 * 試合情報登録／更新ダイアログ
 * @author Y.Katou (YKSolution)
 * @since 2020/01/04
 */
public class GameInfoDialog extends DialogFragmentEx {
    private static final String TAG = GameInfoDialog.class.getSimpleName();

    public GameInfoDialog() { super(); }

    private GameInfoDto updateGameInfo = null;
    public GameInfoDialog(GameInfoDto updateGameInfo) {
        super();
        this.updateGameInfo = updateGameInfo;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Activity activity = getActivity();
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.game_info_dialog, null);

        if (this.updateGameInfo != null) {
            // TODO ここで更新するデータをビューに設定する
        }
        AlertDialog dlg = new AlertDialog.Builder(activity)
            .setView(dialogView)
            .setTitle(R.string.opt_menu_new_game)
            .setPositiveButton((this.updateGameInfo == null) ? R.string.DLG_ENTRY : R.string.DLG_UPDATE
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                         if (updateGameInfo == null) {
                             // 選手情報登録
                             addGameInfo(dialogView);
                         } else {
                             // 選手情報更新
                             updateGameInfo(dialogView);
                         }
                    }
                })
            .create();
        if (this.updateGameInfo != null) {
            String buttonText = getString(R.string.DLG_DELETE);
            dlg.setButton(AlertDialog.BUTTON_NEGATIVE, buttonText
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // 選手情報削除
                        mNoticeDialogListener.deleteGameInfo(updateGameInfo);
                    }
                }
            );
        }
        return dlg;
    }

    public interface NoticeDialogListener extends NoticeDialogListenerBase {
        /**
         * ゲーム情報登録.
         * @param gameInfoDto
         */
        void addGameInfo(GameInfoDto gameInfoDto);
        /**
         * ゲーム情報更新.
         * @param gameInfoDto
         */
        void updateGameInfo(GameInfoDto gameInfoDto);
        /**
         * ゲーム情報削除
         * @param gameInfoDto
         */
        void deleteGameInfo(GameInfoDto gameInfoDto);
    }
    private NoticeDialogListener mNoticeDialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mNoticeDialogListener = (NoticeDialogListener)super.getActiveFragment();
    }

    /**
     * チームのメンバーを登録する.
     * @param dialogView
     */
    private void addGameInfo(View dialogView) {
        mNoticeDialogListener.addGameInfo(this.makeGameInfoDto(dialogView));
    }

    /**
     * ダイアログビューから入力データ（ゲーム情報）を取得する.
     * @param dialogView
     * @return
     */
    private GameInfoDto makeGameInfoDto(View dialogView) {
        // TODO 作成
        return null;
    }

    /**
     * チームのメンバー情報を更新する.
     * @param dialogView
     */
    private void updateGameInfo(View dialogView) {
        GameInfoDto dto = this.makeGameInfoDto(dialogView);
        dto.setGameId(this.updateGameInfo.getGameId());
        mNoticeDialogListener.updateGameInfo(dto);
    }
}