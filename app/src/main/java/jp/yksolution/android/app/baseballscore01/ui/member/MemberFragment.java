package jp.yksolution.android.app.baseballscore01.ui.member;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import jp.yksolution.android.app.baseballscore01.R;
import jp.yksolution.android.app.baseballscore01.db.dao.TeamMemberDao;
import jp.yksolution.android.app.baseballscore01.db.entity.TeamMemberEntity;
import jp.yksolution.android.app.baseballscore01.ui.common.PopupMenuOperation;
import jp.yksolution.android.app.baseballscore01.ui.dialogs.TeamMemberDialog;

public class MemberFragment extends Fragment
    implements PopupMenuOperation, TeamMemberDialog.NoticeDialogListener {
    private static final String TAG = MemberFragment.class.getSimpleName();

    private MemberViewModel memberViewModel;
    private ListView listView;
    private MemberListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.memberViewModel =
                ViewModelProviders.of(this).get(MemberViewModel.class);
        View root = inflater.inflate(R.layout.fragment_member, container, false);

        this.listView = (ListView) root.findViewById(R.id.teamMemberList);
        this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            /**
             * ロングタップで登録選手を選択し、更新画面を開く
             * @param parent
             * @param view
             * @param position 一覧の行インデックス
             * @param id 選手ID
             * @return
             */
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,View view, int position, long id) {
                showUpdateDialog(position);
                return true;
            }
        });
        this.adapter = new MemberListAdapter(this.getContext());
        this.memberViewModel.getTeamMembers(this).observe(this, new Observer<List<TeamMemberDto>>() {
            @Override
            public void onChanged(@Nullable List<TeamMemberDto> list) {
                adapter.setTeamMemberList(list);
                listView.setAdapter(adapter);
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
     * 登録選手を更新するダイアログを開く
     * @param rowIndex 一覧の行インデックス
     */
    private void showUpdateDialog(int rowIndex) {
        TeamMemberDto dto = (TeamMemberDto)this.adapter.getItem(rowIndex);
        Log.d(TAG, dto.toString());
        TeamMemberDialog dlg = new TeamMemberDialog(dto);
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
        if (this.isValid(teamMemberDto)) {
            // ＤＢ登録エンティティを編集
            TeamMemberEntity entity = this.makeTeamMemberEntity(teamMemberDto);
            // ＤＢに登録
            TeamMemberDao dao = new TeamMemberDao(this);
            int count = dao.addTeamMember(entity);
            // 登録結果を確認
            String message;
            if (count == 1) {
                // 登録後の一覧を更新するため再読み込み
                this.memberViewModel.refreshTeamMembers();
                message = getResources().getString(R.string.MSG_DB_INS_OK);
            } else {
                message = getResources().getString(R.string.MSG_DB_INS_NG);
            }
            Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
        } else {
            // 入力に誤りあり
            String message = getResources().getString(R.string.MSG_INP_ERR_001);
            Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * ＤＢに登録／更新する選手情報エンティティを編集する.
     * @param teamMemberDto
     * @return
     */
    private TeamMemberEntity makeTeamMemberEntity(TeamMemberDto teamMemberDto) {
        return TeamMemberEntity.builder()
            .memberId(teamMemberDto.getMemberId())
            .name1(teamMemberDto.getName1())
            .name2(teamMemberDto.getName2())
            .sex(teamMemberDto.getSex())
            .birthday(teamMemberDto.getBirthday().longValue())
            .positionCategory(teamMemberDto.getPositionCategory())
            .pitching(teamMemberDto.getPitching())
            .batting(teamMemberDto.getBatting())
            .build();
    }

    /**
     * チームメンバーの更新処理を行う.<br/>
     * for TeamMemberDialog.NoticeDialogListener
     * @param teamMemberDto
     */
    @Override
    public void updateTeamMember(TeamMemberDto teamMemberDto) {
        Log.d(TAG, "update info : " + teamMemberDto.toString());
        if (this.isValid(teamMemberDto)) {
            // ＤＢ更新エンティティを編集
            TeamMemberEntity entity = this.makeTeamMemberEntity(teamMemberDto);
            // ＤＢを更新
            TeamMemberDao dao = new TeamMemberDao(this);
            int count = dao.updateTeamMember(entity);
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
        } else {
            // 入力に誤りあり
            String message = getResources().getString(R.string.MSG_INP_ERR_001);
            Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 入力項目のチェック（未入力ＮＧ）
     * @param dto
     * @return true：未入力項目なし
     */
    private boolean isValid(TeamMemberDto dto) {
        if (StringUtils.isEmpty(dto.getName1())) return false;
        if (StringUtils.isEmpty(dto.getName2())) return false;
        if (dto.getBirthday() == null) return false;
        return true;
    }

    /**
     * チームメンバーの削除処理を行う.<br/>
     * for TeamMemberDialog.NoticeDialogListener
     * @param teamMemberId
     */
    @Override
    public void deleteTeamMember(long teamMemberId, String name) {
        Log.d(TAG, "delete Member ID : " + Long.toString(teamMemberId));

        // ＤＢから削除
        TeamMemberDao dao = new TeamMemberDao(this);
        int count = dao.deleteTeamMember(teamMemberId);
        // 削除結果を確認
        int messageFmtId;
        if (count == 1) {
            // 更新後の一覧を更新するため再読み込み
            this.memberViewModel.refreshTeamMembers();
            messageFmtId = R.string.MSG_DB_DLT_OK;
        } else {
            messageFmtId = R.string.MSG_DB_DLT_NG;
        }
        String msgFrm = getResources().getString(messageFmtId);
        String message = String.format(msgFrm, name);
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }
}