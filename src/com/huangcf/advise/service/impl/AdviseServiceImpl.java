package com.huangcf.advise.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.huangcf.advise.dao.AdviseDAO;
import com.huangcf.advise.service.AdviseService;
import com.huangcf.advise.vo.AdviseVO;

public class AdviseServiceImpl implements AdviseService {
	private AdviseDAO advise_dao;

	public AdviseDAO getAdvise_dao() {
		return advise_dao;
	}

	public void setAdvise_dao(AdviseDAO advise_dao) {
		this.advise_dao = advise_dao;
	}

	@Override
	public List<AdviseVO> queryAdvise(String page) {
		int pageIndex = Integer.parseInt(page);
		Properties p = new Properties();
		try {
			p.load(AdviseServiceImpl.class.getClassLoader().getResourceAsStream("properties.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("∂¡»°≈‰÷√Œƒº˛ ß∞‹~");
		}
		String size = p.getProperty("advisePageSize");
		int pageSize = Integer.parseInt(size);
		return advise_dao.queryAdvise(pageIndex, pageSize);
	}
}
