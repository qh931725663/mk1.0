package com.haaa.cloudmedical.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.haaa.cloudmedical.common.dao.BaseTemplateDao;
import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.util.BeanUtil;
import com.haaa.cloudmedical.common.util.IdCard;
import com.haaa.cloudmedical.entity.Patient;
import com.haaa.cloudmedical.entity.User;

@Repository
public class UserAppDao extends BaseTemplateDao {

	public int countByAccount(String account) {
		Object[] args = new Object[] { account,account,account };
		String sql = "select count1,count2,count3 from ("
				+ "(select count(*) count1 from n_user where user_phone = ? ) t1,"
				+ "(select count(*) count2 from n_user where user_card  = ? ) t2,"
				+ "(select count(*) count3 from n_user where passport   = ? ) t3)";
		Map<String,Object> value = this.get(sql, args);
		int count1 = Integer.parseInt(value.get("count1").toString());
		int count2 = Integer.parseInt(value.get("count2").toString());
		int count3 = Integer.parseInt(value.get("count3").toString());
		int count = count1==1?count1:count2==1?count2:count3==1?count3:0;
		return count;
	}

	public int countByAccount(String account, String user_pwd) {
		Object[] arg = new Object[] { account, user_pwd, Constant.USER_TYPE_PATIENT };
		Object[] args = Stream.of(arg,arg,arg).flatMap(u->Stream.of(u)).toArray();
		String sql = "select count1,count2,count3 from ("
				+ "(select count(*) count1 from n_user where user_phone = ? and user_pwd = ? and user_type = ? ) t1,"
				+ "(select count(*) count2 from n_user where user_card  = ? and user_pwd = ? and user_type = ? ) t2,"
				+ "(select count(*) count3 from n_user where passport   = ? and user_pwd = ? and user_type = ? ) t3)";
		Map<String,Object> value = this.get(sql, args);
		int count1 = Integer.parseInt(value.get("count1").toString());
		int count2 = Integer.parseInt(value.get("count2").toString());
		int count3 = Integer.parseInt(value.get("count3").toString());
		int count = count1==1?count1:count2==1?count2:count3==1?count3:0;
		return count;
	}

	// 获取患者ID
	public String getPatientId(String account) {
		Object[] arg = { account, Constant.USER_TYPE_PATIENT };
		Object[] args = Stream.of(arg,arg,arg).flatMap(u->Stream.of(u)).toArray();
		String sql = "select * from ("
				+ "(select if(count(*)=0,null,(select user_id from n_user where user_phone = ? and user_type = ? )) user_id_1 ) t1,"
				+ "(select if(count(*)=0,null,(select user_id from n_user where user_card  = ? and user_type = ? )) user_id_2 ) t2,"
				+ "(select if(count(*)=0,null,(select user_id from n_user where passport   = ? and user_type = ? )) user_id_3 ) t3)";
		Map<String,Object> value = this.get(sql, args);
		String id1 = value.get("user_id_1")==null?null:value.get("user_id_1").toString();
		String id2 = value.get("user_id_2")==null?null:value.get("user_id_2").toString();
		String id3 = value.get("user_id_3")==null?null:value.get("user_id_3").toString();
		return id1!=null?id1:id2!=null?id2:id3;
	}

	// 获取患者信息
	public Map<String, Object> getPatientInfoById(String patient_id) {
		String url = BeanUtil.getProperty("dbconfig").getString("pictureuploadhttp");
		String sql = "select a.user_id,a.user_type,a.user_is_vip,a.user_name,a.user_sex,a.user_age,a.user_birthday,a.user_card,"
				+ "a.user_height,a.user_weight,a.user_blood,a.user_medicare_card,a.user_marriage,a.user_address,a.user_phone,"
				+ "a.user_phs_phone,a.user_pwd,a.email,a.user_work,a.user_medical_burden,a.user_waist,a.picture_head_order_id,"
				+ "a.user_head_pic_upload_index,a.picture_card_front_order_id,a.user_card_front_upload_index,a.picture_card_back_order_id,"
				+ "a.user_card_back_upload_index,date_format(a.create_date,'%Y-%m-%d %T') create_date,date_format(a.update_date,'%Y-%m-%d %T') update_date,"
				+ "a.user_source,a.user_flag,a.ahdi_value,a.user_nation,a.clientId,a.os_type,a.qq,a.wechat,a.ahdi_level,a.chronic,a.mrs_value,"
				+ "a.mrs_level,a.passport,a.mainland,a.country,a.country_name,b.is_send,b.doctor_id,c.doctor_name "
				+ "from n_user a,d_patient b left outer join d_doctor c on b.doctor_id = c.doctor_id where	a.user_id = b.patient_id and a.user_id = ? ";
		Map<String, Object> patientInfo = this.get(sql, new Object[] { patient_id });
		if (patientInfo.get("user_head_pic_upload_index") != null) {
			patientInfo.put("user_head_pic_upload_index", url + patientInfo.get("user_head_pic_upload_index"));
		}
		if (patientInfo.get("user_card_front_upload_index") != null) {
			patientInfo.put("user_card_front_upload_index", url + patientInfo.get("user_card_front_upload_index"));
		}
		if (patientInfo.get("user_card_back_upload_index") != null) {
			patientInfo.put("user_card_back_upload_index", url + patientInfo.get("user_card_back_upload_index"));
		}
		if (patientInfo.get("mainland").toString().equals("1")) {
			if (patientInfo.get("user_card") != null) {
				IdCard card = IdCard.of(patientInfo.get("user_card").toString());
				if (card.flag) {
					patientInfo.put("user_age", card.getAge());
					patientInfo.put("user_sex", card.getSex() % 2 == 1 ? "200001" : "200002");
					patientInfo.put("user_birthday", card.getBirthday());
				}
			}
		} else if (patientInfo.get("mainland").toString().equals("0")) {
			Object user_birthday = patientInfo.get("user_birthday");
			if (user_birthday != null) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				long age = LocalDate.parse(user_birthday.toString(), formatter).until(LocalDate.now(),
						ChronoUnit.YEARS);
				patientInfo.put("user_age", age + "");
			}
		}
		return patientInfo;
	}

	// 修改密码
	public void changePassword(String user_phone, String user_pwd) {
		String sql = "update n_user set user_pwd = ? where user_phone = ? ";
		this.execute(sql, user_pwd, user_phone);
	}

	// 查询
	public User getUserById(String user_id) {
		String sql = "select * from n_user where user_id = ? ";
		User user = this.get(sql, new Object[] { user_id }, User.class);
		return user;
	}

	// 查询
	public Patient getPatientById(String patient_id) {
		String sql = "select * from d_patient where patient_id = ? ";
		Patient patient = this.get(sql, new Object[] { patient_id }, Patient.class);
		return patient;
	}

	// 新增
	public void save(User user, Patient patient) {
		Long patient_id = this.insert(user, "n_user");
		user.setUser_id(patient_id.toString());
		patient.setPatient_id(patient_id.toString());
		this.insert(patient, "d_patient");
	}

	// 删除
	public void delete(User user, Patient patient) {
		this.delete(user, "n_user");
		this.delete(patient, "n_user");
	}

	// 更新
	public void update(User user, Patient patient) {
		this.update(user, "n_user");
		if (patient != null) {
			Patient toPatient = this.get("select * from d_patient where patient_id = ? ",
					new Object[] { user.getUser_id() }, Patient.class);
			BeanUtil.copy(patient, toPatient);
			this.update(patient, "d_patient");
		}
	}

	public Map<String, Object> selectByAccount(String user_phone) {
		String sql = " select user_id from n_user where user_phone = ? ";
		String user_id = jdbcTemplate.queryForObject(sql, String.class, user_phone);
		Map<String, Object> map = getPatientInfoById(user_id);
		return map;
	}

	public Map<String, Object> selectByIdCard6500(String identityCard) {
		String sql = "select a.user_id,a.user_type,a.user_is_vip,a.user_name,a.user_sex,a.user_age,a.user_birthday,a.user_card,a.user_height,a.user_weight,a.user_blood,"
				+ "a.user_phone,a.user_pwd from  n_user a left join d_patient b on a.user_id = b.patient_id where a.user_card = ? ";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql, identityCard);
		return map;
	}

	public Map<String, Object> selectByIdCard(String identityCard) {
		/*
		 * String url =
		 * BeanUtil.getProperty("dbconfig").getString("pictureuploadhttp");
		 * String sql =
		 * "select a.user_id,a.user_type,a.user_is_vip,a.user_name,a.user_sex,a.user_age,a.user_birthday,a.user_card,a.user_height,a.user_weight,a.user_blood,a.user_medicare_card,a.user_marriage,a.user_address,"
		 * +
		 * "a.user_phone,a.user_pwd,a.email,a.user_work,a.user_medical_burden,a.user_waist,a.picture_head_order_id,"
		 * + "concat('" + url +
		 * "',a.user_head_pic_upload_index) user_head_pic_upload_index,concat('"
		 * + url +
		 * "',a.picture_card_front_order_id) picture_card_front_order_id,concat('"
		 * + url +
		 * "',a.user_card_front_upload_index) user_card_front_upload_index," +
		 * "a.picture_card_back_order_id,a.user_card_back_upload_index,date_format(a.create_date,'%Y-%m-%d %H:%i') create_date,date_format(a.update_date,'%Y-%m-%d %H:%i') update_date,a.user_source,"
		 * +
		 * "a.user_flag,a.ahdi_value,b.doctor_id,date_format(now(), '%Y') sys_year,a.wechat,a.qq from n_user a left join d_patient b on a.user_id = b.patient_id where a.user_card= ? "
		 * ; Map<String, Object> map = null; List<Map<String, Object>> list =
		 * select(sql, identityCard); if (list.size() > 0) map = list.get(0);
		 */

		String patient_id = this.getPatientId(identityCard);
		Map<String, Object> map = this.getPatientInfoById(patient_id);
		return map;
	}

	public double getMrsValue(String patient_id) {
		Double mrs_value = this.getValue("select mrs_value from n_user where user_id = ? ", new Object[] { patient_id },
				Double.class);
		if (mrs_value != null) {
			return mrs_value.doubleValue();
		} else {
			return 0.0;
		}
	}

	public List<Map<String, Object>> getChronicPlan(String patient_id) {
		String sql = "select order_id,user_id,chronic_code,chronic_name,manage_text,check_target,check_num,is_end,"
				+ "date_format(create_date,'%Y-%m-%d %T') create_date,date_format(update_date,'%Y-%m-%d %T') update_date,"
				+ "remarks,chronic_source from m_chronic_manage where chronic_code in (?,?) and user_id = ? ";
		List<Map<String, Object>> chronicPlan = this.select(sql, "900001", "900002", patient_id);
		return chronicPlan;
	}

	public Map<String, Object> getUserByOpenId(String open_id) {
		String sql = "select user_id,user_name,user_sex,user_birthday,user_type,user_weight,user_height,user_card,passport from n_user where open_id=?";
		return this.jdbcTemplate.queryForMap(sql, open_id);
	}
}
