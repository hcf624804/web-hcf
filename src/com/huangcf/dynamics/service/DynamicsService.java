package com.huangcf.dynamics.service;

import java.util.List;

import com.huangcf.dynamics.vo.DynamicsVO;
import com.huangcf.login.vo.UserVO;

public interface DynamicsService {
	/**
	 * ��ѯ��̬����ҳs
	 * @param use
	 * @param page
	 * @return
	 */
	public List<DynamicsVO> queryDynamics(UserVO use,String page);
	/**
	 * д��̬
	 * @param userId
	 * @param dynamics
	 */
	public void addDynamics(String userId,String dynamics);
	/**
	 * ��ѯ��ҳ��
	 * @param userId
	 * @return
	 */
	public String queryTotalPage(String userId);
	/**
	 * 保存文章
	 * @param use
	 * @param title
	 * @param content
	 */
	public void addArticle(UserVO use,String title,String content);
	/**
	 * 查询文章
	 * @param status
	 * @param userid
	 * @param page
	 * @return
	 */
	public DynamicsVO queryArticle(String id,String status);
}
