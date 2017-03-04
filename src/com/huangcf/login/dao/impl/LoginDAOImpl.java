package com.huangcf.login.dao.impl;

import com.huangcf.common.BaseDAO;
import com.huangcf.login.dao.LoginDAO;
import com.huangcf.login.vo.UserVO;

public class LoginDAOImpl extends BaseDAO implements LoginDAO {

	@Override
	public UserVO queryUserByUsername(String username) {
		String sql = "select login_name username,password,real_name realname,email,id,status,role from tb_user where login_name = '"+username+"'";
		System.out.println("查询用户"+sql);
		return this.queryForObject(sql, UserVO.class);
	}

	@Override
	public void addRecord(String id, String type, String ip) {
		StringBuffer sql = new StringBuffer();
		sql.append("	insert into TB_USER_RECORD	");
		sql.append("	  (id, user_id, type, ip, time)	");
		sql.append("	values	");
		sql.append("	  (to_char(sysdate, 'yyyyMMdd') ||	");
		sql.append("	   LPAD(seq_tb_user_record.nextval, 8, '0'),	");
		sql.append("	   '"+id+"',	");
		sql.append("	   '"+type+"',	");
		sql.append("	   '"+ip+"',	");
		sql.append("	   sysdate)	");
		System.out.println("记录用户操作"+sql);
		this.getJdbcTemplate().execute(sql.toString());
	}

	@Override
	public UserVO queryUserMessage(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("	select real_name realname,	role roleid,");
		sql.append("	       decode(role,	");
		sql.append("	              '00',	");
		sql.append("	              '管理员',	");
		sql.append("	              '01',	");
		sql.append("	              '会员',	");
		sql.append("	              '02',	");
		sql.append("	              '正式会员',	");
		sql.append("	              '03',	");
		sql.append("	              '游客',	");
		sql.append("	              role) role,	");
		sql.append("	       (select count(*) from TB_USER_RECORD where a.id = user_id) count	");
		sql.append("	  from tb_user a	");
		sql.append("		where id='"+id+"'");
		return queryForObject(sql.toString(), UserVO.class);
	}

	@Override
	public void enroll(String realname, String loginname, String password,
			String sex, String email) {
		StringBuffer sql = new StringBuffer();
		sql.append("	insert into tb_user	");
		sql.append("	values	");
		sql.append("	  ('"+loginname+"',	");
		sql.append("	   '"+password+"',	");
		sql.append("	   '"+realname+"',	");
		sql.append("	   '"+email+"',	");
		sql.append("	   to_char(sysdate, 'yyyyMMdd') || LPAD(seq_tb_user.nextval, 2, '0'),	");
		sql.append("	   '00',	");
		sql.append("	   '01',	");
		sql.append("		'"+sex+"')");
		this.getJdbcTemplate().execute(sql.toString());
	}

	@Override
	public String queryVisitorSum() {
		String sql = "select count(*) from TB_USER_RECORD where type='00'";
		return (String) this.getJdbcTemplate().queryForObject(sql, String.class);
	}
}
