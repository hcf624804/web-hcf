package com.huangcf.like.service;

import java.util.List;

public interface LikeService {
	/**
	 * �޻���ȡ����
	 * @param id �û�ID
	 * @param did ��̬ID
	 */
	public void likeDynamics(String id,String did);
	/**
	 * ������
	 * @param id
	 * @param did
	 * @return
	 */
	public List<String> updateDynamicsLike(String id,String did);
}
