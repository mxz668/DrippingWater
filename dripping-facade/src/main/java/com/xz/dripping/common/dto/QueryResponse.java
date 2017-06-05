package com.xz.dripping.common.dto;

/**
 * 功能: RPC查询响应基类
 * 创建: Created by MABIAO on 2017/6/5 0005.
 * 日期: 2017/3/29 0029 14:07
 * 版本: V1.0
 */
public class QueryResponse<T> extends BaseResponse {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -164149266074747758L;

    /**
     * 查询结果数据
     */
    private T data = null;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
