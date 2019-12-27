package jp.yksolution.android.app.baseballscore01.ui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import jp.yksolution.android.app.baseballscore01.R;

public class TeamMemberDialog extends DialogFragment {
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

    private void addMember(View dialogView) {

    }
}