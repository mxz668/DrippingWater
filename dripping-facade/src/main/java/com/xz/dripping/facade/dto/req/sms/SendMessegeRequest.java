package com.xz.dripping.facade.dto.req.sms;

import com.xz.dripping.common.dto.BaseRequest;

/**
 * Created by MABIAO on 2017/6/5 0005.
 */
public class SendMessegeRequest extends BaseRequest {

    private String phoneNum;

    private String templeteCode;

    private String sendContent;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getTempleteCode() {
        return templeteCode;
    }

    public void setTempleteCode(String templeteCode) {
        this.templeteCode = templeteCode;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }
}
