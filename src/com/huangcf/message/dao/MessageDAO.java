package com.huangcf.message.dao;

import java.util.List;

import com.huangcf.message.vo.MessageVO;

public interface MessageDAO {
	/**
	 * ²éÑ¯ÁôÑÔ£¬´ø·ÖÒ³
	 * @param minIndex
	 * @param maxIndex
	 * @param flag
	 * @return
	 */
	public List<MessageVO> queryMessage(int minIndex,int maxIndex,String flag,String id);
	/**
	 * Ğ´ÁôÑÔ
	 * @param message
	 * @param secret
	 * @param id
	 */
	public void writeMessage(String message,String secret,String id);
}
