package com.zhijia.wechatserver.src.deviceserver.service.play;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhijia.wechatserver.src.deviceserver.entity.play.PlayResourceAlbum;
import com.zhijia.wechatserver.src.deviceserver.entity.play.PlayResourceList;
import com.zhijia.wechatserver.src.deviceserver.entity.play.PlayResourceTag;
import com.zhijia.wechatserver.src.deviceserver.mapper.play.PlayResourceMapper;

/**
 * @author Administrators
 * @date 2018年12月27日 下午8:09:25
 * @description:
 *
 */
@Service
@Transactional
public class PlayResourceServiceImpl implements PlayResourceService {
	@Autowired
	private PlayResourceMapper playResourceMapper;

	/**
	 * 插入资源大类信息
	 * @param resourceTag
	 * @return
	 */
	public int insertPlayResourceTag(PlayResourceTag resourceTag) {
		return playResourceMapper.insertPlayResourceTag(resourceTag);
	}
	
	/**
	 * 获取资源大类列表（资源名称）
	 * @return
	 */
	public List<String> selectPlayResourceTagName() {
		return playResourceMapper.selectPlayResourceTagName();
	}

	/**
	 * 插入资源专辑信息
	 * @param resourceAlbum
	 * @return
	 */
	public int insertPlayResourceAlbum(PlayResourceAlbum resourceAlbum) {
		return playResourceMapper.insertPlayResourceAlbum(resourceAlbum);
	}

	/**
	 * 批量插入资源
	 * @param resourceLists
	 * @return
	 */
	public int insertPlayResourceList(List<PlayResourceList> resourceLists) {
		return playResourceMapper.insertPlayResourceList(resourceLists);
	}

	/**
	 * 清空 资源大类、资源专辑、资源信息
	 */
	public void deletePlayResourceAlbum() {
		playResourceMapper.deletePlayResourceAlbum();
	}

	public void deletePlayResourceList() {
		playResourceMapper.deletePlayResourceList();
	}

	public void deletePlayResourceTag() {
		playResourceMapper.deletePlayResourceTag();
	}

	/**
	 * 获取资源大类列表
	 * @return
	 */
	public List<PlayResourceTag> selectPlayResourceTag() {
		return playResourceMapper.selectPlayResourceTag();
	}

	/**
	 * 获取资源专辑列表
	 * @param tagId
	 * @return
	 */
	public List<PlayResourceAlbum> selectPlayResourceAlbumList(Integer tagId) {
		return playResourceMapper.selectPlayResourceAlbumList(tagId);
	}
	
	/**
	 * 根据专辑ID过去专辑信息
	 * @param resId
	 * @return
	 */
	public PlayResourceAlbum selectPlayResourceAlbum(Integer resId) {
		return playResourceMapper.selectPlayResourceAlbum(resId);
	}

	/**
	 * 获取资源列表
	 * @param resId
	 * @return
	 */
	public List<PlayResourceList> selectPlayResourceList(Integer resId) {
		return playResourceMapper.selectPlayResourceList(resId);
	}

	/**
	 * 根据资源id获取资源信息
	 * @param id
	 * @return
	 */
	public PlayResourceList selectPlayResourceById(Integer id) {
		return playResourceMapper.selectPlayResourceById(id);
	}

}
