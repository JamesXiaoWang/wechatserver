package com.zhijia.wechatserver.src.deviceserver.service.user;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhijia.wechatserver.src.common.utils.HttpUtils;
import com.zhijia.wechatserver.src.common.utils.Utils;
import com.zhijia.wechatserver.src.deviceserver.entity.user.UserBind;
import com.zhijia.wechatserver.src.deviceserver.mapper.user.UserMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Administrators
 * @date 2018年9月18日 下午4:21:31
 * @description: 业务实现
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	public boolean isAlreadyBinding(String accessToken, String openid) throws HttpException, IOException {
		String url = "https://api.weixin.qq.com/device/get_bind_device?access_token=" + accessToken + "&openid=" + openid;
		
		JSONObject json = JSONObject.fromObject(HttpUtils.sendGet(url, "utf-8"));
		
		JSONObject resp_msg = json.containsKey("resp_msg") ? json.getJSONObject("resp_msg") : null;
		if(!Utils.isObjNull(resp_msg) && resp_msg.getInt("ret_code") == 0){
			// JSONArray array = resp_msg.getJSONArray("device_list");
			// if(array.size() > 0){
				return true;				
			// }
		}		
		return false;
	}

	public void userUnbinding(String accessToken, String ticket, String openid) throws HttpException, IOException, KeyManagementException, NoSuchAlgorithmException {
		String url = "https://api.weixin.qq.com/device/get_bind_device?access_token=" + accessToken + "&openid=" + openid;
		
		JSONObject json = JSONObject.fromObject(HttpUtils.sendGet(url, "utf-8"));
		
		JSONObject resp_msg = json.containsKey("resp_msg") ? json.getJSONObject("resp_msg") : null;
		if(!Utils.isObjNull(resp_msg) && resp_msg.getInt("ret_code") == 0){
			JSONArray deviceList = json.getJSONArray("device_list");
			for (int i = 0; i < deviceList.size(); i++) {
				String unbind_url = "https://api.weixin.qq.com/device/unbind?access_token=" + accessToken;
				String deviceId = deviceList.getJSONObject(i).getString("device_id");
				JSONObject params = new JSONObject();
				params.put("ticket", ticket);
				params.put("device_id", deviceId);
				params.put("openid", openid);
				// 解绑用户绑定设备
				JSONObject unbind_res = JSONObject.fromObject(HttpUtils.sendPost(unbind_url, params.toString(), "application/json"));
				JSONObject base_req = unbind_res.getJSONObject("base_resp");
				// 解绑成功，即保存数据
				if(base_req.getInt("errcode") == 0){
					UserBind userBind = new UserBind();
					userBind.setOpenid(openid);
					userBind.setDeviceId(deviceId);
					
					userBind.setIsBind(0); // 解绑
					userBind.setUnbindDate(new Date());
					
					userMapper.insertUserBind(userBind);
				}
			}
		}	
	}

	/**
	 * 插入微信用户与设备绑定记录
	 * @param userBind
	 * @return
	 */
	public int insertUserBind(UserBind userBind) {
		return userMapper.insertUserBind(userBind);
	}

	/**
	 * 更新用户绑定记录
	 * @param userBind
	 * @return
	 */
	public int updateUserBind(UserBind userBind) {
		return userMapper.updateUserBind(userBind);
	}

	/**
	 * 获取用户绑定的设备记录
	 * @param openid
	 * @return
	 */
	public List<UserBind> selectUserIsBindForDevice(String openid) {
		return userMapper.selectUserIsBindForDevice(openid);
	}

}
