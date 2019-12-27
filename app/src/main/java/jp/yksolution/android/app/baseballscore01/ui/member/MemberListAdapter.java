package jp.yksolution.android.app.baseballscore01.ui.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jp.yksolution.android.app.baseballscore01.R;

public class MemberListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater = null;
    private List<TeamMemberEntity> mTeamMemberList = null;

    public MemberListAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setTeamMemberList(List<TeamMemberEntity> list) {
        this.mTeamMemberList = list;
    }

    @Override
    public int getCount() {
        if (this.mTeamMemberList == null) {
            return 0;
        } else {
            return this.mTeamMemberList.size();
        }
    }

    @Override
    public Object getItem(int index) {
        return this.mTeamMemberList.get(index);
    }

    @Override
    public long getItemId(int index) {
        return this.mTeamMemberList.get(index).getMemberId();
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        convertView = this.mLayoutInflater.inflate(R.layout.team_member_list_row,parent,false);

        TeamMemberEntity entity = (TeamMemberEntity)this.getItem(index);
        ((TextView)convertView.findViewById(R.id.memberName)).setText(entity.getName());

        return convertView;
    }
}