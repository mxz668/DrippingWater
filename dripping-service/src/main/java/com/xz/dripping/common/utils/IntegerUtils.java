package com.xz.dripping.common.utils;

/**
 * 功能: Integer工具类
 * 版本: V1.0
 */
public class IntegerUtils {

    /**
     * 判断是否是null或者零
     *
     * @param value
     * @return
     */
    public static boolean isNullOrZero(Integer value) {
        boolean is = true;
        if (value != null) {
            if (value != 0) {
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
    public static boolean isNotNullOrZero(Integer value) {
        return (!isNullOrZero(value));
    }

    /**
     * 判断是否是正数
     *
     * @param value
     * @return
     */
    public static boolean isPositive(Integer value) {
        boolean is = false;
        if (value != null) {
            if (value > 0) {
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
    public static boolean isNotPositive(Integer value) {
        return (!isPositive(value));
    }

    /**
     * 判断是否大于
     *
     * @param value
     * @param compare
     * @return
     */
    public static boolean gt(Integer value, Integer compare) {
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
    public static boolean ge(Integer value, Integer compare) {
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
    public static boolean lt(Integer value, Integer compare) {
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
    public static boolean le(Integer value, Integer compare) {
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
    public static boolean equals(Integer value, Integer compare) {
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
