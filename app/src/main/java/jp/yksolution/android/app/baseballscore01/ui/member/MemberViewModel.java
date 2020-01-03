package jp.yksolution.android.app.baseballscore01.ui.member;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import jp.yksolution.android.app.baseballscore01.db.DbHelper;
import jp.yksolution.android.app.baseballscore01.db.dao.TeamMemberDao;
import jp.yksolution.android.app.baseballscore01.db.entity.TeamMemberEntity;

public class MemberViewModel extends ViewModel {
    private MutableLiveData<List<TeamMemberDto>> mTeamMembers = null;
    private Fragment mFragment = null;
    public LiveData<List<TeamMemberDto>> getTeamMembers(Fragment fragment) {
        this.mFragment = fragment;
        if (this.mTeamMembers == null) {
            this.mTeamMembers = new MutableLiveData<List<TeamMemberDto>>();
            this.loadTeamMembers();
        }
        return this.mTeamMembers;
    }

    public void refreshTeamMembers() {
        this.loadTeamMembers();
    }

    private void loadTeamMembers() {
        List<TeamMemberDto> memberList = new ArrayList<>();
        TeamMemberDao teamMemberDao = DbHelper.getInstance().getDb().teamMemberDao();
        for (TeamMemberEntity entity : teamMemberDao.getTeamMemberList()) {
            memberList.add(TeamMemberDto.builder()
                .memberId(entity.getMemberId())
                .name1(entity.getName1())
                .name2(entity.getName2())
                .sex(entity.getSex())
                .birthday(entity.getBirthday())
                .positionCategory(entity.getPositionCategory())
                .pitching(entity.getPitching())
                .batting(entity.getBatting())
                .status(entity.getStatus())
                .newDateTime(entity.getNewDateTime())
                .updateDateTime(entity.getUpdateDateTime())
                .versionNo(entity.getVersionNo())
                .build());
        }

        this.mTeamMembers.setValue(memberList);
    }
}