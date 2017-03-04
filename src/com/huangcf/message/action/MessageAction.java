package com.huangcf.message.action;

import java.util.List;

import com.huangcf.common.BaseAction;
import com.huangcf.common.JsonUtil;
import com.huangcf.login.vo.UserVO;
import com.huangcf.message.service.MessageService;
import com.huangcf.message.vo.MessageVO;

public class MessageAction extends BaseAction {
	private String jsonData;
	private MessageService message_service;
	public String queryMessage(){
		try{
			String page = this.getRequest().getParameter("page");
			UserVO user = (UserVO) this.getRequest().getSession().getAttribute("userInfo");
			String role = user.getRole();
			String id = user.getId();
			List<MessageVO> list = message_service.queryMessage(page, role,id);
			jsonData = "{\"success\":true,\"result\":'"+JsonUtil.getJsonStringFromCollection(list)+"'}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""+e.getMessage()+"\"}";
		}
		System.out.println(jsonData);
		return "success";
	}
	public String writeMessage(){
		try{
			String message = this.getRequest().getParameter("message");
			String secret = this.getRequest().getParameter("secret");
			UserVO user = (UserVO) this.getRequest().getSession().getAttribute("userInfo");
			String id = user.getId();
			message_service.writeMessage(message, secret, id);
			jsonData = "{\"success\":true}";
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
	public MessageService getMessage_service() {
		return message_service;
	}
	public void setMessage_service(MessageService message_service) {
		this.message_service = message_service;
	}
}
