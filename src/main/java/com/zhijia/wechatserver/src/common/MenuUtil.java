package com.zhijia.wechatserver.src.common;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import com.zhijia.wechatserver.src.common.utils.HttpUtils;
import com.zhijia.wechatserver.src.common.utils.Utils;
import com.zhijia.wechatserver.src.deviceserver.entity.menu.Button;
import com.zhijia.wechatserver.src.deviceserver.entity.menu.ClickButton;
import com.zhijia.wechatserver.src.deviceserver.entity.menu.Menu;
import com.zhijia.wechatserver.src.deviceserver.entity.menu.ViewButton;

import net.sf.json.JSONObject;

/**
 * @author Administrators
 * @date 2018年9月11日 下午2:41:38
 * @description:
 *
 */
public class MenuUtil {
	private static final String CTRATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 创建菜单
	 * 
	 * @param accessToken
	 * @param Menu
	 *            菜单json格式字符串
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static int createMenu(String accessToken, String menu) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
		int result = Integer.MIN_VALUE;
		String url = CTRATE_MENU_URL.replaceAll("ACCESS_TOKEN", accessToken);

		JSONObject json = JSONObject.fromObject(HttpUtils.sendPost(url, menu, "application/json"));
		if (json != null) {
			// 从返回的数据包中取数据{"errcode":0,"errmsg":"ok"}
			result = json.getInt("errcode");
		}
		return result;
	}

	public static String initMenu() {
		// 创建点击一级菜单
		String REDIRECT_PLAY_URI = Utils.getURLEncoderString("http://www.cszjsmart.com/wechatserver/play/zjdbr/wxe2c15d10dd6983bf");
		ViewButton button11 = new ViewButton();
		button11.setName("智佳点播");
		button11.setType("view");
		button11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Conts.APP_ID + "&redirect_uri=" + REDIRECT_PLAY_URI + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");

		String REDIRECT_SETTING_URI = Utils.getURLEncoderString("http://www.cszjsmart.com/wechatserver/setting/setting/wxe2c15d10dd6983bf");
		ViewButton button35 = new ViewButton();
		button35.setName("设置");
		button35.setType("view");
		button35.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Conts.APP_ID + "&redirect_uri=" + REDIRECT_SETTING_URI + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
		
		// 创建跳转型一级菜单
		String REDIRECT_MY_PLAY_URI = Utils.getURLEncoderString("http://www.cszjsmart.com/wechatserver/play/mycloud/wxe2c15d10dd6983bf");
		ViewButton button21 = new ViewButton();
		button21.setName("使用说明");
		button21.setType("view");
		button21.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Conts.APP_ID + "&redirect_uri=" + REDIRECT_MY_PLAY_URI + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");

		// 创建其他类型的菜单与click型用法一致
		String REDIRECT_URI = Utils.getURLEncoderString("http://www.cszjsmart.com/wechatserver/authorize/operation/wxe2c15d10dd6983bf");
		ViewButton button32 = new ViewButton();
		button32.setName("配网绑定");
		button32.setType("view");
		button32.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Conts.APP_ID + "&redirect_uri=" + REDIRECT_URI + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");

		ViewButton button34 = new ViewButton();
		button34.setName("联系我们");
		button34.setType("view");
		button34.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Conts.APP_ID + "&redirect_uri=" + REDIRECT_MY_PLAY_URI + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");

		// 封装到一级菜单
		ClickButton button = new ClickButton();
		button.setName("智佳助手");
		button.setKey("30");
		button.setType("click");
		button.setSub_button(new Button[] { button32, button21, button34 });
		// 封装菜单
		Menu menu = new Menu();
		menu.setButton(new Button[] { button11, button35, button });
		return JSONObject.fromObject(menu).toString();
	}
	
	public static void main(String[] args)throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
		/*System.out.println(initMenu());*/
	}
}
