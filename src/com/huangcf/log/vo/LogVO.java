package com.huangcf.log.vo;
/**
 * 更新日志VO
 * @author Administrator
 *
 */
public class LogVO {
	private String sid;//序号
	private String content;//内容
	private String time;//时间
	private String status;//状态 0：正常 1：删除
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
}
