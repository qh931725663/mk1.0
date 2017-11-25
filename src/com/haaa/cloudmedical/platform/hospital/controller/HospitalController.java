package com.haaa.cloudmedical.platform.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.common.entity.ResponseDTO;
import com.haaa.cloudmedical.entity.Hospital;
import com.haaa.cloudmedical.platform.hospital.service.HospitalService;

@Controller
@RequestMapping("/hospital-platform")
public class HospitalController {
	
	@Autowired
	private HospitalService service;
	
	/**
	 * 医院列表分页查询
	 * @param hospital
	 * @param startDate
	 * @param endDate
	 * @param pageno
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/gridQuery.action")
	public Page gridQuery(Hospital hospital,String startDate,String endDate,Integer pageno){
		Page page = service.gridQuery(hospital, startDate, endDate, pageno);
		return page;		
	}
	
	/**
	 * 医院名称校验
	 * @param hosp_name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/validateHosp.action")
	public ResponseDTO validate(String hosp_name){
		ResponseDTO dto = service.validate(hosp_name);
		return dto;
	}
	
	/**
	 * 增加一家新的医院
	 * @param hospital
	 * @param department_name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addHospital.action")
	public ResponseDTO addHospital(Hospital hospital,String department_name){
		ResponseDTO dto = service.addHospital(hospital, department_name);
		return dto;
	}
	
	/**
	 * 获取当前医院信息
	 * @param order_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getHospitalInfo.action")
	public ResponseDTO getHospitalInfo(String order_id){
		ResponseDTO dto = service.getHospitalInfo(order_id);
		return dto;
	}
	
	/**
	 * 修改当前医院信息
	 * @param hospital
	 * @param department_name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/modifyHospital.action")
	public ResponseDTO modifyHospital(Hospital hospital,String department_name){
		ResponseDTO dto = service.modifyHospital(hospital, department_name);
		return dto;
	}
	
	/**
	 * 删除当前医院下的科室
	 * @param department_order_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteDepartment.action")
	public ResponseDTO deleteDepartment(String department_order_id){
		ResponseDTO dto = service.deleteDepartment(department_order_id);
		return dto;
	}

}
