package com.zhijia.wechatserver.src.common;

/**
 * @Author: WangJiaPeng
 * @Date: 2019/6/17 16:59
 * @Version 1.0
 */
public class AccessToken {
    private String accessToken;
    private long expireTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public AccessToken(String accessToken, String expireIn) {
        super();
        this.accessToken = accessToken;
        expireTime = System.currentTimeMillis()+Integer.parseInt(expireIn)*1000;
    }

    /**
     * 判断token是否过期
     * @return
     * by 罗召勇 Q群193557337
     */
    public boolean isExpired() {
        return System.currentTimeMillis()>expireTime;
    }
}
