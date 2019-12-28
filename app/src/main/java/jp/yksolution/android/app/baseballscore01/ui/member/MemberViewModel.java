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

        memberList.add(TeamMemberDto.builder().name("加藤 康夫").build());
        memberList.add(TeamMemberDto.builder().name("イチロー").build());

        this.mTeamMembers.setValue(memberList);
    }
}