package com.huangcf.log.service;

import java.util.List;

import com.huangcf.log.vo.LogVO;

public interface LogService {
	/**
	 * ��ѯ������־������ҳ
	 * @param page
	 * @return
	 */
	public List<LogVO> queryLog(String page);
	/**
	 * д��־
	 * @param log
	 */
	public void addLog(String log);
}
