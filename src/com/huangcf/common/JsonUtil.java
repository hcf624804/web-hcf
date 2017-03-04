package com.huangcf.common;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 处理json工具类
 * @author Administrator
 *
 */
public class JsonUtil {
	/**
	 * 从json字符串中获取对象
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object getObjectFromJsonString(String jsonString, Class pojoCalss) {
		  JSONObject jsonObject = JSONObject.fromObject(jsonString);
		  Object pojo = JSONObject.toBean(jsonObject, pojoCalss);
		  return pojo;
	}
	/**
	 * 从json字符串中获取map
	 * @param jsonString
	 * @return
	 */
	public static Map getMapFromJson(String jsonString) {
		  JSONObject jsonObject = JSONObject.fromObject(jsonString);
		  Iterator keyIter = jsonObject.keys();
		  String key;
		  Object value;
		  Map<String, Object> valueMap = new HashMap<String, Object>();
		  while (keyIter.hasNext()) {
			   key = (String) keyIter.next();
			   value = jsonObject.get(key);
			   valueMap.put(key, value);
		  }
		  return valueMap;
	}
	/**
	 * 从json字符串中获取对象数组
	 * @param jsonString
	 * @return
	 */
	public static Object[] getObjectArrayFromJson(String jsonString) {
		  JSONArray jsonArray = JSONArray.fromObject(jsonString);
		  return jsonArray.toArray();
	}
	/**
	 * 从json字符串中获取对象集合
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static List getList4Json(String jsonString, Class pojoClass) {
		  JSONArray jsonArray = JSONArray.fromObject(jsonString);
		  JSONObject jsonObject;
		  Object pojoValue;
		  List list = new ArrayList();
		  for (int i = 0; i < jsonArray.size(); i++) {
			   jsonObject = jsonArray.getJSONObject(i);
			   pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			   list.add(pojoValue);
		  }
		  return list;
	}
	/**
	 * 从json字符串中解析出json字符串数组
	 * @param jsonString
	 * @return
	 */
	public static String[] getStringArrayFromJson(String jsonString) {
		  JSONArray jsonArray = JSONArray.fromObject(jsonString);
		  String[] stringArray = new String[jsonArray.size()];
		  for (int i = 0; i < jsonArray.size(); i++) {
			  stringArray[i] = jsonArray.getString(i);
		  }
		  return stringArray;
	}
	/**
	 * 将对象转为json字符串
	 * @param javaObj
	 * @return
	 */
	public static String getJsonStringFromJavaPOJO(Object javaObj) {
		  JSONObject json;
		  json = JSONObject.fromObject(javaObj);
		  return json.toString();
	}
	/**
	 * 将对象集合转为json字符串
	 * @param collection
	 * @return
	 */
	public static String getJsonStringFromCollection(Collection collection){
		JSONArray json =  JSONArray.fromObject(collection);
		return json.toString();
	}
	//---------------------------------------------------------------------------------------------//
	public static Long[] getLongArrayFromJson(String jsonString) {
		  JSONArray jsonArray = JSONArray.fromObject(jsonString);
		  Long[] longArray = new Long[jsonArray.size()];
		  for (int i = 0; i < jsonArray.size(); i++) {
			  longArray[i] = jsonArray.getLong(i);
		  }
		  return longArray;
	}

		 /**
		  * 从json数组中解析出java Integer型对象数组
		  *
		  * @param jsonString
		  * @return
		  */
		 public static Integer[] getIntegerArrayFromJson(String jsonString) {
			  JSONArray jsonArray = JSONArray.fromObject(jsonString);
			  Integer[] integerArray = new Integer[jsonArray.size()];
			  for (int i = 0; i < jsonArray.size(); i++) {
				  integerArray[i] = jsonArray.getInt(i);
			  }
			  return integerArray;
		 }

//		 /**
//		  * 从json数组中解析出java Date 型对象数组，使用本方法必须保证
//		  *
//		  * @param jsonString
//		  * @return
//		  */
//		 public static Date[] getDateArray4Json(String jsonString, String DataFormat) {
//		  JSONArray jsonArray = JSONArray.fromObject(jsonString);
//		  Date[] dateArray = new Date[jsonArray.size()];
//		  String dateString;
//		  Date date;
//		  for (int i = 0; i < jsonArray.size(); i++) {
//		   dateString = jsonArray.getString(i);
//		   try {
//		    date = DateUtils.parseDate(dateString, new String[] { "yyyy-MM-ddd" });
//		    dateArray[i] = date;
//		   } catch (ParseException e) {
//		    new RuntimeException(e);
//		   }
//		  }
//		  return dateArray;
//		 }

		 /** */
		 /**
		  * 从json数组中解析出java Integer型对象数组
		  *
		  * @param jsonString
		  * @return
		  */
	 public static Double[] getDoubleArray4Json(String jsonString) {

		  JSONArray jsonArray = JSONArray.fromObject(jsonString);
		  Double[] doubleArray = new Double[jsonArray.size()];
		  for (int i = 0; i < jsonArray.size(); i++) {
			  doubleArray[i] = jsonArray.getDouble(i);
		  }
		  return doubleArray;
	 }
}
