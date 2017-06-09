package com.xz.dripping.common.utils;

import java.math.BigDecimal;

/**
 * Created by wangwanbin on 2017/3/14.
 * BigDecimal工具类
 */
public class BigDecimalUtil {
    /**
     * 保留4位小数,舍去多余的位数
     *
     * @return
     */
    public static BigDecimal round4(BigDecimal arg) {
        if (arg == null) return BigDecimal.ZERO;
        return arg.setScale(4, BigDecimal.ROUND_DOWN);
    }
}
