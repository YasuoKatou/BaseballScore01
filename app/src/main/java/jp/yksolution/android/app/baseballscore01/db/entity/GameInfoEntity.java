package jp.yksolution.android.app.baseballscore01.db.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import jp.yksolution.android.app.baseballscore01.db.MyDB;
import lombok.Getter;
import lombok.Setter;

/**
 * 試合情報エンティティ.
 * @author Y.Katou (YKSolution)
 * @since 2020/01/04
 */
@Entity(tableName = MyDB.TNAME_GAME_INFO
        , indices = {@Index(value = {"game_date", "start_time"}, unique = false)})
public class GameInfoEntity {
    /** ゲームID. */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id", typeAffinity=ColumnInfo.INTEGER)
    @Getter @Setter private Long gameId;
    /** 試合名. */
    @NonNull
    @ColumnInfo(name = "game_name", typeAffinity=ColumnInfo.TEXT)
    @Getter @Setter private String gameName;
    /** 試合場所. */
    @Nullable
    @ColumnInfo(name = "place", typeAffinity=ColumnInfo.TEXT)
    @Getter @Setter private String place;
    /** 試合日. */
    @ColumnInfo(name = "game_date", typeAffinity=ColumnInfo.INTEGER)
    @Getter @Setter private long gameDate;
    /** 開始時刻. */
    @ColumnInfo(name = "start_time", typeAffinity=ColumnInfo.INTEGER)
    @Getter @Setter private Long startTime;
    /** 終了時刻. */
    @ColumnInfo(name = "end_time", typeAffinity=ColumnInfo.INTEGER)
    @Getter @Setter private Long endTime;
    /** 表裏. */
    @ColumnInfo(name = "top_bottom", typeAffinity=ColumnInfo.INTEGER)
    @Getter @Setter private int topBottom;
    /** 対戦チーム名. */
    @Nullable
    @ColumnInfo(name = "competition_team_name", typeAffinity=ColumnInfo.TEXT)
    @Getter @Setter private String competitionTeamName;
    /** 審判（主）. */
    @Nullable
    @ColumnInfo(name = "umpire1", typeAffinity=ColumnInfo.TEXT)
    @Getter @Setter private String umpire1;
    /** 審判２. */
    @Nullable
    @ColumnInfo(name = "umpire2", typeAffinity=ColumnInfo.TEXT)
    @Getter @Setter private String umpire2;
    /** 審判３. */
    @Nullable
    @ColumnInfo(name = "umpire3", typeAffinity=ColumnInfo.TEXT)
    @Getter @Setter private String umpire3;
    /** 審判４. */
    @Nullable
    @ColumnInfo(name = "umpire4", typeAffinity=ColumnInfo.TEXT)
    @Getter @Setter private String umpire4;
    /** 審判５. */
    @Nullable
    @ColumnInfo(name = "umpire5", typeAffinity=ColumnInfo.TEXT)
    @Getter @Setter private String umpire5;
    /** 審判６. */
    @Nullable
    @ColumnInfo(name = "umpire6", typeAffinity=ColumnInfo.TEXT)
    @Getter @Setter private String umpire6;

    /** 登録日時. */
    @ColumnInfo(name = "new_date_time")
    @Getter @Setter private long newDateTime;
    /** 更新日時. */
    @ColumnInfo(name = "upd_date_time")
    @Getter @Setter private long updateDateTime;
    /** バージョンNo. */
    @ColumnInfo(name = "verno")
    @Getter @Setter private int versionNo;

    /**
     * 新規登録時の共通情報を設定する
     */
    public void prepreForInsert() {
        this.gameId = null;
        this.versionNo = 1;

        Date now = new Date();
        this.newDateTime = now.getTime();
        this.updateDateTime = now.getTime();
    }

    /**
     * 更新時の共通情報を設定する
     */
    public void prepreForUpdate() {
        this.versionNo++;

        Date now = new Date();
        this.updateDateTime = now.getTime();
    }
}