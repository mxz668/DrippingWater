package com.xz.dripping.facade.dto.req.ons;

import com.xz.dripping.common.dto.BaseRequest;

/**
 * Created by MABIAO on 2017/6/6 0006.
 */
public class OnsProducerRequest extends BaseRequest {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
