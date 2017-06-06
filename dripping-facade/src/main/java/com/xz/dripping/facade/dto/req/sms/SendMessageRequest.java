package com.xz.dripping.facade.dto.req.sms;

import com.xz.dripping.common.dto.BaseRequest;

/**
 * 发送短信请求
 * Created by MABIAO on 2017/6/5 0005.
 */
public class SendMessageRequest extends BaseRequest {

    /**
     * 手机号码
     */
    private String phoneNum;

    /**
     * 模板编号
     */
    private String templateCode;

    /**
     * 短信内容
     */
    private String sendContent;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getTempleteCode() {
        return templateCode;
    }

    public void setTempleteCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }
}
