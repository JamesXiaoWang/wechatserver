package com.zhijia.wechatserver.src;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 主程序入口
 * @author Administrator
 *
 */
@SpringBootApplication
@EnableScheduling
/*@ImportResource("classpath:spring-mvc.xml")*/
public class WechatserverApplication extends SpringBootServletInitializer {
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WechatserverApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WechatserverApplication.class, args);
	}
}
