package com.huangcf.main.service.impl;

import com.huangcf.main.dao.MainDAO;
import com.huangcf.main.service.MainService;

public class MainServiceImpl implements MainService {
	private MainDAO main_dao;
	
	public MainDAO getMain_dao() {
		return main_dao;
	}

	public void setMain_dao(MainDAO main_dao) {
		this.main_dao = main_dao;
	}
}
