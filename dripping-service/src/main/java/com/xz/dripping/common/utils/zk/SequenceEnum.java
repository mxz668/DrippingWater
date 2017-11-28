package com.xz.dripping.common.utils.zk;

public enum SequenceEnum {

    ASSET_CODE("ASSET_CODE", "资产编码sequence"),
    ORDER("ORDER", "订单sequence");


    private String code;
    private String desc;

    SequenceEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
