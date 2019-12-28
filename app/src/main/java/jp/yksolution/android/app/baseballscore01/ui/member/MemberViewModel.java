package jp.yksolution.android.app.baseballscore01.ui.member;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MemberViewModel extends ViewModel {
    private MutableLiveData<List<TeamMemberDto>> mTeamMembers = null;
    public LiveData<List<TeamMemberDto>> getTeamMembers() {
        if (this.mTeamMembers == null) {
            this.mTeamMembers = new MutableLiveData<List<TeamMemberDto>>();
            this.loadTeamMembers();
        }
        return this.mTeamMembers;
    }

    private void loadTeamMembers() {
        List<TeamMemberDto> memberList = new ArrayList<>();

        TeamMemberDto entity = new TeamMemberDto();
        entity.setMemberId(1L);
        entity.setName("加藤 康夫");
        memberList.add(entity);

        entity = new TeamMemberDto();
        entity.setMemberId(2L);
        entity.setName("イチロー");
        memberList.add(entity);

        this.mTeamMembers.setValue(memberList);
    }
}