package com.cn.common;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;


/**
 * 工具类
 * @author songzhili
 * 2016年6月29日下午1:19:19
 */
@SuppressWarnings("unchecked")
public class Utils {
   
	public static boolean isEmpty(String source){
		if(source == null || source.length() == 0){
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param clazz
	 * @param source
	 * @return
	 */
	public static <T> List<T> converseMapToObject(Class<T> clazz,Map<String, ? extends Object> source){
		
		Object object = source.get("data");
		List<T> listObject = new ArrayList<T>();
		try {
			if(object instanceof List){
				List<Object> objectOne = (List<Object>)object;
				Field [] fields = clazz.getDeclaredFields();
				for(Object objectTwo : objectOne){
					if(objectTwo instanceof Map){
						Map<String,Object> objectThree = (Map<String,Object>)objectTwo;
						T objectT = clazz.newInstance();
						for(Field field : fields){
							String fieldName = field.getName();
							Object objectFour = objectThree.get(fieldName);
							if(objectFour != null){
								field.setAccessible(true);
								if("briberyMoneyRule".equals(fieldName)
										|| "shareExplain".equals(fieldName)){
									field.set(objectT, converseListToJson(objectFour).toString());
								}else{
									field.set(objectT, objectFour);
								}
							}
						}
						listObject.add(objectT);
					}
				}
			}
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		}
		return listObject;
	}
	/**
	 * @param objectFour
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static ArrayNode converseListToJson(Object objectFour){
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode array = mapper.createArrayNode();
		if(objectFour instanceof List){
			List list = (List)objectFour;
			for(Object objectOne : list){
				if(objectOne instanceof Map){
					Map<String, Object> objectTwo = (Map<String, Object>)objectOne;
					ObjectNode node = mapper.createObjectNode();
					for(Map.Entry<String, Object> enrty : objectTwo.entrySet()){
						node.put(enrty.getKey(), enrty.getValue().toString());
					}
					array.add(node);
				}
			}
		}
		return array;
	}
	/**
	 * 
	 * @param key
	 * @param source
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String obtainValue(String key,Map<String, Object> source){
		
		Object object = source.get("data");
		if(object instanceof List){
			List objectOne = (List)object;
			for(Object objectTwo : objectOne){
				if(objectTwo instanceof Map){
					Map objectThree = (Map)objectTwo;
					if(objectThree.get(key) != null){
						return objectThree.get(key).toString();
					}
				}
			}
		}
		return null;
	}
	/**
	 * 
	 * @param source
	 * @param keys
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> obtianValues(Map<String, Object> source,List<String> keys){
		
		Map<String, String> resultMap = new HashMap<String, String>();
		Object object = source.get("data");
		if(object instanceof List){
			List objectOne = (List)object;
			for(Object objectTwo : objectOne){
				if(objectTwo instanceof Map){
					Map objectThree = (Map)objectTwo;
					for(String key : keys){
						Object objectFour = objectThree.get(key);
						if(objectFour != null){
							resultMap.put(key, objectFour.toString());
						}else{
							resultMap.put(key, null);
						}
					}
				}
			}
		}
		return resultMap;
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static List<String> obtainParams(String key){
		List<String> keys = new ArrayList<String>();
		keys.add(key);
		keys.add("curragePage");
		keys.add("pageSize");
		return keys;
	}
	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int obtainSpaceDays(String startTime, String endTime) {
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		Long countOne = 0l;
		try {
			Date dateOne = format.parse(startTime);
			Date dateTwo = format.parse(endTime);
			long timeOne = dateOne.getTime();
			long timeTwo = dateTwo.getTime();
			long count = (timeTwo - timeOne) / (24 * 3600 * 1000);
			countOne = Math.abs(count);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return countOne.intValue();
	}
	
	public static void main(String[] args) {
		System.out.println(obtainSpaceDays("20161228", "20161228"));
	}
}
