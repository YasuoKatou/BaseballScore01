package jp.yksolution.android.app.baseballscore01.ui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

import jp.yksolution.android.app.baseballscore01.R;
import jp.yksolution.android.app.baseballscore01.ui.common.Const;
import jp.yksolution.android.app.baseballscore01.ui.member.TeamMemberDto;
import jp.yksolution.android.app.baseballscore01.utils.DateTime;

/**
 * チームメンバー登録／更新ダイアログ
 * @author Y.Katou (YKSolution)
 */
public class TeamMemberDialog extends DialogFragmentEx {
    private static final String TAG = TeamMemberDialog.class.getSimpleName();

    public TeamMemberDialog() { super(); }

    /**
     * 更新対象のメンバー情報を設定するコンストラクタ
     */
    private TeamMemberDto updateMember = null;
    public TeamMemberDialog(TeamMemberDto updateMember) {
        this();
        this.updateMember = updateMember;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Activity activity = getActivity();
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.team_member_dialog, null);

        if (this.updateMember != null) {
            TeamMemberDto dto = this.updateMember;
            ((TextView)dialogView.findViewById(R.id.teamMemberName1)).setText(dto.getName1());
            ((TextView)dialogView.findViewById(R.id.teamMemberName2)).setText(dto.getName2());
            ((ToggleButton)dialogView.findViewById(R.id.teamMemberSex)).setChecked(
                (dto.getSex() == Const.SEX.GIRL) ? false : true);
            ((EditText)dialogView.findViewById(R.id.teamMemberBirthday)).setText(
                DateTime.getDate(dto.getBirthday()));

            Resources res = this.getResources();
            ((Spinner)dialogView.findViewById(R.id.position_category)).setSelection(
                Const.getPositionCategoryIndex(res, dto.getPositionCategory()));
            ((Spinner)dialogView.findViewById(R.id.pitching)).setSelection(
                Const.getPichingIndex(res, dto.getPitching()));
            ((Spinner)dialogView.findViewById(R.id.batting)).setSelection(
                    Const.getBattingIndex(res, dto.getBatting()));
            ((Spinner)dialogView.findViewById(R.id.memberStatus)).setSelection(
                    Const.getTeamMemberStatusIndex(res, dto.getStatus()));
        }

        AlertDialog dlg = new AlertDialog.Builder(activity)
            .setView(dialogView)
            .setTitle(R.string.opt_menu_team_member_append)
            .setPositiveButton((this.updateMember == null) ? R.string.DLG_ENTRY : R.string.DLG_UPDATE, null)
            .setNeutralButton(R.string.DLG_CLOSE, null)
            .create();
        if (this.updateMember != null) {
            String buttonText = getString(R.string.DLG_DELETE);
            dlg.setButton(AlertDialog.BUTTON_NEGATIVE, buttonText
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    // 選手情報削除
                    mNoticeDialogListener.deleteTeamMember(updateMember);
                    }
                }
            );
        }
        dlg.setCanceledOnTouchOutside(false);       // ダイアログの外側をタップしてもダイアログを消さない
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

        if (this.updateMember == null) {
            // 選手情報登録
            addTeamMember(dialog);
        } else {
            // 選手情報更新
            updateTeamMember(dialog);
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
        // 「選手名（姓）」
        strVal = ((TextView)dialog.findViewById(R.id.teamMemberName1)).getText().toString();
        if (StringUtils.isEmpty(strVal)) {
            super.showRequiredInputMessage(R.string.dlg_view_001_name1);
            return false;
        }
        // 「選手名（名）」
        strVal = ((TextView)dialog.findViewById(R.id.teamMemberName2)).getText().toString();
        if (StringUtils.isEmpty(strVal)) {
            super.showRequiredInputMessage(R.string.dlg_view_001_name2);
            return false;
        }
        // 「誕生日」
        String birthday = ((TextView)dialog.findViewById(R.id.teamMemberBirthday)).getText().toString();
        if (StringUtils.isEmpty(birthday)) {
            super.showRequiredInputMessage(R.string.dlg_view_001_birthday);
            return false;
        }

        // ---------------------------
        // 日付入力チェック
        // ---------------------------
        if (!DateTime.isDateFormat(birthday)) {
            super.showDateFormatMessage(R.string.dlg_view_001_birthday);
            return false;
        }
        return true;
    }

    public interface NoticeDialogListener extends NoticeDialogListenerBase {
        /**
         * 選手登録.
         * @param teamMemberDto
         */
        void addTeamMember(TeamMemberDto teamMemberDto);
        /**
         * 選手データ更新.
         * @param teamMemberDto
         */
        void updateTeamMember(TeamMemberDto teamMemberDto);
        /**
         * 選手データ削除
         * @param teamMemberDto
         */
        void deleteTeamMember(TeamMemberDto teamMemberDto);
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
    private void addTeamMember(Dialog dialog) {
        mNoticeDialogListener.addTeamMember(this.makeTeamMemberDto(dialog));
    }

    /**
     * ダイアログビューから入力データ（選手情報）を取得する.
     * @param dialog
     * @return
     */
    private TeamMemberDto makeTeamMemberDto(Dialog dialog) {
        String name1 = ((TextView)dialog.findViewById(R.id.teamMemberName1)).getText().toString();
        String name2 = ((TextView)dialog.findViewById(R.id.teamMemberName2)).getText().toString();
        int sex = (((ToggleButton)dialog.findViewById(R.id.teamMemberSex)).isChecked()) ? Const.SEX.BOY : Const.SEX.GIRL;
        String strBirthday = ((EditText)dialog.findViewById(R.id.teamMemberBirthday)).getText().toString();
        String status = (String)((Spinner)dialog.findViewById(R.id.memberStatus)).getSelectedItem();

        String positionCategory = (String)((Spinner)dialog.findViewById(R.id.position_category)).getSelectedItem();
        String piching = (String)((Spinner)dialog.findViewById(R.id.pitching)).getSelectedItem();
        String batting = (String)((Spinner)dialog.findViewById(R.id.batting)).getSelectedItem();

        Date birthDay;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
            birthDay = sdf.parse(strBirthday);
        } catch (Exception ex) {
            // 入力チェックを追加したので、処理不要！！（念のため残します）
            birthDay = null;
            Log.e(TAG, "日付エラー : " + strBirthday);
        }
        return TeamMemberDto.builder()
            .name1(name1)
            .name2(name2)
            .sex(sex)
            .birthday(birthDay.getTime())
            .positionCategory(Const.getPositionCategoryCodeByString(positionCategory))
            .pitching(Const.getPichingCodeByString(piching))
            .batting(Const.getBattingCodeByString(batting))
            .status(Const.getTeamMemberStatusByString(status))
            .build();
    }

    /**
     * チームのメンバー情報を更新する.
     * @param dialog
     */
    private void updateTeamMember(Dialog dialog) {
        TeamMemberDto dto = this.makeTeamMemberDto(dialog);
        dto.setMemberId(this.updateMember.getMemberId());
        mNoticeDialogListener.updateTeamMember(dto);
    }
}