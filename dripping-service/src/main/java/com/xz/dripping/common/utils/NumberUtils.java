package com.xz.dripping.common.utils;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * 数值相关工具
 */
public final class NumberUtils {
    /**
     * formatString: #,###0.00
     */
    public static final String DECIMAL_FORMAT_N2 = "#,###0.00";
    /**
     * formatString: #,###0.00##
     */
    public static final String DECIMAL_FORMAT_N4 = "#,###0.00##";
    /**
     * 精度：2
     */
    public static final String FORMAT_TYPE_N2 = "n2";
    /**
     * 精度：4
     */
    public static final String FORMAT_TYPE_N4 = "n4";

    /**
     * constructor
     */
    private NumberUtils() {
    }

    /**
     * 限制数值在指定的范围内
     *
     * @param num
     *            要限制的数值
     * @param min
     *            最小值
     * @param max
     *            最大值
     * @return 限制后的值
     */
    public static Integer limitRange(Integer num, Integer min, Integer max) {
        if (num == null) {
            num = min;
        }
        if (num < min) {
            num = min;
        }
        if (num > max) {
            num = max;
        }
        return num;
    }

    /**
     * 进行精确的加法运算，可以避免java double类型运算出现误差
     *
     * @param values
     *            参与加法的参数组,可变参数，不定长度
     * @return double
     */
    public static double add(double... values) {
        // 浣跨敤String鏉ユ瀯閫燘igDecimal锛岃兘澶熼伩鍏嶅紩璧疯宸?
        BigDecimal sum = new BigDecimal("0.0");

        for (double v : values) {
            sum = sum.add(new BigDecimal(String.valueOf(v)));
        }

        return sum.doubleValue();
    }

    /**
     * 乘法运算
     *
     * @param values
     *            参与乘法的参数组，可变参数，不定长度
     * @return sum
     */
    public static double multi(double... values) {

        BigDecimal sum = new BigDecimal(BigInteger.ONE);

        for (double v : values) {
            sum = sum.multiply(new BigDecimal(v));
        }
        return sum.doubleValue();
    }

    /**
     * 除法运算
     *
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @return BigDecimal
     */
    public static double div(double v1, double v2) {

        return new BigDecimal(v1).divide(new BigDecimal(v2)).doubleValue();
    }

    /**
     * 减法运算
     *
     * @param v1
     *            被减数
     * @param v2
     *            减数
     * @return double
     */
    public static double sub(double v1, double v2) {

        return new BigDecimal(v1).subtract(new BigDecimal(v2)).doubleValue();
    }

    /**
     * 取整数
     *
     * @param d 参数
     * @return bd
     */
    public static long round(double d) {
        BigDecimal bd = new BigDecimal(String.valueOf(d));

        return bd.longValue();
    }

    /**
     * 四舍五入
     *
     * @param d 参数
     * @param scale 参数
     * @return bd
     */
    public static double round(double d, int scale) {
        BigDecimal bd = new BigDecimal(String.valueOf(d)).setScale(scale, BigDecimal.ROUND_HALF_UP);

        return bd.doubleValue();
    }

    /**
     * 判断给定字符串str是否全部为数字
     *
     * @param str String
     * @return boolean
     */
    public static boolean isDigit(String str) {
        return org.apache.commons.lang.StringUtils.isNumeric(str);
    }

    /**
     * 整数取余
     *
     * @param x 参数
     * @param y 参数
     * @return int
     */
    public static int mod(int x, int y) {
        int z = x / y; // 只保留整数
        return x - y * z;

    }

    /**
     * sum的中文名称：针对多个BigDecimal型的数值加法操作
     *
     * @param round
     *            boolean的中文名称：是否四舍五入并精确到分
     * @param numArray
     *            []的中文名称：或任意个数的参数,例如 sum(num1,num2,num3),sum(num1,num2)
     * @return BigDecimal
     */
    public static BigDecimal sum(final boolean round, final BigDecimal... numArray) {
        BigDecimal result = BigDecimal.ZERO;
        for (final BigDecimal num : numArray) {
            if (num != null) {
                result = result.add(num);
            }

        }
        return round ? result.setScale(2, BigDecimal.ROUND_HALF_UP) : result;
    }

    /**
     * sum的中文名称：针对多个BigDecimal型的数值加法操作
     *
     * @param numArray
     *            []的中文名称：或任意个数的参数,例如 sum(num1,num2,num3),sum(num1,num2)
     * @return BigDecimal
     */
    public static BigDecimal sum(final BigDecimal... numArray) {
        return sum(false, numArray);
    }

    /**
     * subtract的中文名称：针对BigDecimal的减法操作
     *
     * @param round
     *            boolean的中文名称：是否四舍五入并精确到分
     * @param master
     *            BigDecimal
     * @param bigDecimals
     *            BigDecimal的中文名称：数组或任意个数的参数,例
     *            subtract(num1,num2)=num1-num2,subtract
     *            (num1,num2,num3)=num1-num2-num3
     * @return BigDecimal
     */
    public static BigDecimal subtract(final boolean round, final BigDecimal master, final BigDecimal... bigDecimals) {
        BigDecimal result = master == null ? BigDecimal.ZERO : master;
        for (final BigDecimal bigDecimal : bigDecimals) {
            if (bigDecimal != null) {
                result = result.subtract(bigDecimal);
            }
        }
        return round ? result.setScale(2, BigDecimal.ROUND_HALF_UP) : result;
    }

    /**
     * subtract的中文名称：针对BigDecimal的减法操作
     *
     * @param master
     *            BigDecimal
     * @param bigDecimals
     *            BigDecimal的中文名称：数组或任意个数的参数,例
     *            subtract(num1,num2)=num1-num2,subtract
     *            (num1,num2,num3)=num1-num2-num3
     * @return BigDecimal
     */
    public static BigDecimal subtract(final BigDecimal master, final BigDecimal... bigDecimals) {
        return subtract(false, master, bigDecimals);
    }

    /**
     * multiply的中文名称：针对BigDecimal的乘法操作
     *
     * @param bigDecimals
     *            ,BigDecimal...
     * @param round
     *            boolean的中文名称：是否四舍五入并精确到分
     * @return BigDecimal
     */
    public static BigDecimal multiply(final boolean round, final BigDecimal... bigDecimals) {

        if (bigDecimals == null || hasZeroOrNull(bigDecimals)) {
            return BigDecimal.ZERO;
        }
        BigDecimal result = null;
        for (final BigDecimal bigDecimal : bigDecimals) {
            if (bigDecimal != null) {
                if (result == null) {
                    result = bigDecimal;
                    continue;
                }
                result = result.multiply(bigDecimal);
            }
        }
        result = result == null ? BigDecimal.ZERO : result;
        return round ? result.setScale(2, BigDecimal.ROUND_HALF_UP) : result;
    }

    /**
     * multiply的中文名称：针对BigDecimal的乘法操作
     *
     * @param bigDecimals
     *            ,BigDecimal...
     * @return BigDecimal
     */
    public static BigDecimal multiply(final BigDecimal... bigDecimals) {
        return multiply(false, bigDecimals);
    }

    /**
     * divide的中文名称：针对BigDecimal的除法操作
     *
     * @param num1
     *            ,BigDecimal...
     * @param num2
     *            ,BigDecimal...
     * @return BigDecimal
     */
    public static BigDecimal divide(final BigDecimal num1, final BigDecimal... num2) {
        return divide(num1, false, 2, BigDecimal.ROUND_HALF_UP, num2);
    }

    /**
     * divide的中文名称：针对BigDecimal的除法操作
     *
     * @param num1
     *            BigDecimal
     * @param scale
     *            int的中文名称：小数位数
     * @param roundingMode
     *            int的中文名称：默认采用4舍5入，见@link BigDecimal
     * @param num2
     *            BigDecimal
     * @return BigDecimal
     */
    public static BigDecimal divide(final BigDecimal num1, final int scale, final int roundingMode,
                                    final BigDecimal... num2) {
        return divide(num1, false, scale, roundingMode, num2);

    }

    /**
     * divide的中文名称：针对BigDecimal的除法操作
     *
     * @param num1
     *            BigDecimal
     * @param roundingMode
     *            int的中文名称：默认采用4舍5入，见@link BigDecimal
     * @param num2
     *            BigDecimal
     * @return BigDecimal
     */
    public static BigDecimal divide(final BigDecimal num1, final int roundingMode, final BigDecimal... num2) {
        return divide(num1, 2, roundingMode, num2);

    }

    /**
     * divide的中文名称：针对BigDecimal的除法操作
     *
     * @param num1
     *            BigDecimal
     * @param throwException
     *            boolean的中文名称：除数为0时，是否抛出异常
     * @param scale
     *            int的中文名称：小数位数
     * @param roundingMode
     *            int的中文名称：默认采用4舍5入，见@link BigDecimal
     * @param num2
     *            BigDecimal
     * @return BigDecimal
     */
    public static BigDecimal divide(final BigDecimal num1, final boolean throwException, final int scale,
                                    final int roundingMode, final BigDecimal... num2) {
        if (hasZeroOrNull(num1)) {
            return BigDecimal.ZERO;
        }
        if (hasZeroOrNull(num2)) {
            if (throwException) {
                throw new ArithmeticException("除数不合法！");
            } else {
                return BigDecimal.ZERO;
            }
        }
        BigDecimal result = num1;
        for (final BigDecimal bigDecimal : num2) {
            if (bigDecimal != null) {
                result = result.divide(bigDecimal, scale, roundingMode);
            }
        }

        return result;
    }

    /**
     * compare的中文名称：比较两个数的大小，注意使用此方法，2.0和2.00将会被视为相等; null值将会被视为0
     *
     * @param num1
     *            BigDecimal
     * @param num2
     *            BigDecimal
     * @return int
     */
    public static int compare(final BigDecimal num1, final BigDecimal num2) {
        final BigDecimal p1 = nvl(num1);
        final BigDecimal p2 = nvl(num2);
        return p1.compareTo(p2);
    }

    /**
     * compare的中文名称：判断传入的第一个参数是否大于第二个参数， 注意使用此方法，2.0和2.00将会被视为相等; null值将会被视为0
     *
     * @param num1
     *            BigDecimal
     * @param num2
     *            BigDecimal
     * @return Boolean
     */
    public static Boolean isGreater(final BigDecimal num1, final BigDecimal num2) {
        final BigDecimal p1 = nvl(num1);
        final BigDecimal p2 = nvl(num2);

        int result = p1.compareTo(p2);
        return result == 1 ? true : false;
    }

    /**
     * compare的中文名称：判断传入的第一个参数是否大于等于第二个参数， 注意使用此方法，2.0和2.00将会被视为相等; null值将会被视为0
     *
     * @param num1
     *            BigDecimal
     * @param num2
     *            BigDecimal
     * @return Boolean
     */
    public static Boolean isGreaterOrEqual(final BigDecimal num1, final BigDecimal num2) {
        final BigDecimal p1 = nvl(num1);
        final BigDecimal p2 = nvl(num2);

        int result = p1.compareTo(p2);
        return (result == 1 || result == 0) ? true : false;
    }

    /**
     * 比较2个BigDecimal对象是否相等.
     * @param num1 BigDecimal
     * @param num2 BigDecimal
     * @return Boolean
     */
    public static Boolean isEqual(final BigDecimal num1, final BigDecimal num2) {
        final BigDecimal p1 = nvl(num1);
        final BigDecimal p2 = nvl(num2);

        int result = p1.compareTo(p2);
        return result == 0 ? true : false;
    }

    /**
     * compare的中文名称：判断传入的第一个参数是否大于第二个参数， 注意使用此方法，2.0和2.00将会被视为相等; null值将会被视为0
     *
     * @param num1
     *            BigDecimal
     * @param num2
     *            BigDecimal
     * @return Boolean
     */
    public static Boolean isSmaller(final BigDecimal num1, final BigDecimal num2) {
        final BigDecimal p1 = nvl(num1);
        final BigDecimal p2 = nvl(num2);

        int result = p1.compareTo(p2);
        return result == -1 ? true : false;
    }

    /**
     * compare的中文名称：判断传入的第一个参数是否小于等于第二个参数， 注意使用此方法，2.0和2.00将会被视为相等; null值将会被视为0
     *
     * @param num1
     *            BigDecimal
     * @param num2
     *            BigDecimal
     * @return Boolean
     */
    public static Boolean isSmallerOrEqual(final BigDecimal num1, final BigDecimal num2) {
        final BigDecimal p1 = nvl(num1);
        final BigDecimal p2 = nvl(num2);

        int result = p1.compareTo(p2);
        return (result == -1 || result == 0) ? true : false;
    }

    /**
     * isPositive的中文名称：判断是否大于0
     *
     * @param numArray
     *            BigDecimal
     * @return Boolean
     */
    public static boolean isPositive(final BigDecimal... numArray) {
        for (final BigDecimal num : numArray) {
            if (num == null) {
                return false;
            }
            if (num.signum() <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * isNegative的中文名称：判断是否小于0
     *
     * @param numArray
     *            BigDecimal
     * @return Boolean
     */
    public static boolean isNegative(final BigDecimal... numArray) {
        for (final BigDecimal num : numArray) {
            if (num == null) {
                return false;
            }
            if (num.signum() >= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * isZero的中文名称：判断是否为0,null值返回true
     *
     * @param numArray
     *            BigDecimal
     * @return Boolean
     */
    public static boolean isZero(final BigDecimal... numArray) {
        for (final BigDecimal num : numArray) {
            if (null != num && num.signum() != 0) {
                return false;
            }
        }
        return true;

    }

    /**
     * isNotZero的中文名称：判断是否为0
     *
     * @param numArray
     *            BigDecimal
     * @return Boolean
     */
    public static boolean isNotZero(final BigDecimal... numArray) {
        for (final BigDecimal num : numArray) {
            if (null == num || 0 == num.signum()) {
                return false;
            }
        }
        return true;

    }

    /**
     * hasZeroOrNull的中文名称：内部使用，判断是否为null值或0，存在的话，返回true;如果传入参数为null，返回false;
     *
     * @param bigDecimals BigDecimal
     * @return Boolean
     */
    private static boolean hasZeroOrNull(final BigDecimal... bigDecimals) {
        for (final BigDecimal bigDecimal : bigDecimals) {
            if (isZero(bigDecimal)) {
                return true;
            }
        }
        return false;
    }

    /**
     * nvl的中文名称：若入参为空，则返回0，否则返回原值
     *
     * @param data
     *            ：要做非空处理的BigDecimal数据
     * @return BigDecimal
     */
    public static BigDecimal nvl(final BigDecimal data) {
        return data == null ? BigDecimal.ZERO : data;
    }

    /**
     * 判断一个Long类型对象是否为null.
     * @param data Long
     * @return Long
     */
    public static Long nvl(final Long data) {
        return data == null ? 0 : data;
    }

    /**
     * 判断一个Integer类型对象是否为null.
     * @param data Integer
     * @return Integer
     */
    public static Integer nvl(final Integer data) {
        return data == null ? 0 : data;
    }

    /**
     * nvl的中文名称：若data1为空，则返回data2，否则返回原值
     *
     * @param data1 BigDecimal
     * @param data2 BigDecimal
     * @return BigDecimal
     */
    public static BigDecimal nvl(final BigDecimal data1, final BigDecimal data2) {
        return data1 == null ? data2 : data1;
    }

    /**
     * 用num除以divNum，如果有余数就取除的结果+1，如果余数为0则直接取除得结果 如：6/5 返回2；6/6 返回1
     * @param num int
     * @param divNum int
     * @return int
     */
    public static int divideCeil(int num, int divNum) {
        if (num % divNum == 0) {
            return num / divNum;
        } else {
            return num / divNum + 1;
        }
    }

    /**
     * 将String数组转换为Long数组
     *
     * @param strings String[]
     * @return Long[]
     *
     */
    public static Long[] stringToLong(String[] strings) {
        Long[] attachmentLongIds = null;
        attachmentLongIds = new Long[strings.length];
        for (int i = 0; i < strings.length; i++) {
            String str = strings[i];
            long lg = Long.parseLong(str);
            attachmentLongIds[i] = lg;
        }
        return attachmentLongIds;
    }

    /**
     * 判断两个Integer是否相等（同时考虑其为0的情况） 1.如果都为null则认为相等 2.如果其中一个为null则认为不相等
     * 3.如果都不为null直接使用原本的equals方法
     *
     * @param num1 Long
     * @param num2 Long
     * @return boolean
     */
    public static boolean equalsIntegerConsiderNull(Long num1, Long num2) {
        if (num1 == null && num2 == null) {
            return true;
        } else if (num1 == null || num2 == null) {
            return false;
        } else {
            return num1.equals(num2);
        }
    }

    /**
     * 将数值进行千分格式化，精度为2位或4位
     *
     * @param numberValue BigDecimal
     * @param format String
     * @return String
     */
    public static String numberFormat(BigDecimal numberValue, String format){
        String decimalFormatStr = DECIMAL_FORMAT_N2;
        if (!FORMAT_TYPE_N2.equals(format)) {
            decimalFormatStr = DECIMAL_FORMAT_N4;
        }
        DecimalFormat decimalFormat = new DecimalFormat(decimalFormatStr);
        return decimalFormat.format(numberValue);
    }

    /**
     * 将数值四舍五入，精度为2
     *
     * @param paramBD BigDecimal
     * @return BigDecimal
     */
    public static BigDecimal roundN2(BigDecimal paramBD){
        BigDecimal bd = nvl(paramBD);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    public static BigDecimal max(BigDecimal a, BigDecimal b){
        return isGreater(a, b)? a:b;
    }

    public static BigDecimal min(BigDecimal a, BigDecimal b){
        return isSmaller(a, b)? a:b;
    }
}
