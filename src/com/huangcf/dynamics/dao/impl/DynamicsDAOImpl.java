package com.huangcf.dynamics.dao.impl;

import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.DefaultLobHandler;

import com.huangcf.common.DBUtil;
import com.huangcf.common.BaseDAO;
import com.huangcf.common.StringUtil;
import com.huangcf.dynamics.dao.DynamicsDAO;
import com.huangcf.dynamics.vo.DynamicsVO;

public class DynamicsDAOImpl extends BaseDAO implements DynamicsDAO {
	private DefaultLobHandler lobHandler = new DefaultLobHandler();
	

	public DefaultLobHandler getLobHandler() {
		return lobHandler;
	}

	public void setLobHandler(DefaultLobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	public List<DynamicsVO> queryDynamics(String id, String minTime,
			String maxTime, String secret, int minIndex, int maxIndex,String status,String flag) {
		StringBuffer sql = new StringBuffer();
		sql.append("	select *	");
		sql.append("	  from (select id,to_char(time,'yyyy-mm-dd hh24:mi:ss') time,status,secret,flag,title,decode(flag,'0',content,'1','') content,user_id userid, rownum n	");
		sql.append("	          from (select * from tb_dynamics order by time desc) a	");
		sql.append("	         where 1 = 1	");
		sql.append("				and status = '0'");
		if(!StringUtil.isEmptyStr(id)){
			sql.append("	           and id = '"+id+"'	");
		}
		if(!StringUtil.isEmptyStr(minTime)){
			sql.append("	           and time >= to_date('"+minTime+"', 'yyyy-MM-dd')	");
		}
		if(!StringUtil.isEmptyStr(maxTime)){
			sql.append("	           and time < to_date('"+maxTime+"', 'yyyy-MM-dd') + 1	");
		}
		if(!StringUtil.isEmptyStr(secret)){
			sql.append("	           and secret in ("+secret+")	");
		}
		sql.append("	           and rownum <= "+maxIndex+"	) t	");
		sql.append("	 where t.n >= "+minIndex+"		");
		System.out.println("��ѯ��̬��"+sql);
		return queryForList(sql.toString(), DynamicsVO.class);
	}

	@Override
	public void addDynamics(String userId, String dynamics) {
		StringBuffer sql = new StringBuffer();
		sql.append("	insert into tb_dynamics	");
		sql.append("	values	");
		sql.append("	  (to_char(sysdate, 'yyyyMMdd') || LPAD(seq_tb_dynamics.nextval, 8, '0'),	");
		sql.append("	   sysdate,	");
		sql.append("	   '0',	");
		sql.append("	   '0',	");
		sql.append("	   '0',	");
		sql.append("	   ' ',	");
		sql.append("	   '"+dynamics+"',	");
		sql.append("	   '"+userId+"')	");
		this.getJdbcTemplate().execute(sql.toString());
	}

	@Override
	public String queryTotalDynamics(String secret) {
		String sql = "select count(*) from tb_dynamics where status = '0' and secret in ("+secret+")";
		return (String) this.getJdbcTemplate().queryForObject(sql.toString(), String.class);
	}

	@Override
	public void addArticle(String userid, String title, String content) {
		Connection conn = null;
		try{
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("select seq_tb_dynamics.nextval from dual");
			ResultSet rs = ps.executeQuery();
			String id = "0";
			while(rs.next()){
				id = rs.getString(1);
			}
			while(id.length() < 8){
				id = "0" + id;
			}
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			id = sdf.format(d) + id;
			ps = conn.prepareStatement("insert into tb_dynamics (id,time,status,secret,flag,title,content,user_id) values('"+id+"',sysdate,'0','0','1','"+title+"',empty_clob(),'"+userid+"' )");
			ps.executeUpdate();
			ps= conn.prepareStatement("select content from tb_dynamics where ID=? for update");
		    ps.setString(1, id);
		    rs = ps.executeQuery();
		    Clob clob = null;
		    if(rs.next()) {
	            clob = rs.getClob(1);
	        }
		    clob.setString(1, content);
		    rs.close();
	        conn.commit();
	        ps.close();
		}catch(Exception e){
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public String queryArticleContent(String id) {
		Connection conn = null;
		try{
			PreparedStatement ps = conn.prepareStatement("select content from tb_dynamics where id = ? ");
			ps.setString(1, id);	        
	        ResultSet rs = ps.executeQuery();
	        if(rs.next()) {
	            Clob clob = rs.getClob("content");
	            Reader rd = clob.getCharacterStream();
	            char [] str = new char[12];
	            while(rd.read(str) != -1) {
	                return new String(str);
	            }
	        }
		}catch(Exception e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public DynamicsVO queryArticle(String id,String status) {
		StringBuffer sql = new StringBuffer();
		sql.append("	select content,id,to_char(time,'yyyy-mm-dd hh24:mi:ss') time,status,secret,flag,title,content,user_id userid ");
		sql.append("	          from tb_dynamics a	");
		sql.append("		where 1=1");
		if(!StringUtil.isEmptyStr(id)){
			sql.append("	and id = '"+id+"' ");
		}
		if(!StringUtil.isEmptyStr(status)){
			sql.append("	and status = '"+status+"'");
		}
		System.out.println(sql);
		return queryForObject(sql.toString(), DynamicsVO.class);
	}
}
