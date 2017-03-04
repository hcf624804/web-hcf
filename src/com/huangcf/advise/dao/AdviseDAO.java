package com.huangcf.advise.dao;

import java.util.List;

import com.huangcf.advise.vo.AdviseVO;

public interface AdviseDAO {
	/**
	 * 查询建议,带分页
	 * @param minIndex
	 * @param maxIndex
	 * @return
	 */
	public List<AdviseVO> queryAdvise(int minIndex,int maxIndex);
}
