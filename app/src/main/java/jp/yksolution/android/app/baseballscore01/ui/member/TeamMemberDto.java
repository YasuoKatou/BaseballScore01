package jp.yksolution.android.app.baseballscore01.ui.member;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
public class TeamMemberDto {
    /** メンバーID. */
    @Getter
    @Setter
    private long memberId;

    /** メンバー姓名（姓）. */
    @Getter
    @Setter
    private String name1;

    /** メンバー姓名（名）. */
    @Getter
    @Setter
    private String name2;

    /** 性別ID. */
    @Getter
    @Setter
    private int sex;

    /** 生年月日. */
    @Getter
    @Setter
    private Long birthday;

    /** 年齢. */
    private int age;

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