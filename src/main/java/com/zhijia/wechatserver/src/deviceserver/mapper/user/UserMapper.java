package com.zhijia.wechatserver.src.deviceserver.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhijia.wechatserver.src.deviceserver.entity.user.UserBind;

/**
 * @author Administrators
 * @date 2018年9月18日 下午4:22:04
 * @description:
 *
 */
@Mapper
public interface UserMapper {
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
	List<UserBind> selectUserIsBindForDevice(@Param("openid")String openid);
	
}
