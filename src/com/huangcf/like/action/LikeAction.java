package com.huangcf.like.action;

import java.util.List;

import com.huangcf.common.BaseAction;
import com.huangcf.like.service.LikeService;

public class LikeAction extends BaseAction {
	private LikeService like_service;
	private String jsonData;
	//赞或者取消赞
	public String likeDynamics(){
		try{
			String id = this.getRequest().getParameter("id");
			String did = this.getRequest().getParameter("did");
			like_service.likeDynamics(id, did);
			jsonData = "{\"success\":true}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""+e.getMessage()+"\"}";
		}
		return "success";
	}
	//更新动态的赞
	public String updateDynamicsLike(){
		try{
			String id = this.getRequest().getParameter("id");
			String did = this.getRequest().getParameter("did");
			List<String> list = like_service.updateDynamicsLike(id, did);
			jsonData = "{\"success\":true,\"sum\":\""+list.get(1)+"\",\"sfz\":\""+list.get(0)+"\"}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""+e.getMessage()+"\"}";
		}
		return "success";
	}
	public LikeService getLike_service() {
		return like_service;
	}

	public void setLike_service(LikeService like_service) {
		this.like_service = like_service;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
}
