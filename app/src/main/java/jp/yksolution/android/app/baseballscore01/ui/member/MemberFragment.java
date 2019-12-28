package jp.yksolution.android.app.baseballscore01.ui.member;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import jp.yksolution.android.app.baseballscore01.R;
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
        memberViewModel.getTeamMembers().observe(this, new Observer<List<TeamMemberDto>>() {
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
     * for TeamMemberDialog.NoticeDialogListener
     * @param teamMemberDto
     */
    @Override
    public void onDialogPositiveClick(TeamMemberDto teamMemberDto) {
        Log.d(TAG, teamMemberDto.toString());
    }
}