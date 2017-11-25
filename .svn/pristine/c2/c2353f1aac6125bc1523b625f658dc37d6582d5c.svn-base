package com.haaa.cloudmedical.common.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.common.entity.ResponseDTO;
import com.haaa.cloudmedical.common.service.NationCommonService;

@RestController
@RequestMapping("common")
public class NationCommonController {
	
	@Autowired
	private NationCommonService service;
	
	@RequestMapping("getCountryCodeAndName.action")
	public ResponseDTO getCountryCodeAndName(){
		ResponseDTO dto = new ResponseDTO();
		List<Map<String,Object>> nation = service.getCountryCodeAndName();
		dto.setData(nation);
		dto.setFlag(true);
		return dto;
	}
}
