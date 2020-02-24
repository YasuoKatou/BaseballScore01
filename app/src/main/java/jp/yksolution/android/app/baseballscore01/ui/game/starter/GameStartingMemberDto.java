package jp.yksolution.android.app.baseballscore01.ui.game.starter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * スタメン情報Dto.
 * @author Y.Katou (YKSolution)
 * @since 2020/02/24
 */
@Builder
@ToString
public class GameStartingMemberDto {
    /** ゲームID. */
    @Getter @Setter private Long gameId;
    /** 打順（1～9）. */
    @Getter @Setter private int battingOrder;
    /** メンバーID. */
    @Getter @Setter private Long memberId;
    /** ポジション. */
    @Getter @Setter private Integer position;
}