package jp.yksolution.android.app.baseballscore01.ui.game.starter;

import android.content.Context;
import android.content.res.TypedArray;
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
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.yksolution.android.app.baseballscore01.R;

public class GameStarterFragment extends Fragment {
    private static final String TAG = GameStarterFragment.class.getSimpleName();

    public class GamePositionAdapter extends ArrayAdapter<String> {

        private class ListItem {
            String value;
            boolean isFree;
        }

        private List<ListItem> positionList = new ArrayList<>();
        public GamePositionAdapter(Context context) {
            super(context, android.R.layout.simple_spinner_item);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            String[] strings = context.getResources().getStringArray(R.array.game_position_list);
            for (int index = 0; index < strings.length; ++index) {
                ListItem item = new ListItem();
                item.value = strings[index];
                item.isFree = true;
                this.positionList.add(item);
            }
        }
        @Override
        public int getCount() {
            return this.positionList.size() + 1;
        }

        @Override
        public String getItem(int position) {
            if (position == 0) {
                // 一覧の先頭は、未選択用の空文字列
                return "";
            }
            return this.positionList.get(position - 1).value;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView)super.getView(position, convertView, parent);
            textView.setText(this.getItem(position));
            return textView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView)super.getDropDownView(position, convertView, parent);
            textView.setText(this.getItem(position));
            int visible;
            if (this.isEnabled(position)) {
                visible = View.VISIBLE;
            } else {
                visible = View.GONE;        // TODO 詰めたい
            }
            textView.setVisibility(visible);
            return textView;
        }

        @Override
        public boolean isEnabled(int position) {
            if (position == 0) return true;
            return this.positionList.get(position - 1).isFree;
        }

        Map<View, ListItem> selectedItemMap = new HashMap<>();
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

    private GamePositionAdapter mGamePositionAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_game_starter, container, false);

        this.mGamePositionAdapter = new GamePositionAdapter(this.getContext());
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

    private AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
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
        spinner.setOnItemSelectedListener(this.mOnItemSelectedListener);
    }
}