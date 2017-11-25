package com.haaa.cloudmedical.platform.analysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.platform.analysis.service.AnalysisService;

@Controller
@RequestMapping("/analysis")
public class AnalysisController {
	
	@Autowired
	private AnalysisService service;
	
	@RequestMapping("getErrData.action")
	@ResponseBody
	public Page getErrData(String startDate,String endDate,String data_type,int pageno){
		Page page = service.getErrData(startDate, endDate, data_type, pageno);
		return page;
	}

}
