package jp.yksolution.android.app.baseballscore01.ui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import jp.yksolution.android.app.baseballscore01.R;
import jp.yksolution.android.app.baseballscore01.ui.game.info.GameInfoDto;
import jp.yksolution.android.app.baseballscore01.utils.DateTime;

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
            .setPositiveButton((this.updateGameInfo == null) ? R.string.DLG_ENTRY : R.string.DLG_UPDATE, null)
            .setNeutralButton(R.string.DLG_CLOSE, null)
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

    @Override
    public void onStart() {
        super.onStart();

        // super.onStart()の後でないと、getButton(...)でnullが返ってくる
        final AlertDialog dialog = (AlertDialog)this.getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View buttonView) {
                    if (execute(dialog)) {
                        // 登録／更新処理が正常に終了した場合、ダイアログを閉じる
                        dialog.dismiss();
                    }
                }
            }
        );
    }

    /**
     * 選手情報の登録／更新を行う.
     * @param dialog
     */
    private boolean execute(Dialog dialog) {
        if (!this.isValidInput(dialog)) return false;

        if (this.updateGameInfo == null) {
            // 試合情報登録
            addGameInfo(dialog);
        } else {
            // 試合情報更新
            updateGameInfo(dialog);
        }
        return true;
    }

    /**
     * 入力データの妥当性を確認する.
     * @param dialog
     * @return
     */
    private boolean isValidInput(Dialog dialog) {
        String strVal;
        // ---------------------------
        // 必須入力チェック
        // ---------------------------
        // 「試合名」
        strVal = ((TextView)dialog.findViewById(R.id.gameInfoName)).getText().toString();
        if (StringUtils.isEmpty(strVal)) {
            super.showRequiredInputMessage(R.string.dlg_view_002_game_name);
            return false;
        }
        // 「開催日」
        String gameDate = ((TextView)dialog.findViewById(R.id.gameInfoDate)).getText().toString();
        if (StringUtils.isEmpty(strVal)) {
            super.showRequiredInputMessage(R.string.dlg_view_002_game_date);
            return false;
        }
        // ---------------------------
        // 日付入力チェック
        // ---------------------------
        if (!DateTime.isDateFormat(gameDate)) {
            super.showDateFormatMessage(R.string.dlg_view_002_game_date);
            return false;
        }
        // ---------------------------
        // 時刻入力チェック
        // ---------------------------
        // 「開始時刻」
        strVal = ((TextView)dialog.findViewById(R.id.gameInfoStartTime)).getText().toString();
        if (StringUtils.isNotEmpty(strVal)) {
            if (!DateTime.isTimeFormat(strVal)) {
                super.showTimeFormatMessage(R.string.dlg_view_002_game_start_time);
            }
        }
        // 「終了時刻」
        strVal = ((TextView)dialog.findViewById(R.id.gameInfoEndTime)).getText().toString();
        if (StringUtils.isNotEmpty(strVal)) {
            if (!DateTime.isTimeFormat(strVal)) {
                super.showTimeFormatMessage(R.string.dlg_view_002_game_end_time);
            }
        }

        return true;
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
     * @param dialog
     */
    private void addGameInfo(Dialog dialog) {
        mNoticeDialogListener.addGameInfo(this.makeGameInfoDto(dialog));
    }

    /**
     * ダイアログビューから入力データ（ゲーム情報）を取得する.
     * @param dialog
     * @return
     */
    private GameInfoDto makeGameInfoDto(Dialog dialog) {
        // TODO 作成
        return null;
    }

    /**
     * チームのメンバー情報を更新する.
     * @param dialog
     */
    private void updateGameInfo(Dialog dialog) {
        GameInfoDto dto = this.makeGameInfoDto(dialog);
        dto.setGameId(this.updateGameInfo.getGameId());
        mNoticeDialogListener.updateGameInfo(dto);
    }
}