package com.huangcf.log.dao;

import java.util.List;

import com.huangcf.log.vo.LogVO;

public interface LogDAO {
	/**
	 * 查询更新日志
	 * @param minIndex
	 * @param maxIndex
	 * @return
	 */
	public List<LogVO> queryLog(int minIndex,int maxIndex);
	/**
	 * 写更新日志
	 * @param log
	 */
	public void addLog(String log);
}
