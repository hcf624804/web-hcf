package com.huangcf.log.action;

import java.util.List;

import com.huangcf.common.BaseAction;
import com.huangcf.common.JsonUtil;
import com.huangcf.log.service.LogService;
import com.huangcf.log.vo.LogVO;

public class LogAction extends BaseAction {
	private LogService log_service;
	private String jsonData;
	/**
	 * 查询更新日志
	 * @return
	 */
	public String showLog(){
		try{
			String page = this.getRequest().getParameter("page");
			List<LogVO> list = log_service.queryLog(page);
			jsonData = "{\"success\":true,\"result\":'"+JsonUtil.getJsonStringFromCollection(list)+"'}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""+e.getMessage()+"\"}";
		}
		System.out.println(jsonData);
		return "success";
	}
	/**
	 * 写更新日志
	 * @return
	 */
	public String writeLog(){
		try{
			String log = this.getRequest().getParameter("log");
			log_service.addLog(log);
			jsonData = "{\"success\":true}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""+e.getMessage()+"\"}";
		}
		return "success";
	}
	public LogService getLog_service() {
		return log_service;
	}

	public void setLog_service(LogService log_service) {
		this.log_service = log_service;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
}
