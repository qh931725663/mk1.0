package com.haaa.cloudmedical.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.dao.UnicodeDao;
import com.haaa.cloudmedical.common.util.redis.RedisCacheUtil;

@Service
public class UnicodeUtil{

	@Resource
	private  UnicodeDao unicodeDao;
	
	@Resource
	private  RedisCacheUtil redisCacheUtil;
	
	public   void init(){
		//unicode_init unicode初始化标志位，如果为true,表示unicode已初始化
		Boolean unicode_init = (Boolean) redisCacheUtil.getCacheObject("unicode_init");
		if (unicode_init==null||!unicode_init) {
			String sql = "select order_id,unicode_name,unicode_type from k_unicode";
		    List<Map<String, Object>> resultlList=unicodeDao.queryAll(sql);
		    
			String order_id = null;
			String unicode_type = null;
			List<Map<String, Object>> subList = null;
		    Map<String, List<Map<String, Object>>> type_map = new HashMap<String, List<Map<String,Object>>>();
		    System.out.println(resultlList);
		    /**
		     * 将order_id取出，作为键值对的键
		     */
		    for (Map<String, Object> map : resultlList) {
				order_id = String.valueOf(map.get("order_id")) ;
				unicode_type = (String) map.get("unicode_type");
                if (!type_map.containsKey(unicode_type)) {
                	subList = new ArrayList<Map<String,Object>>();
                	subList.add(map);
					type_map.put(unicode_type,subList);
				}else{
					subList = type_map.get(unicode_type);
					subList.add(map);
				}
                order_id="unicode_id_"+order_id;
                redisCacheUtil.setCacheObject(order_id, map);
			}
            Set<String> type_set = type_map.keySet();
            for (Object key : type_set) {
            	System.out.println(key);
				redisCacheUtil.setCacheList("unicode_type_"+key,type_map.get(key));
			}
		}
		unicode_init = true;
		redisCacheUtil.setCacheObject("unicode_init", unicode_init);
	}
	
	public   Map<String, Object> getById(String id){
		return (Map<String, Object>) redisCacheUtil.getCacheObject("unicode_id_"+id);
	}
	
	
	public   List<Map<String, Object>>getByType(String type){
		return redisCacheUtil.getCacheList("unicode_type_"+type);
	}
	
}
