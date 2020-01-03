package jp.yksolution.android.app.baseballscore01.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import jp.yksolution.android.app.baseballscore01.R;

public class ConfirmationDialog extends DialogFragmentEx {
    public ConfirmationDialog() { super(); }

    private int messageFmtId;
    private Object[] values;
    private Object positiveParam;
    public ConfirmationDialog(int messageFmtId, Object[] values, Object positiveParam) {
        super();
        this.messageFmtId = messageFmtId;
        this.values = values;
        this.positiveParam = positiveParam;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String messageFmt = this.getResources().getString(this.messageFmtId);
        String message = String.format(messageFmt, this.values);
        return new AlertDialog.Builder(getActivity())
            .setTitle(R.string.DLG_TITLE_CONF)
            .setMessage(message)
            .setPositiveButton(R.string.DLG_CONF_YES, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    mNoticeDialogListener.forwardProcess(positiveParam);
                }
            })
            .setNegativeButton(R.string.DLG_CONF_NO, null)
            .create();
    }

    public interface NoticeDialogListener {
        void forwardProcess(Object obj);
    }
    private NoticeDialogListener mNoticeDialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mNoticeDialogListener = (NoticeDialogListener)super.getActiveFragment();
    }
}