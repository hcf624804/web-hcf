package com.huangcf.mp3.dao;

import com.huangcf.mp3.vo.Mp3VO;

public interface Mp3DAO {
	/**
	 * ��ѯMP3
	 * @param rownum
	 * @return
	 */
	public Mp3VO queryMp3ByRownum(int rownum);
	/**
	 * ��ѯ�ܹ��ж��ٸ�
	 * @return
	 */
	public String queryCount();
}
