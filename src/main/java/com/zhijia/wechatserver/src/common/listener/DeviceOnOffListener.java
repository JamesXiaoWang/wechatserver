package com.zhijia.wechatserver.src.common.listener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.zhijia.wechatserver.src.common.mqtt.MQTTConnectDevice;
import com.zhijia.wechatserver.src.common.utils.Utils;
import com.zhijia.wechatserver.src.deviceserver.service.device.DeviceMqttService;

import net.sf.json.JSONObject;

/**
 * @author Administrators
 * @date 2019年1月4日 下午3:16:49
 * @description: 设备上下线监听器
 *
 */
@Component
public class DeviceOnOffListener implements CommandLineRunner {
	@Autowired
	private DeviceMqttService deviceMqttService;
	
	public void run(String... args) throws Exception {
		try {
			MQTTConnectDevice connectDevice = new MQTTConnectDevice();
			MqttClient client = connectDevice.connect("admin", "cszj1608", "admin_device");

			client.subscribe(new String[]{"$SYS/brokers/+/clients/+/connected","$SYS/brokers/+/clients/+/disconnected"}, new int[]{2,2});
			
			MqttCallback mqttCallback = new MqttCallback() {
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					String action = topic.substring(topic.lastIndexOf("/") + 1, topic.length());
					if(!Utils.isObjNull(message)){
						JSONObject obj = JSONObject.fromObject(message.toString());
						String clientid = obj.getString("clientid");
						int online = 0;
						switch (action) {
							case "connected":
								online = 1;
								break;
							case "disconnected":
								online = 0;
								break;
							default:
								break;
						}
						deviceMqttService.updateDeviceIsOnline(online, clientid);
					}
				}
				
				public void deliveryComplete(IMqttDeliveryToken token) {
					
				}
				
				public void connectionLost(Throwable cause) {
					
				}
			};
			client.setCallback(mqttCallback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
