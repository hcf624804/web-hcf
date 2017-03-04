package com.huangcf.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class CookieUtil {
	/**
	 * 增加cookie
	 * @param name
	 * @param value
	 * @param timeLong
	 */
	public static void addCookie(String name,String value, int timeLong){
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(timeLong);
        ServletActionContext.getResponse().addCookie(cookie);
    }
	/**
	 * 获得cookie
	 * @param name
	 * @return
	 */
	public static String getCookie(String name){
        HttpServletRequest request = ServletActionContext.getRequest();
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies)
            {
                if(cookie.getName().equals(name))
                {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
