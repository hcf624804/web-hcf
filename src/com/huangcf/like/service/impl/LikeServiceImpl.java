package com.huangcf.like.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.huangcf.like.dao.LikeDAO;
import com.huangcf.like.service.LikeService;

public class LikeServiceImpl implements LikeService {
	private LikeDAO like_dao;

	public LikeDAO getLike_dao() {
		return like_dao;
	}

	public void setLike_dao(LikeDAO like_dao) {
		this.like_dao = like_dao;
	}

	@Override
	public void likeDynamics(String id, String did) {
		String countL = like_dao.queryLikeOrNot(did, id);
		int i = Integer.parseInt(countL);
		if(i>0){//ȡ����
			like_dao.dontLikeDynamics(id, did);
		}else{//��
			like_dao.likeDynamics(id, did);
		}
	}

	@Override
	public List<String> updateDynamicsLike(String id, String did) {
		List<String> list = new ArrayList<String>();
		String countL = like_dao.queryLikeOrNot(did, id);
		int i = Integer.parseInt(countL);
		if(i>0){
			list.add("已赞");
		}else{
			list.add("赞");
		}
		String sum = like_dao.querySum(did);
		list.add(sum);
		return list;
	}
}
