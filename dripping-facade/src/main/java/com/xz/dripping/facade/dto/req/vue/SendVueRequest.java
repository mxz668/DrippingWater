package com.xz.dripping.facade.dto.req.vue;

import com.xz.dripping.common.dto.BaseRequest;

/**
 * Created by MABIAO on 2017/6/5 0005.
 */
public class SendVueRequest extends BaseRequest {

    private String productCode;

    private String poolCode;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPoolCode() {
        return poolCode;
    }

    public void setPoolCode(String poolCode) {
        this.poolCode = poolCode;
    }
}
