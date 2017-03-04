package com.huangcf.login.dao;

import com.huangcf.login.vo.UserVO;

public interface LoginDAO {
	/**
	 * 根据登录名查询用户
	 * @param username
	 * @return
	 */
	public UserVO queryUserByUsername(String username);
	/**
	 * 增加记录
	 * @param id
	 */
	public void addRecord(String id,String type,String ip);
	/**
	 * 查询登录用户的信息
	 * @param id
	 * @return
	 */
	public UserVO queryUserMessage(String id);
	/**
	 * 注册
	 * @param realname
	 * @param loginname
	 * @param password
	 * @param sex
	 * @param email
	 */
	public void enroll(String realname,String loginname,String password,String sex,String email);
	/**
	 * 查询访问总人数
	 * @return
	 */
	public String queryVisitorSum();
}
