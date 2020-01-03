package jp.yksolution.android.app.baseballscore01.ui.dialogs;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import jp.yksolution.android.app.baseballscore01.R;

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
        throw new ClassCastException(TAG + " must implement NoticeDialogListener");
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