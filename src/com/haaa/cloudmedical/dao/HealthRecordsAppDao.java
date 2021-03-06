package com.haaa.cloudmedical.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.haaa.cloudmedical.common.dao.BaseTemplateDao;

@Repository
public class HealthRecordsAppDao extends BaseTemplateDao{
	
	public List<Map<String,Object>> selectAllReprot(String user_id){
		
		String sql = "select report_year from medical_report where user_id = ? group by report_year order by report_year desc";
		List<Map<String,Object>> report_years = this.jdbcTemplate.queryForList(sql,user_id);
		sql = "select order_id,user_id,user_name,date_format(up_time,'%Y/%m/%d %H:%i') up_time,date_format(report_time,'%Y/%m/%d %H:%i') report_time,report_type,hosp_name,report_no,report_year from medical_report where user_id = ? order by up_time desc";
		List<Map<String,Object>> allReports = this.jdbcTemplate.queryForList(sql,user_id);
		List<Map<String,Object>> report_list = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> report_list_by_year = null;
		Map<String,Object> report_by_year = null;
		for (Map<String, Object> report_year : report_years) {
			String year = report_year.get("report_year").toString();
			
			report_by_year = new HashMap<String, Object>();
			report_by_year.put("year", year);
			
			report_list_by_year = new ArrayList<Map<String,Object>>();			
			for (Map<String, Object> report : allReports) {
				if(report.get("report_year").toString().equals(year)){
					report_list_by_year.add(report);
				}					
			}
			List<Map<String,Object>> result = report_list_by_year.stream().sorted((a,b)->b.get("up_time").toString().compareTo(a.get("up_time").toString())).collect(Collectors.toList());
			report_by_year.put("reports", result);
			report_list.add(report_by_year);
		}
		return report_list;
	}
	
	public Map<String,Object> getUser(String user_id){
		String sql = "select * from n_user where user_id = ? ";
		Map<String,Object> map = this.jdbcTemplate.queryForMap(sql, user_id);
		return map;
	}
}
