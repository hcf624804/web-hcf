package com.huangcf.dynamics.vo;
/**
 * ��̬������չʾ��VO
 * @author Administrator
 *
 */
public class DynamicsVO {
	private String id;//��̬��id
	private String time;//��̬�����ʱ��
	private String status;//��̬��״̬0:���� 1:ɾ��״̬
	private String secret;//�Ƿ���˽�ܵ�0������ 1��˽��
	private String flag;//��־ 0���̵�΢�� 1����������
	private String title;//���⣬ֻ�г������²��б���
	private String content;//���ݣ��̵�΢��ֻ������
	private String contentSum;//��������
	private String likeSum;//������
	private String userid;//�û�ID
	private String username;//�û���
	private String sfz;//��ǰ�û��Ƿ�����
	public String getSfz() {
		return sfz;
	}
	public void setSfz(String sfz) {
		this.sfz = sfz;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getContentSum() {
		return contentSum;
	}
	public void setContentSum(String contentSum) {
		this.contentSum = contentSum;
	}
	public String getLikeSum() {
		return likeSum;
	}
	public void setLikeSum(String likeSum) {
		this.likeSum = likeSum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
