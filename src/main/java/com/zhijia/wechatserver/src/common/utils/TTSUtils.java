package com.zhijia.wechatserver.src.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.zhijia.wechatserver.src.common.Conts;

import net.sf.json.JSONObject;

/**
 * @author Administrators
 * @date 2019年1月11日 下午5:00:24
 * @description:
 *
 */
public class TTSUtils {

	public static String getTTS(String access_token, String type, String content) {
		String result = "";

		// 初始化文字转语音接口
		AipSpeech aipSpeech = AipSpeechSingleton.getAipSpeech();
		// 可选：设置网络连接参数
		aipSpeech.setConnectionTimeoutInMillis(2000);
		aipSpeech.setSocketTimeoutInMillis(60000);

		// 设置可选参数
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put("spd", "4");
		options.put("pit", "6");
		options.put("per", "4");

		if(!"TEXTMSG".equals(type)){
			content = "接下来要播放的是" + content;
		}
		
		// 调用接口
		TtsResponse res = aipSpeech.synthesis(content, "zh", 1, options);
		byte[] data = res.getData();
		if (data != null) {
			FileOutputStream out = null;
			String filePath = "";
			try {
				SimpleDateFormat simFormat = new SimpleDateFormat("yyyyMMdd");
				String parentDirectory = Conts.WORD_TO_VOICE_FILEPATH + "play" + File.separator + simFormat.format(new Date());

				String fileName = UUID.randomUUID().toString().replace("-", "") + ".mp3";
				filePath = parentDirectory + File.separator + fileName;

				File file = new File(parentDirectory);
				if (!file.exists() && !file.isDirectory()) {
					file.mkdirs();
				}

				out = new FileOutputStream(filePath);
				out.write(data);

				String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=" + access_token + "&type=voice";
				JSONObject jsonObj = JSONObject.fromObject(HttpUtils.uploadTemporaryMaterial(url, filePath));

				if (jsonObj.toString().indexOf("media_id") != -1) {
					result = jsonObj.getString("media_id");
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = "";
			}
		}
		return result;
	}
}
