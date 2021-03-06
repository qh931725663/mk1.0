package com.haaa.cloudmedical.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.log.service.LogService;

@Controller
@RequestMapping("/log")
public class LogController {
	
	@Autowired
	private LogService service;
	
	@RequestMapping("/query.action")
	@ResponseBody
	public Page query(String user_name,String op_type,String startDate,String endDate,int pageno){
		Page page  = service.queryLog(user_name, op_type, startDate, endDate, pageno);
		return page;
	}

}
