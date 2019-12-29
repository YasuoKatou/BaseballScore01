package jp.yksolution.android.app.baseballscore01.ui.member;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
        memberViewModel =
                ViewModelProviders.of(this).get(MemberViewModel.class);
        View root = inflater.inflate(R.layout.fragment_member, container, false);

        this.listView = (ListView) root.findViewById(R.id.teamMemberList);
        adapter = new MemberListAdapter(this.getContext());
        memberViewModel.getTeamMembers(this).observe(this, new Observer<List<TeamMemberDto>>() {
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
    public void onDialogPositiveClick(TeamMemberDto teamMemberDto) {
        Log.d(TAG, teamMemberDto.toString());
        if (this.isValid(teamMemberDto)) {
            // ＤＢ登録エンティティを編集
            TeamMemberEntity entity = TeamMemberEntity.builder()
                    .name1(teamMemberDto.getName1())
                    .name2(teamMemberDto.getName2())
                    .sex(teamMemberDto.getSex())
                    .birthday(teamMemberDto.getBirthday().longValue())
                    .build();
            // ＤＢに登録
            TeamMemberDao dao = new TeamMemberDao(this);
            int count = dao.addTeamMember(entity);
            // 登録結果を確認
            String message;
            if (count == 1) {
                message = getResources().getString(R.string.MSG_DB_INS_OK);
            } else {
                message = getResources().getString(R.string.MSG_DB_INS_OK);
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
}