package com.huangcf.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.huangcf.login.vo.UserVO;

public class LoginCheckFilter implements Filter {

	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;  
        HttpServletResponse res = (HttpServletResponse) response;  
        HttpSession session = req.getSession(true);  
  
        // 从session里取的用户名信息  
        UserVO user = (UserVO) session.getAttribute("userInfo");  
        String   uri   =   ((HttpServletRequest)   request).getRequestURI();   
        // 判断如果没有取到用户信息,就跳转到登陆页面  
        if (user != null || uri.endsWith("toLogin") ) {               
            chain.doFilter(request, response);  
        } else {  
            // 跳转到登陆页面  
            //RequestDispatcher dispatcher = request.getRequestDispatcher("login/toLogin");    
            //dispatcher.forward(request, response);
        	System.out.println(req.getContextPath()+"/login/toLogin");
        	res.sendRedirect(req.getContextPath()+"/login/toLogin");
        }
	}
	public void init(FilterConfig arg0) throws ServletException {
	}

}
