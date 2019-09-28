package com.zhijia.wechatserver.src.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Administrators
 * @date 2018年11月30日 上午10:44:18
 * @description: 跨域请求过滤器
 *
 */
@Component
public class CorsFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(CorsFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("初始化跨域请求过滤器");
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		// String origin = request.getHeader("Origin");
		// response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "-1");
		
		String method = request.getMethod();
		if (method.equalsIgnoreCase("OPTIONS")) {
			servletResponse.getOutputStream().write("SUCCESS".getBytes("utf-8"));
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	public void destroy() {
		logger.info("销毁跨域请求过滤器");
	}

}
