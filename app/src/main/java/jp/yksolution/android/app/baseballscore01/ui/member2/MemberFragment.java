package jp.yksolution.android.app.baseballscore01.ui.member2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import jp.yksolution.android.app.baseballscore01.R;
import jp.yksolution.android.app.baseballscore01.db.DbHelper;
import jp.yksolution.android.app.baseballscore01.db.dao.TeamMemberDao;
import jp.yksolution.android.app.baseballscore01.db.entity.TeamMemberEntity;
import jp.yksolution.android.app.baseballscore01.ui.common.PopupMenuOperation;
import jp.yksolution.android.app.baseballscore01.ui.dialogs.ConfirmationDialog;
import jp.yksolution.android.app.baseballscore01.ui.dialogs.TeamMemberDialog;
import jp.yksolution.android.app.baseballscore01.ui.member.MemberViewModel;
import jp.yksolution.android.app.baseballscore01.ui.member.TeamMemberDto;

/**
 * チームメンバーフラグメント.
 * @author Y.Katou (YKSolution)
 * @since 2020/01/11
 */
public class MemberFragment extends Fragment
    implements PopupMenuOperation, TeamMemberDialog.NoticeDialogListener,
        ConfirmationDialog.NoticeDialogListener {

    private static final String TAG = MemberFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MemberViewModel memberViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.memberViewModel =
                ViewModelProviders.of(this).get(MemberViewModel.class);

        View root = inflater.inflate(R.layout.fragment_member_2, container, false);
        this.mRecyclerView = (RecyclerView)root.findViewById(R.id.memberFragment2);
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        this.memberViewModel.getTeamMembers(this).observe(this, new Observer<List<TeamMemberDto>>() {
            @Override
            public void onChanged(@Nullable List<TeamMemberDto> list) {
                MemberViewAdapter adapter = new MemberViewAdapter(list);
                mRecyclerView.setAdapter(adapter);
            }
        });

        setHasOptionsMenu(true);        // フラグメントで onOptionsItemSelected を受ける設定

        return root;
    }

    /**
     * for PopupMenuOperation
     * @param menu
     */
    @Override
    public void setupMenu(Menu menu) {
        menu.setGroupVisible(R.id.optMenuTeamMember, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.teamMemberAppend:
                // 選手登録メニュー選択
                this.addTeamMember();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 選手登録するダイアログを開く
     */
    private void addTeamMember() {
        TeamMemberDialog dlg = new TeamMemberDialog();
        dlg.show(getActivity().getSupportFragmentManager(), dlg.getTag());
    }

    /**
     * チームメンバーの登録処理を行う.<br/>
     * for TeamMemberDialog.NoticeDialogListener
     * @param teamMemberDto
     */
    @Override
    public void addTeamMember(TeamMemberDto teamMemberDto) {
        Log.d(TAG, "append info : " + teamMemberDto.toString());

        // ＤＢに登録
        TeamMemberDao teamMemberDao = DbHelper.getInstance().getDb().teamMemberDao();
        TeamMemberEntity entity = this.makeTeamMemberEntity(teamMemberDto);
        entity.prepreForInsert();
        String message;
        try {
             teamMemberDao.addTeamMember(entity);
             // 登録後の一覧を更新するため再読み込み
             this.memberViewModel.refreshTeamMembers();
             message = this.getResources().getString(R.string.MSG_DB_INS_OK);
        } catch (Exception ex) {
             ex.printStackTrace();
             message = this.getResources().getString(R.string.MSG_DB_INS_NG);
        }
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * ＤＢに登録／更新する選手情報エンティティを編集する.
     * @param teamMemberDto
     * @return
     */
    private TeamMemberEntity makeTeamMemberEntity(TeamMemberDto teamMemberDto) {
        TeamMemberEntity entity = new TeamMemberEntity();
        entity.setMemberId(teamMemberDto.getMemberId());
        entity.setName1(teamMemberDto.getName1());
        entity.setName2(teamMemberDto.getName2());
        entity.setSex(teamMemberDto.getSex());
        entity.setBirthday(teamMemberDto.getBirthday());
        entity.setPositionCategory(teamMemberDto.getPositionCategory());
        entity.setPitching(teamMemberDto.getPitching());
        entity.setBatting(teamMemberDto.getBatting());
        entity.setStatus(teamMemberDto.getStatus());
        entity.setNewDateTime(teamMemberDto.getNewDateTime());
        entity.setUpdateDateTime(teamMemberDto.getUpdateDateTime());
        entity.setVersionNo(teamMemberDto.getVersionNo());
        return entity;
    }

    /**
     * チームメンバーの更新処理を行う.<br/>
     * for TeamMemberDialog.NoticeDialogListener
     * @param teamMemberDto
     */
    @Override
    public void updateTeamMember(TeamMemberDto teamMemberDto) {
        Log.d(TAG, "update info : " + teamMemberDto.toString());

        // ＤＢを更新
        TeamMemberDao teamMemberDao = DbHelper.getInstance().getDb().teamMemberDao();
        TeamMemberEntity entity = this.makeTeamMemberEntity(teamMemberDto);
        entity.prepreForUpdate();
        int count = teamMemberDao.updateTeamMember(entity);
        // 更新結果を確認
        String message;
        if (count == 1) {
            // 更新後の一覧を更新するため再読み込み
            this.memberViewModel.refreshTeamMembers();
            message = getResources().getString(R.string.MSG_DB_UPD_OK);
        } else {
            message = getResources().getString(R.string.MSG_DB_UPD_NG);
        }
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * チームメンバーの削除処理を行う.<br/>
     * for TeamMemberDialog.NoticeDialogListener
     * @param teamMemberDto
     */
    @Override
    public void deleteTeamMember(TeamMemberDto teamMemberDto) {
        String[] params = new String[]{teamMemberDto.getName()};
        ConfirmationDialog dlg = new ConfirmationDialog(R.string.MSG_CONG_001, params, teamMemberDto);
        dlg.show(getActivity().getSupportFragmentManager(), dlg.getTag());
    }

    /**
     * チームメンバーの削除処理を行う.<br/>
     * for ConfirmationDialog.NoticeDialogListener
     */
    @Override
    public void forwardProcess(Object obj) {
        Log.d(TAG, "delete Member info : " + obj.toString());
        TeamMemberDto dto = (TeamMemberDto)obj;

        // ＤＢから削除
        TeamMemberDao teamMemberDao = DbHelper.getInstance().getDb().teamMemberDao();
        int count = teamMemberDao.deleteTeamMember(dto.getMemberId());
        // 削除結果を確認
        int messageFmtId;
        if (count == 1) {
            // 更新後の一覧を更新するため再読み込み
            this.memberViewModel.refreshTeamMembers();
            messageFmtId = R.string.MSG_DB_DLT_OK;
        } else {
            messageFmtId = R.string.MSG_DB_DLT_NG;
        }
        String msgFrm = this.getResources().getString(messageFmtId);
        String message = String.format(msgFrm, dto.getName());
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }
}