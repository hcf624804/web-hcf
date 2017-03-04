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
			//session��adminΪ�գ�˵��û��½���߻ص�½ҳ��
			return "login";
			
		}else{
			//��Ϊ�գ�˵����½��
			return ai.invoke();
		}
	}

}
