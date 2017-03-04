package com.huangcf.common;

import org.apache.log4j.Logger;

public class ExceptionLogger {
	private Logger logger = 
			Logger.getLogger(ExceptionLogger.class);
		/**
		 * ��¼�쳣��Ϣ
		 * @param e Ŀ������׳����쳣��Ϣ
		 */
		public void logger(Exception e){
			logger.info(e.getMessage());
//			e.printStackTrace();
			StackTraceElement[] els =  e.getStackTrace();
			for(StackTraceElement el :els){
				logger.error(el);
			}
		}
}
