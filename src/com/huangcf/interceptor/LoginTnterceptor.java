package com.huangcf.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoginTnterceptor implements Interceptor {

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		Map<String,Object> session=ai.getInvocationContext().getSession();
		if(session.get("userInfo")==null){
			//session中admin为空，说明没登陆，踢回登陆页面
			return "login";
			
		}else{
			//不为空，说明登陆了
			return ai.invoke();
		}
	}

}
