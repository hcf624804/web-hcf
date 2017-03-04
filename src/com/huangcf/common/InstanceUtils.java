/** 
 * @(#)InstanceUtils.java Jun 3, 2010
 * 
 * Copyright (c) 1995-2010 Wonders Information Co.,Ltd. 
 * 1518 Lianhang Rd,Shanghai 201112.P.R.C.
 * All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Wonders Group.
 * (Social Security Department). You shall not disclose such
 * Confidential Information and shall use it only in accordance with 
 * the terms of the license agreement you entered into with Wonders Group. 
 *
 * Distributable under GNU LGPL license by gnu.org
 */

package com.huangcf.common;

/**
 * ʵ��ߣ����ո��clazz����ʵ��
 * @author xieguojun
 * @version $Revision: 7324 $ Jun 3, 2010
 * @author ($Date: 2011-05-16 13:01:22 +0800 (��һ, 16 ���� 2011) $ modification by $Author: Shaoyf $)
 * @since 1.0
 */
public class InstanceUtils {
	
	public static <T> T getInstance(Class<T> clazz){
		
		T t =null;
		
		try {
			t =clazz.newInstance();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return t;
		
	}
	
	public static <T> T getInstance(String className){
		
		T t =null;
		
		try {
			Class clazz =Class.forName(className);
			t =(T)clazz.newInstance();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return t;
		
	}
	
	public static Class getClassFromClassName(String className){

		Class clazz =null;
		try {
			clazz=Class.forName(className);
			 
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
		
	}
	
	public static void main(String args[]){
		System.out.println(getClassFromClassName("java.lang.String"));
		System.out.println(String.class);
	}

}
