package com.huangcf.log.dao;

import java.util.List;

import com.huangcf.log.vo.LogVO;

public interface LogDAO {
	/**
	 * ��ѯ������־
	 * @param minIndex
	 * @param maxIndex
	 * @return
	 */
	public List<LogVO> queryLog(int minIndex,int maxIndex);
	/**
	 * д������־
	 * @param log
	 */
	public void addLog(String log);
}
