package com.zhijia.wechatserver.src.deviceserver.service.device;

import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceAuthorize;

/**
 * @author Administrators
 * @date 2018年9月15日 上午9:42:31
 * @description:
 *
 */
public interface DeviceAuthorizeService {
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
	DeviceAuthorize selectDevciceByParams(String zjkey, String deviceid);
}
