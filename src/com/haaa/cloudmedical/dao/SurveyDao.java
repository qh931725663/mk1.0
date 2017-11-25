package com.haaa.cloudmedical.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Repository;

import com.haaa.cloudmedical.common.dao.BaseTemplateDao;
import com.haaa.cloudmedical.common.util.LogPrinter;

@Repository
public class SurveyDao extends BaseTemplateDao {

	/*public <T> T select(String sql, Object[] args, Class<T> cla) {
		T value = this.jdbcTemplate.queryForObject(sql, args, cla);
		return value;
	}*/

	public List<Map<String, Object>> getTimeList(String user_id) {
		String sql = "select a.order_id, date_format(a.create_date,'%Y-%m-%d') date from s_survey a where user_id = ? order by a.create_date desc";
		return jdbcTemplate.queryForList(sql, user_id);
	}

	public List<Map<String, Object>> getDetail(String order_id) {
		String sql = "select a.survey_option_order_id,a.survey_option_content,a.survey_answer_text,b.question_k_order_id,b.survey_question_content from s_survey_answer a, s_survey_question b where b.order_id = a.survey_question_order_id and a.survey_order_id = ?";
		return jdbcTemplate.queryForList(sql, order_id);
	}

	public Map<String, Object> getBasicInfo(String order_id) {
		String sql = "select a.*, date_format(a.create_date,'%Y-%m-%d') date,b.user_name,b.user_birthday,findName(b.user_sex) user_sex,b.user_phone,b.user_card,b.passport,b.mainland from s_survey a,n_user b where a.user_id=b.user_id and a.order_id = ?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, order_id);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	
	public Map<String, Object> getBasicInfo2(String order_id) {
		String sql = "select a.*, date_format(a.create_date,'%Y-%m-%d') date,b.user_name,b.user_birthday,b.user_sex,b.user_phone,b.user_card,b.passport,b.mainland from s_survey a,n_user b where a.user_id=b.user_id and a.order_id = ?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, order_id);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	
	public Map<String, Object> getAhdiBasicInfo(String order_id) {
		String sql = "select a.*, date_format(a.create_date,'%Y-%m-%d') date,b.user_name,b.user_birthday,b.user_sex,b.user_phone,b.email,b.user_card,b.passport,b.mainland from s_survey a,n_user b where a.user_id=b.user_id and a.order_id = ?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, order_id);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public void call(String order_id) {
		LogPrinter.info("开始执行存储过程");
		Connection conn = null;
		try {
			conn = this.jdbcTemplate.getDataSource().getConnection();
			CallableStatement cs = conn.prepareCall("{call calculate_ahdi_value(?)}");
			cs.setString(1, order_id);
			cs.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		LogPrinter.info("结束执行存储过程");
	}
	
	public Map<String,Object> getPatientInfo(String user_id){
		String sql = "select a.user_id,a.user_name,a.user_card,a.user_birthday,a.user_sex,a.user_age,a.user_phone,a.email,a.user_address,a.user_height,a.user_weight,a.user_waist,b.post_code,b.order_id from n_user a,s_survey b where a.user_id = b.user_id and a.user_id = ? order by b.update_date desc limit 0,1";
		List<Map<String,Object>> list = this.select(sql, new Object[]{user_id});
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public List<Map<String,Object>> getSurveyInfo(String order_id){
		String sql ="select a.survey_option_order_id,a.survey_option_content,a.survey_answer_text,b.question_k_order_id,b.survey_question_content,"
				+ "c.parent_id from	s_survey_answer a,s_survey_question b,s_survey_question_k c "
				+ "where b.order_id = a.survey_question_order_id and b.question_k_order_id = c.order_id and a.survey_order_id = ?";
		return this.select(sql, new Object[]{order_id});
	}
	/**
	 * @description 当风险评估表填写完成时候修改report表
	 * @param user_id
	 */
	public void updateReport(String user_id) {
				String sql = "update p_plan_report r set r.real_num = real_num+1 where r.user_id = ? AND r.plan_name='疾病（并发症）风险评估' AND r.create_date>DATE_SUB(CURDATE(), INTERVAL 1 YEAR)";
		jdbcTemplate.update(sql, user_id);
	}
}
