package jp.yksolution.android.app.baseballscore01.ui.member;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MemberViewModel extends ViewModel {
    private MutableLiveData<List<TeamMemberEntity>> mTeamMembers = null;
    public LiveData<List<TeamMemberEntity>> getTeamMembers() {
        if (this.mTeamMembers == null) {
            this.mTeamMembers = new MutableLiveData<List<TeamMemberEntity>>();
            this.loadTeamMembers();
        }
        return this.mTeamMembers;
    }

    private void loadTeamMembers() {
        List<TeamMemberEntity> memberList = new ArrayList<>();

        TeamMemberEntity entity = new TeamMemberEntity();
        entity.setMemberId(1L);
        entity.setName("加藤 康夫");
        memberList.add(entity);

        entity = new TeamMemberEntity();
        entity.setMemberId(2L);
        entity.setName("イチロー");
        memberList.add(entity);

        this.mTeamMembers.setValue(memberList);
    }
}