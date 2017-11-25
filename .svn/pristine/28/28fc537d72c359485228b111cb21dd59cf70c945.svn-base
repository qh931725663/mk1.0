package com.haaa.cloudmedical.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.dao.NationCommonDao;

@Service
public class NationCommonService {
	
	@Autowired
	private NationCommonDao dao;
	
	public List<Map<String,Object>> getCountryCodeAndName(){
		List<Map<String,Object>> nation = dao.getCountryCodeAndName();
		return nation;
	}

}
