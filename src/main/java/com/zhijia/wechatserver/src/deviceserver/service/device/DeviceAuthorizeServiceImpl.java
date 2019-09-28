package com.zhijia.wechatserver.src.deviceserver.service.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhijia.wechatserver.src.deviceserver.entity.device.DeviceAuthorize;
import com.zhijia.wechatserver.src.deviceserver.mapper.device.DeviceAuthorizeMapper;

/**
 * @author Administrators
 * @date 2018年9月15日 上午9:42:47
 * @description:
 *
 */
@Service
@Transactional
public class DeviceAuthorizeServiceImpl implements DeviceAuthorizeService {
	@Autowired
	private DeviceAuthorizeMapper deviceAuthorizeMapper;
	
	/**
	 * 添加设备授权记录
	 * @param deviceAuthorize
	 * @return
	 */
	public int insertAuthorizeDevice(DeviceAuthorize deviceAuthorize) {
		int res = deviceAuthorizeMapper.insertAuthorizeDevice(deviceAuthorize);
		if(res <= 0){
			throw new RuntimeException("insert authorize device is error");
		}
		return res;
	}

	/**
	 * 更新设备授权记录
	 * @param deviceAuthorize
	 * @return
	 */
	public int updateAuthorizeDevice(DeviceAuthorize deviceAuthorize) {
		int res = deviceAuthorizeMapper.updateAuthorizeDevice(deviceAuthorize);
		if(res <= 0){
			throw new RuntimeException("update authorize device is error");
		}
		return res;
	}

	/**
	 * 根据zjkey和设备ID获取授权信息
	 * @param zjkey
	 * @param deviceid
	 * @return
	 */
	public DeviceAuthorize selectDevciceByParams(String zjkey, String deviceid) {
		return deviceAuthorizeMapper.selectDevciceByParams(zjkey, deviceid);
	}

}
