package com.huangcf.advise.service;

import java.util.List;

import com.huangcf.advise.vo.AdviseVO;

public interface AdviseService {
	/**
	 * 查询建议,带分页
	 * @param page
	 * @return
	 */
	public List<AdviseVO> queryAdvise(String page);
}
