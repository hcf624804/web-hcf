package com.huangcf.mp3.action;

import com.huangcf.common.BaseAction;
import com.huangcf.common.JsonUtil;
import com.huangcf.mp3.service.Mp3Service;
import com.huangcf.mp3.vo.Mp3VO;

public class Mp3Action extends BaseAction {
	private Mp3Service mp3_service;
	private String jsonData;
	
	public String doQueryMp3(){
		try{
			String rownum = this.getRequest().getParameter("rownum");
			String bz = this.getRequest().getParameter("bz");
			Mp3VO vo = mp3_service.queryMp3(rownum, bz);
			jsonData = "{\"success\":true,\"result\":'"+JsonUtil.getJsonStringFromJavaPOJO(vo)+"'}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""+e.getMessage()+"\"}";
		}
		System.out.println(jsonData);
		return "success";
	}
	public Mp3Service getMp3_service() {
		return mp3_service;
	}

	public void setMp3_service(Mp3Service mp3_service) {
		this.mp3_service = mp3_service;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
}
