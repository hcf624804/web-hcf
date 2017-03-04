package com.huangcf.content.action;

import java.util.List;

import com.huangcf.common.BaseAction;
import com.huangcf.common.JsonUtil;
import com.huangcf.content.service.ContentService;
import com.huangcf.content.vo.ContentVO;
import com.huangcf.login.vo.UserVO;

public class ContentAction extends BaseAction {
	private ContentService content_service;
	private String jsonData;
	
	public String showContent(){
		try{
			String id = this.getRequest().getParameter("id");
			String page = this.getRequest().getParameter("page");
			List<ContentVO> list = content_service.queryContent(id, page);
			jsonData = "{\"success\":true,\"result\":'"+JsonUtil.getJsonStringFromCollection(list)+"'}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""+e.getMessage()+"\"}";
		}
		System.out.println(jsonData);
		return "success";
	}
	//д����
	public String writeContent(){
		try{
			UserVO use = (UserVO) this.getRequest().getSession(true).getAttribute("userInfo");
			String userId = use.getId();
			String id = this.getRequest().getParameter("id");
			String content = this.getRequest().getParameter("content");
			content_service.addContent(userId, id, content);
			jsonData = "{\"success\":true}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""+e.getMessage()+"\"}";
		}
		return "success";
	}
	//���ز���̬����������
	public String updateContentSum(){
		try{
			String id = this.getRequest().getParameter("id");
			String sum = content_service.queryContentSum(id);
			jsonData = "{\"success\":true,\"sum\":'"+sum+"'}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""+e.getMessage()+"\"}";
		}
		return "success";
	}
	public String queryTotalPage(){
		try{
			String id = this.getRequest().getParameter("id");
			String page = content_service.queryTotalPage(id);
			jsonData = "{\"success\":true,\"page\":'"+page+"'}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""+e.getMessage()+"\"}";
		}
		return "success";
	}
	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public ContentService getContent_service() {
		return content_service;
	}

	public void setContent_service(ContentService content_service) {
		this.content_service = content_service;
	}
}
