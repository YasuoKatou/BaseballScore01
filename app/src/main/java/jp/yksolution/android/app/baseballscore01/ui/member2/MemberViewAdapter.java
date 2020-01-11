package jp.yksolution.android.app.baseballscore01.ui.member2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jp.yksolution.android.app.baseballscore01.R;
import jp.yksolution.android.app.baseballscore01.ui.member.TeamMemberDto;

/**
 * チームメンバー一覧表示アダプタ.
 * @author Y.Katou (YKSolution)
 * @since 2020/01/11
 */
public class MemberViewAdapter extends RecyclerView.Adapter<MemberViewRowHolder> {

    private List<TeamMemberDto> teamMemberList;

    public MemberViewAdapter(List<TeamMemberDto> list) {
        this.teamMemberList = list;
    }

    @Override
    public MemberViewRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_member_list_row, parent,false);
        return new MemberViewRowHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MemberViewRowHolder holder, int position) {
        TeamMemberDto dto = this.teamMemberList.get(position);
        holder.getMemberId().setText(Long.toString(dto.getMemberId()));
        holder.getMemberName().setText(dto.getName());
        holder.getMemberAge().setText(Integer.toString(dto.getAge()));
    }

    @Override
    public int getItemCount() {
        return this.teamMemberList.size();
    }
}