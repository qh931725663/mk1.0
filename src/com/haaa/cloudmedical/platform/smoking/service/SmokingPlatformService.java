package com.haaa.cloudmedical.platform.smoking.service;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.haaa.cloudmedical.entity.SmokingPlatformView;
import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.dao.SmokingDao;

@Service
public class SmokingPlatformService {
	@Autowired
	private SmokingDao dao;
   //用户查询接口where
	public Page findSmokingUser (SmokingPlatformView view,Integer pageno )throws SQLException, Exception{
		Map<String, Object> mapW = new HashMap<String, Object>();
		String temp1;
		mapW.put("user_name", view.getUser_name());
		mapW.put("user_card", view.getUser_card());
		mapW.put("user_phone", view.getUser_phone());
		mapW.put("doctor_name", view.getDoctor_name());
		mapW.put("quit_smoking_plan_end", view.getQuit_smoking_plan_end());
		mapW.put("dateAfter", view.getDateAfter());
		mapW.put("dateBefore", view.getDateBefore());
		mapW.put("user_is_vip", view.getUser_is_vip());
		mapW.put("dateAfter", view.getDateAfter());
		if (view.getDateBefore()==null||"".equals(view.getDateBefore()) )
			temp1=null;
		else
			temp1=view.getDateBefore()+" 23:59:59";
		mapW.put("dateBefore",temp1);
		
		
		return queryWhere(mapW,pageno);
	
	}
	//后台戒烟模块查询用户信息where

	public Page queryWhere(Map<String,Object> mapW ,Integer pageno) {
		Object[] params=new Object[]{};
		Object[] values=new Object[]{};
		String sql;
		Map<String, Object[]> map=getWhere(mapW);
		Page list =new Page();

		if(map!=null){
		params = map.get("params");
		values  =map.get("values");
		if (params.length>0) 
		     sql ="select quit_smoking_plan_name,getahdi(ahdi_value) ahdi_value,findname(user_marriage) user_marriage,user_id ,user_name ,user_card ,user_phone ,findname(user_is_vip) user_is_vip ,findname(user_sex) user_sex ,user_age ,findname(user_blood) user_blood ,findname(user_medical_burden) user_medical_burden ,user_medicare_card ,findname(user_work) user_work ,user_height ,user_weight ,date_format(user_create, '%Y-%m-%d  %H:%i:%s') user_create,smoked_year ,cigarettes_per_day ,doctor_id ,doctor_name ,order_id ,findname(quit_smoking_plan_end) quit_smoking_plan_end ,date_format(q_plan_create, '%Y-%m-%d  %H:%i:%s') q_plan_create  from q_smoking_platform_view where "+StringUtils.join(params, "= ? and ") +" = ? and ";
		else
			 sql="select quit_smoking_plan_name,getahdi(ahdi_value) ahdi_value,findname(user_marriage) user_marriage,user_id ,user_name ,user_card ,user_phone ,findname(user_is_vip) user_is_vip ,findname(user_sex) user_sex ,user_age ,findname(user_blood) user_blood ,findname(user_medical_burden) user_medical_burden ,user_medicare_card ,findname(user_work) user_work ,user_height ,user_weight ,date_format(user_create, '%Y-%m-%d  %H:%i:%s') user_create,smoked_year ,cigarettes_per_day ,doctor_id ,doctor_name ,order_id ,findname(quit_smoking_plan_end) quit_smoking_plan_end ,date_format(q_plan_create, '%Y-%m-%d  %H:%i:%s') q_plan_create  from q_smoking_platform_view where ";
		sql=sql+"q_plan_create > ? and q_plan_create < ? ";
		System.out.println(sql);
		System.out.println(values.length);
		try {
			list=dao.pageQuery(sql, values, pageno, 10);
		} catch (Exception e) {
			return null;
		}
		}else
		{
			sql="select quit_smoking_plan_name,getahdi(ahdi_value) ahdi_value,findname(user_marriage) user_marriage,user_id ,user_name ,user_card ,user_phone ,findname(user_is_vip) user_is_vip ,findname(user_sex) user_sex ,user_age ,findname(user_blood) user_blood ,findname(user_medical_burden) user_medical_burden ,user_medicare_card ,findname(user_work) user_work ,user_height ,user_weight ,date_format(user_create, '%Y-%m-%d  %H:%i:%s') user_create ,smoked_year ,cigarettes_per_day ,doctor_id ,doctor_name ,order_id ,findname(quit_smoking_plan_end) quit_smoking_plan_end ,date_format(q_plan_create, '%Y-%m-%d  %H:%i:%s' ) q_plan_create from q_smoking_platform_view where q_plan_create > ? and q_plan_create < ? ";
			try {
				list=dao.pageQuery(sql, new Object[]{"2000-12-12","2999-12-12" }, pageno, 10);
			} catch (Exception e) {
				return null;
			}
		}


		return list;
	}
	//后台戒烟模块查询where拼接
	protected Map<String, Object[]> getWhere(Map<String,Object> map) {
		Object dateAfter = null;
		Object dateBefore = null;
		System.out.println(map.get("dateAfter"));
		System.out.println(map.get("dateBefore"));
		Map<String, Object[]> property = null;
		List<String> params = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			Object value = map.get(key);
			if(key.equals("dateAfter")) dateAfter=value;
			  else
				if(key.equals("dateBefore")) dateBefore=value;
				 else
			if(value!=null&&!"".equals(value.toString())){
				params.add(key);
				values.add(value);
			}			
		}
		if (dateAfter==null||"".equals(dateAfter.toString())) dateAfter="2000-12-12";
		if (dateBefore==null||"".equals(dateBefore.toString())) dateBefore="2999-12-12";
		values.add(dateAfter);
		values.add(dateBefore);
		if (values.size() > 0) {
			property = new HashMap<String, Object[]>();
			property.put("params", params.toArray());
			property.put("values", values.toArray());
		}
		return property;
	}
	
	
	//用户查询接口
	
	//后台戒烟模块查询用户信息view
	public 	Map<String,Object> selectUserInfo(Long user_id){
	        
		return deleteNull(dao.selectUserInfo(user_id));
	}
    //分页查询戒烟计划
	public Page selectAllPlanP(Long user_id,Integer pageno ){
		String sql = "select order_id,user_id,quit_smoking_plan_name,quit_smoking_plan_target,quit_smoking_plan_day,quit_smoking_plan_number,date_format(create_date, '%Y-%m-%d  %H:%i:%s') create_date,date_format(update_date, '%Y-%m-%d  %H:%i:%s') update_date,findname(quit_smoking_plan_end) quit_smoking_plan_end,sign_date from q_quit_smoking_plan where user_id = ? order by order_id desc";
		return dao.pageQuery(sql, new Object[]{user_id}, pageno, 10);
		
	}
	//List<Map<String,Object>>去除null
	public List<Map<String,Object>> deleteNull(List<Map<String,Object>> list){
		for (Map<String, Object> map : list) {
			for (Map.Entry<String,Object> entry : map.entrySet()) {
				if(entry.getValue()==null)
					entry.setValue("");
			}
		}
		return list;
		
	}
	public Map<String,Object> deleteNull(Map<String,Object> map){
		

			for (Map.Entry<String,Object> entry : map.entrySet()) {
				if(entry.getValue()==null)
					entry.setValue("");
			}
		return map;
		
	}

}
