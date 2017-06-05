package com.xz.dripping.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能: 数据模型基类
 * 创建: Created by MABIAO on 2017/6/5 0005.
 * 日期: 2017/3/28 0028 14:57
 * 版本: V1.0
 */
public class BaseModel implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 7140622715423306450L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 最后修改时间
     */
    private Date modifyTime;

    /**
     * 最后修改人
     */
    private String modifyBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }
}
