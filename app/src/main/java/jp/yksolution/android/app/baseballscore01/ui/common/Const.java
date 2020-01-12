package jp.yksolution.android.app.baseballscore01.ui.common;

import android.content.res.Resources;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import jp.yksolution.android.app.baseballscore01.R;

/**
 * 定数
 * @author Y.Katou (YKSolution)
 * @since 2019/12/28
 */
public class Const {
    /**
     * 先攻／後攻
     */
    public static class TOP_BOTTOM {
        /** 先攻. */
        public static final int TOP = 1;
        /** 後攻. */
        public static final int BOTTOM = 2;
    }
    /**
     * 性別.
     */
    public static class SEX {
        /** 性別：男子. */
        public static final int BOY = 1;
        /** 性別：女子. */
        public static final int GIRL = 2;
    }

    /**
     * 右投げ／左投げ.
     */
    public static class PITCHING {
        /** 右投げ：1. */
        public static final Integer RIGHT = Integer.valueOf(1);
        /** 左投げ：2. */
        public static final Integer LEFT = Integer.valueOf(2);
        /** 両投げ：3. */
        public static final Integer BOTH = Integer.valueOf(3);
    }
    /** ピッチングキーワード */
    private static final String[] PITCHING_KEYWORDS = {"右", "左", "両"};

    /**
     * 指定文字より右投げ／左投げコードを取得する.
     * @param pitching
     * @return
     */
    public static Integer getPichingCodeByString(String pitching) {
        if (StringUtils.isEmpty(pitching)) return null;

        if (pitching.indexOf(PITCHING_KEYWORDS[0]) != -1) return PITCHING.RIGHT;
        if (pitching.indexOf(PITCHING_KEYWORDS[1]) != -1) return PITCHING.LEFT;
        if (pitching.indexOf(PITCHING_KEYWORDS[2]) != -1) return PITCHING.BOTH;

        return null;
    }

    /**
     * ピッチング設定値からピッチング一覧のインデックスを決定する.
     * @param res リソース
     * @param value ピッチング設定値
     * @return インデックス
     */
    public static int getPichingIndex(Resources res, Integer value) {
        String keyword;
        if (PITCHING.RIGHT.equals(value)) {
            keyword = PITCHING_KEYWORDS[0];
        } else if (PITCHING.LEFT.equals(value)) {
            keyword = PITCHING_KEYWORDS[1];
        } else if (PITCHING.BOTH.equals(value)) {
            keyword = PITCHING_KEYWORDS[2];
        } else {
            Log.e("getPichingIndex", "no such code : " + ((value == null) ? "null" : value.toString()));
            return 0;
        }
        String[] values = res.getStringArray(R.array.pitching_type_list);
        if (values == null) {
            Log.e("getPichingIndex", "no such array");
            return 0;
        }
        String item;
        for (int index = 0; index < values.length; ++index) {
            if (values[index].indexOf(keyword) != -1) return index;
        }

        return 0;
    }

    /**
     * ピッチング設定値からピッチング名を取得する.
     * @param res リソース
     * @param value ピッチング設定値
     * @return ピッチング名
     */
    public static String getPitchingName(Resources res, Integer value) {
        String[] values = res.getStringArray(R.array.pitching_type_list);
        if (values == null) {
            Log.e("getPitchingName", "no such array");
            return "";
        }
        if (PITCHING.RIGHT.equals(value)) {
            return values[0];   // 右投
        } else if (PITCHING.LEFT.equals(value)) {
            return values[1];   // 左投
        } else if (PITCHING.BOTH.equals(value)) {
            return values[2];   // 両投
        }
        Log.e("getPitchingName", "no such code : " + ((value == null) ? "null" : value.toString()));
        return "";
    }

    /**
     * 右打ち／左打ち.
     */
    public static class BATTING {
        /** 右打ち：1. */
        public static final Integer RIGHT = Integer.valueOf(1);
        /** 左打ち：2. */
        public static final Integer LEFT = Integer.valueOf(2);
        /** 両打ち：3. */
        public static final Integer BOTH = Integer.valueOf(3);
    }
    /** バッティングキーワード */
    private static final String[] BATTING_KEYWORDS = {"右", "左", "両"};

    /**
     * 指定文字より右打ち／左打ちコードを取得する.
     * @param batting
     * @return
     */
    public static Integer getBattingCodeByString(String batting) {
        if (StringUtils.isEmpty(batting)) return null;

        if (batting.indexOf(BATTING_KEYWORDS[0]) != -1) return BATTING.RIGHT;
        if (batting.indexOf(BATTING_KEYWORDS[1]) != -1) return BATTING.LEFT;
        if (batting.indexOf(BATTING_KEYWORDS[2]) != -1) return BATTING.BOTH;

        return null;
    }

    /**
     * バッティング設定値からバッティング一覧のインデックスを決定する.
     * @param res リソース
     * @param value バッティング設定値
     * @return インデックス
     */
    public static int getBattingIndex(Resources res, Integer value) {
        String keyword;
        if (BATTING.RIGHT.equals(value)) {
            keyword = BATTING_KEYWORDS[0];
        } else if (BATTING.LEFT.equals(value)) {
            keyword = BATTING_KEYWORDS[1];
        } else if (BATTING.BOTH.equals(value)) {
            keyword = BATTING_KEYWORDS[2];
        } else {
            Log.e("getBattingIndex", "no such code : " + ((value == null) ? "null" : value.toString()));
            return 0;
        }

        String[] values = res.getStringArray(R.array.batting_type_list);
        if (values == null) {
            Log.e("getBattingIndex", "no such array");
            return 0;
        }
        String item;
        for (int index = 0; index < values.length; ++index) {
            if (values[index].indexOf(keyword) != -1) return index;
        }

        return 0;
    }

    /**
     * バッティング設定値からバッティング名を取得する.
     * @param res リソース
     * @param value バッティング設定値
     * @return バッティング名
     */
    public static String getBattingName(Resources res, Integer value) {
        String[] values = res.getStringArray(R.array.batting_type_list);
        if (values == null) {
            Log.e("getBattingName", "no such array");
            return "";
        }
        if (BATTING.RIGHT.equals(value)) {
            return values[0];   // 右打
        } else if (BATTING.LEFT.equals(value)) {
            return values[1];   // 左打
        } else if (BATTING.BOTH.equals(value)) {
            return values[2];   // 両打
        }
        Log.e("getBattingName", "no such code : " + ((value == null) ? "null" : value.toString()));
        return "";
    }

    /**
     * ポジションカテゴリ.
     */
    public static class POSITION_CATEGOTY {
        /** 内野：100. */
        private static final Integer INFIELD = Integer.valueOf(100);
        /** 外野：200. */
        private static final Integer OUTFIELD = Integer.valueOf(200);
        /** 投手：300. */
        private static final Integer PITCHER = Integer.valueOf(300);
        /** 捕手：400. */
        private static final Integer CATCHER = Integer.valueOf(400);
    }
    /** ポジションカテゴリキーワード */
    private static final String[] POSITION_CATEGOTY_KEYWORDS = {"内", "外", "投", "捕"};

    /**
     * 指定文字より右打ち／左打ちコードを取得する.
     * @param batting
     * @return
     */
    public static Integer getPositionCategoryCodeByString(String batting) {
        if (StringUtils.isEmpty(batting)) return null;

        if (batting.indexOf(POSITION_CATEGOTY_KEYWORDS[0]) != -1) return POSITION_CATEGOTY.INFIELD;
        if (batting.indexOf(POSITION_CATEGOTY_KEYWORDS[1]) != -1) return POSITION_CATEGOTY.OUTFIELD;
        if (batting.indexOf(POSITION_CATEGOTY_KEYWORDS[2]) != -1) return POSITION_CATEGOTY.PITCHER;
        if (batting.indexOf(POSITION_CATEGOTY_KEYWORDS[3]) != -1) return POSITION_CATEGOTY.CATCHER;

        return null;
    }

    /**
     * ポジションカテゴリ設定値からポジションカテゴリ一覧のインデックスを決定する.
     * @param res リソース
     * @param value ポジションカテゴリ設定値
     * @return インデックス
     */
    public static int getPositionCategoryIndex(Resources res, Integer value) {
        String keyword;
        if (POSITION_CATEGOTY.INFIELD.equals(value)) {
            keyword = POSITION_CATEGOTY_KEYWORDS[0];
        } else if (POSITION_CATEGOTY.OUTFIELD.equals(value)) {
            keyword = POSITION_CATEGOTY_KEYWORDS[1];
        } else if (POSITION_CATEGOTY.PITCHER.equals(value)) {
            keyword = POSITION_CATEGOTY_KEYWORDS[2];
        } else if (POSITION_CATEGOTY.CATCHER.equals(value)) {
            keyword = POSITION_CATEGOTY_KEYWORDS[3];
        } else {
            Log.e("getPositionCategoryIndex", "no such code : " + ((value == null) ? "null" : value.toString()));
            return 0;
        }

        String[] values = res.getStringArray(R.array.position_category_list);
        if (values == null) {
            Log.e("getPositionCategoryIndex", "no such array");
            return 0;
        }
        String item;
        for (int index = 0; index < values.length; ++index) {
            if (values[index].indexOf(keyword) != -1) return index;
        }

        return 0;
    }


    /**
     * ポジションカテゴリ設定値から名称を取得する.
     * @param res リソース
     * @param value ポジションカテゴリ設定値
     * @return ポジションカテゴリ名称
     */
    public static String getPositionCategoryName(Resources res, Integer value) {
        String[] values = res.getStringArray(R.array.position_category_list);
        if (values == null) {
            Log.e("getPositionCategoryName", "no such array");
            return "";
        }
        if (POSITION_CATEGOTY.INFIELD.equals(value)) {
            return values[1];   // 内野
        } else if (POSITION_CATEGOTY.OUTFIELD.equals(value)) {
            return values[0];   // 外野
        } else if (POSITION_CATEGOTY.PITCHER.equals(value)) {
            return values[2];   // 投手
        } else if (POSITION_CATEGOTY.CATCHER.equals(value)) {
            return values[3];   // 捕手
        }
        Log.e("getPositionCategoryName", "no such code : " + ((value == null) ? "null" : value.toString()));
        return "";
    }

    /**
     * チームメンバー状態.
     */
    public static class TEAM_MEMBER_STATUS {
        /** 団員：1. */
        public static final Integer ACTIVE = Integer.valueOf(1);
        /** 卒団：2. */
        public static final Integer OLD = Integer.valueOf(2);
        /** 中退：3. */
        public static final Integer DROP_OUT = Integer.valueOf(3);
    }
    /** チームメンバー状態キーワード */
    private static final String[] TEAM_MEMBER_STATUS_KEYWORDS = {"員", "卒", "退"};

    /**
     * 指定文字よりチームメンバー状態コードを取得する.
     * @param status
     * @return
     */
    public static Integer getTeamMemberStatusByString(String status) {
        if (StringUtils.isEmpty(status)) return null;

        if (status.indexOf(TEAM_MEMBER_STATUS_KEYWORDS[0]) != -1) return TEAM_MEMBER_STATUS.ACTIVE;
        if (status.indexOf(TEAM_MEMBER_STATUS_KEYWORDS[1]) != -1) return TEAM_MEMBER_STATUS.OLD;
        if (status.indexOf(TEAM_MEMBER_STATUS_KEYWORDS[2]) != -1) return TEAM_MEMBER_STATUS.DROP_OUT;

        return null;
    }

    /**
     * チームメンバー状態の設定値からチームメンバー状態一覧のインデックスを決定する.
     * @param res リソース
     * @param value チームメンバー状態の設定値
     * @return インデックス
     */
    public static int getTeamMemberStatusIndex(Resources res, Integer value) {
        String keyword;
        if (TEAM_MEMBER_STATUS.ACTIVE.equals(value)) {
            keyword = TEAM_MEMBER_STATUS_KEYWORDS[0];
        } else if (TEAM_MEMBER_STATUS.OLD.equals(value)) {
            keyword = TEAM_MEMBER_STATUS_KEYWORDS[1];
        } else if (TEAM_MEMBER_STATUS.DROP_OUT.equals(value)) {
            keyword = TEAM_MEMBER_STATUS_KEYWORDS[2];
        } else {
            Log.e("getTeamMemberStatusIndex", "no such code : " + ((value == null) ? "null" : value.toString()));
            return 0;
        }

        String[] values = res.getStringArray(R.array.team_member_status);
        if (values == null) {
            Log.e("getTeamMemberStatusIndex", "no such array");
            return 0;
        }
        String item;
        for (int index = 0; index < values.length; ++index) {
            if (values[index].indexOf(keyword) != -1) return index;
        }

        return 0;
    }
}