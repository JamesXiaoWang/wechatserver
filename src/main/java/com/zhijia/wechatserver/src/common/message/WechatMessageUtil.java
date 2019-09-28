package com.zhijia.wechatserver.src.common.message;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.zhijia.wechatserver.src.common.utils.Utils;
import com.zhijia.wechatserver.src.deviceserver.entity.message.Article;
import com.zhijia.wechatserver.src.deviceserver.entity.message.ArticleMessage;
import com.zhijia.wechatserver.src.deviceserver.entity.message.ImageMessage;
import com.zhijia.wechatserver.src.deviceserver.entity.message.MusicMessage;
import com.zhijia.wechatserver.src.deviceserver.entity.message.TextMessage;
import com.zhijia.wechatserver.src.deviceserver.entity.message.VideoMessage;
import com.zhijia.wechatserver.src.deviceserver.entity.message.VoiceMessage;

import net.sf.json.JSONObject;

/**
 * @author Administrators
 * @date 2018年9月11日 上午11:09:07
 * @description: 处理微信消息的工具类（以XML的形式反馈）
 *
 */
@Component
public class WechatMessageUtil {
	private static Logger logger = LoggerFactory.getLogger(WechatMessageUtil.class);
	/**
	 * 请求消息类型：文本
	 */
	public final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：语音
	 */
	public final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：视频
	 */
	public final String REQ_MESSAGE_TYPE_VIDEO = "video";

	/**
	 * 请求消息类型：链接
	 */
	public final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：小视频
	 */
	public final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";

	/**
	 * 请求消息类型：事件推送
	 */
	public final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 返回消息类型：文本
	 */
	public final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 消息返回类型：图片
	 */
	public final String RESP_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 消息返回类型:语音
	 */
	public final String RESP_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 消息返回类型：音乐
	 */
	public final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 消息返回类型：图文
	 */
	public final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 消息返回类型：视频
	 */
	public final String RESP_MESSAGE_TYPE_VIDEO = "video";

	/**
	 * 事件类型:订阅
	 */
	public final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：取消订阅
	 */
	public final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：scan(关注用户扫描带参二维码)
	 */
	public final String EVENT_TYPE_SCAN = "scan";

	/**
	 * 事件类型：location(上报地理位置)
	 */
	public final String EVENT_TYPE_LOCATION = "location";

	/**
	 * 事件类型：CLICK(点击菜单拉取消息)
	 */
	public final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 事件类型：VIEW(自定义菜单URl视图)
	 */
	public final String EVENT_TYPE_VIEW = "VIEW";

	/**
	 * 事件类型：TEMPLATESENDJOBFINISH(模板消息送达情况提醒)
	 */
	public final String EVENT_TYPE_TEMPLATESENDJOBFINISH = "TEMPLATESENDJOBFINISH";
	
	
	/**
	 * 微信设备绑定事件
	 */
	public final String EVENT_TYPE_BIND = "bind";
	/**
	 * 微信设备解绑事件
	 */
	public final String EVENT_TYPE_UNBIND = "unbind";
	
	public final String EVENT_TYPE_DEVICE_EVENT = "device_event";
	
	public final String EVENT_TYPE_SUBSCRIBE_STATUS = "subscribe_status";
	
	public final String EVENT_TYPE_UNSUBSCRIBE_STATUS = "unsubscribe_status";

	public Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = null;
		try {
			// 从request中得到输入流
			inputStream = request.getInputStream();
			byte[] bytes = new byte[1024*1024];  
            int nRead = 1;  
            int nTotalRead = 0;  
            while (nRead > 0) {  
                nRead = inputStream.read(bytes, nTotalRead, bytes.length - nTotalRead);  
                if (nRead > 0)  
                    nTotalRead = nTotalRead + nRead;  
            }  
            String result = new String(bytes, 0, nTotalRead, "utf-8");  
			
            if(!Utils.isObjNull(result)){
            	if(result.trim().indexOf("<xml>") != -1){
        			// 读取输入流
        			// SAXReader reader = new SAXReader();
        			// Document document = reader.read(inputStream);
        			Document document = DocumentHelper.parseText(result.trim());
        			// 得到XML的根元素
        			Element root = document.getRootElement();
        			// 得到根元素的所有子节点
        			List<Element> elementList = root.elements();
        			// 判断又没有子元素列表
        			if (elementList.size() == 0) {
        				map.put(root.getName(), root.getText());
        			} else {
        				for (Element e : elementList)
        					map.put(e.getName(), e.getText());
        			}
                }else{
                	JSONObject obj = JSONObject.fromObject(result.trim());
                	
    				map.put("DeviceID", obj.getString("device_id"));
    				map.put("DeviceType", obj.getString("device_type"));
    				map.put("MsgId", obj.get("msg_id") + "");
    				map.put("MsgType", obj.getString("msg_type"));
    				map.put("CreateTime", obj.get("create_time") + "");
    				map.put("OpenID", obj.getString("open_id"));
    				map.put("SessionID", obj.get("session_id") + "");
    				map.put("Content", obj.getString("content"));
    				if(obj.getString("msg_type").equals("bind")){
    					map.put("QrcodeSuffixData", obj.getString("qrcode_suffix_data"));					
    				}
    				map.put("ToUserName", obj.getString("device_type"));
    				map.put("FromUserName", obj.getString("open_id"));
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			if (inputStream != null) {
				inputStream.close();
				inputStream = null;
			}
		}
		return map;
	}

	/**
	 * 文本信息解析成xml
	 * @param textMessage
	 * @return
	 */
	public String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 图文信息解析成xml
	 * @param newsMessage
	 * @return
	 */
	public String newsMessageToXml(ArticleMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 图片信息解析成xml
	 * @param imageMessage
	 * @return
	 */
	public String imageMessageToXml(ImageMessage imageMessage) {
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	public String voiceMessageToXml(VoiceMessage voiceMessage) {
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}

	public String videoMessageToXml(VideoMessage videoMessage) {
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}

	public String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	private XStream xstream = new XStream(new XppDriver() {
		@Override
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				@Override
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

}
