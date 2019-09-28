package com.zhijia.wechatserver.src.deviceserver.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhijia.wechatserver.src.common.utils.Utils;

/**
 * @author Administrators
 * @date 2018年10月29日 下午4:46:36
 * @description: 微信配置控制器
 *
 */
@Controller
@RequestMapping(value = "/")
public class WechatConfigController {
 
	private static Logger logger = LoggerFactory.getLogger(WechatConfigController.class);
	
	/**
	 * Windows下可正常访问， 但是Linux下无法正常访问
	 * @param response
	 * @return
	 */
    public String returnConfigFile(HttpServletResponse response) {
		StringBuilder result = new StringBuilder();
		try {
			File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "MP_verify_HhYFyiB8ewdbn6FM.txt");
			logger.info("域名配置文件：" + file.getPath());
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result.append(System.lineSeparator() + s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("域名配置文件：" + result.toString());
		
		return result.toString();
    }
	
    /**
     * 用于下载网页授权文件（可直接访问下载即可）
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/MP_verify_HhYFyiB8ewdbn6FM.txt")
    public ResponseEntity<InputStreamResource> downloadFile(HttpServletRequest request, HttpServletResponse response)  
            throws IOException {  
    	InputStream stream = Utils.class.getClassLoader().getResourceAsStream("authfile/MP_verify_HhYFyiB8ewdbn6FM.txt");
		/*String filePath = request.getServletContext().getRealPath("/authfile/MP_verify_HhYFyiB8ewdbn6FM.txt");
        FileSystemResource file = new FileSystemResource(filePath);  */
        HttpHeaders headers = new HttpHeaders();  
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
        headers.add("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("MP_verify_HhYFyiB8ewdbn6FM.txt", "UTF-8"));  
        headers.add("Pragma", "no-cache");  
        headers.add("Expires", "0");  
  
        return ResponseEntity.ok().headers(headers)  
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(new InputStreamResource(stream));  
    }  
    
	public void Download(HttpServletRequest request, HttpServletResponse response) {
		String result = Utils.downloadFile(response, "MP_verify_HhYFyiB8ewdbn6FM.txt");
		logger.info(result);
	}

}
