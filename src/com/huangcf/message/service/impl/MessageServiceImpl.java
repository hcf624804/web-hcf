package com.huangcf.message.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.huangcf.advise.service.impl.AdviseServiceImpl;
import com.huangcf.message.dao.MessageDAO;
import com.huangcf.message.service.MessageService;
import com.huangcf.message.vo.MessageVO;

public class MessageServiceImpl implements MessageService {
	private MessageDAO message_dao;
	public MessageDAO getMessage_dao() {
		return message_dao;
	}

	public void setMessage_dao(MessageDAO message_dao) {
		this.message_dao = message_dao;
	}

	@Override
	public List<MessageVO> queryMessage(String page,String role,String id) {
		int pageIndex = Integer.parseInt(page);
		Properties p = new Properties();
		try {
			p.load(MessageServiceImpl.class.getClassLoader().getResourceAsStream("properties.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("∂¡»°≈‰÷√Œƒº˛ ß∞‹~");
		}
		String size = p.getProperty("messagePageSize");
		int pageSize = Integer.parseInt(size);
		int minIndex = (pageIndex-1)*pageSize+1;
		int maxIndex = minIndex+pageSize-1;
		String flag = "";
		if("00".equals(role)){
			flag = "'0','2'";
		}else{
			flag = "'0'";
		}
		return message_dao.queryMessage(minIndex, maxIndex, flag,id);
	}

	@Override
	public String writeMessage(String message, String secret, String id) {
		message_dao.writeMessage(message, secret, id);
		return null;
	}
	
}
