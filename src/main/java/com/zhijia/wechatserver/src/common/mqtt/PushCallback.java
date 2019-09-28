package com.zhijia.wechatserver.src.common.mqtt;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

/**
 * 发布消息的回调类
 * 
 * 必须实现MqttCallback的接口并实现对应的相关接口方法 ◦CallBack 类将实现
 * MqttCallBack。每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据。在回调中，将它用来标识已经启动了该回调的哪个实例。
 * ◦必须在回调类中实现三个方法：
 * 
 * public void messageArrived(MqttTopic topic, MqttMessage message) 接收已经预订的发布。
 * 
 * public void connectionLost(Throwable cause) 在断开连接时调用。
 * 
 * public void deliveryComplete(MqttDeliveryToken token)) 接收到已经发布的 QoS 1 或 QoS 2
 * 消息的传递令牌时调用。 ◦由 MqttClient.connect 激活此回调。
 * 
 */
public class PushCallback implements MqttCallback {
	private MqttClient client;
	private ScheduledExecutorService scheduler;
	
	private String username;
	private String password;

	public PushCallback(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// 重新链接
	public void startReconnect() {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable() {
			public void run() {
				if (!client.isConnected()) {
					try {
						/*MqttConnectOptions options = new MqttConnectOptions();
						options.setCleanSession(false);
						options.setUserName(username);
						options.setPassword(password.toCharArray());
						// 设置超时时间
						options.setConnectionTimeout(10);
						// 设置会话心跳时间
						options.setKeepAliveInterval(20);

						client.connect(options);*/

						client.reconnect();
							
						System.out.println("重启完成。");
					} catch (MqttSecurityException e) {
						e.printStackTrace();
					} catch (MqttException e) {
						e.printStackTrace();
					}
				}
			}
		}, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
	}

	public void connectionLost(Throwable cause) {
		// 连接丢失后，一般在这里面进行重连
		System.out.println("连接断开，可以做重连:" + cause);
		/**
		 * 如果连接总是断开，可能的原因是多个相同的clientId连接mqtt服务器
		 */
		startReconnect();
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		// publish后会执行到这里
		System.out.println("deliveryComplete---------" + token.isComplete());

		System.out.println("连接设备的ID：" + token.getClient().getClientId());
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// subscribe后得到的消息会执行到这里面
		System.out.println("接收消息主题:" + topic);
		System.out.println("接收消息Qos:" + message.getQos());
		System.out.println("接收消息内容:" + new String(message.getPayload()));
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
