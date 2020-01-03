package jp.yksolution.android.app.baseballscore01.ui.member;

import android.text.format.DateFormat;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
public class TeamMemberDto {
    /** メンバーID. */
    @Getter @Setter private long memberId;

    /** メンバー姓名（姓）. */
    @Getter @Setter private String name1;

    /** メンバー姓名（名）. */
    @Getter @Setter private String name2;

    /** 性別ID. */
    @Getter @Setter private int sex;

    /** 生年月日. */
    @Getter @Setter private Long birthday;

    /** ポジション. */
    @Getter @Setter private Integer positionCategory;

    /** 右投げ／左投げ. */
    @Getter @Setter private Integer pitching;

    /** 右打ち／左打ち. */
    @Getter @Setter private Integer batting;

    /** 状態（団員、卒団、中退）. */
    @Getter @Setter private int status;

    /** 年齢. */
    public int getAge() {
        String now = DateFormat.format("yyyyMMdd", Calendar.getInstance()).toString();
        String b   = DateFormat.format("yyyyMMdd", this.birthday).toString();
        return (Integer.parseInt(now) - Integer.parseInt(b)) / 10000;
    }

    /** メンバー姓名. */
    public String getName() {
        StringBuffer sb = new StringBuffer("");
        if (StringUtils.isNotEmpty(this.name1)) sb.append(this.name1);
        if (StringUtils.isNotEmpty(this.name2)) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(this.name2);
        }
        return sb.toString().trim();
    }
}