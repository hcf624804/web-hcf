package com.huangcf.content.service;

import java.util.List;

import com.huangcf.content.vo.ContentVO;

public interface ContentService {
	/**
	 * 查询某条动态的评论，带分页
	 * @param id
	 * @param page
	 * @return
	 */
	public List<ContentVO> queryContent(String id,String page);
	/**
	 * 增加评论s
	 * @param userId
	 * @param id
	 * @param content
	 */
	public void addContent(String userId,String id,String content);
	/**
	 * 获取动态的评论总数
	 * @param id
	 * @return
	 */
	public String queryContentSum(String id);
	/**
	 * 获取评论的总页数
	 * @param id
	 * @return
	 */
	public String queryTotalPage(String id);
}
