package com.huangcf.log.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.huangcf.advise.service.impl.AdviseServiceImpl;
import com.huangcf.log.dao.LogDAO;
import com.huangcf.log.service.LogService;
import com.huangcf.log.vo.LogVO;

public class LogServiceImpl implements LogService {
	private LogDAO log_dao;

	public LogDAO getLog_dao() {
		return log_dao;
	}

	public void setLog_dao(LogDAO log_dao) {
		this.log_dao = log_dao;
	}

	@Override
	public List<LogVO> queryLog(String page) {
		int pageIndex = Integer.parseInt(page);
		Properties p = new Properties();
		try {
			p.load(LogServiceImpl.class.getClassLoader().getResourceAsStream("properties.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("∂¡»°≈‰÷√Œƒº˛ ß∞‹~");
		}
		String size = p.getProperty("logPageSize");
		int pageSize = Integer.parseInt(size);
		int minIndex = (pageIndex-1)*pageSize+1;
		int maxIndex = minIndex+pageSize-1;
		return log_dao.queryLog(minIndex, maxIndex);
	}

	@Override
	public void addLog(String log) {
		log_dao.addLog(log);
	}
}
