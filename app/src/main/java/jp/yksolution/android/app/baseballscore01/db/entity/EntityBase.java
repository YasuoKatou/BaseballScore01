package jp.yksolution.android.app.baseballscore01.db.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * エンティティの基底クラス.
 * @author Y.Katou (YKSolution)
 * @since 2019/12/28
 */
@Getter
@Setter
public class EntityBase {
    /** 登録日時. */
    private long newDateTime;
    /** 更新日時. */
    private long updateDateTime;
    /** バージョンNo. */
    private int versionNo;

    /**
     * データ登録時の設定を行う.     */
    public void prepareInsert() {
        long now = (new Date()).getTime();
        this.newDateTime = now;         // 登録日時
        this.updateDateTime = now;      // 更新日時
        this.versionNo = 1;             // バージョンNo
    }

    /**
     * データ登録時の設定を行う.     */
    public void prepareUpdate() {
        this.updateDateTime = (new Date()).getTime();      // 更新日時
        this.versionNo++;
    }
}