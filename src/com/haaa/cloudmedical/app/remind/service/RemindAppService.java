package com.haaa.cloudmedical.app.remind.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.ResponseDTO;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.dao.RemindManageDao;

@Service
public class RemindAppService {

	@Autowired
	private RemindManageDao dao;

	@Transactional
	public void addRemind(Map<String, Object> remind, String remind_type) {
		String table = "";
		if (remind_type.equals(Constant.remind_type_medication)) {
			table = "r_medication_remind";
		} else if (remind_type.equals(Constant.remind_type_doctor)) {
			table = "r_doctor_remind";
		} else if (remind_type.equals(Constant.remind_type_measurement)) {
			table = "r_measurement_remind";
		}
		dao.insert(remind, table);
	}

	// 删除提醒
	@Transactional
	public void deleteRemind(String order_id, String remind_type) {
		String table = "";
		if (remind_type.equals(Constant.remind_type_medication)) {
			table = "r_medication_remind";
		} else if (remind_type.equals(Constant.remind_type_doctor)) {
			table = "r_doctor_remind";
		} else if (remind_type.equals(Constant.remind_type_measurement)) {
			table = "r_measurement_remind";
		}
		dao.delete(table, order_id);

	}

	// 修改提醒
	@Transactional
	public void updateRemind(Map<String, Object> remind, String remind_type) {
		String table = "";
		if (remind_type.equals(Constant.remind_type_medication)) {
			table = "r_medication_remind";
			Map<String,Object> remindInfo = dao.select("select * from r_medication_remind where order_id = ? ", remind.get("order_id")).get(0);
			remind.put("user_id", remindInfo.get("user_id"));
			remind.put("chronic_code", remindInfo.get("chronic_code"));
			remind.put("chronic_name", remindInfo.get("chronic_name"));
		} else if (remind_type.equals(Constant.remind_type_doctor)) {
			table = "r_doctor_remind";
			Object patient_id = dao.select("select * from r_doctor_remind where order_id = ? ", remind.get("order_id")).get(0).get("patient_id");
			remind.put("patient_id", patient_id);
		} else if (remind_type.equals(Constant.remind_type_measurement)) {
			table = "r_measurement_remind";
			Map<String,Object> remindInfo = dao.select("select * from r_measurement_remind where order_id = ?",remind.get("order_id")).get(0);
			remind.put("user_id", remindInfo.get("user_id"));
			remind.put("measurement_remind_title", remindInfo.get("measurement_remind_title"));
			remind.put("measrtrment_remind_type", remindInfo.get("measurement_remind_title"));
			remind.put("measurement_remind_content", remindInfo.get("measurement_remind_content"));
		}
		dao.delete(table, remind.get("order_id").toString());
		String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		remind.put("create_date", date);
		dao.insert(remind, table);
	}

	public ResponseDTO getRemind(String user_id) {

		Object measurement = getMeasurementRemind(user_id).getData();// 测量提醒
		Object medication = getMedicationRemind(user_id).getData();// 用药提醒
		Object doctor = getDoctorRemind(user_id).getData();// 就诊提醒

		Map<String, Object> remind = new HashMap<String, Object>();
		remind.put("measurement", measurement);
		remind.put("medication", medication);
		remind.put("doctor", doctor);

		ResponseDTO dto = new ResponseDTO();
		dto.setData(remind);
		dto.setFlag(true);

		return dto;
	}

	// 获取用药提醒
	public ResponseDTO getMedicationRemind(String user_id) {
		String sql = "select order_id,findname(chronic_code) chronic_name,generic_drug_name,drug_specification,"
				+ "date_format(medication_remind_time_one,'%H:%i') medication_remind_time_one,"
				+ "date_format(medication_remind_time_two,'%H:%i') medication_remind_time_two,"
				+ "date_format(medication_remind_time_three,'%H:%i') medication_remind_time_three,"
				+ "date_format(medication_remind_time_four,'%H:%i') medication_remind_time_four,"
				+ "findname(drug_note) drug_note,medication_remind_num,medication_remind_dosage "
				+ "from r_medication_remind where user_id = ? ";
		List<Map<String, Object>> list = dao.select(sql, user_id);
		list.forEach(map -> map.forEach((k, v) -> map.putIfAbsent(k, "")));
		Map<String, List<Map<String, Object>>> listByChronicName = list.stream()
				.collect(Collectors.groupingBy(m -> m.get("chronic_name").toString()));
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Map.Entry<String, List<Map<String, Object>>> entry : listByChronicName.entrySet()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("chronic_name", entry.getKey());
			map.put("meical_list", entry.getValue());
			result.add(map);
		}
		ResponseDTO dto = new ResponseDTO();
		dto.setFlag(true);
		dto.setData(result);
		return dto;
	}

	// 获取就诊提醒
	public ResponseDTO getDoctorRemind(String user_id) {
		String sql = "select a.order_id,a.doctor_remind_date,date_format(a.doctor_remind_time,'%H:%i') doctor_remind_time,"
				+ "a.hosp_order_id,c.hosp_name doctor_remind_hospital,a.department_id,b.department_name doctor_remind_department "
				+ "from r_doctor_remind a,k_department b,k_hosp c "
				+ "where 1=1 and a.hosp_order_id=c.order_id and a.department_id=b.order_id and a.patient_id = ? ";
		List<Map<String, Object>> list = dao.select(sql, user_id);
		list.forEach(map -> map.forEach((k, v) -> map.putIfAbsent(k, "")));
		ResponseDTO dto = new ResponseDTO();
		dto.setFlag(true);
		dto.setData(list);
		return dto;
	}

	// 获取测量提醒
	public ResponseDTO getMeasurementRemind(String user_id) {
		String sql = "select a.order_id,findname(a.measurement_remind_title) measurement_remind_title,a.measurement_remind_content,"
				+ "if(a.monday=1,'星期一','') monday,if(a.tuesday=2,'星期二','') tuesday,if(a.wednesday=3,'星期三','') wednesday,"
				+ "if(a.thursday=4,'星期四','') thursday,if(a.friday=5,'星期五','') friday,if(a.saturday=6,'星期六','') saturday,"
				+ "if(a.sunday=0,'星期日','') sunday,a.interval_day,date_format(a.time1,'%H:%i') time1,"
				+ "date_format(a.time2,'%H:%i') time2,date_format(a.time3,'%H:%i') time3,date_format(a.time4,'%H:%i') time4,a.begin_day "
				+ "from r_measurement_remind a where a.user_id = ? ";
		List<Map<String, Object>> list = dao.select(sql, user_id);
		list.forEach(map -> map.forEach((k, v) -> map.putIfAbsent(k, "")));
		ResponseDTO dto = new ResponseDTO();
		dto.setFlag(true);
		dto.setData(list);
		return dto;
	}
}
