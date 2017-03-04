package com.huangcf.log.service;

import java.util.List;

import com.huangcf.log.vo.LogVO;

public interface LogService {
	/**
	 * 查询更新日志，带分页
	 * @param page
	 * @return
	 */
	public List<LogVO> queryLog(String page);
	/**
	 * 写日志
	 * @param log
	 */
	public void addLog(String log);
}
