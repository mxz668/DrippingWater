package com.xz.dripping.facade.dto.req.mycat;

import com.xz.dripping.common.dto.BaseRequest;

/**
 * Created by MABIAO on 2017/9/5.
 */
public class TestRequest extends BaseRequest {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}
