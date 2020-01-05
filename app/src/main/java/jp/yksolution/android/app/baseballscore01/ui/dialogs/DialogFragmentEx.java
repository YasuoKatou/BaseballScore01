package jp.yksolution.android.app.baseballscore01.ui.dialogs;

import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import jp.yksolution.android.app.baseballscore01.R;

/**
 * ダイアログの基本クラス.
 * @author Y.Katou (YKSolution)
 */
public abstract class DialogFragmentEx extends DialogFragment {
    private static final String TAG = DialogFragmentEx.class.getSimpleName();

    /**
     * デフォルトのコンストラクタ
     */
    protected DialogFragmentEx() { super(); }

    /**
     * アクティブなフラグメントを判定するインターフェース.
     */
    protected interface NoticeDialogListenerBase {}

    /**
     * アクティブなフラグメントを取得する.
     * @return
     */
    protected Fragment getActiveFragment() {
        Fragment host = this.getFragmentManager().findFragmentById(R.id.nav_host_fragment);
        for (Fragment fragment : host.getChildFragmentManager().getFragments()) {
            if (fragment instanceof NoticeDialogListenerBase) {
                return fragment;
            }
        }
        throw new NoClassDefFoundError(TAG + " must implement NoticeDialogListener");
    }

    /**
     * 必須入力のメッセージを表示する.
     * @param itemId
     */
    protected void showRequiredInputMessage(int itemId) {
        this.showToastMessage(itemId, R.string.MSG_INP_ERR_002);
    }

    /**
     * 必須入力のメッセージを表示する.
     * @param itemId
     */
    protected void showDateFormatMessage(int itemId) {
        this.showToastMessage(itemId, R.string.MSG_INP_ERR_003);
    }

    /**
     * メッセージを編集し、メッセージを表示する.
     * @param itemId
     * @param formatId
     */
    protected void showToastMessage(int itemId, int formatId) {
        String msgParam = this.getResources().getString(itemId);
        String msgFmt   = this.getResources().getString(formatId);

        String message = String.format(msgFmt, msgParam);
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
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