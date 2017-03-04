package com.huangcf.dynamics.vo;
/**
 * 动态，用来展示的VO
 * @author Administrator
 *
 */
public class DynamicsVO {
	private String id;//动态的id
	private String time;//动态发表的时间
	private String status;//动态的状态0:正常 1:删除状态
	private String secret;//是否是私密的0：公开 1：私密
	private String flag;//标志 0：短的微博 1：长的文章
	private String title;//标题，只有长的文章才有标题
	private String content;//内容，短的微博只有内容
	private String contentSum;//评论总数
	private String likeSum;//赞总数
	private String userid;//用户ID
	private String username;//用户名
	private String sfz;//当前用户是否赞了
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
