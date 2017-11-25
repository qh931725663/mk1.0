package com.haaa.cloudmedical.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haaa.cloudmedical.common.entity.ResponseDTO;
import com.haaa.cloudmedical.common.util.BeanUtil;

@Controller
@RequestMapping("/yyb-app")
public class YingYongBao {
	
	@RequestMapping("/getUrl.action")
	@ResponseBody
	public ResponseDTO getUrl(){
		String url = BeanUtil.getProperty("dbconfig").getString("yingyongbao");
		ResponseDTO dto = new ResponseDTO();
		dto.setFlag(true);
		dto.setData(url);
		return dto;
	}

}
