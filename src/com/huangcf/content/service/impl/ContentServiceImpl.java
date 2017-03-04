package com.huangcf.content.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.huangcf.content.dao.ContentDAO;
import com.huangcf.content.service.ContentService;
import com.huangcf.content.vo.ContentVO;

public class ContentServiceImpl implements ContentService {
	private ContentDAO content_dao;

	public ContentDAO getContent_dao() {
		return content_dao;
	}

	public void setContent_dao(ContentDAO content_dao) {
		this.content_dao = content_dao;
	}

	@Override
	public List<ContentVO> queryContent(String id,String page) {
		int pageIndex = Integer.parseInt(page);
		Properties p = new Properties();
		try {
			p.load(ContentServiceImpl.class.getClassLoader().getResourceAsStream("properties.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("获取评论分页失败~");
		}
		String size = p.getProperty("contentPageSize");
		int pageSize = Integer.parseInt(size);
		int minIndex = (pageIndex-1)*pageSize+1;
		int maxIndex = minIndex+pageSize-1;
		List<ContentVO> list = content_dao.queryContent(id, minIndex, maxIndex);
		int totalPage = 0;
		if(list.size() > 0){
			String totalContent = content_dao.getTotalContent(id);
			int contentSum = Integer.parseInt(totalContent);
			if(contentSum%pageSize == 0){
				totalPage = contentSum/pageSize;
			}else{
				totalPage = contentSum%pageSize + 1;
			}
			list.get(0).setTotalPage(totalPage);
		}
		return list;
	}

	@Override
	public void addContent(String userId, String id, String content) {
		content_dao.addContent(userId, id, content);
	}

	@Override
	public String queryContentSum(String id) {
		return content_dao.querySum(id);
	}

	@Override
	public String queryTotalPage(String id) {
		Properties p = new Properties();
		try {
			p.load(ContentServiceImpl.class.getClassLoader().getResourceAsStream("properties.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("��ȡ�����ļ�ʧ��~");
		}
		String size = p.getProperty("contentPageSize");
		int pageSize = Integer.parseInt(size);
		String sum = content_dao.querySum(id);
		int contentSum = Integer.parseInt(sum);
		int page = 0;
		if(contentSum%pageSize == 0){
			page = contentSum/pageSize;
		}else{
			page = contentSum/pageSize + 1;
		}
		return String.valueOf(page);
	}
}
