package com.haaa.cloudmedical.platform.unicode.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.dao.UnicodePlatformDao;

@Service
public class UnicodePlatformService {
	
	@Autowired
	private UnicodePlatformDao dao;
	
	public Page getUnicodeList(String unicode_name,String unicode_type,int pageno){
		StringBuilder sql = new StringBuilder();
		List<String> values = new ArrayList<String>();
		sql.append(" select order_id,unicode_name,unicode_type from k_unicode where 1=1");
		if(unicode_name!=null&&!"".equals(unicode_name)){
			sql.append(" and unicode_name like ? ");
			values.add("%"+unicode_name+"%");
		}
		if(unicode_type!=null&&!"".equals(unicode_type)){
			sql.append(" and unicode_type= ? ");
			values.add(unicode_type);
		}
		Page page = dao.pageQuery(sql.toString(), values.toArray(), pageno, 20);
		return page;
	}
	
	public List<Map<String,Object>> getTypeList(){
		String sql = " select distinct unicode_type from k_unicode";
		List<Map<String,Object>> list = dao.select(sql);
		return list;
	}

}
