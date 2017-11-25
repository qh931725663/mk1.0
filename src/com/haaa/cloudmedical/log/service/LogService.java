package com.haaa.cloudmedical.log.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.dao.CommonDao;
import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.common.util.DateUtil;

@Service
public class LogService {

	@Autowired
	private CommonDao dao;

	public long saveLog(Map<String, Object> log) {
		Date date = new Date();
		log.put("create_date", DateUtil.DateFormat(date, "yyyy-MM-dd HH:mm:ss"));
		return dao.insert(log, "log_operate");
	}

	public Page queryLog(String user_name,String op_type,String startDate,String endDate,int pageno) {
		StringBuilder sql = new StringBuilder("select order_id,user_id,user_name,ip_address,op_type,class_name,param,result,"
				+ "date_format(create_date,'%Y-%m-%d %H:%i:%s') create_date from log_operate where 1=1");
		List<String> value = new ArrayList<>();
		if(user_name!=null&&!"".equals(user_name)){
			sql.append(" and user_name like ? ");
			value.add("%"+user_name+"%");
		}
		if(op_type!=null&&!"".equals(op_type)){
			sql.append(" and op_type = ? ");
			value.add(op_type);
		}
		if(startDate!=null&&!"".equals(startDate)){
			sql.append(" and date_format(create_date,'%Y-%m-%d %H:%i:%s') >= ? ");
			value.add(startDate);
		}
		if(endDate!=null&&!"".equals(endDate)){
			sql.append(" and date_format(create_date,'%Y-%m-%d %H:%i:%s') <= ? ");
			value.add(endDate);
		}
		sql.append(" order by create_date desc");
		Page page = dao.pageQuery(sql.toString(), value.toArray(), pageno, 20);
		return page;
	}

}
