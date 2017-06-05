package com.xz.dripping.common.dto;

import java.io.Serializable;

/**
 * 功能: RPC分页查询请求基类
 * 创建: Created by MABIAO on 2017/6/5 0005.
 * 日期: 2017/3/28 0028 13:30
 * 版本: V1.0
 */
public class PageQueryRequest implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 116506480140504486L;

    /**
     * 页码(当前页)
     */
    private Integer pageNo;

    /**
     * 分页大小(每页数量)
     */
    private Integer pageSize;

    /**
     * 排序字段
     */
    private Integer sortField;

    /**
     * 排序方式,1.升序,2.降序
     */
    private Integer sortType;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getSortField() {
        return sortField;
    }

    public void setSortField(Integer sortField) {
        this.sortField = sortField;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}
