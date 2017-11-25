package com.haaa.cloudmedical.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class XMLUtil {
	public static Map<String, Object> xml2Map(String xml) {
		Map<String, Object> map = null;
		try {
			Document doc = DocumentHelper.parseText(xml);// 将xml转为dom对象
			Element root = doc.getRootElement();// 获取根节点
			map = subMap(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	private static Map<String, Object> subMap(Element root) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Element> elements = root.elements();
		Map<String, Object> elementTypeMap = new HashMap<String, Object>();
		for (Element element : elements) { // 遍历子元素,判断是list还是map
			String name = element.getName();
			if (!elementTypeMap.containsKey(name)) {
				elementTypeMap.put(name, "map");
			} else {
				elementTypeMap.put(name, "list");
				map.put(element.getName(), new ArrayList<Object>());
			}
		}

		for (Element element : elements) { // 遍历子元素
			if (elementTypeMap.get(element.getName()).equals("map")) {
				map.put(element.getName(), element.getTextTrim());
				List<Element> elements2 = element.elements();
				if (elements2.size() > 0) {
					map.put(element.getName(), subMap(element));
				} else {
					map.put(element.getName(), element.getTextTrim());
				}
			}else{
				List<Object> list = (List<Object>) map.get(element.getName());
				list.add(subMap(element));
			}
		}
		return map;
	}

}
