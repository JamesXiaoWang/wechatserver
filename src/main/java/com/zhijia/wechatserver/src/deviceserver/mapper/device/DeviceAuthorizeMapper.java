package com.zhijia.wechatserver.src.deviceserver.mapper.device;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceAuthorize;

/**
 * @author Administrators
 * @date 2018年9月15日 上午9:25:58
 * @description:	设备授权Mapper接口
 *
 */
@Mapper
public interface DeviceAuthorizeMapper {
	/**
	 * 添加设备授权记录
	 * @param deviceAuthorize
	 * @return
	 */
	int insertAuthorizeDevice(DeviceAuthorize deviceAuthorize);
	
	/**
	 * 更新设备授权记录
	 * @param deviceAuthorize
	 * @return
	 */
	int updateAuthorizeDevice(DeviceAuthorize deviceAuthorize);
	
	/**
	 * 根据zjkey和设备ID获取授权信息
	 * @param zjkey
	 * @param deviceid
	 * @return
	 */
	DeviceAuthorize selectDevciceByParams(@Param("zjkey") String zjkey, @Param("deviceid")String deviceid);
}
