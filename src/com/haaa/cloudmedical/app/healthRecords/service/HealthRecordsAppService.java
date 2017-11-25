package com.haaa.cloudmedical.app.healthRecords.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.ResponseDTO;
import com.haaa.cloudmedical.common.util.BeanUtil;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.dao.HealthRecordsAppDao;
import com.haaa.cloudmedical.platform.user.service.UserPlatformService;

@Service
public class HealthRecordsAppService {

	@Autowired
	private HealthRecordsAppDao dao;

	private Logger logger = Logger.getLogger(UserPlatformService.class);

	public ResponseDTO selectAllReprot(String user_id) {
		ResponseDTO dto = new ResponseDTO();
		List<Map<String, Object>> list = dao.selectAllReprot(user_id);
		dto.setData(list);
		dto.setFlag(true);
		return dto;
	}

	public ResponseDTO getReport(String order_id, String report_type) {
		String sql = "";
		ResponseDTO dto = new ResponseDTO();
		String url = BeanUtil.getProperty("dbconfig").getString("pictureuploadhttp");
		if (report_type.equals(Constant.CHECK_REPORT)) {
			sql = "select a.order_id,a.user_name,date_format(a.check_time,'%Y-%m-%d') check_time,a.hosp_name,a.report_no from p_check_report a where a.order_id = ? ";
		} else if (report_type.equals(Constant.CLINIC_REPORT)) {
			sql = "select a.order_id,date_format(a.clinic_report_time,'%Y-%m-%d') clinic_report_time,a.user_name,a.hosp_name,a.department_name,a.doctor_name,"
					+ "a.report_doctor,a.report_doctor_id,a.clinic_report_disease,a.clinic_report_result from p_clinic_report a where a.order_id = ? ";
		} else if (report_type.equals(Constant.HOSPITAL_REPORT)) {
			sql = "select a.order_id,a.patient_name,date_format(a.hospital_report_time,'%Y-%m-%d') hospital_report_time,a.hospital_report_days,a.hosp_name,"
					+ "a.department_name,a.doctor_name,a.report_doctor,a.report_doctor_id,a.hospital_report_disease,a.hospital_report_result "
					+ "from p_hospital_report a where a.order_id = ? ";
		} else if (report_type.equals(Constant.INSURANCE_REPORT)) {
			sql = "select a.order_id,(select c.user_medicare_card from n_user c where c.user_id = a.user_id) user_medicare_card,a.user_name,"
					+ "(select c.user_card from n_user c where c.user_id = a.user_id) user_card,a.hosp_name,a.medical_insurance_type,"
					+ "date_format(a.medical_insurance_time,'%Y-%m-%d') medical_insurance_time,a.medical_insurance_total,a.medical_insurance_reimbursement_amount,a.medical_insurance_subsidy_amount "
					+ "from p_medical_insurance_report a where a.order_id = ? ";
		}
		try {
			Map<String, Object> report_info = dao.select(sql, order_id).get(0);
			String parent_id = order_id;
			sql = "select b.pic_num,concat('" + url
					+ "',b.medical_picture_upload) medical_picture_upload from p_picture b where b.parent_id= ? and b.pic_type = ? order by pic_num";
			List<Map<String, Object>> pic_path_list = dao.select(sql, parent_id, report_type);
			report_info.put("pic_path_list", pic_path_list);
			dto.setData(report_info);
			dto.setFlag(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public Map<String, Object> getUser(String user_id) {
		Map<String, Object> map = dao.getUser(user_id);
		return map;
	}

	public long uploadHealthReport(Map<String, Object> report, String table) {
		long order_id = 0;
		String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		report.put("create_date", date);
		try {
			order_id = dao.insert(report, table);
		} catch (Exception e) {
			logger.error("报告上传失败", e);
			order_id = 0;
		}
		return order_id;
	}

}
