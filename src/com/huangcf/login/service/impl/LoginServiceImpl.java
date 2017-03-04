package com.huangcf.login.service.impl;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.huangcf.common.StringUtil;
import com.huangcf.login.dao.LoginDAO;
import com.huangcf.login.service.LoginService;
import com.huangcf.login.vo.UserVO;

public class LoginServiceImpl implements LoginService {
	private LoginDAO login_dao;
	@Override
	public UserVO doLogin(String username,String password,String ip) {
		UserVO vo = login_dao.queryUserByUsername(username);
		if(vo == null){
			vo = new UserVO();
			vo.setMsg("用户名不存在~");
			return vo;
		}
		if(!password.equals(vo.getPassword().trim())){
			vo.setMsg("用户名错误或密码不正确~");
			return vo;
		}
		//登录成功，记录
		login_dao.addRecord(vo.getId(), "00", ip);
		vo.setMsg("");//登录成功，返回的vo中msg为空
		vo.setIp(ip);
		return vo;
	}
	/**
	 * @return the login_dao
	 */
	public LoginDAO getLogin_dao() {
		return login_dao;
	}
	/**
	 * @param login_dao the login_dao to set
	 */
	public void setLogin_dao(LoginDAO login_dao) {
		this.login_dao = login_dao;
	}
	@Override
	public UserVO queryUserMessage(UserVO use) {
		UserVO user = login_dao.queryUserMessage(use.getId());
		user.setIp(use.getIp());
		return user;
	}
	@Override
	public void logout(String id, String ip) {
		login_dao.addRecord(id, "01", ip);
	}
	@Override
	public void enroll(String realname, String loginname, String password,
			String sex, String email) {
		if(StringUtil.isEmptyStr(realname)){
			throw new RuntimeException("用户名为空~");
		}
		if(StringUtil.isEmptyStr(loginname)){
			throw new RuntimeException("登录名名为空~");
		}
		if(StringUtil.isEmptyStr(password)){
			throw new RuntimeException("密码为空~");
		}
		if(StringUtil.isEmptyStr(sex)){
			throw new RuntimeException("性别为空~");
		}
		login_dao.enroll(realname, loginname, password, sex, email);
	}
	@Override
	public boolean checkLoginname(String loginname) {
		UserVO vo = login_dao.queryUserByUsername(loginname);
		if(vo == null){
			return true;
		}
		return false;
	}
	@Override
	public String queryVisitorSum() {		
		return login_dao.queryVisitorSum();
	}
}
