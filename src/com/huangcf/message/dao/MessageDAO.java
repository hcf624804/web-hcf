package com.huangcf.message.dao;

import java.util.List;

import com.huangcf.message.vo.MessageVO;

public interface MessageDAO {
	/**
	 * ��ѯ���ԣ�����ҳ
	 * @param minIndex
	 * @param maxIndex
	 * @param flag
	 * @return
	 */
	public List<MessageVO> queryMessage(int minIndex,int maxIndex,String flag,String id);
	/**
	 * д����
	 * @param message
	 * @param secret
	 * @param id
	 */
	public void writeMessage(String message,String secret,String id);
}
