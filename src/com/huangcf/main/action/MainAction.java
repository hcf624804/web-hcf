package com.huangcf.main.action;

import com.huangcf.common.BaseAction;
import com.huangcf.main.service.MainService;

public class MainAction extends BaseAction {
	private MainService main_service;
	public String toShowMain(){
		return "success";
	}
	public MainService getMain_service() {
		return main_service;
	}
	public void setMain_service(MainService main_service) {
		this.main_service = main_service;
	}
}
