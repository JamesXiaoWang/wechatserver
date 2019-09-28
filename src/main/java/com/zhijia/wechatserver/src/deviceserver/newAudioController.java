package com.zhijia.wechatserver.src.deviceserver;

import com.zhijia.wechatserver.src.common.JSONResult;
import com.zhijia.wechatserver.src.common.base.BaseController;
import com.zhijia.wechatserver.src.common.utils.HttpUtils;
import com.zhijia.wechatserver.src.common.utils.Utils;
import com.zhijia.wechatserver.src.deviceserver.entity.wechat.WechatAuthorize;
import com.zhijia.wechatserver.src.deviceserver.executor.RemoteShellExecutor;
import com.zhijia.wechatserver.src.deviceserver.service.wechat.WechatAudioService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: WangJiaPeng
 * @Date: 2019/4/1 16:19
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/audio")
public class newAudioController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(newAudioController.class);
    @Autowired
    WechatAudioService service;

    @ResponseBody
    @PostMapping(value = "/generateAudio")
    public JSONResult generateAudio(@RequestParam String name, @RequestParam String pwd, @RequestParam String data)throws Exception{
        System.out.println("方法进来了,Wifi名称"+name+"\t"+"Wifi密码"+pwd+"\t"+"data"+data);
        RemoteShellExecutor executor = new RemoteShellExecutor("47.106.172.221", "root", "Cszj1608");
        if (data.length()>40){
            return new JSONResult("ERROR","字符长度过大", "");
        }
        //去空格
        String tempUserName = name.replaceAll(" ", "");
        String tempPassWord = pwd.replaceAll("","");


        String cmd = "/home/syn/newA.sh " + tempUserName +"\t"+tempPassWord + "\t" + data;

        System.out.println(executor.exec(cmd));

        return null;
    }

}
