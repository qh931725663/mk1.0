package com.haaa.cloudmedical.wechat.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.StdDTO;
import com.haaa.cloudmedical.wechat.service.IWeixinNewsService;

@RestController
@RequestMapping("/news-wx")
public class WeixinNewsController {
	
	@Autowired
	IWeixinNewsService service;
	
	private Logger logger = Logger.getLogger(WeixinNewsController.class);
	
	@RequestMapping("/news.action")
	public StdDTO getNews(String section, Integer pageno, Integer pagesize){
		StdDTO stdDTO = new StdDTO();
		try {
			if (pagesize == null) {
				pagesize = Constant.DEFAULT_PAGESIZE;
			}
			if (pageno == null) {
				pageno = 1;
			}
			stdDTO = service.getNews("2", pageno, pagesize);
		} catch (Exception e) {
			stdDTO.setStatus(1);
			logger.error(e.getMessage(), e);
		}
		
		return stdDTO;
		
	}

}
