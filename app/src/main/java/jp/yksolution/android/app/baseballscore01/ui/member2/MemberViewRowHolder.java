package jp.yksolution.android.app.baseballscore01.ui.member2;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Member;

import jp.yksolution.android.app.baseballscore01.R;
import lombok.Getter;
import lombok.Setter;

/**
 * チームメンバー一覧表示コンポーネント.
 * @author Y.Katou (YKSolution)
 * @since 2020/01/11
 */
public class MemberViewRowHolder extends RecyclerView.ViewHolder {

    @Getter private final View itemView;
    @Getter private final TextView positionCategory;
    @Getter private final TextView memberName;
    @Getter private final TextView memberAge;

    /** メンバーID. */
    @Getter @Setter private Long memberId;

    public MemberViewRowHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        this.positionCategory = (TextView)itemView.findViewById(R.id.positionCategory);
        this.memberName = (TextView)itemView.findViewById(R.id.memberName);
        this.memberAge = (TextView)itemView.findViewById(R.id.memberAge);
    }
}