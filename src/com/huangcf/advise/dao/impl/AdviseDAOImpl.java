package com.huangcf.advise.dao.impl;

import java.util.List;

import com.huangcf.advise.dao.AdviseDAO;
import com.huangcf.advise.vo.AdviseVO;
import com.huangcf.common.BaseDAO;

public class AdviseDAOImpl extends BaseDAO implements AdviseDAO {

	public List<AdviseVO> queryAdvise(int page, int pageSize) {
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT content,	");
		sql.append("	       date_format(time, '%m.%d.%Y %h:%i:%s') time,	");
		sql.append("	       (SELECT realname FROM tb_user WHERE id = t.user_id) name	");
		sql.append("	  FROM TB_ADVISE t	");
		sql.append("	 WHERE flag = '0'	");
		sql.append("	 ORDER BY time DESC LIMIT "+page+", "+pageSize+"	");
		System.out.println("≤È—ØΩ®“È£∫"+sql);
		return queryForList(sql.toString(), AdviseVO.class);
	}
	public void addAdvise(String user_id,String content){
		StringBuffer sql = new StringBuffer();
		sql.append("	insert into TB_ADVISE	");
		sql.append("	  (user_id, content, flag, time)	");
		sql.append("	values	");
		sql.append("	  ("+user_id+", '"+content+"', '0', now())	");
		this.getJdbcTemplate().execute(sql.toString());
	}
}
