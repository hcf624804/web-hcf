package com.huangcf.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{
	public static Logger logger = 
			Logger.getLogger(BaseAction.class);
	public HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	public HttpServletResponse getReponse(){
		return ServletActionContext.getResponse();
	}
	//·µ»Øjson
	public void returnJson(String json) {
        HttpServletResponse response = ServletActionContext.getResponse(); 
        response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");  
	    PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    writer.print(json);
	}
}
