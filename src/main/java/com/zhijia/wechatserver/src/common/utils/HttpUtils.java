package com.zhijia.wechatserver.src.common.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

/**
 * http 请求方式工具类
 * 
 * @author Administrator
 *
 */
public class HttpUtils {

	@SuppressWarnings({ "deprecation", "resource" })
	public static String sendGet(String url, String charset) throws HttpException, IOException {
		HttpClient client = new DefaultHttpClient();
		String result = null;
		HttpGet httpGet = new HttpGet();
		// 设置参数
		try {
			httpGet.setURI(new URI(url));
		} catch (URISyntaxException e) {
			throw new HttpException("请求url格式错误。" + e.getMessage());
		}
		// 发送请求
		HttpResponse httpResponse = client.execute(httpGet);
		// 获取返回的数据
		HttpEntity entity = httpResponse.getEntity();
		byte[] body = EntityUtils.toByteArray(entity);
		StatusLine sL = httpResponse.getStatusLine();
		int statusCode = sL.getStatusCode();
		if (statusCode == 200) {
			result = new String(body, charset);
			entity.consumeContent();
		} else {
			throw new HttpException("statusCode=" + statusCode);
		}
		return result;
	}

	@SuppressWarnings({ "deprecation", "resource" })
	public static String sendGet(String url, String charset, String header) throws HttpException, IOException {
		HttpClient client = new DefaultHttpClient();
		String result = null;
		HttpGet httpGet = new HttpGet();
		// 设置参数
		try {
			httpGet.setHeader("app-name", header);
			httpGet.setURI(new URI(url));
		} catch (URISyntaxException e) {
			throw new HttpException("请求url格式错误。" + e.getMessage());
		}
		// 发送请求
		HttpResponse httpResponse = client.execute(httpGet);
		// 获取返回的数据
		HttpEntity entity = httpResponse.getEntity();
		byte[] body = EntityUtils.toByteArray(entity);
		StatusLine sL = httpResponse.getStatusLine();
		int statusCode = sL.getStatusCode();
		if (statusCode == 200) {
			result = new String(body, charset);
			entity.consumeContent();
		} else {
			throw new HttpException("statusCode=" + statusCode);
		}
		return result;
	}

	/**
	 * 
	 * @param url
	 *            资源地址
	 * @param map
	 *            参数列表
	 * @param encoding
	 *            编码
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String sendPost(String url, String params, String method)
			throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
		String body = "";
		// 采用绕过验证的方式处理https请求
		SSLContext sslcontext = createIgnoreVerifySSL();

		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext)).build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClients.custom().setConnectionManager(connManager);

		// 创建自定义的httpclient对象
		CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
		// CloseableHttpClient client = HttpClients.createDefault();

		// 创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);

		// 装填参数
		System.out.println("请求地址：" + url);
		System.out.println("请求参数：" + params);

		StringEntity stringEntity = new StringEntity(params, "utf-8");
		// 设置参数到请求对象中
		httpPost.setEntity(stringEntity);

		// 设置header信息
		// 指定报文头【Content-type】、【User-Agent】
		httpPost.setHeader("Content-type", method);
		// httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;
		// Windows NT; DigExt)");

		System.out.println("http 信息：" + httpPost);

		// 执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpPost);
		// 获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// 按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, "utf-8");
		}
		EntityUtils.consume(entity);
		// 释放链接
		response.close();
		return body;
	}

	/**
	 * 绕过验证
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}

	public static final String download_media_url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	public static String downloadMediaFromWx(String media_url, String fileSavePath) throws IOException {

		URL url = new URL(media_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		InputStream in = conn.getInputStream();

		File dir = new File(fileSavePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (!fileSavePath.endsWith("/")) {
			fileSavePath += "/";
		}

		String ContentDisposition = conn.getHeaderField("Content-disposition");
		String weixinServerFileName = ContentDisposition.substring(ContentDisposition.indexOf("filename") + 10,
				ContentDisposition.length() - 1);
		fileSavePath += weixinServerFileName;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileSavePath));
		byte[] data = new byte[1024];
		int len = -1;

		while ((len = in.read(data)) != -1) {
			bos.write(data, 0, len);
		}

		bos.close();
		in.close();
		conn.disconnect();

		return fileSavePath;
	}
	
	public static String uploadTemporaryMaterial(String url, String localFile){
		String result = "";
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(url);
            // 把文件转换成流对象FileBody
            FileBody bin = new FileBody(new File(localFile));
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("media", bin).build();
            httpPost.setEntity(reqEntity);
            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);
            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity);
                // 销毁
                EntityUtils.consume(resEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(response != null){
                    response.close();
                }
                if(httpClient != null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
