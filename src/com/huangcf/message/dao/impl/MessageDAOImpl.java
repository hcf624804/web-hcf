package com.huangcf.message.dao.impl;

import java.util.List;

import com.huangcf.common.BaseDAO;
import com.huangcf.message.dao.MessageDAO;
import com.huangcf.message.vo.MessageVO;

public class MessageDAOImpl extends BaseDAO implements MessageDAO {

	@Override
	public List<MessageVO> queryMessage(int minIndex, int maxIndex, String flag,String id) {
		StringBuffer sql = new StringBuffer();
//		sql.append("	select  to_char(t.time,'yyyy-mm-dd hh24:mi:ss') time, t.content,(select real_name from tb_user where id = t.user_id) name	");
//		sql.append("	  from (select a.*, rownum n	");
//		sql.append("	          from (select * from tb_message order by time desc) a	");
//		sql.append("	         where rownum <= "+maxIndex+"	");
//		sql.append("	           and flag in ("+flag+")	) t	");
//		sql.append("	 where t.n >= "+minIndex+"	");
		sql.append("	select b.*	");
		sql.append("	  from (select t.*, rownum n	");
		sql.append("	          from (select to_char(a.time, 'yyyy-mm-dd hh24:mi:ss') time,	");
		sql.append("	                       a.content,	");
		sql.append("	                       (select sex from tb_user where id = a.user_id) sex,	");
		sql.append("	                       (select real_name from tb_user where id = a.user_id) name	");
		sql.append("	                  from (select * from tb_message order by time desc) a	");
		sql.append("	                 where a.flag = '2'	");
		sql.append("	                   and a.user_id = '"+id+"'	");
		sql.append("	                union all	");
		sql.append("	                select to_char(a.time, 'yyyy-mm-dd hh24:mi:ss') time,	");
		sql.append("	                       a.content,	");
		sql.append("	                       (select sex from tb_user where id = a.user_id) sex,	");
		sql.append("	                       (select real_name from tb_user where id = a.user_id) name	");
		sql.append("	                  from (select * from tb_message order by time desc) a	");
		sql.append("	                 where flag in ("+flag+")	");
		sql.append("	                 ) t	");
		sql.append("	         where rownum <= "+maxIndex+") b	");
		sql.append("	 where b.n >= "+minIndex+" 	");
		System.out.println("查询留言"+sql);
		return queryForList(sql.toString(), MessageVO.class);
	}

	@Override
	public void writeMessage(String message, String secret, String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("	insert into tb_message	");
		sql.append("	values	");
		sql.append("	  (to_char(sysdate, 'yyyyMMdd') || LPAD(seq_tb_message.nextval, 8, '0'),	");
		sql.append("	   '"+id+"',	");
		sql.append("	   '"+message+"',	");
		sql.append("	   sysdate,	");
		sql.append("	   '"+secret+"')	");
		this.getJdbcTemplate().execute(sql.toString());
	}
	
}
