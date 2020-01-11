package jp.yksolution.android.app.baseballscore01.ui.member2;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import jp.yksolution.android.app.baseballscore01.R;
import lombok.Getter;

/**
 * チームメンバー一覧表示コンポーネント.
 * @author Y.Katou (YKSolution)
 * @since 2020/01/11
 */
public class MemberViewRowHolder extends RecyclerView.ViewHolder {

    @Getter private final TextView memberId;
    @Getter private final TextView memberName;
    @Getter private final TextView memberAge;

    public MemberViewRowHolder(View itemView) {
        super(itemView);
        this.memberId = (TextView) itemView.findViewById(R.id.memberId);
        this.memberName = (TextView) itemView.findViewById(R.id.memberName);
        this.memberAge = (TextView) itemView.findViewById(R.id.memberAge);
    }
}