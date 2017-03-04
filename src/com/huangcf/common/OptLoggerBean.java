package com.huangcf.common;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;

public class OptLoggerBean {
	public Object logger(ProceedingJoinPoint pjp) throws Throwable{
		Object retVal = pjp.proceed();//ִ��Ŀ�����Action����
		//1.��ȡ��ǰִ��������ͺͷ�����
		String className = pjp.getTarget()
			.getClass().getName();
		String methodName = 
			pjp.getSignature().getName();
		//2.���������ͺͷ����жϲ�����¼����
		//����className+methodName��key
		//ȥopt.properties��ȡ��������Ϣ
		String key = className+"."+methodName;
		String msg = PropertiesUtil.getOptmsg(key);
		String time = new Date().toString();
		System.out.println("xxx��"+time+"ʱ��ִ����"+msg);
		return retVal;
	}
}
