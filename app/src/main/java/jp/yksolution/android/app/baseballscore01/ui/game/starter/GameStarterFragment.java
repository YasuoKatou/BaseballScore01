package jp.yksolution.android.app.baseballscore01.ui.game.starter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import jp.yksolution.android.app.baseballscore01.ui.game.info.GameInfoDto;
import jp.yksolution.android.app.baseballscore01.ui.game.info.GameInfoViewModel;
import jp.yksolution.android.app.baseballscore01.ui.member.MemberViewModel;
import jp.yksolution.android.app.baseballscore01.ui.member.TeamMemberDto;
import jp.yksolution.android.app.baseballscore01.utils.DateTime;
import lombok.Builder;
import lombok.ToString;

/**
 * スタメン設定フラグメント
 * @author Y.Katou (YKSolution)
 * @since 2020/02/01
 */
public class GameStarterFragment extends Fragment {
    private static final String TAG = GameStarterFragment.class.getSimpleName();

    @Builder
    @ToString
    private static class PositionItem {
        private int positionCategory;
        private int positionId;
        private String shortName;
        private String value;
        private boolean isFree;
    }

    /**
     * スタメンポジションのスピナーアダプター
     */
    public class GamePositionAdapter extends ArrayAdapter<String> {

        Context mContext;
        private List<PositionItem> positionList = new ArrayList<>();
        public GamePositionAdapter(Context context, List<GameStarterViewModel.GamePositionItem> gamePositionList) {
            super(context, android.R.layout.simple_spinner_item);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            this.mContext = context;

            // 守備のポジションを初期設定
            for (GameStarterViewModel.GamePositionItem item : gamePositionList) {
                this.positionList.add(PositionItem.builder()
                    .positionCategory(item.getPositionCategory())
                    .shortName(item.getShortName())
                    .value(item.getValue())
                    .positionId(item.getPositionId())
                    .isFree(true)
                    .build());
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

        private void setBackground(final TextView view, PositionItem listItem) {
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
            PositionItem item;
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

        private Map<View, PositionItem> selectedItemMap = new HashMap<>();
        public void selectedItem(final View view, final int position) {
            PositionItem selectedItem = this.selectedItemMap.get(view);
            if (position > 0) {
                PositionItem item = this.positionList.get(position - 1);
                item.isFree = false;
                this.selectedItemMap.put(view, item);
            }
            if (selectedItem != null) selectedItem.isFree = true;
        }

        public PositionItem getPositionItem(final View view) {
            return this.selectedItemMap.get(view);
        }
    }

    /**
     * スタメンメンバーのスピナーアダプター
     */
    public class PlayerAdapter extends ArrayAdapter<String> {

        private class ListItem {
            private long memberId;
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
                item.memberId = member.getMemberId().longValue();
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

        public Map<Integer, PlayerAdapter.ListItem> getStartingMember() {
            Map<Integer, PlayerAdapter.ListItem> list = new HashMap<>();
            for (int index = 0; index < player_view.length; ++index) {
                PlayerAdapter.ListItem item = this.selectedItemMap.get(player_view[index]);
                if (item != null) {
                    list.put(Integer.valueOf(index + 1), item);
                }
            }
            return list;
        }
    }

    private MemberViewModel memberViewModel;
    private GameInfoViewModel gameInfoViewModel;
    private GameStarterViewModel gameStarterViewModel;
    private final List<GameInfoDto> gameList = new ArrayList<>();

    private GamePositionAdapter mGamePositionAdapter;
    private PlayerAdapter mPlayerAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_game_starter, container, false);
        final Context context = this.getContext();

        gameNameTextView = (TextView)root.findViewById(R.id.game_name);

        // 試合の選択ボタン
        final Button button = (Button)root.findViewById(R.id.game_select_button);
        button.setEnabled(false);

        // スタメン登録ボタン
        Button saveBotton = (Button)root.findViewById(R.id.starter_set_button);
        saveBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // スタメン登録
                saveStarter();
            }
        });

        this.gameInfoViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication()).create(GameInfoViewModel.class);
        this.gameInfoViewModel.getGameInfos(this).observe(this, new Observer<List<GameInfoDto>>() {
            @Override
            public void onChanged(@Nullable List<GameInfoDto> list) {
                long today = DateTime.getTodayDate();
                int games = 0;
                for (GameInfoDto dto : list) {
                    // 未来（今日以降）の試合を取り出す
                    if (dto.getGameDate() >= today) {
                        ++games;
                        gameList.add(dto);
                    }
                }
                if (games > 0) {
                    // TODO 時系列に並び替える
                    // 登録可能な試合が存在する場合、試合を選択するボタンの設定
                    button.setEnabled(true);
                    // ボタンクリックイベントリスナーを登録
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 試合を選択する
                            selectGame();
                        }
                    });
                } else {
                    // 試合名に試合が登録されていない旨のメッセージを表示する
                    String msg = context.getResources().getString(R.string.MSG_INP_ERR_101);
                    gameNameTextView.setText(msg);
                }
            }
        });

        this.gameStarterViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication()).create(GameStarterViewModel.class);
        this.gameStarterViewModel.getGameStartterDatas(this).observe(this, new Observer<GameStarterViewModel.GameStartterDatas>() {
            @Override
            public void onChanged(@Nullable GameStarterViewModel.GameStartterDatas datas) {
                // ポジション情報の展開
                mGamePositionAdapter = new GamePositionAdapter(context, datas.getGamePositionList());
                for (int index = 0; index < game_position_view_id.length; ++index) {
                    game_position_view[index] = initPositionSpinner(root, game_position_view_id[index]);
                }
                // TODO ゲーム情報の展開
                // TODO メンバー
            }
        });

        this.memberViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication()).create(MemberViewModel.class);
        this.memberViewModel.getTeamMembers(this).observe(this, new Observer<List<TeamMemberDto>>() {
            @Override
            public void onChanged(@Nullable List<TeamMemberDto> list) {
                mPlayerAdapter = new PlayerAdapter(context, list);
                for (int index = 0; index < player_view_id.length; ++index) {
                    player_view[index] = initPlayerSpinner(root, player_view_id[index]);
                }
            }
        });

        return root;
    }

    private final int[] player_view_id = {
        R.id.starter_1, R.id.starter_2, R.id.starter_3, R.id.starter_4, R.id.starter_5,
        R.id.starter_6, R.id.starter_7, R.id.starter_8, R.id.starter_9
    };
    private final Spinner player_view[] = new Spinner[player_view_id.length];

    private final int[] game_position_view_id = {
        R.id.game_position_1, R.id.game_position_2, R.id.game_position_3, R.id.game_position_4, R.id.game_position_5,
        R.id.game_position_6, R.id.game_position_7, R.id.game_position_8, R.id.game_position_9
    };
    private final Spinner game_position_view[] = new Spinner[game_position_view_id.length];

    private TextView gameNameTextView;
    private Integer gameId = null;

    private void selectGame() {
        int num = this.gameList.size();
        CharSequence[] gameNameList = new CharSequence[num];
        for (int index = 0 ; index < num; ++index) {
            gameNameList[index] = this.gameList.get(index).getGameName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(R.string.DLG_TITLE_GAME_SELECT)
            .setItems(gameNameList, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("selected game", "which : " + which);
                    gameNameTextView.setText(gameList.get(which).getGameName());
                    gameId = Integer.valueOf(which);
                }
            });
        builder.create().show();
    }

    private void saveStarter() {
        Log.d("starter save button", "clicked");
        // 入力項目のチェック
        Map<Integer, PlayerAdapter.ListItem> list = this.checkItems();
        if (list == null) return;

        // スタメン保存
        saveToDb(list);
    }

    /**
     * スタメン登録前のチェックを行う.
     * @return
     */
    private Map<Integer, PlayerAdapter.ListItem> checkItems() {
        // 試合の確認
        if (this.gameId == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setMessage(R.string.MSG_INP_ERR_102)
                .setPositiveButton(R.string.DLG_CONF_YES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // 何もしません（試合は、利用者がボタンを操作して選択します）
                    }
                });
            builder.create().show();
            return null;
        }

        // メンバーの確認（１人以上の設定で登録可能とする）
        Map<Integer, PlayerAdapter.ListItem> list = this.mPlayerAdapter.getStartingMember();
        if (list.size() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setMessage(R.string.MSG_INP_ERR_103)
                .setPositiveButton(R.string.DLG_CONF_YES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    // 何もしません（試合は、利用者がボタンを操作して選択します）
                    }
                });
            builder.create().show();
            return null;
        }
        return list;
    }

    /**
     * スタメン情報を保存する.
     * @param list
     */
    private void saveToDb(Map<Integer, PlayerAdapter.ListItem> list) {
        this.gameStarterViewModel.initMemberList(this.gameId.longValue());
        for (Map.Entry<Integer, PlayerAdapter.ListItem> rec : list.entrySet()) {
            int battingOrder = rec.getKey().intValue();
            Integer position = null;
            Spinner positionView = this.game_position_view[battingOrder - 1];
            PositionItem positionitem = this.mGamePositionAdapter.getPositionItem(positionView);
            if (positionitem != null) {
                position = Integer.valueOf(positionitem.positionId);
            }

            this.gameStarterViewModel.addStartingMember(
                GameStartingMemberDto.builder()
                    .battingOrder(battingOrder)
                    .memberId(rec.getValue().memberId)
                    .position(position)
                    .build());
        }
        this.gameStarterViewModel.saveToDb();

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage("保存しました.")
            .setPositiveButton(R.string.DLG_CONF_YES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                // 何もしません
                }
            });
        builder.create().show();
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

    private Spinner initPositionSpinner(View root, int resource) {
        Spinner spinner = (Spinner)root.findViewById(resource);
        spinner.setSelection(-1);
        spinner.setAdapter(this.mGamePositionAdapter);
        spinner.setOnItemSelectedListener(this.mOnPositionItemSelectedListener);
        return spinner;
    }

    private Spinner initPlayerSpinner(View root, int resource) {
        Spinner spinner = (Spinner)root.findViewById(resource);
        spinner.setSelection(-1);
        spinner.setAdapter(this.mPlayerAdapter);
        spinner.setOnItemSelectedListener(this.mOnPlayerItemSelectedListener);
        return spinner;
    }
}