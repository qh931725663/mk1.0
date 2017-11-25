package com.haaa.cloudmedical.app.remind.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.app.remind.service.RemindAppService;
import com.haaa.cloudmedical.app.util.CommonUserService;
import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.RemindMeasurement;
import com.haaa.cloudmedical.common.util.BeanUtil;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.entity.DoctorRemind;
import com.haaa.cloudmedical.entity.MeasurementRemind;
import com.haaa.cloudmedical.entity.MedicationRemind;

@RestController
@RequestMapping("remind-app")
public class RemindAppcontroller {

	@Autowired
	private RemindAppService service;
	
	@Autowired
	private CommonUserService common;

	/**
	 * 增加测量提醒
	 * 
	 * @return
	 */
	@RequestMapping("/addMeasurementRemind.action")
	@ResponseBody
	public Object addMeasurementRemind(MeasurementRemind remind) {
		String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		remind.setCreate_date(date);

		if (remind.getMeasurement_remind_title().equals(RemindMeasurement.bloodpressure.getValue()))
			remind.setMeasurement_remind_content(RemindMeasurement.bloodpressure.getMessage());

		if (remind.getMeasurement_remind_title().equals(RemindMeasurement.bloodsugar.getValue()))
			remind.setMeasurement_remind_content(RemindMeasurement.bloodsugar.getMessage());

		if (remind.getMeasurement_remind_title().equals(RemindMeasurement.heartrate.getValue()))
			remind.setMeasurement_remind_content(RemindMeasurement.heartrate.getMessage());

		if (remind.getMeasurement_remind_title().equals(RemindMeasurement.oxygen.getValue()))
			remind.setMeasurement_remind_content(RemindMeasurement.oxygen.getMessage());

		if (remind.getMeasurement_remind_title().equals(RemindMeasurement.temperature.getValue()))
			remind.setMeasurement_remind_content(RemindMeasurement.temperature.getMessage());

		if (remind.getMeasurement_remind_title().equals(RemindMeasurement.urine.getValue()))
			remind.setMeasurement_remind_content(RemindMeasurement.urine.getMessage());

		if (remind.getMeasurement_remind_title().equals(RemindMeasurement.vitalcapacity.getValue()))
			remind.setMeasurement_remind_content(RemindMeasurement.vitalcapacity.getMessage());

		if (remind.getMeasurement_remind_title().equals(RemindMeasurement.weight.getValue()))
			remind.setMeasurement_remind_content(RemindMeasurement.weight.getMessage());
		Map<String, Boolean> response = new HashMap<>();
		try {
			remind.setMeasrtrment_remind_type(remind.getMeasurement_remind_title());
			service.addRemind(BeanUtil.toMapFromObject(remind), Constant.remind_type_measurement);
			response.put("flag", true);
		} catch (Exception e) {
			response.put("flag", false);
		}
		return response;
	}

	/**
	 * 增加用药提醒
	 * 
	 * @return
	 */
	@RequestMapping("/addMedicationRemind.action")
	@ResponseBody
	public Object addMedicationRemind(MedicationRemind remind) {
		String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		remind.setCreate_date(date);
		Map<String, Boolean> response = new HashMap<>();
		try {
			service.addRemind(BeanUtil.toMapFromObject(remind), Constant.remind_type_medication);
			response.put("flag", true);
		} catch (Exception e) {
			response.put("flag", false);
		}
		return response;
	}

	/**
	 * 增加就诊提醒
	 * 
	 * @return
	 */
	@RequestMapping("/addDoctorRemind.action")
	@ResponseBody
	public Object addDoctorRemind(DoctorRemind remind) {
		String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		remind.setCreate_date(date);
		Map<String, Boolean> response = new HashMap<>();
		try {
			Map<String, Object> user_map = common.getUserMap();
			String doctor_id = user_map.get("user_id").toString();
			remind.setDoctor_id(doctor_id);
			service.addRemind(BeanUtil.toMapFromObject(remind), Constant.remind_type_doctor);
			response.put("flag", true);
		} catch (Exception e) {
			response.put("flag", false);
		}
		return response;
	}

	/**
	 * 删除测量提醒功能
	 * 
	 * @return
	 */
	@RequestMapping("/deleteMeasurementRemind.action")
	@ResponseBody
	public Object deleteMeasurementRemind(String order_id) {
		Map<String, Boolean> response = new HashMap<>();
		try {
			service.deleteRemind(order_id, Constant.remind_type_measurement);
			response.put("flag", true);
		} catch (Exception e) {
			response.put("flag", false);
		}
		return response;
	}

	/**
	 * 删除用药提醒功能
	 * 
	 * @return
	 */
	@RequestMapping("/deleteMedicationRemind.action")
	@ResponseBody
	public Object deleteMedicationRemind(String order_id) {
		Map<String, Boolean> response = new HashMap<>();
		try {
			service.deleteRemind(order_id, Constant.remind_type_medication);
			response.put("flag", true);
		} catch (Exception e) {
			response.put("flag", false);
		}
		return response;
	}

	/**
	 * 删除就诊提醒功能
	 * 
	 * @return
	 */
	@RequestMapping("/deleteDoctorRemind.action")
	@ResponseBody
	public Object deleteDoctorRemind(String order_id) {
		Map<String, Boolean> response = new HashMap<>();
		try {
			service.deleteRemind(order_id, Constant.remind_type_doctor);
			response.put("flag", true);
		} catch (Exception e) {
			response.put("flag", false);
		}
		return response;
	}

	/**
	 * 修改就诊提醒功能
	 * 
	 * @return
	 */
	@RequestMapping("/updateDoctorRemind.action")
	@ResponseBody
	public Object updateDoctorRemind(DoctorRemind remind) {
		Map<String, Boolean> response = new HashMap<>();
		try {
			Map<String,Object> user_map = common.getUserMap();
			String doctor_id = user_map.get("user_id").toString();
			remind.setDoctor_id(doctor_id);
			service.updateRemind(BeanUtil.toMapFromObject(remind), Constant.remind_type_doctor);
			response.put("flag", true);
		} catch (Exception e) {
			response.put("flag", false);
		}
		return response;
	}

	/**
	 * 修改测量提醒功能
	 * 
	 * @return
	 */
	@RequestMapping("/updateMeasurementRemind.action")
	@ResponseBody
	public Object updateMeasurementRemind(MeasurementRemind remind) {		
		Map<String, Boolean> response = new HashMap<>();
		try {
			service.updateRemind(BeanUtil.toMapFromObject(remind), Constant.remind_type_measurement);
			response.put("flag", true);
		} catch (Exception e) {
			response.put("flag", false);
		}
		return response;
	}

	/**
	 * 修改用药提醒功能
	 * 
	 * @return
	 */
	@RequestMapping("/updateMedicationRemind.action")
	@ResponseBody
	public Object updateMedicationRemind(MedicationRemind remind) {
		Map<String, Boolean> response = new HashMap<>();
		try {
			service.updateRemind(BeanUtil.toMapFromObject(remind), Constant.remind_type_medication);
			response.put("flag", true);
		} catch (Exception e) {
			response.put("flag", false);
		}
		return response;
	}

}
