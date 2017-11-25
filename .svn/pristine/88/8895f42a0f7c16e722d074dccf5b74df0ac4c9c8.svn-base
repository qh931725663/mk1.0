package com.haaa.cloudmedical.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.haaa.cloudmedical.common.dao.BaseTemplateDao;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.common.util.IdCard;
import com.haaa.cloudmedical.entity.Hypertension;
import com.haaa.cloudmedical.entity.HypertensionAnswer;
import com.haaa.cloudmedical.entity.HypertensionQuestion;

@Repository
public class HypertensionAppDao extends BaseTemplateDao {

	// hypertension

	public List<Map<String, String>> getAllQuestions() {
		String sql = "select a.* from s_hypertension_question_k a where length(a.order_id)= 6 ";
		List<Map<String, Object>> list = this.select(sql);
		List<Map<String, String>> result = new ArrayList<>();
		list.forEach(map -> {
			Map<String, String> temp = new HashMap<>(map.size());
			map.forEach((key, value) -> {
				if (value != null) {
					temp.put(key, String.valueOf(value));
				} else {
					temp.put(key, null);
				}
			});
			result.add(temp);
		});
		return result;
	}

	public List<Map<String, String>> getAllAnswers() {
		String sql = "select a.* from s_hypertension_answer_k a ";
		List<Map<String, Object>> list = this.select(sql);
		List<Map<String, String>> result = new ArrayList<>();
		list.forEach(map -> {
			Map<String, String> temp = new HashMap<>(map.size());
			map.forEach((key, value) -> {
				if (value != null) {
					temp.put(key, String.valueOf(value));
				} else {
					temp.put(key, null);
				}
			});
			result.add(temp);
		});
		return result;
	}
	
	
	/**
	 * 检验该患者有没有完善基础档案 0 没有
	 * 
	 * @param patient_id
	 * @return
	 */
	public int countByPatientId(String patient_id) {
		String sql = "select count(*) from s_hypertension a where a.user_id = ? ";
		int count = this.getValue(sql, new Object[] { patient_id }, Integer.class);
		return count;
	}

	/**
	 * 新增高血压慢病管理档案
	 * 
	 * @param hypertension
	 * @return
	 */
	public long addHypertension(Hypertension hypertension) {
		String user_id = hypertension.getUser_id();
		String now = LocalDate.now().toString();
		String sql = "select count(*) from s_hypertension where user_id = ? and date_format(create_date,'%Y-%m-%d')<=? and date_format(date_add(create_date,INTERVAL 1 YEAR),'%Y-%m-%d')>=?";
		if(this.getValue(sql, new Object[]{user_id,now,now}, Integer.class)==0){
			return this.insert(hypertension, "s_hypertension");
		}
		return 0;
	}

	/**
	 * 保存到业务-问题表
	 * 
	 * @param question
	 */
	public void saveQuestion(HypertensionQuestion question) {
		this.insert(question, "s_hypertension_question");
	}
	
	/**
	 * 删除业务表中的问题
	 * @param question
	 */
	public void deleteQuestion(String hypertension_order_id,String question_region) {
		String sql = "delete from s_hypertension_question where hypertension_order_id = ? and question_region = ? ";
		this.execute(sql, new Object[]{hypertension_order_id,question_region});
	}

	/**
	 * 保存到业务-答案表
	 * 
	 * @param question
	 */
	public void saveAnswer(HypertensionAnswer answer) {
		this.insert(answer, "s_hypertension_answer");
	}
	
	/**
	 * 删除业务表中的答案
	 * @param answer
	 */
	public void deleteAnswer(String hypertension_order_id,String question_region) {
		String sql = "delete from s_hypertension_answer where hypertension_order_id = ? and question_region = ? ";
		this.execute(sql, new Object[]{hypertension_order_id,question_region});
	}
	
	/**
	 * 检查区域1，区域2是否已完善
	 * true 已完善；false 未完善
	 * @param order_id
	 * @return
	 */
	public boolean checkFlag(String order_id){
		String sql = " select count(*) from s_hypertension a where order_id = ? and a.flag1 = '1' and a.flag2 = '1'";
		int count = this.getValue(sql, new Object[]{order_id}, Integer.class);
		if(count == 0){
			return false;
		}
		if(count ==1){
			return true;
		}
		return false;
	}
	
	/**
	 * 更改档案完善标志
	 * @param order_id
	 */
	public void changeFlag1(String hypertension_order_id){
		String sql = "update s_hypertension a set a.flag1 = '1' where order_id = ? ";
		this.execute(sql, hypertension_order_id);
	}
	
	/**
	 * 更改档案完善标志
	 * @param order_id
	 */
	public void changeFlag2(String hypertension_order_id){
		String sql = "update s_hypertension a set a.flag2 = '1' where order_id = ? ";
		this.execute(sql, hypertension_order_id);
	}
	
	/**
	 * 更改档案完善标志
	 * @param order_id
	 */
	public void changeFlag3(String hypertension_order_id){
		String sql = "update s_hypertension a set a.flag3 = '1' where order_id = ? and a.flag1 = '1' and a.flag2 = '1'";
		this.execute(sql, hypertension_order_id);
	}
	
	/**
	 * 更改档案完善标志
	 * @param order_id
	 */
	public void changeFlag(String hypertension_order_id){
		String sql = "update s_hypertension a set a.flag='1' where order_id = ? and a.flag1 = '1' and a.flag2 = '1' and a.flag3 = '1'";
		this.execute(sql, hypertension_order_id);
	}
	
	/**
	 * 删除高血压档案
	 * @param order_id
	 */
	public void deleteHypertension(String order_id){
		this.execute("delete s_hypertension a where a.order_id = ? ", order_id);
		this.execute("delete s_hypertension_question a where a.hypertension_order_id = ? ", order_id);
		this.execute("delete s_hypertension_answer a where a.hypertension_order_id = ? ", order_id);
	}
	
	/**
	 * 获取患者所有的高血压档案
	 * @param patient_id
	 * @return
	 */
	public List<Map<String,String>> getHypertension(String patient_id){
		String sql = "select a.order_id,a.user_id,a.risk,a.level,a.flag1,a.flag2,a.flag3,a.flag,"
				+ "date_format(a.create_date,'%Y-%m-%d %T') create_date,date_format(a.update_date,'%Y-%m-%d %T') update_date "
				+ "from s_hypertension a where a.user_id = ? order by a.update_date desc";
		List<Map<String,Object>> hypertensions = this.select(sql, new Object[]{patient_id});
		List<Map<String,String>> hypertensionInfo = new ArrayList<>(hypertensions.size());
		hypertensions.forEach(map->{
			Map<String,String> temp = new HashMap<>();
			map.forEach((k,v)->{
				if(v!=null){
					temp.put(k, String.valueOf(v));
				}else{
					temp.put(k, null);
				}
			});
			hypertensionInfo.add(temp);
		});
		return hypertensionInfo;
	}
	
	/**
	 * 获取所回答的问题
	 * @param hypertension_order_id
	 * @param question_region
	 * @return
	 */
	public Map<String, String> getDocQuestion(String hypertension_order_id,String question_region){
		String sql = "select * from s_hypertension_question a where a.hypertension_order_id = ? and question_region = ? ";
		List<HypertensionQuestion> questions = this.select(sql, new Object[]{hypertension_order_id,question_region},HypertensionQuestion.class);
		Map<String, String> relation = new HashMap<>();
		questions.forEach(question -> {
			String parent_id = question.getParent_id();
			String question_id = question.getQuestion_order_id();
			if (parent_id.length() == 6) {
				relation.put(parent_id, question_id);
			} else {
				relation.putIfAbsent(question_id, null);
			}
			
		});
		return relation;
	}
	
	/**
	 * 获取回答问题的答案
	 * @param hypertension_order_id
	 * @param question_region
	 * @return
	 */
	public Map<String,List<HypertensionAnswer>> getDocAnswer(String hypertension_order_id,String question_region){
		String sql = "select * from s_hypertension_answer a where a.hypertension_order_id = ? and question_region = ? ";
		Map<String,List<HypertensionAnswer>> answers = this.select(sql, new Object[]{hypertension_order_id,question_region},HypertensionAnswer.class)
				.stream().collect(Collectors.groupingBy(answer->answer.getQuestion_order_id()));
		return answers;
	}
	
	/**
	 * 获取该患者信息
	 * @param patient_id
	 * @return
	 */
	public Map<String,String> getPatientInfoById(String order_id){
		String sql="select a.user_sex,a.user_birthday,a.user_age,a.user_card,a.user_phone "
				+ "from n_user a where a.user_id = (select b.user_id from s_hypertension b where b.order_id = ? )";
		Map<String,Object> temp = this.select(sql, order_id).get(0);
		Object user_card = temp.get("user_card");
		if(user_card!=null){
			IdCard card = IdCard.of(user_card.toString());
			if(card.flag){
				temp.put("user_age", card.getAge());
				temp.put("user_birthday", card.getBirthday());
				temp.put("user_sex", card.getSex()==1?"200001":"200002");
			}
		}
		Map<String,String> patientInfo = new HashMap<>();
		temp.forEach((k,v)->{
			if(v!=null){
				patientInfo.put(k, String.valueOf(v));
			}else{
				patientInfo.put(k, null);
			}
		});
		return patientInfo;
		
	}
	
	public void getInitQuestionsAndAnswers(){
		String sql = "select a.order_id,a.question_content,b.answer_code,b.answer_content "
				+ "from s_hypertension_question_k a left join s_hypertension_answer_k b on a.order_id = b.question_order_id "
				+ "where length(a.order_id)=6 order by a.order_id";
		Map<String,List<Map<String,Object>>> maps = this.select(sql).stream()
				.collect(Collectors.groupingBy(m->m.get("order_id").toString()));
		
		Map<String,List<Map<String,String>>> questions = new HashMap<>();
		maps.forEach((k,v)->{
			List<Map<String,String>> answers = new ArrayList<>(v.size());
			v.forEach(m->{
				Map<String,String> answer = new HashMap<>(1);
				if(m.get("answer_code")!=null){
					answer.put(m.get("answer_code").toString(), m.get("answer_content").toString());
					answers.add(answer);
				}				
			});
			questions.put(k, answers);
		});
	}
	
	public Map<String,String>  getDocInfo(String order_id){
		String sql = "select a.order_id,a.user_id,a.risk,a.level,a.flag1,a.flag2,a.flag3,a.flag,"
				+ "date_format(a.create_date,'%Y-%m-%d %T') create_date,date_format(a.update_date,'%Y-%m-%d %T') update_date "
				+ "from s_hypertension a where a.order_id = ? ";
		Map<String,String> result = new HashMap<>();
		this.get(sql, new Object[]{order_id}).forEach((k,v)->result.put(k, v==null?null:String.valueOf(v)));
		return result;
	}
	
	/**
	 * 生成管理计划
	 * @param patient_id	患者id
	 * @param order_id		档案id
	 */
	public void callProcedure(String patient_id,String order_id){
		String sql = "select date_format(max(plan_end),'%Y-%m-%d %T') plan_end from p_plan where type = '900002' and user_id = ? ";
		String time = this.getValue(sql, new Object[]{patient_id}, String.class);
		if(time==null){
			this.callProcedure("proc_chronic_plan_hp(?)", new Object[]{order_id}, null);
			return;
		}
		LocalDateTime plan_end = LocalDateTime.parse(time,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		if(LocalDateTime.now().isAfter(plan_end)){
			this.callProcedure("proc_chronic_plan_hp(?)", new Object[]{order_id}, null);
			return;
		}
	}
	
	//追踪慢病档案
	public void track(String patient_id){
		
		String sql1 = "select order_id from p_plan where type = '900002' and user_id = ? and plan_begin < ? and plan_end > ? ";
		String now = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		String order_id = this.getValue(sql1, new Object[]{patient_id,now,now}, String.class);
		String sql2 = "update p_plan_report a set a.real_num = ifnull(a.real_num,0)+1 where a.user_id = ? and a.plan_order_id = ? "
				+ "and a.plan_dict_order_id in (13,17,21)";
		this.execute(sql2, patient_id,order_id);		
	}

}
