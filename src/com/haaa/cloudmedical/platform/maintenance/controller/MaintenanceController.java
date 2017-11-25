/**
 * 
 */
package com.haaa.cloudmedical.platform.maintenance.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.service.PositionService;
import com.haaa.cloudmedical.common.service.UnicodeService;
import com.haaa.cloudmedical.platform.maintenance.service.MaintenanceService;


/**
 * @author Bowen Fan
 *
 */
@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

	@Resource
	private MaintenanceService maintenanceService;
	
	@Resource
	private UnicodeService unicodeService;
	
	@Resource
	private PositionService positionService;

	private Logger logger = Logger.getLogger(MaintenanceController.class);

	@RequestMapping("/page.action")
	public InfoJson maintenancePage(String datemin, String datemax, Integer pageno, Integer pagesize) {
		InfoJson infoJson = new InfoJson();
		try {
			if (pageno == null) {
				pageno = 1;
			}
			if (pagesize == null) {
				pagesize = Constant.DEFAULT_PAGESIZE;
			}
			infoJson = maintenanceService.maintenancePage(datemin, datemax, pageno, pagesize);
		} catch (Exception e) {
			logger.error("", e);
		}
		return infoJson;
	}

	@RequestMapping("/cacheInit.action")
	public InfoJson cacheInit(){
		try {
			unicodeService.init();
			positionService.positionInfoInit();
		} catch (Exception e) {
			logger.error("", e);
			return InfoJson.setFaile();
		}
		return InfoJson.setSucc("");
	}
	
}
