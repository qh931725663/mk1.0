package com.haaa.cloudmedical.platform.unicode.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.common.entity.ResponseDTO;
import com.haaa.cloudmedical.platform.unicode.service.UnicodePlatformService;


@Controller
@RequestMapping("/unicode-platform")
public class UnicodePlatformController {
	
	@Autowired
	private UnicodePlatformService service;
	
	@RequestMapping("/getUnicodeList.action")
	@ResponseBody
	public Page getUnicodeList(String unicode_name,String unicode_type,Integer pageno){
		Page page = service.getUnicodeList(unicode_name, unicode_type, pageno);
		return page;
	}
	
	@RequestMapping("/getUnicodeTypeList.action")
	@ResponseBody
	public ResponseDTO getUnicodeTypeList(){
		List<Map<String,Object>> list = service.getTypeList();
		ResponseDTO dto = new ResponseDTO();
		dto.setData(list);
		dto.setFlag(true);
		return dto;
	}

}
