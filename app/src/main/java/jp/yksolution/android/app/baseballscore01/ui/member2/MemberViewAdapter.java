package jp.yksolution.android.app.baseballscore01.ui.member2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jp.yksolution.android.app.baseballscore01.R;
import jp.yksolution.android.app.baseballscore01.ui.common.Const;
import jp.yksolution.android.app.baseballscore01.ui.member.TeamMemberDto;

/**
 * チームメンバー一覧表示アダプタ.
 * @author Y.Katou (YKSolution)
 * @since 2020/01/11
 */
public class MemberViewAdapter extends RecyclerView.Adapter<MemberViewRowHolder> {
    private static final String TAG = MemberViewAdapter.class.getSimpleName();

    public interface EventListener {
        void onClicked(TeamMemberDto dto, final int position);
    }

    private final EventListener parent;
    private List<TeamMemberDto> teamMemberList;
    private final Context context;

    public MemberViewAdapter(EventListener parent, List<TeamMemberDto> list, Context context) {
        this.parent = parent;
        this.teamMemberList = list;
        this.context = context;
    }

    @Override
    public MemberViewRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_member_list_row, parent,false);
        return new MemberViewRowHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MemberViewRowHolder holder, final int position) {
        final TeamMemberDto dto = this.teamMemberList.get(position);

        holder.getPositionCategory().setText(Const.getPositionCategoryName(this.context.getResources(),  dto.getPositionCategory()));
        holder.getMemberName().setText(dto.getName());
        holder.getMemberAge().setText(Integer.toString(dto.getAge()));

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d(TAG, "position : " + Integer.toString(position));
                parent.onClicked(dto, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.teamMemberList.size();
    }

    public ItemTouchHelper.SimpleCallback getSwipeCallback() {
        return new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 横にスワイプされたら要素を消す
                int position = viewHolder.getAdapterPosition();
                MemberViewAdapter.this.teamMemberList.remove(position);     // データ
                MemberViewAdapter.this.notifyItemRemoved(position);           // 一覧
            }
        };
    }
}