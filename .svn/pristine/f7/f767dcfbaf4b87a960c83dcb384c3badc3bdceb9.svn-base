package com.haaa.cloudmedical.app.equipment.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.app.equipment.service.UrineTestService;
import com.haaa.cloudmedical.app.util.CommonUserService;
import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.entity.EquipmentUse;
import com.haaa.cloudmedical.entity.UrineTest;

@RestController
@RequestMapping({ "/urine_test" })
public class UrineTestController {
	@Resource
	private UrineTestService service;
	@Resource
	private CommonUserService commonUserService;
	
	private Logger logger = Logger.getLogger(UrineTestController.class);

	@RequestMapping(value = { "/add.action" }, method = { RequestMethod.POST })
	public InfoJson add(EquipmentUse equipmentUse, UrineTest urineTest, HttpServletRequest request) {
		InfoJson infoJson = new InfoJson();
		try {
			//获取用户以及医生的id(从缓存中获取)
			equipmentUse.setUser_id(commonUserService.getPatientId(equipmentUse.getUser_id()));
			equipmentUse.setDoctor_id(commonUserService.getDoctorId());
			if (urineTest.getBil() != null || urineTest.getBld() != null || urineTest.getGlu() != null
					|| urineTest.getKet() != null || urineTest.getNit() != null || urineTest.getPh() != null
					|| urineTest.getPro() != null || urineTest.getSg() != null || urineTest.getUro() != null
					|| urineTest.getVc() != null) {
				infoJson = service.add(equipmentUse, urineTest);
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
	 * @Title: query @Description: 数据查询 @param user_id @param recent
	 * 不为null时表示，曲线图查询 @param year_month @param pageno @param pagesize @param
	 * request @return @throws
	 */
	@RequestMapping(value = { "/query.action" }, method = { RequestMethod.GET })
	public InfoJson query(Long user_id, String recent, String type, Integer pageno, Integer pagesize, Integer days,
			HttpServletRequest request) {
		InfoJson infoJson = new InfoJson();
		try {
			//根据是否传递user_id判断从患者端还是医生端传递
			user_id = commonUserService.getPatientId(user_id);
			//默认数据DEFAULT_PAGESIZE = 15
			if (pagesize == null) {
				pagesize = 1;
			}
			//默认查询最近 RECENT = 30
			if (recent != null) {
				if (days == null) {
					days = Constant.RECENT;
				}
				infoJson = service.queryRecent(user_id, type, days);
			} else if (pageno != null) {
				infoJson = service.pageQuery(user_id, pageno, pagesize);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return infoJson;
	}

}
