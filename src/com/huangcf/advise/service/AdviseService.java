package com.huangcf.advise.service;

import java.util.List;

import com.huangcf.advise.vo.AdviseVO;

public interface AdviseService {
	/**
	 * ��ѯ����,����ҳ
	 * @param page
	 * @return
	 */
	public List<AdviseVO> queryAdvise(String page);
}
