package jp.yksolution.android.app.baseballscore01.ui.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamMemberDto {
    private long memberId;
    private String name;
    private int birthday;
    private int age;
}