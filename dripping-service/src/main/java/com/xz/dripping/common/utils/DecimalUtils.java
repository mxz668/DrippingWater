package com.xz.dripping.common.utils;

import java.math.BigDecimal;

/**
 * 功能: BigDecimal工具类
 */
public class DecimalUtils {

    /**
     * 判断是否是null或者零
     *
     * @param value
     * @return
     */
    public static boolean isNullOrZero(BigDecimal value) {
        boolean is = true;
        if (value != null) {
            if (value.compareTo(BigDecimal.ZERO) != 0) {
                is = false;
            }
        }
        return is;
    }

    /**
     * 判断是否非null或者零
     *
     * @param value
     * @return
     */
    public static boolean isNotNullOrZero(BigDecimal value) {
        return (!isNullOrZero(value));
    }

    /**
     * 判断是否是正数
     *
     * @param value
     * @return
     */
    public static boolean isPositive(BigDecimal value) {
        boolean is = false;
        if (value != null) {
            if (value.compareTo(BigDecimal.ZERO) > 0) {
                is = true;
            }
        }
        return is;
    }

    /**
     * 判断是否非正数
     *
     * @param value
     * @return
     */
    public static boolean isNotPositive(BigDecimal value) {
        return (!isPositive(value));
    }

    /**
     * 判断是否大于
     *
     * @param value
     * @param compare
     * @return
     */
    public static boolean gt(BigDecimal value, BigDecimal compare) {
        boolean gt = false;
        if (value != null && compare != null) {
            if (value.compareTo(compare) > 0)
                gt = true;
        } else if (value != null && compare == null) {
            gt = true;
        }
        return gt;
    }

    /**
     * 判断是否大于等于
     *
     * @param value
     * @param compare
     * @return
     */
    public static boolean ge(BigDecimal value, BigDecimal compare) {
        boolean ge = false;
        if (value != null && compare != null) {
            if (value.compareTo(compare) >= 0)
                ge = true;
        } else if (compare == null) {
            ge = true;
        }
        return ge;
    }

    /**
     * 判断是否小于
     *
     * @param value
     * @param compare
     * @return
     */
    public static boolean lt(BigDecimal value, BigDecimal compare) {
        boolean lt = false;
        if (value != null && compare != null) {
            if (value.compareTo(compare) < 0)
                lt = true;
        } else if (value == null && compare != null) {
            lt = true;
        }
        return lt;
    }

    /**
     * 判断是否小于等于
     *
     * @param value
     * @param compare
     * @return
     */
    public static boolean le(BigDecimal value, BigDecimal compare) {
        boolean le = false;
        if (value != null && compare != null) {
            if (value.compareTo(compare) <= 0)
                le = true;
        } else if (value == null) {
            le = true;
        }
        return le;
    }

    /**
     * 判断是否等于
     *
     * @param value
     * @param compare
     * @return
     */
    public static boolean equals(BigDecimal value, BigDecimal compare) {
        boolean equals = false;
        if (value != null && compare != null) {
            if (value.compareTo(compare) == 0)
                equals = true;
        } else if (value == null && compare == null) {
            equals = true;
        }
        return equals;
    }
}
