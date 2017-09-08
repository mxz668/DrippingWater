package com.xz.dripping.common.utils;


/**
 *  全局变量
 */
public class Constants {

    public static final String FINANCE_SUBJECT_CODE_PREFIX = "FS";

    /**
     * ************************************** 通用状态 start ************************************
     */
    public static final String SUCCESS_RESP_CODE = "0000";
    public static final String SUCCESS_RESP_DESC = "成功";

    public static final String FAIL_RESP_CODE = "9999";
    public static final String FAIL_RESP_DESC = "处理失败";

    public static final String PARAM_NOBLANK_CODE = "9001";
    public static final String PARAM_NOBLANK_DESC = "必填参数不能为空";

    public static final String RECORD_IS_LOCKED_CODE = "9002";
    public static final String RECORD_IS_LOCKED_DESC = "更新记录已被锁定，请稍后再试";

    public static final String PARAM_VALIDATE_ERROR_CODE = "9003";
    public static final String PARAM_VALIDATE_ERROR_DESC = "参数校验未通过";

    public static final String PARAM_RESULTBLANK_CODE = "9998";
    public static final String PARAM_RESULTBLANK_DESC = "查询结果为空";

    /*******************************************
     * 通用状态 end
     ***************************************/
}
