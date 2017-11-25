package com.haaa.cloudmedical.common.util;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.haaa.cloudmedical.entity.User;

/**
 * @author 蒋永海 2017-08-30
 */

public class BeanUtil {
	
	/**
	 * 从静态资源文件获取值
	 * @param name
	 * @return
	 */
	public static ResourceBundle getProperty(String name) {
		ResourceBundle rb = ResourceBundle.getBundle(name);
		return rb;
	}
	
	/**
	 * 从静态资源文件获取值
	 * @param name
	 * @return
	 */
	public static String getProperty(String filename,String propertyname) {
		String value = "";
		ResourceBundle rb = ResourceBundle.getBundle(filename);
		if(rb.containsKey(propertyname)){
			value = rb.getString(propertyname);
		}
		return value;
	}

	/**
	 * 将实体对象转换为映射
	 * @param from
	 * @return
	 */
	public static <T> Map<String, Object> toMapFromObject(T from) {
		Map<String, Object> to = new HashMap<String, Object>();
		Field[] fields = from.getClass().getDeclaredFields();
		Field.setAccessible(fields, true);
		try {
			for (Field field : fields) {
				Object value = field.get(from);
				if (value != null && value.toString().length() > 0) {
					to.put(field.getName(), value);
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return to;
	}
	
	public static <T> List<Map<String, Object>> toMapFromObject(List<T> from) {
		List<Map<String, Object>> result = new ArrayList<>();
		if(from!=null&&from.size()>0){
			from.forEach(obj->{
				Map<String, Object> to = toMapFromObject(obj);
				result.add(to);
			});
		}
		return result;
	}

	/**
	 * 将映射转换为实体对象
	 * @param from
	 * @param to
	 * @return
	 */
	public static <T> T toObjectFromMap(Map<String, Object> from, Class<T> to) {
		Field[] fields = to.getDeclaredFields();
		Field.setAccessible(fields, true);
		T obj = null;
		try {
			obj = to.newInstance();
			for (Field field : fields) {
				for (Map.Entry<String, Object> entry : from.entrySet()) {
					if (entry.getKey().equals(field.getName())) {
						Object value = entry.getValue();
						if (value != null && value.toString().length() > 0) {
							if (value.getClass() == Date.class) {
								value = DateFormat.getDateTimeInstance().format(value);
							}
							field.set(obj, value.toString());
							break;
						}
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 将映射转换为实体对象
	 * @param from
	 * @param to
	 * @return
	 */
	public static <T> List<T> toObjectFromMap(List<Map<String, Object>> from, Class<T> to) {
		List<T> list = new ArrayList<T>();
		from.forEach(map->{
			T obj = toObjectFromMap(map, to);
			list.add(obj);
		});
		return list;
	}

	public static <T> void copy(T from, T to) {
		Field[] fromFields = from.getClass().getDeclaredFields();
		Field[] toFields = to.getClass().getDeclaredFields();
		Field.setAccessible(fromFields, true);
		Field.setAccessible(toFields, true);
		try {
			for (Field fromField : fromFields) {
				Object value = fromField.get(from);
				if (value != null) {
					for (Field toField : toFields) {
						if (toField.getName().equals(fromField.getName())) {
							toField.set(to, value);
							break;
						}
					}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将映射中的value由Object转换为String
	 * @param map
	 * @return
	 */
	public static Map<String, String> obj2Str(Map<String, Object> map) {
		Map<String, String> result = new HashMap<>();
		map.forEach((key, value) -> result.put(key, value == null ? null : String.valueOf(value)));
		return result;
	}

	/**
	 * 将映射中的value由Object转换为String
	 * @param list
	 * @return
	 */
	public static List<Map<String, String>> obj2Str(List<Map<String, Object>> list) {
		List<Map<String, String>> result = list.stream().map(t->obj2Str(t)).collect(Collectors.toList());
		return result;
	}
}
