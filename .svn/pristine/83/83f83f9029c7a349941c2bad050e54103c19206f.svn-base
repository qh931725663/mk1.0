/**
 * 
 */
package com.haaa.cloudmedical.app.chrondoc.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.app.chrondoc.service.DiabetesDocAppService;
import com.haaa.cloudmedical.app.util.CommonUserService;
import com.haaa.cloudmedical.common.entity.InfoJson;


/**
 * @author Bowen Fan
 *
 */
@RestController
@RequestMapping("/diabetesDoc-app")
public class DiabetesDocAppController {


	@Resource
	private DiabetesDocAppService diabetesDocAppService;
	
	@Resource
	private CommonUserService commonUserService;

	private Logger logger = Logger.getLogger(getClass());
	
	
	/**
	 * 获取档案列表
	 * @param patient_id
	 */
	@RequestMapping("/getDocList.action")
	public InfoJson getDocList(String patient_id) {
		InfoJson infoJson = new InfoJson(); 
		try {
			infoJson = InfoJson.setSucc(diabetesDocAppService.getDocList(patient_id));
		} catch (Exception e) {
			infoJson = InfoJson.setFaile(e.getMessage());
			e.printStackTrace();
		}
		return infoJson;
	}
	
	
	/**
	 * 新增高血压档案主表
	 * @param user_id
	 * @return
	 */
	@RequestMapping("/addDoc.action")
	public InfoJson addDoc(String patient_id) {
		InfoJson infoJson = new InfoJson();
		try {
			// long patient_id = commonUserService.getPatientId(Long.valueOf(user_id));
			infoJson = diabetesDocAppService.addDoc(Long.parseLong(patient_id));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return infoJson;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/addAnswers.action")
	public InfoJson addAnswers(@RequestParam MultiValueMap<String, Object> map) {		
		InfoJson infoJson = new InfoJson();
		try {
		Map<String, List<Object>> map2 = map;
		infoJson= diabetesDocAppService.addAnswers(map2);
		String order_id = String.valueOf(map2.get("order_id").get(0));
		diabetesDocAppService.init((Map<String,Object>)infoJson.getData(), order_id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
	}



	
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateAnswers.action")
	public InfoJson updateAnswers(@RequestParam MultiValueMap<String, Object> map) {		
		InfoJson infoJson = new InfoJson();
		try {
		Map<String, List<Object>> map2 = map;
		infoJson= diabetesDocAppService.update(map2);
		String order_id = String.valueOf(map2.get("order_id").get(0));
		diabetesDocAppService.init((Map<String,Object>)infoJson.getData(), order_id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
	}
	
	@RequestMapping("/getAnswers.action")
	public InfoJson getAnswers(long order_id,String scope_flag) {		
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = diabetesDocAppService.getAnswers(order_id, scope_flag);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
	}
	
	@RequestMapping("/getBasicDoc.action")
	public InfoJson getBasicDoc(long order_id) {		
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = diabetesDocAppService.getBasicDoc(order_id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
	}
	
}


