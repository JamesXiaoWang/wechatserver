package com.zhijia.wechatserver.src.common;
import net.sf.json.JSONObject;

/**
 * @Author: WangJiaPeng
 * @Date: 2019/6/17 16:55
 * @Version 1.0
 */
public class GetToken {
    private static final String GET_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //微信公众号
    private static final String APPID="wx871713cfa989bbab";
    private static final String APPSECRET="7dcfb8601f27ef09739b1e6995438adc";
//        wx493456583e84349a  ffa7168621a92ccc2ce75202c6990a71
//    private static final String APPID="wx871713cfa989bbab";
//    private static final String APPSECRET="ced3a7cdce654a83970e0144c2bc5acf";
    //用于存储token
    private static AccessToken at;

    /**
     * 获取token
     * by 罗召勇 Q群193557337
     */
    private static void getToken(String appid,String appsecret) {
        String url = GET_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
        String tokenStr = Util.get(url);
        JSONObject jsonObject = JSONObject.fromObject(tokenStr);
        String token = jsonObject.getString("access_token");
        String expireIn = jsonObject.getString("expires_in");
        //创建token对象,并存起来。
        at = new AccessToken(token, expireIn);
    }

    /**
     * 向处暴露的获取token的方法
     * @return
     * by 罗召勇 Q群193557337
     */
    public static String getAccessToken(String appid,String appsecret) {
        if(at==null||at.isExpired()) {
            getToken(appid,appsecret);
        }
        return at.getAccessToken();
    }
}
