package com.huangcf.message.service;

import java.util.List;

import com.huangcf.message.vo.MessageVO;

public interface MessageService {
	/**
	 * ��ѯ���ԣ�����ҳ
	 * @param page
	 * @param role
	 * @return
	 */
	public List<MessageVO> queryMessage(String page,String role,String id);
	/**
	 * д����
	 * @param message
	 * @param secret
	 * @param id
	 * @return
	 */
	public String writeMessage(String message,String secret,String id);
}
