package com.haaa.cloudmedical.common.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.entity.ResponseDTO;
import com.haaa.cloudmedical.common.service.PositionService;

@RestController
@RequestMapping("/constant")
public class ConstantController {

	@Resource
	private PositionService positionService;

	private Logger logger = Logger.getLogger(ConstantController.class);

	public InfoJson positionInit() {
		try {
			positionService.positionInfoInit();
		} catch (Exception e) {
			return InfoJson.setFaile();
		}
		return InfoJson.setSucc(1);
	}

	@RequestMapping("/position.action")
	public InfoJson getLocationInto(String type, String parent_id) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = positionService.getPositionList(type, parent_id);
		} catch (Exception e) {
			logger.error("", e);
		}
		return infoJson;
	}

	@RequestMapping("/hospitals.action")
	public InfoJson getHospitals(String condition) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = positionService.getHospitals(condition);
		} catch (Exception e) {
			logger.error("", e);
		}
		return infoJson;
	}

	@RequestMapping("/doctors.action")
	public InfoJson getDoctors(String condition) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = positionService.getDoctors(condition);
		} catch (Exception e) {
			logger.error("", e);
		}
		return infoJson;
	}

	@RequestMapping("/doctorId.action")
	public InfoJson getDoctorById(Long doctor_id) {
		InfoJson infoJson = new InfoJson();
		try {
			if (doctor_id == null) {
			} else {
				infoJson = positionService.getDoctorById(doctor_id);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return infoJson;
	}

	@RequestMapping("/hospitalsByArea.action")
	public InfoJson getHospitalsByArea(String name) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = positionService.getHospitalsByArea(name);
		} catch (Exception e) {
			logger.error("", e);
		}
		return infoJson;
	}

	@RequestMapping("/getProvinceList.action")
	@ResponseBody
	public ResponseDTO getProvinceList() {
		List<Map<String,Object>> list = positionService.getProvinceList();
		ResponseDTO dto = new ResponseDTO();
		dto.setData(list);
		dto.setFlag(true);
		return dto;
	}

	@RequestMapping("/getPrefectureCity.action")
	@ResponseBody
	public ResponseDTO getPrefectureCity(String province_id) {
		if(province_id==null||province_id.length()==0){
			province_id="330000";
		}
		List<Map<String,Object>> list = positionService.getPrefectureCity(province_id);
		ResponseDTO dto = new ResponseDTO();
		dto.setData(list);
		dto.setFlag(true);
		return dto;
	}

	@RequestMapping("/getCountyCity.action")
	@ResponseBody
	public ResponseDTO getCountyCity(String prefecture_city_id) {
		if(prefecture_city_id==null||prefecture_city_id.length()==0){
			prefecture_city_id="330200";
		}
		List<Map<String,Object>> list = positionService.getCountyCity(prefecture_city_id);
		ResponseDTO dto = new ResponseDTO();
		dto.setData(list);
		dto.setFlag(true);
		return dto;
	}

	@RequestMapping("/getHospitalList.action")
	@ResponseBody
	public ResponseDTO getHospitalList(String county_city_id) {
		if(county_city_id==null||county_city_id.length()==0){
			county_city_id="330226";
		}
		List<Map<String,Object>> list = positionService.getHospitalList(county_city_id);
		ResponseDTO dto = new ResponseDTO();
		dto.setData(list);
		dto.setFlag(true);
		return dto;
	}

	@RequestMapping("/getDepartmentList.action")
	@ResponseBody
	public ResponseDTO getDepartmentList(String hosp_order_id) {
		List<Map<String,Object>> list = positionService.getDepartmentList(hosp_order_id);
		ResponseDTO dto = new ResponseDTO();
		dto.setData(list);
		dto.setFlag(true);
		return dto;
	}

	@RequestMapping("/getDoctorList.action")
	@ResponseBody
	public ResponseDTO getDoctorList(String department_order_id) {
		List<Map<String,Object>> list = positionService.getDoctorList(department_order_id);
		ResponseDTO dto = new ResponseDTO();
		dto.setData(list);
		dto.setFlag(true);
		return dto;
	}
}
