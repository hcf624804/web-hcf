package com.huangcf.content.service;

import java.util.List;

import com.huangcf.content.vo.ContentVO;

public interface ContentService {
	/**
	 * ��ѯĳ����̬�����ۣ�����ҳ
	 * @param id
	 * @param page
	 * @return
	 */
	public List<ContentVO> queryContent(String id,String page);
	/**
	 * ��������s
	 * @param userId
	 * @param id
	 * @param content
	 */
	public void addContent(String userId,String id,String content);
	/**
	 * ��ȡ��̬����������
	 * @param id
	 * @return
	 */
	public String queryContentSum(String id);
	/**
	 * ��ȡ���۵���ҳ��
	 * @param id
	 * @return
	 */
	public String queryTotalPage(String id);
}
