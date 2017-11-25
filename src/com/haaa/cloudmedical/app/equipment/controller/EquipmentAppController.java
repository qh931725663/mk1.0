/**
 * 
 */
package com.haaa.cloudmedical.app.equipment.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.app.equipment.service.EquipmentAppService;
import com.haaa.cloudmedical.app.util.CommonUserService;
import com.haaa.cloudmedical.common.entity.InfoJson;

/**
 * @author Bowen Fan
 *
 */
@RestController
@RequestMapping({ "/equipment-app" })
public class EquipmentAppController {
	
	@Resource
	private EquipmentAppService service;
	
	@Resource
	private CommonUserService commonUserService;
	
	private Logger logger = Logger.getLogger(ElectrocardiographController.class);
	
	/**
	 * 
	 * @Title: getMostRecentAll 
	 * @Description: android端获取用户所有7件套最近一次数据 
	 * @param user_id
	 * @return
	 * @throws
	 */
	@RequestMapping(value = { "/getAllRecentData.action" }, method = { RequestMethod.GET })
	public InfoJson getMostRecentAll(String user_id,HttpServletRequest request){
		InfoJson infoJson = new InfoJson();
		try {
			user_id = commonUserService.getPatientId(user_id);
			infoJson = service.getMostRecentAllData(user_id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
		
	}
	
	/**
	 * 
	 * @Title: getIOSMostRecentAll
	 * @Description: ios端获取7件套最近一次数据
	 * @param user_id
	 * @return
	 * @throws
	 */
	@RequestMapping(value = { "/getIOSAllRecentData.action" }, method = { RequestMethod.GET })
	public InfoJson getIOSMostRecentAll(String user_id,HttpServletRequest request){
		InfoJson infoJson = new InfoJson();
		try {
			user_id = commonUserService.getPatientId(user_id);
			infoJson = service.getIOSMostRecentAllData(user_id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
		
	}
}
