package com.huangcf.message.service;

import java.util.List;

import com.huangcf.message.vo.MessageVO;

public interface MessageService {
	/**
	 * ²éÑ¯ÁôÑÔ£¬´ø·ÖÒ³
	 * @param page
	 * @param role
	 * @return
	 */
	public List<MessageVO> queryMessage(String page,String role,String id);
	/**
	 * Ğ´ÁôÑÔ
	 * @param message
	 * @param secret
	 * @param id
	 * @return
	 */
	public String writeMessage(String message,String secret,String id);
}
