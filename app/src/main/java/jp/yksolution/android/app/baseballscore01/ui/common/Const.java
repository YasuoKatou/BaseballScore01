package jp.yksolution.android.app.baseballscore01.ui.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 定数
 * @author Y.Katou (YKSolution)
 * @since 2019/12/28
 */
public class Const {
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
    public static class PICHING {
        /** 右投げ：1. */
        public static final Integer RIGHT = Integer.valueOf(1);
        /** 左投げ：2. */
        public static final Integer LEFT = Integer.valueOf(2);
        /** 左右投げ：3. */
        public static final Integer BOTH = Integer.valueOf(3);
    }

    /**
     * 指定文字より右投げ／左投げコードを取得する.
     * @param piching
     * @return
     */
    public static Integer getPichingCodeByString(String piching) {
        if (StringUtils.isEmpty(piching)) return null;

        if (piching.indexOf("左右") != -1) return PICHING.BOTH;
        if (piching.indexOf("右") != -1) return PICHING.RIGHT;
        if (piching.indexOf("左") != -1) return PICHING.LEFT;

        return null;
    }

    /**
     * 右打ち／左打ち.
     */
    public static class BATTING {
        /** 右打ち：1. */
        public static final Integer RIGHT = Integer.valueOf(1);
        /** 左打ち：2. */
        public static final Integer LEFT = Integer.valueOf(2);
        /** 左右打ち：3. */
        public static final Integer BOTH = Integer.valueOf(3);
    }

    /**
     * 指定文字より右打ち／左打ちコードを取得する.
     * @param batting
     * @return
     */
    public static Integer getBattingCodeByString(String batting) {
        if (StringUtils.isEmpty(batting)) return null;

        if (batting.indexOf("左右") != -1) return BATTING.BOTH;
        if (batting.indexOf("右") != -1) return BATTING.RIGHT;
        if (batting.indexOf("左") != -1) return BATTING.LEFT;

        return null;
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

    /**
     * 指定文字より右打ち／左打ちコードを取得する.
     * @param batting
     * @return
     */
    public static Integer getPositionCategoryCodeByString(String batting) {
        if (StringUtils.isEmpty(batting)) return null;

        if (batting.indexOf("内") != -1) return POSITION_CATEGOTY.INFIELD;
        if (batting.indexOf("外") != -1) return POSITION_CATEGOTY.OUTFIELD;
        if (batting.indexOf("投") != -1) return POSITION_CATEGOTY.PITCHER;
        if (batting.indexOf("捕") != -1) return POSITION_CATEGOTY.CATCHER;

        return null;
    }
}