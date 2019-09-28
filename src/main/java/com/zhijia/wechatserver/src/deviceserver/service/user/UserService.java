package com.zhijia.wechatserver.src.deviceserver.service.user;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.http.HttpException;

import com.zhijia.wechatserver.src.deviceserver.entity.user.UserBind;

/**
 * @author Administrators
 * @date 2018年9月18日 下午4:21:10
 * @description: 用户业务接口
 *
 */
public interface UserService {
	/**
	 * 插入微信用户与设备绑定记录
	 * @param userBind
	 * @return
	 */
	int insertUserBind(UserBind userBind);
	
	/**
	 * 更新用户绑定记录
	 * @param userBind
	 * @return
	 */
	int updateUserBind(UserBind userBind);
	
	/**
	 * 获取用户绑定的设备记录
	 * @param openid
	 * @return
	 */
	List<UserBind> selectUserIsBindForDevice(String openid);
	
	/**
	 * 判断用户是否有绑定设备
	 * @param accessToken
	 * @param openid
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	boolean isAlreadyBinding(String accessToken, String openid) throws HttpException, IOException;
	
	/**
	 * 解绑用户绑定过的所有设备
	 * @param accessToken
	 * @param ticket
	 * @param openid
	 * @throws HttpException
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	void userUnbinding(String accessToken, String ticket, String openid) throws HttpException, IOException, KeyManagementException, NoSuchAlgorithmException;
}
