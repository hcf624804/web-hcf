package com.huangcf.mp3.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.huangcf.mp3.dao.Mp3DAO;
import com.huangcf.mp3.service.Mp3Service;
import com.huangcf.mp3.vo.Mp3VO;

public class Mp3ServiceImpl implements Mp3Service {
	private Mp3DAO mp3_dao;
	@Override
	public Mp3VO queryMp3(String rownum, String bz) {
		String count = mp3_dao.queryCount();
		int cnt = Integer.parseInt(count);
		int r = Integer.parseInt(rownum);
		Mp3VO vo = new Mp3VO();
		if(cnt == 1){//总共就一首歌
			vo = mp3_dao.queryMp3ByRownum(r);
		}else{
			if("0".equals(bz)){//上一首
				if(r == 1){
					vo = mp3_dao.queryMp3ByRownum(cnt);
				}else{
					vo = mp3_dao.queryMp3ByRownum(r-1);
				}
			}else if("1".equals(bz)){//下一首
				if(r == cnt){
					vo = mp3_dao.queryMp3ByRownum(1);
				}else{
					vo = mp3_dao.queryMp3ByRownum(r+1);
				}
			}else{//初始化页面，放第一首
				vo = mp3_dao.queryMp3ByRownum(6);
			}
		}
		return vo;
	}
	public Mp3DAO getMp3_dao() {
		return mp3_dao;
	}
	public void setMp3_dao(Mp3DAO mp3_dao) {
		this.mp3_dao = mp3_dao;
	}
	
}
