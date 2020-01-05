package jp.yksolution.android.app.baseballscore01.ui.game.info;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 試合情報Dto.
 * @author Y.Katou (YKSolution)
 * @since 2020/01/04
 */
@Builder
public class GameInfoDto {
    /** ゲームID. */
    @Getter @Setter private long gameId;
    /** 試合名. */
    @Getter @Setter private String gameName;
    /** 試合場所. */
    @Getter @Setter private String place;
    /** 試合日. */
    @Getter @Setter private long gameDate;
    /** 開始時刻. */
    @Getter @Setter private Long startTime;
    /** 終了時刻. */
    @Getter @Setter private Long endTime;
    /** 表裏. */
    @Getter @Setter private int topBottom;
    /** 対戦チーム名. */
    @Getter @Setter private String competitionTeamName;
    /** 審判（主）. */
    @Getter @Setter private String umpire1;
    /** 審判２. */
    @Getter @Setter private String umpire2;
    /** 審判３. */
    @Getter @Setter private String umpire3;
    /** 審判４. */
    @Getter @Setter private String umpire4;
    /** 審判５. */
    @Getter @Setter private String umpire5;
    /** 審判６. */
    @Getter @Setter private String umpire6;

    /** 登録日時. */
    @Getter @Setter private long newDateTime;
    /** 更新日時. */
    @Getter @Setter private long updateDateTime;
    /** バージョンNo. */
    @Getter @Setter private int versionNo;

    /**
     * 新規登録時の共通情報を設定する
     */
    public void prepreForInsert() {
        Date now = new Date();
        this.newDateTime = now.getTime();
        this.updateDateTime = now.getTime();
        this.versionNo = 1;
    }

    /**
     * 更新時の共通情報を設定する
     */
    public void prepreForUpdate() {
        Date now = new Date();
        this.updateDateTime = now.getTime();
        this.versionNo++;
    }
}