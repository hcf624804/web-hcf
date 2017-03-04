package com.huangcf.content.vo;
/**
 * ����
 * @author Administrator
 *
 */
public class ContentVO {
	private String id;
	private String dynamicsId;
	private String userId;
	private String time;
	private String content;
	private String flag;
	private String username;//�������۵���
	private int totalPage;//������ҳ��
	private String sex;//x性别
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDynamicsId() {
		return dynamicsId;
	}
	public void setDynamicsId(String dynamicsId) {
		this.dynamicsId = dynamicsId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
