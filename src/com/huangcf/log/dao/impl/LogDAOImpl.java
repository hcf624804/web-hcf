package com.huangcf.log.dao.impl;

import java.util.List;

import com.huangcf.common.BaseDAO;
import com.huangcf.log.dao.LogDAO;
import com.huangcf.log.vo.LogVO;

public class LogDAOImpl extends BaseDAO implements LogDAO {

	@Override
	public List<LogVO> queryLog(int minIndex, int maxIndex) {
		StringBuffer sql = new StringBuffer();
		sql.append("	select t.n sid, to_char(t.time,'yyyy-MM-dd') time, t.content	");
		sql.append("	  from (select a.*, rownum n	");
		sql.append("	          from (select * from tb_log order by time desc) a	");
		sql.append("	         where rownum <= "+maxIndex+"	");
		sql.append("	           and status = '0'	) t	");
		sql.append("	 where t.n >= "+minIndex+"	");
		System.out.println("查询更新日志："+sql);
		return queryForList(sql.toString(), LogVO.class);
	}

	@Override
	public void addLog(String log) {
		StringBuffer sql = new StringBuffer();
		sql.append("	insert into tb_log	");
		sql.append("	values	");
		sql.append("	  (to_char(sysdate, 'yyyyMMdd') || LPAD(seq_tb_log.nextval, 4, '0'),	");
		sql.append("	   sysdate,	");
		sql.append("	   '"+log+"',	");
		sql.append("	   '0')	");
		System.out.println("新增日志："+sql);
		this.getJdbcTemplate().execute(sql.toString());
	}

}
