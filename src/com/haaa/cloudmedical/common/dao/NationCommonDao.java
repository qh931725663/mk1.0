package com.haaa.cloudmedical.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class NationCommonDao extends BaseTemplateDao{
	
	public List<Map<String,Object>> getCountryCodeAndName(){
		String sql = "select country,country_name from nations where country not in (0,86) order by country";
		List<Map<String,Object>> nation = this.select(sql);
		return nation;
	}

}
