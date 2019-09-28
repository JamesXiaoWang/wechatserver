package com.zhijia.wechatserver.src.common.scheduled;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zhijia.wechatserver.src.common.Conts;
import com.zhijia.wechatserver.src.common.JSONResult;
import com.zhijia.wechatserver.src.common.redis.RedisService;
import com.zhijia.wechatserver.src.common.utils.HttpUtils;
import com.zhijia.wechatserver.src.common.utils.Utils;
import com.zhijia.wechatserver.src.deviceserver.entity.play.PlayResourceAlbum;
import com.zhijia.wechatserver.src.deviceserver.entity.play.PlayResourceList;
import com.zhijia.wechatserver.src.deviceserver.entity.play.PlayResourceTag;
import com.zhijia.wechatserver.src.deviceserver.entity.wechat.WechatAuthorize;
import com.zhijia.wechatserver.src.deviceserver.service.WechatCoreService;
import com.zhijia.wechatserver.src.deviceserver.service.play.PlayResourceService;
import com.zhijia.wechatserver.src.deviceserver.service.wechat.WechatAuthorizeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Administrators
 * @date 2018年11月20日 上午11:42:56
 * @description: 定时任务处理类
 *
 */
@Component
public class ScheduledController {
	private static Logger logger = LoggerFactory.getLogger(ScheduledController.class);
	
	private static final String QMZRESOURCE_URL = "http://cloud.qdreamer.com:8001/api-a/v1/resources";
	
	private static final String QMZRESOURCE_URL_CLASSIFY = "http://cloud.qdreamer.com:8001/api-a/v1/resources/classify";
	@Autowired
	private RedisService redisService;
	@Autowired
	private WechatCoreService wechatCoreService;
	@Autowired
	private WechatAuthorizeService wechatAuthorizeService;
	
	@Autowired
	private PlayResourceService playResourceService;

	/**
	 * 同步奇梦者资源，每天凌晨一点钟更新一次
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	@Scheduled(cron = "0 0 01 * * ?")
	public JSONResult updatePlayResource() throws HttpException, IOException{
		try {
			logger.info("##################################################");
			logger.info("####################开始更新资源#####################");
			logger.info("##################################################");
			JSONObject classify = JSONObject.fromObject(HttpUtils.sendGet(QMZRESOURCE_URL_CLASSIFY, Conts.CHARSET, Conts.APP_NAME));
			List<PlayResourceList> resourceLists = new ArrayList<PlayResourceList>();
			if(!Utils.isObjNull(classify) && classify.getInt("code") == 0){
				playResourceService.deletePlayResourceTag();
				// 先删除专辑历史数据
				logger.info("先删除专辑历史数据");
				playResourceService.deletePlayResourceAlbum();
				logger.info("删除专辑历史数据完成！");
				
				JSONArray classify_list = classify.getJSONArray("list");
				for (int i = 0; i < classify_list.size(); i++) {
					
					PlayResourceTag tag = new PlayResourceTag();
					tag.setTagName(classify_list.get(i) + "");
					tag.setTagIco("images/story_ico.png");
					tag.setStatus(1);
					playResourceService.insertPlayResourceTag(tag);
					
					String url = QMZRESOURCE_URL + "?assign=tag&tag_name=" + classify_list.get(i);
					JSONObject ablum_resource = JSONObject.fromObject(HttpUtils.sendGet(url, "utf-8", "zhijia"));
					if(!Utils.isObjNull(ablum_resource) && ablum_resource.getInt("code") == 0){
						JSONObject response = ablum_resource.getJSONObject("response");
						if(!Utils.isObjNull(response)){
							JSONArray list = response.getJSONArray("list");
							for (int j = 0; j < list.size(); j++) {
								
								JSONObject ablum = list.getJSONObject(j);
								
								PlayResourceAlbum resourceAlbum = new PlayResourceAlbum();
								resourceAlbum.setTagId(tag.getId());
								resourceAlbum.setHost(response.getString("host"));
								resourceAlbum.setResUrl(response.getString("res_url"));
								resourceAlbum.setImgUrl(response.getString("img_url"));
								
								resourceAlbum.setName(ablum.getString("name"));
								resourceAlbum.setImg(ablum.getString("img"));
								resourceAlbum.setIntroduce(ablum.getString("introduce"));
								resourceAlbum.setTagName(ablum.getString("tag_name"));
								resourceAlbum.setTotal(ablum.getInt("total"));
								playResourceService.insertPlayResourceAlbum(resourceAlbum);
								
								
								String item_url = QMZRESOURCE_URL + "?assign=item&tag_name=" + ablum.getString("name");
								JSONObject item_resource = JSONObject.fromObject(HttpUtils.sendGet(item_url, "utf-8", "zhijia"));
								if(!Utils.isObjNull(item_resource) && item_resource.getInt("code") == 0){
									
									JSONObject item_response = item_resource.getJSONObject("response");
									if(!Utils.isObjNull(item_response)){
										JSONArray resource_list = item_response.getJSONArray("list");
										if(!Utils.isObjNull(resource_list) && resource_list.size() > 0){
											for (int k = 0; k < resource_list.size(); k++) {
												PlayResourceList resourceList = new PlayResourceList();
												resourceList.setResId(resourceAlbum.getId());
												resourceList.setSection(resource_list.getJSONObject(k).getString("section"));
												resourceList.setIsFavorite(resource_list.getJSONObject(k).getString("is_favorite"));
												resourceList.setFavoriteId(resource_list.getJSONObject(k).getString("favorite_id"));
												resourceList.setRes(resource_list.getJSONObject(k).getString("res"));
												resourceList.setIsMusic(resource_list.getJSONObject(k).getString("is_music"));
												resourceLists.add(resourceList);
											}
										}
									}
								}
							}
						}
					}
				}
				
				if(!Utils.isObjNull(resourceLists) && resourceLists.size() > 0){
					logger.info("先删除资源列表历史数据");
					playResourceService.deletePlayResourceList();
					logger.info("批量插入资源数据");
					playResourceService.insertPlayResourceList(resourceLists);
				}
			}
			
			return new JSONResult("SUCCESS", "成功", resourceLists.size());
		} catch (Exception e) {
			return new JSONResult("失败");
		}
	}
	
	/**
	 * 获取访问微信平台的唯一凭据
	 * 开机启动获取一次，然后每隔1个小时获取一次
	 * @throws HttpException
	 * @throws IOException
	 */
	@Scheduled(fixedRate = 5400*1000)
	public void refreshAccessToken() throws HttpException, IOException{
		logger.info("############################开始获取公众号的全局唯一接口调用凭据###########################");
		List<WechatAuthorize> authorizes = wechatAuthorizeService.selectWechatAuthorizeList();
		
		for (int i = 0; i < authorizes.size(); i++) {
			WechatAuthorize authorize = authorizes.get(i);
			
			String appid = authorize.getAppid();
			String secret = authorize.getAppsecret();
			
			String token_key = appid + "_atoken", ticket_key = appid + "_ticket";
			// 将访问的AccessTOKEN添加到Redis数据库中，超时7200s
			// 获取access——token（网页访问的唯一凭证）
			JSONObject jobj = wechatCoreService.getAccess_token(appid, secret);
			redisService.set(token_key, jobj.getString("access_token"), jobj.getLong("expires_in"));
			
			String jsapi_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + jobj.getString("access_token") + "&type=jsapi";
			String jsapiticket = HttpUtils.sendGet(jsapi_url, "utf-8");
			// 获取JS访问凭证
			JSONObject json_jsapiticket = JSONObject.fromObject(jsapiticket);
			String ticket = json_jsapiticket.getString("ticket");
			
			redisService.set(ticket_key, ticket, (long)7200);
		}
		logger.info("############################结束获取公众号的全局唯一接口调用凭据###########################");
	}
	
	public static void main(String[] args) throws HttpException, IOException {
		String url = QMZRESOURCE_URL + "?assign=item&tag_name=灰姑娘";
		JSONObject resource = JSONObject.fromObject(HttpUtils.sendGet(url, "utf-8", "zhijia"));
		
		System.out.println(resource.toString());
	}
}
