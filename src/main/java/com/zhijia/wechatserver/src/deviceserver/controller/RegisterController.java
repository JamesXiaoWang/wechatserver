package com.zhijia.wechatserver.src.deviceserver.controller;

import com.zhijia.wechatserver.src.common.GetToken;
import com.zhijia.wechatserver.src.common.base.BaseController;
import com.zhijia.wechatserver.src.common.redis.RedisService;
import com.zhijia.wechatserver.src.common.utils.HttpUtils;
import com.zhijia.wechatserver.src.common.utils.Utils;
import com.zhijia.wechatserver.src.deviceserver.entity.wechat.Audio;
import com.zhijia.wechatserver.src.deviceserver.entity.wechat.WechatAuthorize;
import com.zhijia.wechatserver.src.deviceserver.executor.RemoteShellExecutor;
import com.zhijia.wechatserver.src.deviceserver.service.wechat.WechatAudioService;
import net.sf.json.JSONObject;
import org.jrebirth.af.api.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: WangJiaPeng
 * @Date: 2019/4/1 16:19
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/wifi")
public class RegisterController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    WechatAudioService service;
    @Autowired
    private RedisService redisService;
    @PostMapping(value = "/login")
    public String Login(@RequestParam String name, @RequestParam String pwd,@RequestParam String openId)throws Exception{
        System.out.println("方法进来了,Wifi名称"+name+"\t"+"Wifi密码"+pwd+"\t"+"openId"+openId);
        RemoteShellExecutor executor = new RemoteShellExecutor("47.106.172.221", "wangjp2", "wjp12345");

        //去空格
        String tempUserName = name.replaceAll(" ", "");
        String tempPassWord = pwd.replaceAll("","");

        String cmd = "/home/syn/testA.sh " + tempUserName +"\t"+tempPassWord + "\t" + openId;
        //执行shell脚本，生成音频文件
        System.out.println(executor.exec(cmd));
        //执行完毕后跳转的音频播放页面
        return "dev/wifi_audio";
    }
    @RequestMapping(value = "/getToken")
    @ResponseBody
    public String getToken(String appid,String appsecret){
       logger.info("获取accessToken,appid：{"+appid+"}"+"\t"+"appsecret：{"+appsecret+"}");

        String token = GetToken.getAccessToken(appid,appsecret);

        logger.info("获取到token："+token);

        return token;
    }
    /**
     * 加载说明页面
     * @param map
     * @return 说明页面
     */
   /* @RequestMapping(value = "/soundAuth/{appid}")*/
  /*  public String soundAuth(Map<String, Object> map, @PathVariable("appid") String appid, String code, String openid) {
        try {
          *//*  map.put("appid", appid);
            List<WechatAuthorize> wechatAuthorizes = wechatAuthorizeService.selectWechatAuthorizeByAppId(appid);
            System.out.println("list"+wechatAuthorizes.size());
            map.put("wechatAuthorizes", wechatAuthorizes);*//*
            System.out.println("appid>>>>>>>>>>>>>>>>>>>"+appid);
            logger.info("logger appid>>>>>>>>>>>>>>>>>>>"+appid);
            System.out.println("openid>>>>>>>>>>>>>>>>>>"+openid);
            logger.info("logger openid>>>>>>>>>>>>>>>>>>>"+openid);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("加载声波Auth页面出错：" + e.getMessage());
        }
        return "wifi/auth";
    }*/
    @RequestMapping(value = "/soundAuth/{appid}")
    public String soundAuth(Map<String, Object> map, @PathVariable("appid") String appid, String code, String openid) {
        map.put("appid", appid);
        try {
            if(!Utils.isObjNull(code) && Utils.isObjNull(openid)){
                WechatAuthorize wa = wechatAuthorizeService.selectWechatAuthorizeByParams(appid, null);
                logger.info("####################################################");
                logger.info("# code #：" + code);
                logger.info("####################################################");
                String secret = wa.getAppsecret();
                System.out.println("有没有获取到appid"+appid);
                String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
                String url = tokenUrl.replaceAll("APPID", appid).replaceAll("SECRET", secret).replaceAll("CODE", code);
                JSONObject json = JSONObject.fromObject(HttpUtils.sendGet(url, "UTF-8"));

                if(json.toString().indexOf("openid") != -1){
                    openid = json.getString("openid");
                }

                logger.info("##########################################################################");
                logger.info("##########################################################################");
                logger.info("##########################################################################");
                logger.info("openid：" + openid);
                logger.info("##########################################################################");
                logger.info("##########################################################################");
            }
            map.put("openid", openid);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("加载声波Auth页面出错：" + e.getMessage());
        }
        return "dev/wifi_auth";
    }

    /**
     * 加载wifi输入页面
     * @return
     */
    @RequestMapping(value = "/loginMethod")
    public String loginMethod(Map<String, Object> map,String openid,String appid){
        //此处 没有openId
        map.put("openid",openid);
        map.put("appid",appid);
        System.out.println("进入如login页面》》》》》》》》》》"+openid+"\t"+"appid>>>"+appid);

        return "dev/wifi_login";
    }
    /**
     * 加载播放声波页面
     * @return
     */
    @RequestMapping(value = "/playAudio")
    public String playAudio() {
        return "wifi/audio";
    }
    @RequestMapping(value = "/testAudio")
//    public String testAudio(@PathVariable("appid") String appid, Map<String, Object> map)
//    {
//        try {
//            map.put("appid", appid);
//            List<WechatAuthorize> wechatAuthorizes = wechatAuthorizeService.selectWechatAuthorizeByAppId(appid);
//            map.put("wechatAuthorizes", wechatAuthorizes);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.info("加载操作指示页面出错：" + e.getMessage());
//        }
//        return "wifi/TestAudio";
//    }
    public String testAudio(@RequestParam String deviceId,Map<String, Object> map)
    {
        System.out.println("deviceId>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+deviceId);
        map.put("deviceId", deviceId);
        map.put("appid", "wxe2c15d10dd6983bf");
        try {
            List<WechatAuthorize> wechatAuthorizes = wechatAuthorizeService.selectWechatAuthorizeByAppId("wxe2c15d10dd6983bf");
            System.out.println("查询条数>>>>>>>>>>>>>>>>>>>"+wechatAuthorizes.size());
            map.put("wechatAuthorizes", wechatAuthorizes);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("加载操作指示页面出错：" + e.getMessage());
        }
        return "wifi/wifi_audio";
    }
    /**
     * 加载操作指示
     * @param request
     * @param response
     * @param map
     * @return	指示页面
     */
//    @RequestMapping(value = "/operation/{appid}")
//    public String operation(Map<String, Object> map, @PathVariable("appid") String appid, String zjkey) {
//        try {
//            WechatAuthorize authorize = wechatAuthorizeService.selectWechatAuthorizeByParams(appid, zjkey);
//            map.put("qrcode", authorize.getQrcodelink());
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.info("加载操作指示页面出错：" + e.getMessage());
//        }
//        return "dms/qrcode";
//    }

}
