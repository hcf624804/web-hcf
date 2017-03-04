package com.huangcf.dynamics.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.huangcf.advise.service.impl.AdviseServiceImpl;
import com.huangcf.content.dao.ContentDAO;
import com.huangcf.dynamics.dao.DynamicsDAO;
import com.huangcf.dynamics.service.DynamicsService;
import com.huangcf.dynamics.vo.DynamicsVO;
import com.huangcf.like.dao.LikeDAO;
import com.huangcf.login.dao.LoginDAO;
import com.huangcf.login.vo.UserVO;

public class DynamicsServiceImpl implements DynamicsService {
	private DynamicsDAO dynamics_dao;
	private ContentDAO content_dao;
	private LoginDAO login_dao;
	private LikeDAO like_dao;
	public List<DynamicsVO> queryDynamics(UserVO use, String page) {
		int pageIndex = Integer.parseInt(page);
		Properties p = new Properties();
		try {
			p.load(DynamicsServiceImpl.class.getClassLoader().getResourceAsStream("properties.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("获取动态分页失败~");
		}
		String size = p.getProperty("dynamicsPageSize");
		int pageSize = Integer.parseInt(size);
		int minIndex = (pageIndex-1)*pageSize+1;
		int maxIndex = minIndex+pageSize -1;
		String secret = "";
		if("00".equals(use.getRole())){
			secret = "'0','1'";//����Ա���Կ�������˽�ܵĶ�̬
		}else{
			secret = "'0'";//�����û�ֻ�ܿ�˽�ܵĶ�̬
		}
		List<DynamicsVO> list = dynamics_dao.queryDynamics("", "", "", secret, minIndex, maxIndex,"0","0");
		for(DynamicsVO vo:list){
			vo.setContentSum(content_dao.querySum(vo.getId()));
			vo.setLikeSum(like_dao.querySum(vo.getId()));
			vo.setUsername(login_dao.queryUserMessage(vo.getUserid()).getRealname());
			String countL = like_dao.queryLikeOrNot(vo.getId(), use.getId());
			int i = Integer.parseInt(countL);
			if(i>0){
				vo.setSfz("已赞");
			}else{
				vo.setSfz("赞");
			}
		}
		return list;
	}
	public DynamicsDAO getDynamics_dao() {
		return dynamics_dao;
	}
	public void setDynamics_dao(DynamicsDAO dynamics_dao) {
		this.dynamics_dao = dynamics_dao;
	}
	public ContentDAO getContent_dao() {
		return content_dao;
	}
	public void setContent_dao(ContentDAO content_dao) {
		this.content_dao = content_dao;
	}
	public LikeDAO getLike_dao() {
		return like_dao;
	}
	public void setLike_dao(LikeDAO like_dao) {
		this.like_dao = like_dao;
	}
	public LoginDAO getLogin_dao() {
		return login_dao;
	}
	public void setLogin_dao(LoginDAO login_dao) {
		this.login_dao = login_dao;
	}
	@Override
	public void addDynamics(String userId, String dynamics) {
		dynamics_dao.addDynamics(userId, dynamics);
	}
	@Override
	public String queryTotalPage(String userId) {
		UserVO use = login_dao.queryUserMessage(userId);
		String secret = "";
		if("01".equals(use.getRoleid())){
			secret = "'0','1'";
		}else{
			secret = "'0'";
		}
		String totalDynamics = dynamics_dao.queryTotalDynamics(secret);
		int dynamicsSum = Integer.parseInt(totalDynamics);
		Properties p = new Properties();
		try {
			p.load(DynamicsServiceImpl.class.getClassLoader().getResourceAsStream("properties.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("获取动态分页失败~");
		}
		String size = p.getProperty("dynamicsPageSize");
		int pageSize = Integer.parseInt(size);
		int page = 0;
		if(dynamicsSum%pageSize == 0){
			page = dynamicsSum/pageSize;
		}else{
			page = dynamicsSum/pageSize + 1;
		}
		return String.valueOf(page);
	}
	@Override
	public void addArticle(UserVO use, String title, String content) {
		dynamics_dao.addArticle(use.getId(), title, content);
	}
	@Override
	public DynamicsVO queryArticle(String id,String status) {
		DynamicsVO vo = new DynamicsVO();
		vo = dynamics_dao.queryArticle(id,status);
		vo.setUsername(login_dao.queryUserMessage(vo.getUserid()).getRealname());
		return vo;
	}
}
