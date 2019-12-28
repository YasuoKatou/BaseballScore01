package jp.yksolution.android.app.baseballscore01.ui.member;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class TeamMemberDto {
    /** メンバーID. */
    private long memberId;
    /** メンバー姓名. */
    private String name;
    /** メンバー姓名（姓）. */
    private String name1;
    /** メンバー姓名（名）. */
    private String name2;
    /** 性別ID. */
    private int sex;
    /** 生年月日. */
    private Date birthday;
    /** 年齢. */
    private int age;
}