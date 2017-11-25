package com.haaa.cloudmedical.wechat.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.haaa.cloudmedical.entity.CheckReport;
import com.haaa.cloudmedical.entity.ClinicReport;
import com.haaa.cloudmedical.entity.HospitalReport;
import com.haaa.cloudmedical.entity.InsuranceReport;

public interface IWxHealthRecordService {

    Long saveCheck(CheckReport model);

    Map<String, Object> getUserByOpenId(String openid);

    Long saveHospital(HospitalReport model);

    Long saveExamine(ClinicReport model);

    Long saveMedical(InsuranceReport model);

    List<Map<String, Object>> getHospitalList();

    void savePicture(HttpServletRequest request, String path);

    List<Map<String, Object>> getDepartmentList(String hosp_order_id);

    List<Map<String, Object>> getDoctorList(String department_order_id);

    Map<String, Object> getCheckById(String order_id);

    Map<String, Object> getExamineById(String order_id);

    Map<String, Object> getHospitalById(String order_id);

    Map<String, Object> getMedicalById(String order_id);

    List<Map<String, Object>> queryList(String user_id);

}
