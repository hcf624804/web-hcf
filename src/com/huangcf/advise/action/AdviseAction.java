package com.huangcf.advise.action;

import java.util.List;

import com.huangcf.advise.service.AdviseService;
import com.huangcf.advise.vo.AdviseVO;
import com.huangcf.common.BaseAction;
import com.huangcf.common.JsonUtil;

public class AdviseAction extends BaseAction {
	private String jsonData;
	private AdviseService advise_service;
	public String queryAdvise(){
		try{
			String page = this.getRequest().getParameter("page");
			List<AdviseVO> list = advise_service.queryAdvise(page);
			jsonData = "{\"success\":true,\"result\":'"+JsonUtil.getJsonStringFromCollection(list)+"'}";
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
	public AdviseService getAdvise_service() {
		return advise_service;
	}
	public void setAdvise_service(AdviseService advise_service) {
		this.advise_service = advise_service;
	}
}
