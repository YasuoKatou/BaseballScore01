package jp.yksolution.android.app.baseballscore01.ui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.yksolution.android.app.baseballscore01.R;
import jp.yksolution.android.app.baseballscore01.ui.common.Const;
import jp.yksolution.android.app.baseballscore01.ui.member.TeamMemberDto;

public class TeamMemberDialog extends DialogFragment {
    private static final String TAG = TeamMemberDialog.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Activity activity = getActivity();
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.team_member_dialog, null);

        return new AlertDialog.Builder(activity)
            .setView(dialogView)
            .setTitle(R.string.opt_menu_team_member_append)
            .setPositiveButton(R.string.DLG_ENTRY
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addMember(dialogView);
                    }
                })
            .create();
    }

    public interface NoticeDialogListener {
        void onDialogPositiveClick(TeamMemberDto teamMemberDto);
    }
    private NoticeDialogListener mNoticeDialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment host = this.getFragmentManager().findFragmentById(R.id.nav_host_fragment);
        for (Fragment fragment : host.getChildFragmentManager().getFragments()) {
            if (fragment instanceof NoticeDialogListener) {
                mNoticeDialogListener = (NoticeDialogListener) fragment;
                return;
            }
        }
        throw new ClassCastException(TAG + " must implement NoticeDialogListener");
    }

    /**
     * チームのメンバーを登録する
     * @param dialogView
     */
    private void addMember(View dialogView) {
        String name1 = ((TextView)dialogView.findViewById(R.id.teamMemberName1)).getText().toString();
        String name2 = ((TextView)dialogView.findViewById(R.id.teamMemberName2)).getText().toString();
        int sex = (((ToggleButton)dialogView.findViewById(R.id.teamMemberSex)).isChecked()) ? Const.SEX.BOY : Const.SEX.GIRL;
        String strBirthday = ((EditText)dialogView.findViewById(R.id.teamMemberBirthday)).getText().toString();
//        Log.d(TAG, "name1    : " + name1);
//        Log.d(TAG, "name2    : " + name2);
//        Log.d(TAG, "sex      : " + sex);
//        Log.d(TAG, "birthday : " + strBirthday);
        Date birthDay;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
            birthDay = sdf.parse(strBirthday);
        } catch (Exception ex) {
            birthDay = null;
            Log.e(TAG, "日付エラー : " + strBirthday);
        }
        mNoticeDialogListener.onDialogPositiveClick(
            TeamMemberDto.builder()
                .name1(name1)
                .name2(name2)
                .sex(sex)
                .birthday((birthDay == null) ? null : birthDay.getTime())
                .build());
    }

    /**
     * おまじない
     * 【コピペしてすぐ使えるアラートダイアログ集】より
     * （https://qiita.com/suzukihr/items/8973527ebb8bb35f6bb8）
     */
    @Override
    public void onPause() {
        super.onPause();
        // onPause でダイアログを閉じる場合
        dismiss();
    }
}