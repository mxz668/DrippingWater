package com.xz.dripping.common.dto;

import java.util.Collections;
import java.util.List;

/**
 * 功能: RPC分页查询响应基类
 * 创建: Created by MABIAO on 2017/6/5 0005.
 * 日期: 2017/3/28 0028 11:28
 * 版本: V1.0
 */
public class PageQueryResponse<T> extends BaseResponse {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 8285007923632630377L;

    /**
     * 默认分页大小
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 页码(当前页)
     */
    private Integer pageNo = 1;

    /**
     * 分页大小(每页数量)
     */
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 总页数
     */
    private Integer totalPage = 0;

    /**
     * 总记录数
     */
    private Integer totalCount = 0;

    /**
     * 查询结果数据集合
     */
    private List<T> dataList = Collections.emptyList();

    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public Integer getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     */
    public void setPageNo(Integer pageNo) {
        if (pageNo == null) {
            this.pageNo = pageNo;
        } else {
            this.pageNo = (pageNo < 1) ? 1 : pageNo;
        }
    }

    /**
     * 获得每页的记录数量,默认为DEFAULT_PAGESIZE.
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页的记录数量,低于1时自动调整为DEFAULT_PAGESIZE.
     */
    public void setPageSize(Integer pageSize) {
        if (pageSize == null) {
            this.pageSize = pageSize;
        } else {
            this.pageSize = (pageSize < 1) ? DEFAULT_PAGE_SIZE : pageSize;
        }
    }

    /**
     * 取得总记录数, 默认值为0.
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数, 默认值为0.
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = (totalCount < 0) ? 0 : totalCount;
    }

    /**
     * 总页数.
     */
    public Integer getTotalPage() {
        if (pageSize == null) {
            return 1;
        } else {
            totalPage = totalCount / pageSize;
            if (totalCount % pageSize > 0) {
                totalPage++;
            }
            return totalPage;
        }
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
