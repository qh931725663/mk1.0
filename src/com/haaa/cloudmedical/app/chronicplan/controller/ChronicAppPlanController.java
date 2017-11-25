package com.haaa.cloudmedical.app.chronicplan.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.app.chronicplan.service.ChronicAppPlanService;
import com.haaa.cloudmedical.common.entity.ResponseDTO;

@RestController
@RequestMapping("chronicplan-app")
public class ChronicAppPlanController {
	
	@Autowired
	private ChronicAppPlanService service;
	
	
	/**
	 * 获取患者慢病管理计划列表
	 * @param patient_id
	 * @return
	 */
	@RequestMapping("getChronicPlanList.action")
	public Map<String,Object> getChronicPlanList(String patient_id,String type){
		Map<String,Object> map = new HashMap<>();
		if(patient_id==null||type==null){
			map.put("flag", false);
			map.put("errmsg", "参数为空");
			return map;
		}
		try {
			map.put("data", service.getChronicPlanList(patient_id, type));
			map.put("count", service.existsPlan(patient_id, type));
			map.put("flag", true);
		} catch (RuntimeException e) {
			map.put("flag", false);
			map.put("errmsg", e.toString());
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取患者慢病管理计划详情
	 * @param order_id
	 * @return
	 */
	@RequestMapping("getChronicPlanDetail.action")
	public ResponseDTO getChronicPlanDetail(String order_id){
		ResponseDTO dto = new ResponseDTO();
		if(order_id==null){
			dto.setErrmsg("参数为空");
			return dto;
		}
		try {
			dto.setData(service.getChronicPlanDetail2(order_id));
			dto.setFlag(true);
		} catch (RuntimeException e) {
			dto.setErrmsg(e.toString());
			e.printStackTrace();
		}
		return dto;
	}
	
	/**
	 * 获取管理计划监测项状态
	 * @param order_id
	 * @return
	 */
	@RequestMapping("getChroncPlanItem.action")
	public ResponseDTO getChroncPlanItem(String order_id){
		ResponseDTO dto = new ResponseDTO();
		if(order_id==null){
			dto.setErrmsg("参数为空");
			return dto;
		}
		try {
			dto.setData(service.getChroncPlanItem(order_id));
			dto.setFlag(true);
		} catch (RuntimeException e) {
			dto.setErrmsg(e.toString());
			e.printStackTrace();
		}
		return dto;
	}
	
	

}
