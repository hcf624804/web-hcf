package com.huangcf.mp3.service;

import com.huangcf.mp3.vo.Mp3VO;

public interface Mp3Service {
	/**
	 * 
	 * @param rownum
	 * @param bz 0:��һ��1 ��һ��
	 * @return
	 */
	public Mp3VO queryMp3(String rownum,String bz);
}
