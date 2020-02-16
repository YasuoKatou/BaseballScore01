package jp.yksolution.android.app.baseballscore01.db.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import jp.yksolution.android.app.baseballscore01.db.MyDB;
import lombok.Getter;
import lombok.Setter;

/**
 * スターティングメンバ―エンティティ.
 * @author Y.Katou (YKSolution)
 * @since 2020/02/16
 */
@Entity(tableName = MyDB.GAME_STARTING_MEMBER, primaryKeys = {"game_id", "batting_order"})
public class GameStartingMemberEntity {
    /** ゲームID. */
    @NonNull
    @ColumnInfo(name = "game_id")
    @Getter @Setter private Long gameId;

    /** 打順（1～9）. */
    @NonNull
    @ColumnInfo(name = "batting_order")
    @Getter @Setter private int battingOrder;

    /** メンバーID. */
    @NonNull
    @ColumnInfo(name = "member_id")
    @Getter @Setter private Long memberId;

    /** ポジション. */
    @Nullable
    @ColumnInfo(name = "position")
    @Getter @Setter private Integer position;
}