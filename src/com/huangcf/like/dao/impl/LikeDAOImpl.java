package com.huangcf.like.dao.impl;

import com.huangcf.common.BaseDAO;
import com.huangcf.like.dao.LikeDAO;

public class LikeDAOImpl extends BaseDAO implements LikeDAO {
	public String querySum(String dynamicsId) {
		String sql = "select count(*) from tb_dynamics_like where dynamics_id = '"+dynamicsId+"' and flag = '0'";
		return (String) this.getJdbcTemplate().queryForObject(sql.toString(), String.class);
	}

	@Override
	public String queryLikeOrNot(String dynamicsId, String userid) {
		String sql = "select count(*) from tb_dynamics_like where dynamics_id = '"+dynamicsId+"' and flag = '0' and user_id = '"+userid+"'";
		System.out.println(sql);
		return (String) this.getJdbcTemplate().queryForObject(sql.toString(), String.class);
	}

	@Override
	public void likeDynamics(String id, String did) {
		StringBuffer sql = new StringBuffer();
		sql.append("	insert into tb_dynamics_like values(to_char(sysdate, 'yyyyMMdd') ||	");
		sql.append("	   LPAD(seq_tb_dynamics_like.nextval, 8, '0'),'"+did+"','"+id+"',sysdate,'0')	");
		this.getJdbcTemplate().execute(sql.toString());
	}

	@Override
	public void dontLikeDynamics(String id, String did) {
		String sql = "update tb_dynamics_like set flag = '1' where flag = '0' and dynamics_id= '"+did+"' and user_id = '"+id+"'";
		System.out.println(sql);
		this.getJdbcTemplate().execute(sql);
	}
}
