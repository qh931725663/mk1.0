package com.haaa.cloudmedical.platform.healthRecords.service.impl;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haaa.cloudmedical.app.user.service.DoctorAppService;
import com.haaa.cloudmedical.common.annotation.Log;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.common.util.IdCard;
import com.haaa.cloudmedical.common.util.StringUtil;
import com.haaa.cloudmedical.dao.ChronicPlanDao;
import com.haaa.cloudmedical.dao.ChronicRecordsDao;
import com.haaa.cloudmedical.entity.Hypertension;
import com.haaa.cloudmedical.entity.HypertensionAnswer;
import com.haaa.cloudmedical.entity.HypertensionQuestion;
import com.haaa.cloudmedical.platform.healthRecords.service.ChronicRecordsService;
import com.haaa.cloudmedical.platform.plan.model.PlanVo;
@Service
@Log(name = "慢病档案管理")
public class ChronicRecordsImpl implements ChronicRecordsService{
	@Autowired
    private ChronicPlanDao planDao;
	@Autowired
    private ChronicRecordsDao dao;
	
	@Override
	public Page getPage(PlanVo model) {
		  String temp = null;
	        List<Object> values = new ArrayList<Object>();
	        StringBuilder sql = new StringBuilder("select user_id,user_name,user_card,user_phone,c.doctor_name,getahdi(a.ahdi_value) ahdi_value"
	                                              + ",chronic,findname(a.user_sex) user_sex,user_age,findname(a.user_marriage) user_marriage"
	                                              + ",findname(a.user_work) user_work,date_format(a.update_date,'%Y-%m-%d %H:%i') update_date"
	                                              + " from n_user a left join d_patient b on a.user_id = b.patient_id left join d_doctor c on "
	                                              + "b.doctor_id = c.doctor_id where a.chronic != '0'");
	        // 姓名
	        temp = model.getUser_name();
	        if (temp != null && !"".equals(temp)) {
	            values.add("%" + temp + "%");
	            sql.append(" and a.user_name like ?");
	        }
	        // 身份证号码
	        temp = model.getUser_card();
	        if (temp != null && !"".equals(temp)) {
	            values.add("%" + temp + "%");
	            sql.append(" and a.user_card like ?");
	        }
	        // 性别
	        temp = model.getUser_sex();
	        if (temp != null && !"".equals(temp)) {
	            values.add(temp);
	            sql.append(" and a.user_sex = ?");
	        }
	        // 慢病类型
	        temp = model.getChronic();
	        if (StringUtils.isNotBlank(temp)) {
	            values.add(temp);
	            sql.append(" and FIND_IN_SET(?, chronic)");
	        }
	        // 工作强度
	        temp = model.getUser_work();
	        if (StringUtils.isNotBlank(temp)) {
	            values.add(temp);
	            sql.append(" and a.user_work = ?");
	        }
	        // 家庭医生
	        temp = model.getDoctor_name();
	        if (StringUtils.isNotBlank(temp)) {
	            values.add("%" + temp + "%");
	            sql.append(" and c.doctor_name like ?");
	        }
	        // 更新时间
	        if (StringUtil.isBlank(model.getDatemin()) && StringUtil.isNotBlank(model.getDatemax())) {
	            values.add(model.getDatemax());
	            sql.append(" and a.update_date <= ?");
	        } else if (StringUtil.isNotBlank(model.getDatemin()) && StringUtil.isBlank(model.getDatemax())) {
	            values.add(model.getDatemin());
	            sql.append(" and a.update_date >= ?");
	        } else if (StringUtil.isNotBlank(model.getDatemin()) && StringUtil.isNotBlank(model.getDatemax())
	                   && !model.getDatemin().equals(model.getDatemax())) {
	            values.add(model.getDatemin());
	            values.add(model.getDatemax());
	            sql.append(" and a.update_date >= ? and a.update_date <= ?");
	        } else if (StringUtil.isNotBlank(model.getDatemin()) && model.getDatemin().equals(model.getDatemax())) {
	            values.add(model.getDatemin());
	            sql.append(" and date_format(a.update_date,'%Y-%m-%d') = ?");
	        }
	        // 查询分页
	        Page page = planDao.pageQuery(sql.toString(), values.toArray(), model.getPageno());

	        List<Map<String, Object>> data = (List<Map<String, Object>>) page.getData();
	        if (data != null && data.size() > 0) {
	            data = data.stream().map(x -> {
	                String chronic = x.get("chronic").toString();
	                StringBuffer bf = new StringBuffer("");
	                Arrays.asList(chronic.split(",")).stream().forEach(i -> {
	                    if (!"0".equals(i)) {
	                        String chr_sql = "select survey_question_content from s_survey_question_k where order_id = " + i;
	                        List<Map<String, Object>> list = planDao.select(chr_sql);
	                        String name = list.get(0).get("survey_question_content").toString();
	                        bf.append(name).append(",");
	                    }
	                });
	                String chronic_name = bf.substring(0, bf.lastIndexOf(","));
	                x.put("chronic_name", chronic_name);
	                return x;
	            }).collect(Collectors.toList());
	        }
	        return page;
	}
///////////////////////////////高血压慢病档案CRUD开始////////////////////////////////////////////////////////////
	
///////////////////////////////高血压慢病档案CRUD结束////////////////////////////////////////////////////////////

}
