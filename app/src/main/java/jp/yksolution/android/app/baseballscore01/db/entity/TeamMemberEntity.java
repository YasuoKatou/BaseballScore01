package jp.yksolution.android.app.baseballscore01.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * チームメンバーエンティティ.
 * @author Y.Katou (YKSolution)
 * @since 2019/12/28
 */
@Builder
@Getter
@Setter
public class TeamMemberEntity extends EntityBase {
    /** メンバーID. */
    private long memberId;
    /** メンバー名（姓） */
    private String name1;
    /** メンバー名（名） */
    private String name2;
    /** 性別. */
    private int sex;
    /** 誕生日. */
    private long birthday;
    /** ポジション. */
    private Integer positionCategory;
    /** 右投げ／左投げ. */
    private Integer pitching;
    /** 右打ち／左打ち. */
    private Integer batting;
}