package com.huangcf.like.service;

import java.util.List;

public interface LikeService {
	/**
	 * 赞或者取消赞
	 * @param id 用户ID
	 * @param did 动态ID
	 */
	public void likeDynamics(String id,String did);
	/**
	 * 更新赞
	 * @param id
	 * @param did
	 * @return
	 */
	public List<String> updateDynamicsLike(String id,String did);
}
