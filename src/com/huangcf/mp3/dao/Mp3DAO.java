package com.huangcf.mp3.dao;

import com.huangcf.mp3.vo.Mp3VO;

public interface Mp3DAO {
	/**
	 * 查询MP3
	 * @param rownum
	 * @return
	 */
	public Mp3VO queryMp3ByRownum(int rownum);
	/**
	 * 查询总共有多少歌
	 * @return
	 */
	public String queryCount();
}
