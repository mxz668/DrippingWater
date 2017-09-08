package com.xz.dripping.common.exception;

/**
 * 功能: 业务异常类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/2/9 0009 09:20
 * 版本: V1.0
 */
public class BusinessException extends RuntimeException {

    /**
     * 业务处理结果编码
     */
    private String resultCode;

    /**
     * 业务处理结果描述
     */
    private String resultMsg;

    /**
     * 附加信息
     */
    private String addition = "";

    /**
     * 异常
     */
    private Throwable exception;

    public BusinessException() {
    }

    public BusinessException(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public BusinessException(String resultCode, String resultMsg, String addition) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.addition = addition;
    }

    public BusinessException(String resultCode, String resultMsg, Throwable exception) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.exception = exception;
    }

    public BusinessException(String resultCode, String resultMsg, String addition, Throwable exception) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.addition = addition;
        this.exception = exception;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }
}
