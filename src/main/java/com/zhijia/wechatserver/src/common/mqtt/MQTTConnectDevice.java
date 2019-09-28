package com.zhijia.wechatserver.src.common.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.zhijia.wechatserver.src.common.Conts;

/**
 * 设备与MQTT服务器连接的工具类
 * @author Administrator
 *
 */
public class MQTTConnectDevice {
	// mqtt客户端
	public MqttClient client;
	// MQTT订阅/发布的主题
	public MqttTopic topic;

	private volatile MQTTConnectDevice mqttConnectDevice = null;
	public MQTTConnectDevice getInstance() throws MqttException {

		if (null == mqttConnectDevice) {
			synchronized (MQTTConnectDevice.class) {
				if (null == mqttConnectDevice) {
					mqttConnectDevice = new MQTTConnectDevice();
				}
			}

		}
		return mqttConnectDevice;
	}
	/**
	 * 客户端连接服务器
	 * @param username 用户名
	 * @param password 密码
	 * @param deviceid 设备id
	 * @return
	 * @throws MqttException
	 */
	public MqttClient connect(String username, String password, String deviceid) throws MqttException {

		client = new MqttClient(Conts.MQTT_HOST, deviceid, new MemoryPersistence());

		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(false);
		options.setUserName(username);
		options.setPassword(password.toCharArray());
		// 设置超时时间
		options.setConnectionTimeout(10);
		// 设置会话心跳时间
		options.setKeepAliveInterval(20);
		try {
			client.setCallback(new PushCallback(username, password));
			client.connect(options);
			/*topic = client.getTopic(mqtt_topic);
			System.out.println(topic + "********************");
			subscribe(topic.getName(), 2);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}

	/**
	 * 发布，默认qos为0，非持久化
	 * 
	 * @param topic
	 * @param pushMessage
	 */
	public void publish(String topic, PushPayload pushMessage) {
		publish(0, false, topic, pushMessage);
	}

	/**
	 * 发布
	 * 
	 * @param qos
	 * @param retained
	 * @param topic
	 * @param pushMessage
	 */
	public void publish(int qos, boolean retained, String topic, PushPayload pushMessage) {
		MqttMessage message = new MqttMessage();
		message.setQos(qos);
		message.setRetained(retained);
		message.setPayload(pushMessage.toString().getBytes());
		MqttTopic mTopic = client.getTopic(topic);
		if (null == mTopic) {
			System.out.println("topic not exist");
		}
		MqttDeliveryToken token;
		try {
			token = mTopic.publish(message);
			token.waitForCompletion();
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 订阅某个主题，qos默认为0
	 * 
	 * @param topic
	 */
	public void subscribe(String topic) {
		subscribe(topic, 0);
	}

	/**
	 * 订阅某个主题
	 * 
	 * @param topic
	 * @param qos
	 */
	public void subscribe(String topic, int qos) {
		try {
			client.subscribe(topic, qos);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public void publish(MqttTopic topic, MqttMessage message) throws MqttPersistenceException, MqttException {
		System.out.println(topic);
		MqttDeliveryToken token = topic.publish(message);
		token.waitForCompletion();
		System.out.println("message is published completely! " + token.isComplete());
	}

	public MqttTopic getTopic() {
		return topic;
	}

	public void setTopic(MqttTopic topic) {
		this.topic = topic;
	}
	
	// 断开连接
	public void disconnect(MqttClient client) throws MqttException{
		if(client.isConnected()){
			client.disconnect();
		}
	}

	/*
	 * public static void main(String[] args) throws MqttException {
	 * 
	 * MqttServerClient server = new MqttServerClient();
	 * 
	 * server.message = new MqttMessage();
	 * 
	 * server.message.setQos(2);
	 * 
	 * server.message.setRetained(true);
	 * 
	 * server.message.setPayload("send message to client, test.".getBytes());
	 * 
	 * server.publish(server.topic, server.message);
	 * 
	 * System.out.println(server.message.isRetained() + "------ratained状态");
	 * 
	 * }
	 */

}
