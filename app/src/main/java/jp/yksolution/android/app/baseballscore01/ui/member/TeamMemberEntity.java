package jp.yksolution.android.app.baseballscore01.ui.member;

public class TeamMemberEntity {
    private long memberId;
    private String name;
    private int birthday;
    private int age;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }
}