package jp.yksolution.android.app.baseballscore01.ui.game.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jp.yksolution.android.app.baseballscore01.R;
import jp.yksolution.android.app.baseballscore01.utils.DateTime;

/**
 * 試合情報一覧表示アダプター.
 * @author Y.Katou (YKSolution)
 * @since 2020/01/04
 */
public class GameInfoListAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater = null;
    private List<GameInfoDto> mGameInfoList = null;

    public GameInfoListAdapter(Context context) {
        this.mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setGameInfoList(List<GameInfoDto> list) {
        this.mGameInfoList = list;
    }

    @Override
    public int getCount() {
        if (this.mGameInfoList == null) {
            return 0;
        } else {
            return this.mGameInfoList.size();
        }
    }

    @Override
    public Object getItem(int index) {
        return this.mGameInfoList.get(index);
    }

    @Override
    public long getItemId(int index) {
        return this.mGameInfoList.get(index).getGameId();
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        convertView = this.mLayoutInflater.inflate(R.layout.game_info_list_row, parent, false);

        GameInfoDto entity = (GameInfoDto)this.getItem(index);
        ((TextView)convertView.findViewById(R.id.gameDate)).setText(DateTime.getMonthday(entity.getGameDate()));
        ((TextView)convertView.findViewById(R.id.gameStartTime)).setText(DateTime.getTime(entity.getStartTime()));
        ((TextView)convertView.findViewById(R.id.gameName)).setText(entity.getGameName());
        ((TextView)convertView.findViewById(R.id.gamePlace)).setText(entity.getPlace());
        ((TextView)convertView.findViewById(R.id.competitionTeamName)).setText(entity.getCompetitionTeamName());

        return convertView;
    }
}
