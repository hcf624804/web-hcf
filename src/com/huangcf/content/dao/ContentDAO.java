package com.huangcf.content.dao;

import java.util.List;

import com.huangcf.content.vo.ContentVO;

public interface ContentDAO {
	/**
	 * 查询动态的评论总数
	 * @param dynamicsId
	 * @return
	 */
	public String querySum(String dynamicsId);
	/**
	 * 查询动态的评论,带分页
	 * @param id
	 * @param minIndex
	 * @param maxIndex
	 * @return
	 */
	public List<ContentVO> queryContent(String id,int minIndex,int maxIndex);
	/**
	 * 增加评论
	 * @param userId
	 * @param id
	 * @param content
	 */
	public void addContent(String userId,String id,String content);
	/**
	 * 获取动态评论总数
	 * @param id
	 * @return
	 */
	public String getTotalContent(String id);
}
