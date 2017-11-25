package com.haaa.cloudmedical.app.equipment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haaa.cloudmedical.app.util.DoctorAppPushUtils;
import com.haaa.cloudmedical.common.annotation.Log;
import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.common.util.StringUtil;
import com.haaa.cloudmedical.dao.ChronicPlanDao;
import com.haaa.cloudmedical.dao.DoctorDao;
import com.haaa.cloudmedical.dao.equipment.BloodPressureDao;
import com.haaa.cloudmedical.entity.BloodPressure;
import com.haaa.cloudmedical.entity.EquipmentUse;

/**
 * 
 * @author Bowen Fan
 *
 */
@Service
@Log(name = "app血压")
public class BloodPressureService {

	@Resource
	private BloodPressureDao bloodPressureDao;
	
	@Resource
	private DoctorDao doctorDao;
	
	//慢性病管理计划的dao
	@Autowired
	private ChronicPlanDao chronicPlanDao;
	/**
	 * 
	 * @Title: add 
	 * @Description: 血压数据添加 
	 * @param equipmentUse 
	 * @param bloodPressure 
	 * @return 
	 * @throws
	 */
	@Transactional
	@Log(name = "血压记录增加")
	public InfoJson add(EquipmentUse equipmentUse, BloodPressure bloodPressure,String create_by) {
		InfoJson infoJson = new InfoJson();
		if (equipmentUse.getCreate_date() == null) {
			Date date = new Date();
			equipmentUse.setCreate_date(DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm:ss"));
		}
		equipmentUse.setEquipment_name("blood_pressure");
		equipmentUse.setEquipment_property_order_id(Constant.BLOOD_PRESSURE_ID);
		//持久化到数据库并获取主键
		Long id = (Long) bloodPressureDao.insertAndGetKey(equipmentUse, "c_equipment_use");
		bloodPressure.setCreate_date(equipmentUse.getCreate_date());
		bloodPressure.setEquipment_use_order_id(String.valueOf(id));
		//持久化到数据库并获取主键
		long result = (Long) bloodPressureDao.insertAndGetKey(bloodPressure, "c_blood_pressure");
		
		//管理向Iterm里面插入数据
		insetIterm(equipmentUse.getUser_id(),equipmentUse.getCreate_date(),create_by);
		
		//通过主键查询出添加的内容用于封装返回对象
		Map<String, Object> map = bloodPressureDao.queryDetailById(result, "c_blood_pressure");
		//格式化日期 yyyy-MM-dd HH:mm:ss转成yyyy-MM-dd 
		map.put("datetime", DateUtil.DateFormat(map.remove("create_date"), "yyyy-MM-dd"));
		pushUrgentMessage(equipmentUse.getUser_id(), equipmentUse.getDoctor_id(), bloodPressure);
		infoJson.setData(map);
		infoJson.setStatus(1);
		return infoJson;
	}
	/**
	 * @description 管理向content里面插入数据计划 并且更新report表
	 * @param patient_id
	 * @param create_date
	 * @param create_by
	 */
	private void insetIterm(String patient_id,String create_date,String create_by) {
		//获取用户的管理计划
		List<Map<String, Object>> plan = bloodPressureDao.getPlan(patient_id);
		if (plan!=null) {
			for (Map<String, Object> map : plan) {
				map.put("create_date", create_date);
				map.put("create_by", create_by);
				map.put("check_time", create_date);
				long item_order_id = bloodPressureDao.insert(map, "p_plan_item");
				//调用存储过程更新report表
				chronicPlanDao.callStoreUpd(String.valueOf(item_order_id));
			}
		}
		
	}





	/**
	 * 
	 * @Title: pushUrgentMessage 
	 * @Description: 推送紧急信息 
	 * @param user_id 
	 * @param   doctor_id 
	 * @param bloodPressure 
	 * @throws
	 */
	private void pushUrgentMessage(String patient_id, String doctor_id, BloodPressure bloodPressure) {
		String content = "";
		Map<String, Object> patientMap = doctorDao.getPatientInfo(patient_id);
		Map<String, Object> pushMessageMap = new HashMap<String, Object>();
		if (StringUtil.isEmpty(doctor_id)) {
			doctor_id = String.valueOf(patientMap.get("doctor_id"));
		}
		int highPressure = Integer.parseInt(bloodPressure.getHighPressure());
		int lowPressure = Integer.parseInt(bloodPressure.getLowPressure());
		int level = 0;
		// 判断高血压级别
		if (highPressure > Constant.BlOOD_PRESSURE_LEVEL.get("H5")
				|| lowPressure > Constant.BlOOD_PRESSURE_LEVEL.get("L5")) {
			level = 3;
		} else if (highPressure > Constant.BlOOD_PRESSURE_LEVEL.get("H4")
				|| lowPressure > Constant.BlOOD_PRESSURE_LEVEL.get("L4")) {
			level = 2;
		} else if (highPressure > Constant.BlOOD_PRESSURE_LEVEL.get("H3")
				|| lowPressure > Constant.BlOOD_PRESSURE_LEVEL.get("L3")) {
			level = 1;
		}
		if (level > 0 && String.valueOf(1).equals(patientMap.get("is_send"))) {
			pushMessageMap.put("measurement_result", level);
			pushMessageMap.put("measurement_type", Constant.remind_measurement_bloodpressure);
			pushMessageMap.put("patient_id", patientMap.get("user_id"));
			pushMessageMap.put("doctor_id", patientMap.get("doctor_id"));
			content = content + "高压：" + bloodPressure.getHighPressure() + "mmHg" + "\40\40" + "低压："
					+ bloodPressure.getLowPressure() + "mmHg";
			pushMessageMap.put("measurement_value", content);
			pushMessageMap.put("create_date", bloodPressure.getCreate_date());
			doctorDao.insert(pushMessageMap, "c_push_message");
			//当血压等级》=2时给医生推送消息
			if (level >= 2) {
				content = bloodPressure.getCreate_date() + "\40\40" + patientMap.get("user_name") + "\40\40" + "测量类型：血压"
						+ "\40\40" + "测量结果：" + content;
				DoctorAppPushUtils.pushMsgToSingleByAlias(doctor_id, content);
			}
		}

	}

	/**
	 * 
	 * @Title: queryRecent @Description: app曲线图近期数据查询 @param
	 *         user_id @return @throws
	 */
	public InfoJson queryRecent(long user_id, int days) {
		InfoJson infoJson = new InfoJson();
		if (bloodPressureDao.queryRecent(user_id, 90).size() != 0) {
			infoJson.setCount(infoJson.getCount() + 1);
		}
		List<Map<String, Object>> list = bloodPressureDao.queryRecent(user_id, days);
		list.forEach(s -> {
			// 判断血压是否正常
			if ((int) s.get("HighPressure") > Constant.HIGH_PRESSURE_HIGH
					|| (int) s.get("LowPressure") > Constant.LOW_PRESSURE_HIGH) {
				s.put("result", "不正常");
				// result_icon为正常标志位，app端用来加载图片
				s.put("result_icon", 0);
			} else if ((int) s.get("HighPressure") < Constant.HIGH_PRESSURE_LOW
					|| (int) s.get("LowPressure") < Constant.LOW_PRESSURE_LOW) {
				s.put("result", "不正常");
				s.put("result_icon", 0);
			} else {
				s.put("result", "正常");
				s.put("result_icon", 1);
			}
		});
		infoJson.setData(list);
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @throws Exception
	 * @Title: pageQuery @Description: app分页查询，按月分页查询 @param user_id @param
	 *         year_month @param pageno @param pagesize @return @throws
	 */
	public InfoJson pageQuery(long user_id, String year_month, int pageno, int pagesize) throws Exception {
		InfoJson infoJson = new InfoJson();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		List<Object> monthList = new ArrayList<Object>();
		// 如果year_month为空，进行普通的分页查询
		if (!StringUtil.isEmpty(year_month)) {
			dataList = bloodPressureDao.queryByMonth(user_id, year_month, pageno, pagesize);
			infoJson.setCount(bloodPressureDao.pageNumberByMonth(user_id, year_month, pagesize));
		} else {
			dataList = bloodPressureDao.pageQuery(user_id, pageno, pagesize);
			infoJson.setCount(bloodPressureDao.pageNumber(user_id, pagesize));
		}
		if (dataList.size() > 0) {
			Iterator<Map<String, Object>> iterator = dataList.iterator();
			Map<String, Object> map = iterator.next();
			Date date = (Date) map.get("datetime");
			String yearMonth = DateUtil.dateFormat(date, "yyyy-MM");
			String monthDay = DateUtil.dateFormat(date, "MM-dd");
			String tempYearMonth = null;
			// 分类可以用groupingBy替换
			outer: while (true) {
				if (map == null) {
					break;
				}
				Map<String, Object> monthMap = new HashMap<String, Object>();
				tempYearMonth = yearMonth;
				monthList.add(monthMap);
				monthMap.put("yearmonth", yearMonth);
				List<Object> timeList = new ArrayList<Object>();
				while (tempYearMonth.equals(yearMonth)) {
					monthMap.put("timelist", timeList);
					Map<String, Object> timeMap = new HashMap<String, Object>();
					map.put("datetime", DateUtil.dateFormat(date, "MM-dd HH:mm"));
					map.put("month", yearMonth);
					timeMap.put("singledata", map);
					int hour = DateUtil.getHour(date);
					int period = periodDefine(hour);
					timeMap.put("period", period);
					timeList.add(timeMap);
					if (iterator.hasNext()) {
						map = iterator.next();
						date = (Date) map.get("datetime");
						yearMonth = DateUtil.dateFormat(date, "yyyy-MM");
						monthDay = DateUtil.dateFormat(date, "MM-dd");
					} else {
						break outer;
					}
				}
			}
		}
		infoJson.setData(monthList);
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: periodDefine @Description: 根据查询的时间 判断早中晚时间段，-1上午，0下午，1晚上 @param
	 *         hour @return int 返回类型 @throws
	 */
	private int periodDefine(int hour) {
		int period = 0;
		if (hour < Constant.MORNING_END && hour >= 0) {
			period = -1;
		} else if (hour < Constant.NIGHT_BEGIN) {
			period = 0;
		} else {
			period = 1;
		}
		return period;
	}

	/**
	 * 返回具有信息的年月
	 * 
	 * @param user_id
	 * @return
	 */
	public InfoJson queryMonth(Long user_id) {
		InfoJson infoJson = new InfoJson();
		infoJson.setData(bloodPressureDao.queryMonth(user_id));
		infoJson.setStatus(1);
		return infoJson;

	}

	/**
	 * 统计近期高压低压反常以及正常次数次数
	 * 
	 * @param user_id
	 * @return
	 */
	public InfoJson dataStat(long user_id, int days) {
		InfoJson infoJson = new InfoJson();
		List<Map<String, Object>> data = bloodPressureDao.queryRecent(user_id, days);
		Map<String, Integer> statMap = new HashMap<String, Integer>();
		int high = 0;
		int low = 0;
		int normal = 0;
		for (Map<String, Object> map : data) {
			int highPressure = (int) map.get("HighPressure");
			int lowPressure = (int) map.get("LowPressure");
			if (highPressure > Constant.HIGH_PRESSURE_HIGH || lowPressure > Constant.LOW_PRESSURE_HIGH) {
				high++;
			} else if (highPressure < Constant.HIGH_PRESSURE_LOW || lowPressure < Constant.LOW_PRESSURE_LOW) {
				low++;
			} else {
				normal++;
			}
		}
		statMap.put("high", high);
		statMap.put("low", low);
		statMap.put("normal", normal);
		infoJson.setData(statMap);
		infoJson.setStatus(1);
		return infoJson;
	}
	/**
	 * @description 血压分级
	 * @param high_pressure
	 * @param low_pressure
	 * @return
	 */
	public InfoJson classify(int high_pressure, int low_pressure) {
		InfoJson infoJson = new InfoJson();
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("high_pressure", high_pressure);
		retMap.put("low_pressure", low_pressure);
		String high_pressure_result;
		String low_pressure_result;
		if (high_pressure < Constant.BlOOD_PRESSURE_LEVEL.get("H1")) {
			high_pressure_result = "低血压";
		} else if (high_pressure < Constant.BlOOD_PRESSURE_LEVEL.get("H2")) {
			high_pressure_result = "正常血压";
		} else if (high_pressure < Constant.BlOOD_PRESSURE_LEVEL.get("H3")) {
			high_pressure_result = "正常高值";
		} else if (high_pressure < Constant.BlOOD_PRESSURE_LEVEL.get("H4")) {
			high_pressure_result = "一级高血压（轻度）";
		} else if (high_pressure < Constant.BlOOD_PRESSURE_LEVEL.get("H5")) {
			high_pressure_result = "二级高血压（中度）";
		} else {
			high_pressure_result = "三级高血压（重度）";
		}
		if (low_pressure < Constant.BlOOD_PRESSURE_LEVEL.get("L1")) {
			low_pressure_result = "低血压";
		} else if (low_pressure < Constant.BlOOD_PRESSURE_LEVEL.get("L2")) {
			low_pressure_result = "正常血压";
		} else if (low_pressure < Constant.BlOOD_PRESSURE_LEVEL.get("L3")) {
			low_pressure_result = "正常高值";
		} else if (low_pressure < Constant.BlOOD_PRESSURE_LEVEL.get("L4")) {
			low_pressure_result = "一级高血压（轻度）";
		} else if (low_pressure < Constant.BlOOD_PRESSURE_LEVEL.get("L5")) {
			low_pressure_result = "二级高血压（中度）";
		} else {
			low_pressure_result = "三级高血压（重度）";
		}
		retMap.put("high_pressure_result", high_pressure_result);
		retMap.put("low_pressure_result", low_pressure_result);
		infoJson.setData(retMap);
		infoJson.setStatus(1);
		return infoJson;

	}
	/**
	 * @description 删除某次血压检测数据 
	 */
	public void deleteBloodPressure(String order_id) {		
		bloodPressureDao.deleteBloodPressure(order_id);
	}
}
