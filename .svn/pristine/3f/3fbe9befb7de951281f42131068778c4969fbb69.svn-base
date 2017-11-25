package com.haaa.cloudmedical.common.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.service.UnicodeService;

@RestController
@RequestMapping("/unicode")
public class UnicodeController {

	@Resource
	private UnicodeService unicodeService;

	private Logger logger = Logger.getLogger(UnicodeController.class);

	@RequestMapping(value = "/id.action", method = RequestMethod.GET)
	public InfoJson getById(String id) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = unicodeService.getById(id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
	}

	@RequestMapping(value = "/type.action", method = RequestMethod.GET)
	public InfoJson getByType(String type) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = unicodeService.getByType(type);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
	}
	
	@RequestMapping(value = "/bloodSugarPeriod.action", method = RequestMethod.GET)
	public InfoJson getBloodSugarPeriod() {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = unicodeService.getBloodSugarPeriod();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
	}
	
	
}
