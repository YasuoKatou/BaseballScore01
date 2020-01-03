package jp.yksolution.android.app.baseballscore01.db.entity;

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
 * チームメンバーエンティティ.
 * @author Y.Katou (YKSolution)
 * @since 2019/12/28
 */
@Entity(tableName = MyDB.TNAME_TEAM_MEMBER
      , indices = {@Index(value = {"status", "birthday", "name1", "name2"}, unique = false)})
public class TeamMemberEntity {
    /** メンバーID. */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "member_id", typeAffinity=ColumnInfo.INTEGER)
    @Getter @Setter private Long memberId;
    /** メンバー名（姓） */
    @ColumnInfo(name = "name1", typeAffinity=ColumnInfo.TEXT)
    @Getter @Setter private String name1;
    /** メンバー名（名） */
    @ColumnInfo(name = "name2", typeAffinity=ColumnInfo.TEXT)
    @Getter @Setter private String name2;
    /** 性別. */
    @ColumnInfo(name = "sex", typeAffinity=ColumnInfo.INTEGER)
    @Getter @Setter private int sex;
    /** 誕生日. */
    @ColumnInfo(name = "birthday", typeAffinity=ColumnInfo.INTEGER)
    @Getter @Setter private long birthday;
    /** ポジション. */
    @Nullable
    @ColumnInfo(name = "position_category", typeAffinity=ColumnInfo.INTEGER)
    @Getter @Setter private Integer positionCategory;
    /** 右投げ／左投げ. */
    @Nullable
    @ColumnInfo(name = "pitching", typeAffinity=ColumnInfo.INTEGER)
    @Getter @Setter private Integer pitching;
    /** 右打ち／左打ち. */
    @Nullable
    @ColumnInfo(name = "batting", typeAffinity=ColumnInfo.INTEGER)
    @Getter @Setter private Integer batting;
    /** 状態（団員、卒団、中退）. */
    @ColumnInfo(name = "status", typeAffinity=ColumnInfo.INTEGER)
    @Getter @Setter private int status;

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
        this.memberId = null;
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