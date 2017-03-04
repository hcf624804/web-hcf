package com.huangcf.dynamics.dao;

import java.util.List;

import com.huangcf.dynamics.vo.DynamicsVO;

public interface DynamicsDAO {
	/**
	 * ��ѯ��̬
	 * @param id id
	 * @param minTime ��Сʱ��
	 * @param maxTime ���ʱ��
	 * @param secret �Ƿ�˽��
	 * @param minIndex ��С�����ڷ�ҳ��rownumber
	 * @param maxIndex ������ڷ�ҳ��rownumber
	 * @return
	 */
	public List<DynamicsVO> queryDynamics(String id,String minTime,String maxTime,String secret,int minIndex,int maxIndex,String status,String flag);
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
	public String queryTotalDynamics(String secret);
	/**
	 * 写文章
	 * @param userid
	 * @param title
	 * @param content
	 */
	public void addArticle(String userid,String title,String content);
	/**
	 * 查询文章内容
	 */
	public String queryArticleContent(String id);
	/**
	 * 查询文章其他信息
	 * @param id
	 * @return
	 */
	public DynamicsVO queryArticle(String id,String status);
}
