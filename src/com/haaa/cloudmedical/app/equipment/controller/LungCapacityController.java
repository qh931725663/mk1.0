package com.haaa.cloudmedical.app.equipment.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.app.equipment.service.LungCapacityService;
import com.haaa.cloudmedical.app.util.CommonUserService;
import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.util.StringUtil;
import com.haaa.cloudmedical.entity.EquipmentUse;
import com.haaa.cloudmedical.entity.LungCapacity;

@RestController
@RequestMapping({ "/lung_capacity" })
public class LungCapacityController {
	@Resource
	private LungCapacityService service;

	@Resource
	private CommonUserService commonUserService;

	private Logger logger = Logger.getLogger(LungCapacityController.class);
	@RequestMapping(value = { "/add.action" }, method = { RequestMethod.POST })
	public InfoJson add(EquipmentUse equipmentUse, LungCapacity lungCapacity, HttpServletRequest request) {
		InfoJson infoJson = new InfoJson();
		try {
			////获取用户以及医生的id(从缓存中获取)
			equipmentUse.setUser_id(commonUserService.getPatientId(equipmentUse.getUser_id()));
			equipmentUse.setDoctor_id(commonUserService.getDoctorId());
			if (lungCapacity.getFev1() != null || lungCapacity.getFvc() != null || lungCapacity.getPef() != null
					|| lungCapacity.getFev1_rate() != null || lungCapacity.getFvc_rate() != null
					|| lungCapacity.getPef_rate() != null) {
				infoJson = service.add(equipmentUse, lungCapacity);
			} else {
				infoJson.setInfo("没有数据传入！！！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return infoJson;
	}

	/**
	 * 
	 * @Title: query 
	 * @Description: 数据查询 
	 * @param user_id 
	 * @param recent 不为null时表示，曲线图查询 
	 * @param year_month 
	 * @param pageno 
	 * @param pagesize 
	 * @param request 
	 * @return 
	 * @throws
	 */
	@RequestMapping(value = { "/query.action" }, method = { RequestMethod.GET })
	public InfoJson query(Long user_id, String recent, String type, String year_month, Integer sex, Integer age,
			Integer days, Double height, Integer pageno, Integer pagesize, HttpServletRequest request) {
		InfoJson infoJson = new InfoJson();
		try {
			// 根据是否传递user_id判断从患者端还是医生端传递
			user_id = commonUserService.getPatientId(user_id);
			//默认数据DEFAULT_PAGESIZE = 15
			if (pagesize == null) {
				pagesize = Constant.DEFAULT_PAGESIZE;
			}
			//默认查询最近 RECENT = 30
			if (recent != null) {
				if (days == null) {
					days = Constant.RECENT;
				}
				if (StringUtil.isEmpty(type)) {
					//三条曲线一起
					type = "all";
				}
				//ios与安卓区分
				if (recent.equals("0")) {
					infoJson = service.queryRecent1(user_id, type, sex, age, height, days);
				} else {
					infoJson = service.queryRecent(user_id, type, sex, age, height, days);
				}
			} else if (pageno != null) {
				infoJson = service.pageQuery(user_id, year_month, pageno, pagesize);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
	}

	/**
	 * 
	 * @Title: queryMonth 
	 * @Description: 查询一年内具有数据的月份
	 * @param user_id 
	 * @return 
	 * @throws
	 */
	@RequestMapping(value = { "/month.action" }, method = { RequestMethod.GET })
	public InfoJson queryMonth(Long user_id) {
		InfoJson infoJson = new InfoJson();
		try {
			// 根据是否传递user_id判断从患者端还是医生端传递
			user_id = commonUserService.getPatientId(user_id);
			infoJson = service.queryMonth(user_id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return infoJson;
	}
}