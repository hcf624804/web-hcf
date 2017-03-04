package com.huangcf.like.dao;

public interface LikeDAO {
	/**
	 * 查询赞的总数
	 * @param dynamicsId
	 * @return
	 */
	public String querySum(String dynamicsId);
	/**
	 * 判断当前用户是否赞了这条动态
	 * @param dynamicsId
	 * @param userid
	 * @return
	 */
	public String queryLikeOrNot(String dynamicsId,String userid);
	/**
	 * 赞
	 * @param id
	 * @param did
	 */
	public void likeDynamics(String id,String did);
	/**
	 * 取消赞
	 * @param id
	 * @param did
	 */
	public void dontLikeDynamics(String id,String did);
}
