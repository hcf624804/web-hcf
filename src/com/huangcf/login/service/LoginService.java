package com.huangcf.login.service;

import com.huangcf.login.vo.UserVO;

public interface LoginService {
	/**
	 * 登录
	 * @param username
	 * @return
	 */
	public UserVO doLogin(final String username,final String password,final String ip);
	/**
	 * 查询用户信息
	 * @param use
	 * @return
	 */
	public UserVO queryUserMessage(UserVO use);
	/**
	 * 登出
	 * @param id
	 * @param ip
	 */
	public void logout(String id,String ip);
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
	 * 判断登录名是否存在
	 * @param loginname
	 * @return
	 */
	public boolean checkLoginname(String loginname);
	/**
	 * 查询访问总人数
	 * @return
	 */
	public String queryVisitorSum();
}
