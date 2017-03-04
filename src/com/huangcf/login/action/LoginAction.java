package com.huangcf.login.action;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import com.huangcf.common.BaseAction;
import com.huangcf.common.JsonUtil;
import com.huangcf.common.StringUtil;
import com.huangcf.login.service.LoginService;
import com.huangcf.login.vo.UserVO;

public class LoginAction extends BaseAction {
	private UserVO use;
	private LoginService login_service;
	private String jsonData;
	/**
	 * 登录
	 * @return
	 */
	public String doLogin(){
		try{
			String ip = this.getIpAddr();
			UserVO vo = login_service.doLogin(use.getUsername(), use.getPassword(),ip);
			if(!StringUtil.isEmptyStr(vo.getMsg())){//返回的user中msg为空，说明登录成功，否则登录失败
				jsonData = "{\"success\":false,\"result\":\""+vo.getMsg()+"\"}";
			}else{
				//登录成功把用户信息放到session中
				HttpSession session = this.getRequest().getSession();//获取会话
				session.setAttribute("userInfo", vo);
				if("remember".equals(use.getRemember())){//勾选了记住密码则设置cookie，否则不记住
					//设置cookie
					Cookie cookieU = new Cookie("username",vo.getUsername());
					Cookie cookieP = new Cookie("password",vo.getPassword());
					//设置cookie存活时间，一个月,如果不设置则会在浏览器关闭后cookie也随之消失
					cookieU.setMaxAge(30*24*60*60);
					cookieP.setMaxAge(30*24*60*60);
					//设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
					cookieU.setPath("/");
					cookieP.setPath("/");
					getReponse().addCookie(cookieU);
					getReponse().addCookie(cookieP);
					jsonData = "{\"success\":true}";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""
					+ e.getMessage().toString().replaceAll("\"", "\'").replaceAll("\n", "") + "\"}";
//			logger.error(e);
//			return "error";
		}
		return "success";
	}
	//展示用户的信息
	public String showUser(){
		try{
			UserVO user = login_service.queryUserMessage(use);
			jsonData = "{\"success\":true,\"result\":\'"+JsonUtil.getJsonStringFromJavaPOJO(user)+"'}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""
					+ e.getMessage().toString().replaceAll("\"", "\'").replaceAll("\n", "") + "\"}";
		}
		System.out.println(jsonData);
		return "success";
	}
	public String toEnroll(){
		return "success";
	}
	public String toLogin(){
		return "success";
	}
	//获取用户的IP地址
	private String getIpAddr() {   
	     String ipAddress = null;   
	     //ipAddress = this.getRequest().getRemoteAddr();   
	     ipAddress = this.getRequest().getHeader("x-forwarded-for");   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	      ipAddress = this.getRequest().getHeader("Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	         ipAddress = this.getRequest().getHeader("WL-Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	      ipAddress = this.getRequest().getRemoteAddr();   
	      if(ipAddress.equals("127.0.0.1")){   
	       //根据网卡取本机配置的IP   
	       InetAddress inet=null;   
	    try {   
	     inet = InetAddress.getLocalHost();   
	    } catch (UnknownHostException e) {   
	    	e.printStackTrace();   
	    }   
	    ipAddress= inet.getHostAddress();   
	      }   
	            
	     }   
	  
	     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   
	     if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15   
	         if(ipAddress.indexOf(",")>0){   
	             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));   
	         }   
	     }   
	     return ipAddress;    
	  }
	//登出
	public String doLogout(){
		try{
			HttpSession session = this.getRequest().getSession();
			UserVO vo = (UserVO) session.getAttribute("userInfo");		
			login_service.logout(vo.getId(), vo.getIp());
			session.removeAttribute("userInfo");
			jsonData = "{\"success\":true}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false}";
		}
		return "success";
	}
	
	public String doEnroll(){
		try{
			String realname = this.getRequest().getParameter("realname");
			String loginname = this.getRequest().getParameter("loginname");
			String password = this.getRequest().getParameter("password");
			String email = this.getRequest().getParameter("email");
			String sex = this.getRequest().getParameter("sex");
			login_service.enroll(realname, loginname, password, sex, email);
			jsonData = "{\"success\":true}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""
					+ e.getMessage().toString().replaceAll("\"", "\'").replaceAll("\n", "") + "\"}";
		}
		return "success";
	}
	//判断登录名是否已存在
	public String checkLoginname(){
		jsonData = "[]";
		try{
			String loginname = this.getRequest().getParameter("loginname");
			boolean flag = login_service.checkLoginname(loginname);
			if(flag){
				jsonData = "{\"success\":true}";
			}else{
				jsonData = "{\"success\":false,\"result\":\""+"登录名已存在"+"\"}";
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""
					+ e.getMessage().toString().replaceAll("\"", "\'").replaceAll("\n", "") + "\"}";
		}
		return "success";
	}
	//游客身份访问
	public String visitorLogin(){
		try{
			String ip = this.getIpAddr();
			UserVO vo = new UserVO();
			vo.setIp(ip);
			vo.setId("1");
			vo.setPassword("visitor");
			vo.setRealname("游客");
			vo.setSex("1");
			vo.setRole("03");
			vo.setUsername("visitor");
			login_service.doLogin(vo.getUsername(), vo.getPassword(), ip);
			//登录成功把用户信息放到session中
			HttpSession session = this.getRequest().getSession();//获取会话
			session.setAttribute("userInfo", vo);
			jsonData = "{\"success\":true}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""
					+ e.getMessage().toString().replaceAll("\"", "\'").replaceAll("\n", "") + "\"}";
		}
		return "success";
	}
	//查询访问总人数
	public String queryVisitorSum(){
		try{
			String count = login_service.queryVisitorSum();
			jsonData = "{\"success\":true,\"result\":\""+count+"\"}";
		}catch(Exception e){
			e.printStackTrace();
			jsonData = "{\"success\":false,\"result\":\""
					+ e.getMessage().toString().replaceAll("\"", "\'").replaceAll("\n", "") + "\"}";
		}
		return "success";
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public UserVO getUse() {
		return use;
	}
	public void setUse(UserVO use) {
		this.use = use;
	}
	/**
	 * @return the login_service
	 */
	public LoginService getLogin_service() {
		return login_service;
	}
	/**
	 * @param login_service the login_service to set
	 */
	public void setLogin_service(LoginService login_service) {
		this.login_service = login_service;
	}
}
