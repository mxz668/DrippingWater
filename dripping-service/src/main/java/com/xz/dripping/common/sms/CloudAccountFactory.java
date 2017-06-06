package com.xz.dripping.common.sms;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.MNSClient;

/**
 * 阿里云
 * Created by MABIAO on 2017/6/6 0006.
 */
public class CloudAccountFactory {

    private String accessId;
    private String accessKey;
    private String accountEndpoint;

    public CloudAccountFactory(){

    }

    public CloudAccount getCloudAccount(){
        CloudAccount cloudAccount = new CloudAccount(accessId,accessKey,accountEndpoint);
        return cloudAccount;
    }

    public MNSClient getMNSClient(){
        return this.getCloudAccount().getMNSClient();
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccountEndpoint() {
        return accountEndpoint;
    }

    public void setAccountEndpoint(String accountEndpoint) {
        this.accountEndpoint = accountEndpoint;
    }
}
