package jp.yksolution.android.app.baseballscore01.ui.game.starter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.yksolution.android.app.baseballscore01.R;
import jp.yksolution.android.app.baseballscore01.ui.common.Const;
import jp.yksolution.android.app.baseballscore01.ui.member.MemberViewModel;
import jp.yksolution.android.app.baseballscore01.ui.member.TeamMemberDto;

/**
 * スタメン設定フラグメント
 * @author Y.Katou (YKSolution)
 * @since 2020/02/01
 */
public class GameStarterFragment extends Fragment {
    private static final String TAG = GameStarterFragment.class.getSimpleName();

    /**
     * スタメンポジションのスピナーアダプター
     */
    public class GamePositionAdapter extends ArrayAdapter<String> {

        private class ListItem {
            private int positionCategory;
            private String shortName;
            private String value;
            private boolean isFree;
        }

        Context mContext;
        private List<ListItem> positionList = new ArrayList<>();
        public GamePositionAdapter(Context context) {
            super(context, android.R.layout.simple_spinner_item);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            this.mContext = context;

            // 守備のポジションを初期設定
            String[] strings = this.mContext.getResources().getStringArray(R.array.game_position_list);
            for (int index = 0; index < strings.length; ++index) {
                ListItem item = new ListItem();
                String[] wk = strings[index].split(",");
                item.positionCategory = Integer.valueOf(wk[0]);
                item.shortName = wk[1];
                item.value = wk[2];
                item.isFree = true;
                this.positionList.add(item);
            }
        }

        /**
         * ドロップダウンリストの要素数を取得する.
         * @return 未選択＋９ポジションの計１０
         */
        @Override
        public int getCount() {
            return this.positionList.size() + 1;
        }

        /**
         * ドロップダウンリストに表示する文字列を取得する.
         * @param position リストのポジション
         * @return position = 0 の時、空文字列／position > 0 の時、ポジション名
         */
        @Override
        public String getItem(int position) {
            if (position == 0) {
                // 一覧の先頭は、未選択用の空文字列
                return "";
            }
            return this.positionList.get(position - 1).value;
        }

        private void setBackground(final TextView view, ListItem listItem) {
            GradientDrawable color = null;
            if (listItem != null) {
                color = new GradientDrawable();
                color.setColor(Const.getPositionCategoryColor(this.mContext, listItem.positionCategory));
            }
            view.setBackground(color);
        }

        /**
         * スピナーに表示するビューを取得する.
         * @param position 選択したドロップダウンリストのポジション
         * @param convertView
         * @param parent
         * @return TextView
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView)super.getView(position, convertView, parent);
            String str;
            ListItem item;
            if (position > 0) {
                item = this.positionList.get(position - 1);
                str = item.shortName;
            } else {
                str = "";
                item = null;
            }
            this.setBackground(textView, item);
            textView.setText(str);
            return textView;
        }

        /**
         * ドロップダウンリストに表示するビューを取得する.
         * @param position ドロップダウンリストのポジション
         * @param convertView
         * @param parent
         * @return TextView
         */
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView)super.getDropDownView(position, convertView, parent);
            textView.setText(this.getItem(position));
//            int visible;
            int color;
            if (this.isEnabled(position)) {
//                visible = View.VISIBLE;
                color = ContextCompat.getColor(this.mContext, R.color.enable);
            } else {
//                visible = View.GONE;        // TODO 詰めたい
                color = ContextCompat.getColor(this.mContext, R.color.disable);
            }
//            textView.setVisibility(visible);
            textView.setTextColor(color);
            if (position > 0) {
                this.setBackground(textView, this.positionList.get(position - 1));
            } else {
                this.setBackground(textView, null);
            }
            return textView;
        }

        /**
         * ドロップダウンリストの項目が有効かを取得する.
         * @param position ドロップダウンリストのポジション
         * @return 有効の場合、true
         */
        @Override
        public boolean isEnabled(int position) {
            if (position == 0) return true;
            return this.positionList.get(position - 1).isFree;
        }

        private Map<View, ListItem> selectedItemMap = new HashMap<>();
        public void selectedItem(final View view, final int position) {
            ListItem selectedItem = this.selectedItemMap.get(view);
            if (position > 0) {
                ListItem item = this.positionList.get(position - 1);
                item.isFree = false;
                this.selectedItemMap.put(view, item);
            }
            if (selectedItem != null) selectedItem.isFree = true;
        }
    }

    /**
     * スタメンメンバーのスピナーアダプター
     */
    public class PlayerAdapter extends ArrayAdapter<String> {

        private class ListItem {
            private Long memberId;
            private String value;
            private Integer positionCategory;
            private boolean isFree;
        }

        Context mContext;
        private List<ListItem> playerList = new ArrayList<>();
        public PlayerAdapter(Context context, List<TeamMemberDto> memberList) {
            super(context, android.R.layout.simple_spinner_item);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.mContext = context;

            for (TeamMemberDto member: memberList) {
                ListItem item = new ListItem();
                item.memberId = member.getMemberId();
                item.value = member.getName();
                item.positionCategory = member.getPositionCategory();
                item.isFree = true;
                this.playerList.add(item);
            }
        }

        /**
         * ドロップダウンリストの要素数を取得する.
         * @return 未選択＋プレーヤ数
         */
        @Override
        public int getCount() {
            return this.playerList.size() + 1;
        }

        /**
         * ドロップダウンリストに表示する文字列を取得する.
         * @param position リストのポジション
         * @return position = 0 の時、空文字列／position > 0 の時、選手名
         */
        @Override
        public String getItem(int position) {
            if (position == 0) {
                // 一覧の先頭は、未選択用の空文字列
                return "";
            }
            return this.playerList.get(position - 1).value;
        }

        private void setBackground(final TextView view, ListItem listItem) {
            GradientDrawable color = null;
            if (listItem != null) {
                color = new GradientDrawable();
                color.setColor(Const.getPositionCategoryColor(this.mContext, listItem.positionCategory));
            }
            view.setBackground(color);
        }

        /**
         * スピナーに表示するビューを取得する.
         * @param position 選択したドロップダウンリストのポジション
         * @param convertView
         * @param parent
         * @return TextView
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView)super.getView(position, convertView, parent);
            String str;
            ListItem item;
            if (position > 0) {
                item = this.playerList.get(position - 1);
                str = item.value;
            } else {
                str = "";
                item = null;
            }
            this.setBackground(textView, item);
            textView.setText(str);
            return textView;
        }

        /**
         * ドロップダウンリストに表示するビューを取得する.
         * @param position ドロップダウンリストのポジション
         * @param convertView
         * @param parent
         * @return TextView
         */
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView)super.getDropDownView(position, convertView, parent);
            textView.setText(this.getItem(position));
            int color;
            if (this.isEnabled(position)) {
                color = ContextCompat.getColor(this.mContext, R.color.enable);
            } else {
                color = ContextCompat.getColor(this.mContext, R.color.disable);
            }
            textView.setTextColor(color);
            if (position > 0) {
                this.setBackground(textView, this.playerList.get(position - 1));
            } else {
                this.setBackground(textView, null);
            }
            return textView;
        }

        /**
         * ドロップダウンリストの項目が有効かを取得する.
         * @param position ドロップダウンリストのポジション
         * @return 有効の場合、true
         */
        @Override
        public boolean isEnabled(int position) {
            if (position == 0) return true;
            return this.playerList.get(position - 1).isFree;
        }

        private Map<View, ListItem> selectedItemMap = new HashMap<>();
        public void selectedItem(final View view, final int position) {
            ListItem selectedItem = this.selectedItemMap.get(view);
            if (position > 0) {
                ListItem item = this.playerList.get(position - 1);
                item.isFree = false;
                this.selectedItemMap.put(view, item);
            }
            if (selectedItem != null) selectedItem.isFree = true;
        }
    }

    private MemberViewModel memberViewModel;

    private GamePositionAdapter mGamePositionAdapter;
    private PlayerAdapter mPlayerAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_game_starter, container, false);
        final Context context = this.getContext();

        this.memberViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication()).create(MemberViewModel.class);
        this.memberViewModel.getTeamMembers(this).observe(this, new Observer<List<TeamMemberDto>>() {
            @Override
            public void onChanged(@Nullable List<TeamMemberDto> list) {
                mPlayerAdapter = new PlayerAdapter(context, list);
                initPlayerSpinner(root, R.id.starter_1);
                initPlayerSpinner(root, R.id.starter_2);
                initPlayerSpinner(root, R.id.starter_3);
                initPlayerSpinner(root, R.id.starter_4);
                initPlayerSpinner(root, R.id.starter_5);
                initPlayerSpinner(root, R.id.starter_6);
                initPlayerSpinner(root, R.id.starter_7);
                initPlayerSpinner(root, R.id.starter_8);
                initPlayerSpinner(root, R.id.starter_9);
            }
        });

        this.mGamePositionAdapter = new GamePositionAdapter(context);
        this.initPositionSpinner(root, R.id.game_position_1);
        this.initPositionSpinner(root, R.id.game_position_2);
        this.initPositionSpinner(root, R.id.game_position_3);
        this.initPositionSpinner(root, R.id.game_position_4);
        this.initPositionSpinner(root, R.id.game_position_5);
        this.initPositionSpinner(root, R.id.game_position_6);
        this.initPositionSpinner(root, R.id.game_position_7);
        this.initPositionSpinner(root, R.id.game_position_8);
        this.initPositionSpinner(root, R.id.game_position_9);

        return root;
    }

    private AdapterView.OnItemSelectedListener mOnPositionItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onNothingSelected(AdapterView adapterView) {
            Log.d(TAG, "onNothingSelected");
        }
        @Override
        public void onItemSelected(AdapterView parent, View view, int position, long id) {
//            Log.d(TAG, "onItemSelected (position:" + position + ", id:" + id + ")");
            mGamePositionAdapter.selectedItem(parent, position);
        }
    };

    private AdapterView.OnItemSelectedListener mOnPlayerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onNothingSelected(AdapterView adapterView) {
            Log.d(TAG, "onNothingSelected");
        }
        @Override
        public void onItemSelected(AdapterView parent, View view, int position, long id) {
            mPlayerAdapter.selectedItem(parent, position);
        }
    };
//
//    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Log.d(TAG, "onItemClick");
//        }
//    };

    private void initPositionSpinner(View root, int resource) {
        Spinner spinner = (Spinner)root.findViewById(resource);
        spinner.setSelection(-1);
        spinner.setAdapter(this.mGamePositionAdapter);
        spinner.setOnItemSelectedListener(this.mOnPositionItemSelectedListener);
    }

    private void initPlayerSpinner(View root, int resource) {
        Spinner spinner = (Spinner)root.findViewById(resource);
        spinner.setSelection(-1);
        spinner.setAdapter(this.mPlayerAdapter);
        spinner.setOnItemSelectedListener(this.mOnPlayerItemSelectedListener);
    }
}