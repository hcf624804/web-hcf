package com.huangcf.like.dao;

public interface LikeDAO {
	/**
	 * ��ѯ�޵�����
	 * @param dynamicsId
	 * @return
	 */
	public String querySum(String dynamicsId);
	/**
	 * �жϵ�ǰ�û��Ƿ�����������̬
	 * @param dynamicsId
	 * @param userid
	 * @return
	 */
	public String queryLikeOrNot(String dynamicsId,String userid);
	/**
	 * ��
	 * @param id
	 * @param did
	 */
	public void likeDynamics(String id,String did);
	/**
	 * ȡ����
	 * @param id
	 * @param did
	 */
	public void dontLikeDynamics(String id,String did);
}
