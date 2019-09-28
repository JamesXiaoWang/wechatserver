package com.zhijia.wechatserver.src.deviceserver.service.play;

import java.util.List;

import com.zhijia.wechatserver.src.deviceserver.entity.play.PlayResourceAlbum;
import com.zhijia.wechatserver.src.deviceserver.entity.play.PlayResourceList;
import com.zhijia.wechatserver.src.deviceserver.entity.play.PlayResourceTag;

/**
 * @author Administrators
 * @date 2018年12月27日 下午8:08:34
 * @description:
 *
 */
public interface PlayResourceService {
	/**
	 * 获取资源大类列表
	 * @return
	 */
	List<PlayResourceTag> selectPlayResourceTag();
	
	/**
	 * 获取资源大类列表（资源名称）
	 * @return
	 */
	List<String> selectPlayResourceTagName();

	/**
	 * 插入资源大类信息
	 * @param resourceTag
	 * @return
	 */
	int insertPlayResourceTag(PlayResourceTag resourceTag);

	/**
	 * 删除资源大类
	 */
	void deletePlayResourceTag();
	
	/**
	 * 获取资源专辑列表
	 * @param tagId
	 * @return
	 */
	List<PlayResourceAlbum> selectPlayResourceAlbumList(Integer tagId);
	
	/**
	 * 根据专辑ID过去专辑信息
	 * @param resId
	 * @return
	 */
	PlayResourceAlbum selectPlayResourceAlbum(Integer resId);

	/**
	 * 删除专辑信息
	 */
	void deletePlayResourceAlbum();

	/**
	 * 插入资源专辑信息
	 * @param resourceAlbum
	 * @return
	 */
	int insertPlayResourceAlbum(PlayResourceAlbum resourceAlbum);
	
	/**
	 * 获取资源列表
	 * @param resId
	 * @return
	 */
	List<PlayResourceList> selectPlayResourceList(Integer resId);

	/**
	 * 批量插入资源
	 * @param resourceLists
	 * @return
	 */
	int insertPlayResourceList(List<PlayResourceList> resourceLists);

	/**
	 * 删除清空资源
	 */
	void deletePlayResourceList();
	
	/**
	 * 根据资源id获取资源信息
	 * @param id
	 * @return
	 */
	PlayResourceList selectPlayResourceById(Integer id);
}
