package com.huangcf.dynamics.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.huangcf.common.BaseAction;
import com.huangcf.common.JsonUtil;
import com.huangcf.dynamics.service.DynamicsService;
import com.huangcf.dynamics.vo.DynamicsVO;
import com.huangcf.login.vo.UserVO;

public class DynamicsAction extends BaseAction {
	private UserVO use;
	private String jsonData;
	private DynamicsService dynamics_service;
	private String title;
	private String content;
	
	public String showDynamics(){
		try{
			String page = this.getRequest().getParameter("page");
			List<DynamicsVO> list = dynamics_service.queryDynamics(use,page);
			jsonData = "{\"success\":true,\"result\":'"+JsonUtil.getJsonStringFromCollection(list)+"'}";
		}catch(Exception e){
			jsonData = "{\"success\":false,\"result\":\""
					+ e.getMessage().toString().replaceAll("\"", "\'").replaceAll("\n", "") + "\"}";
		}
		System.out.println(jsonData);
		return "success";
	}
	public String writeDynamics(){
		try{
			UserVO user = (UserVO) this.getRequest().getSession().getAttribute("userInfo");
			String userId = user.getId();
			String dynamics = this.getRequest().getParameter("dynamics");
			dynamics_service.addDynamics(userId, dynamics);
			jsonData = "{\"success\":true}";
		}catch(Exception e){
			jsonData = "{\"success\":false,\"result\":\""
					+ e.getMessage().toString().replaceAll("\"", "\'").replaceAll("\n", "") + "\"}";
		}
		return "success";
	}
	public String queryTotalPage(){
		try{
			UserVO user = (UserVO) this.getRequest().getSession().getAttribute("userInfo");
			String userId = user.getId();
			String page = dynamics_service.queryTotalPage(userId);
			jsonData = "{\"success\":true,\"page\":'"+page+"'}";
		}catch(Exception e){
			jsonData = "{\"success\":false,\"result\":\""
					+ e.getMessage().toString().replaceAll("\"", "\'").replaceAll("\n", "") + "\"}";
		}
		System.out.println(jsonData);
		return "success";
	}
	public String writeArticle(){
		try{
			UserVO user = (UserVO) this.getRequest().getSession().getAttribute("userInfo");
			dynamics_service.addArticle(user, title, content);
			jsonData = "{\"success\":true}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""
					+ e.getMessage().toString().replaceAll("\"", "\'").replaceAll("\n", "") + "\"}";
			return "fail";
		}
		System.out.println(jsonData);
		return "success";
	}
	//查询关于
	public String toShowAbout(){
		try{
			DynamicsVO vo = dynamics_service.queryArticle("", "2");
			HttpSession session = this.getRequest().getSession();//获取会话
			session.setAttribute("dynamics", vo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	//查询文章
	public String toShowArticle(){
		try{
			String id = this.getRequest().getParameter("id");
			DynamicsVO vo = dynamics_service.queryArticle(id, "0");
			HttpSession session = this.getRequest().getSession();//获取会话
			session.setAttribute("dynamics", vo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	public String toWriteArticle(){
		return "success";
	}
	public UserVO getUse() {
		return use;
	}

	public void setUse(UserVO use) {
		this.use = use;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public DynamicsService getDynamics_service() {
		return dynamics_service;
	}
	public void setDynamics_service(DynamicsService dynamics_service) {
		this.dynamics_service = dynamics_service;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
