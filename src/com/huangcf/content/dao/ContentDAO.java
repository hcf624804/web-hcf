package com.huangcf.content.dao;

import java.util.List;

import com.huangcf.content.vo.ContentVO;

public interface ContentDAO {
	/**
	 * ��ѯ��̬����������
	 * @param dynamicsId
	 * @return
	 */
	public String querySum(String dynamicsId);
	/**
	 * ��ѯ��̬������,����ҳ
	 * @param id
	 * @param minIndex
	 * @param maxIndex
	 * @return
	 */
	public List<ContentVO> queryContent(String id,int minIndex,int maxIndex);
	/**
	 * ��������
	 * @param userId
	 * @param id
	 * @param content
	 */
	public void addContent(String userId,String id,String content);
	/**
	 * ��ȡ��̬��������
	 * @param id
	 * @return
	 */
	public String getTotalContent(String id);
}
