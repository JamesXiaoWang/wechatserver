package com.zhijia.wechatserver.src.common.utils;

import com.baidu.aip.speech.AipSpeech;

/**
 * TTS翻译实例
 * @author Administrator
 *
 */
public class AipSpeechSingleton {
    //设置APPID/AK/SK
    public static final String APP_ID = "11525326";
    public static final String API_KEY = "3AHwjR93TNThIeGjq1kIkEeL";
    public static final String SECRET_KEY = "WBlRVuXhYYaVz1EXsMfn5QDdjH0qBmwM";

    private static AipSpeech  sipSpeech = null;
    private AipSpeechSingleton(){};
    public static synchronized AipSpeech getAipSpeech(){
        if(sipSpeech == null){
            sipSpeech = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        }
        return sipSpeech;
    }

}
