package com.huangcf.content.dao.impl;

import java.util.List;

import com.huangcf.common.BaseDAO;
import com.huangcf.content.dao.ContentDAO;
import com.huangcf.content.vo.ContentVO;

public class ContentDAOImpl extends BaseDAO implements ContentDAO {
	public String querySum(String dynamicsId) {
		String sql = "select count(*) from tb_dynamics_content where dynamics_id = '"+dynamicsId+"' and flag = '0'";
		return (String) this.getJdbcTemplate().queryForObject(sql, String.class);
	}

	public List<ContentVO> queryContent(String id, int minIndex, int maxIndex) {
		StringBuffer sql = new StringBuffer();
		sql.append("	select t.content,	");
		sql.append("	       t.n,	");
		sql.append("	       to_char(t.time, 'yyyy-mm-dd hh24:mi:ss') time,	");
		sql.append("	       (select sex from tb_user a where a.id = t.user_id) sex	,");
		sql.append("	       (select real_name from tb_user a where a.id = t.user_id) username	");
		sql.append("	  from (select a.*, rownum n	");
		sql.append("	          from (select * from tb_dynamics_content order by time desc) a	");
		sql.append("	         where dynamics_id = '"+id+"'	");
		sql.append("	           and flag = '0'	");
		sql.append("	           and rownum <= "+maxIndex+") t	");
		sql.append("	 where t.n >= "+minIndex+"	");
		System.out.println("查询动态评论:"+sql);
		return queryForList(sql.toString(), ContentVO.class);
	}

	@Override
	public void addContent(String userId, String id, String content) {
		StringBuffer sql = new StringBuffer();
		sql.append("	insert into tb_dynamics_content	");
		sql.append("	values	");
		sql.append("	  (to_char(sysdate, 'yyyyMMdd') ||	");
		sql.append("	   LPAD(seq_tb_dynamics_content.nextval, 8, '0'),	");
		sql.append("	   '"+id+"',	");
		sql.append("	   '"+userId+"',	");
		sql.append("	   sysdate,	");
		sql.append("	   '"+content+"','0')	");
		this.getJdbcTemplate().execute(sql.toString());
		
	}

	@Override
	public String getTotalContent(String id) {
		String sql = "select count(*) from tb_dynamics_content where id = '"+id+"' and flag = '0'";
		return (String) this.getJdbcTemplate().queryForObject(sql.toString(), String.class);
	}
}
