package com.huangcf.mp3.dao.impl;

import com.huangcf.common.BaseDAO;
import com.huangcf.mp3.dao.Mp3DAO;
import com.huangcf.mp3.vo.Mp3VO;

public class Mp3DAOImpl extends BaseDAO implements Mp3DAO {

	@Override
	public Mp3VO queryMp3ByRownum(int rownum) {
		StringBuffer sql = new StringBuffer();
		sql.append("	select *	");
		sql.append("	  from (select a.*, rownum n from tb_mp3 a where rownum <= "+rownum+") t	");
		sql.append("	 where t.n >= "+rownum+"	");
		return this.queryForObject(sql.toString(), Mp3VO.class);
	}

	@Override
	public String queryCount() {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from tb_mp3");
		return (String) this.getJdbcTemplate().queryForObject(sql.toString(), String.class);
	}

}
