package com.haaa.cloudmedical.wechat.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.annotation.Log;
import com.haaa.cloudmedical.common.util.BeanUtil;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.common.util.StringUtil;
import com.haaa.cloudmedical.entity.CheckReport;
import com.haaa.cloudmedical.entity.ClinicReport;
import com.haaa.cloudmedical.entity.HospitalReport;
import com.haaa.cloudmedical.entity.InsuranceReport;
import com.haaa.cloudmedical.wechat.dao.WxHealthRecordDao;
import com.haaa.cloudmedical.wechat.service.IWxHealthRecordService;

@Service
public class WxHealthRecordServiceImpl implements IWxHealthRecordService {

    @Autowired
    WxHealthRecordDao dao;

    @Override
    public Long saveCheck(CheckReport model) {
        String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        model.setCreate_date(date);
        return dao.insert(model, "p_check_report");
    }

    @Override
    public Map<String, Object> getUserByOpenId(String openid) {
        if (StringUtil.isNotBlank(openid)) {
            String sql = "select user_id, user_name, user_card from n_user where open_id = '" + openid + "'";
            List<Map<String, Object>> list = dao.select(sql);
            if (list.size() > 0) {
                return list.get(0);
            }
            return null;
        }
        return null;
    }

    @Override
    public Long saveHospital(HospitalReport model) {
        String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        model.setCreate_date(date);
        return dao.insert(model, "p_hospital_report");
    }

    @Override
    public Long saveExamine(ClinicReport model) {
        String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        model.setCreate_date(date);
        return dao.insert(model, "p_clinic_report");
    }

    @Override
    public Long saveMedical(InsuranceReport model) {
        String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        model.setCreate_date(date);
        return dao.insert(model, "p_medical_insurance_report");
    }

    @Override
    public List<Map<String, Object>> getHospitalList() {
        String sql = "select order_id ,hosp_name from k_hosp";
        return dao.select(sql);
    }

    @Override
    public void savePicture(HttpServletRequest request, String path) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pic_type", request.getParameter("report_type"));
        map.put("parent_id", request.getParameter("order_id"));
        map.put("pic_num", 1);
        map.put("medical_picture_upload", path);
        String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        map.put("create_date", date);
        dao.insert(map, "p_picture");
    }

    @Override
    public List<Map<String, Object>> getDepartmentList(String hosp_order_id) {
        String sql = "select order_id ,department_name  from k_department where hosp_order_id = " + hosp_order_id;
        return dao.select(sql);
    }

    @Override
    public List<Map<String, Object>> getDoctorList(String department_order_id) {
        String sql = "select doctor_id ,doctor_name  from d_doctor where department_order_id = " + department_order_id;
        return dao.select(sql);
    }

    @Override
    public Map<String, Object> getCheckById(String order_id) {
        if (StringUtil.isNotBlank(order_id)) {
            String sql = "select user_name,date_format(check_time,'%Y-%m-%d') check_time,hosp_name,report_no from p_check_report where order_id="
                         + order_id;
            List<Map<String, Object>> list = dao.select(sql);

            Map<String, Object> data = null;
            if (list.size() > 0) {
                data = list.get(0);
                this.queryPicList("500001", order_id, data);
                return data;
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> getExamineById(String order_id) {
        if (StringUtil.isNotBlank(order_id)) {
            String sql = "select user_name,date_format(clinic_report_time,'%Y-%m-%d') clinic_report_time,hosp_name,department_name,"
                         + "report_doctor,clinic_report_disease,clinic_report_result from p_clinic_report where order_id=" + order_id;
            List<Map<String, Object>> list = dao.select(sql);

            Map<String, Object> data = null;
            if (list.size() > 0) {
                data = list.get(0);
                this.queryPicList("500002", order_id, data);
                return data;
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> getHospitalById(String order_id) {
        if (StringUtil.isNotBlank(order_id)) {
            String sql = "select patient_name,date_format(hospital_report_time,'%Y-%m-%d') hospital_report_time,hospital_report_days,hosp_name,"
                         + "department_name,report_doctor,hospital_report_disease,hospital_report_result from p_hospital_report where order_id="
                         + order_id;
            List<Map<String, Object>> list = dao.select(sql);

            Map<String, Object> data = null;
            if (list.size() > 0) {
                data = list.get(0);
                this.queryPicList("500003", order_id, data);
                return data;
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> getMedicalById(String order_id) {
        if (StringUtil.isNotBlank(order_id)) {
            String sql = "select a.user_name,b.user_card,report_no,date_format(medical_insurance_time,'%Y-%m-%d') medical_insurance_time,hosp_name,"
                         + "medical_insurance_type,medical_insurance_total,medical_insurance_reimbursement_amount,medical_insurance_subsidy_amount "
                         + "from p_medical_insurance_report a left join n_user b on a.user_id = b.user_id where order_id=" + order_id;
            List<Map<String, Object>> list = dao.select(sql);

            Map<String, Object> data = null;
            if (list.size() > 0) {
                data = list.get(0);
                this.queryPicList("500004", order_id, data);
                return data;
            }
        }
        return null;
    }

    private void queryPicList(String type, String order_id, Map<String, Object> data) {
        String uploadUrl = BeanUtil.getProperty("dbconfig").getString("pictureuploadhttp");
        String pic_sql = "select parent_id,concat('" + uploadUrl + "',medical_picture_upload) medical_picture_upload from p_picture where "
                         + "pic_type =" + type + " and parent_id =" + order_id;
        List<Map<String, Object>> piclist = dao.select(pic_sql);
        data.put("piclist", piclist);
    }

    @Override
    public List<Map<String, Object>> queryList(String openid) {
        Map<String, Object> user = this.getUserByOpenId(openid);
        if (null != user) {
            String user_id = user.get("user_id").toString();
            //
            String sql = "select * from ("
                         + "select order_id,user_name,date_format(check_time,'%Y-%m-%d') time,hosp_name,date_format(create_date,'%Y-%m-%d %H:%m:%s') create_date,500001 as type from p_check_report where user_id ="
                         + user_id + " union all "
                         + "select order_id,user_name,date_format(clinic_report_time,'%Y-%m-%d') time,hosp_name,date_format(create_date,'%Y-%m-%d %H:%m:%s') create_date,500002 as type from p_clinic_report where user_id ="
                         + user_id + " union all "
                         + "select order_id,patient_name user_name,date_format(hospital_report_time,'%Y-%m-%d') time,hosp_name,date_format(create_date,'%Y-%m-%d %H:%m:%s') create_date,500003 as type from p_hospital_report where patient_id ="
                         + user_id + " union all "
                         + "select order_id,user_name,date_format(medical_insurance_time,'%Y-%m-%d') time,hosp_name,date_format(create_date,'%Y-%m-%d %H:%m:%s') create_date,500004 as type from p_medical_insurance_report where user_id ="
                         + user_id + ") a order by a.create_date desc";
            return dao.select(sql);
        }
        return null;
    }

}
