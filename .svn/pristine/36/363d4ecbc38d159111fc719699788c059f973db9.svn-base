package com.haaa.cloudmedical.platform.plan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.app.chronicplan.service.ChronicAppPlanService;
import com.haaa.cloudmedical.common.entity.ResponseDTO;

@RestController
@RequestMapping("chronicplan-platform")
public class ChronicPlatformPlanController {

	@Autowired
	private ChronicAppPlanService service;

	/**
	 * 获取患者慢病管理计划列表
	 * 
	 * @param patient_id
	 * @return
	 */
	@RequestMapping("getChronicPlanList.action")
	public ResponseDTO getChronicPlanList(String patient_id, String type) {
		ResponseDTO dto = new ResponseDTO();
		if (patient_id == null || type == null) {
			dto.setErrmsg("参数为空");
			return dto;
		}
		try {
			dto.setData(service.getChronicPlanList(patient_id, type));
			dto.setFlag(true);
		} catch (RuntimeException e) {
			dto.setErrmsg(e.toString());
			e.printStackTrace();
		}
		return dto;
	}

	/**
	 * 获取患者慢病管理计划详情
	 * 
	 * @param order_id
	 * @return
	 */
	@RequestMapping("getChronicPlanDetail.action")
	public ResponseDTO getChronicPlanDetail(String order_id) {
		ResponseDTO dto = new ResponseDTO();
		if (order_id == null) {
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
	 * 
	 * @param order_id
	 * @return
	 */
	@RequestMapping("getChroncPlanItem.action")
	public ResponseDTO getChroncPlanItem(String order_id) {
		ResponseDTO dto = new ResponseDTO();
		if (order_id == null) {
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
