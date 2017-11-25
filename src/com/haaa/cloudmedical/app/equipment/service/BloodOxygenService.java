package com.haaa.cloudmedical.app.equipment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haaa.cloudmedical.common.annotation.Log;
import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.common.util.NumberUtil;
import com.haaa.cloudmedical.common.util.StringUtil;
import com.haaa.cloudmedical.dao.equipment.BloodOxygenDao;
import com.haaa.cloudmedical.dao.equipment.EquipmentDao;
import com.haaa.cloudmedical.entity.BloodOxygen;
import com.haaa.cloudmedical.entity.EquipmentUse;

/**
 * 
 * @author Bowen Fan
 *
 */
@Service
@Log(name="app血氧")
public class BloodOxygenService {

	@Resource
	private BloodOxygenDao bloodOxygenDao;

	@Resource
	private EquipmentDao equipmentDao;

	/**
	 * 
	 * @Title: add 
	 * @Description: 血氧数据添加 
	 * @param equipmentUse 
	 * @param bloodOxygen 
	 * @return 
	 * @throws
	 */
	@Transactional
	@Log(name="血氧记录添加")
	public InfoJson add(EquipmentUse equipmentUse, BloodOxygen bloodOxygen) {
		InfoJson infoJson = new InfoJson();
		//自动上传是后台生成时间
		if (equipmentUse.getCreate_date()==null) {
			Date date = new Date();
			equipmentUse.setCreate_date(DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm:ss"));
		}	
		equipmentUse.setEquipment_name("pulse_bloodoxygen");
		// BLOOD_OXYGEN_ID = "21";
		equipmentUse.setEquipment_property_order_id(Constant.BLOOD_OXYGEN_ID);
		//持久化到数据库并获取主键
		Long id = (Long) equipmentDao.insertAndGetKey(equipmentUse, "c_equipment_use");
		bloodOxygen.setCreate_date(equipmentUse.getCreate_date());
		bloodOxygen.setEquipment_use_order_id(String.valueOf(id));
		//持久化到数据库并获取主键
		long result = (Long) bloodOxygenDao.insertAndGetKey(bloodOxygen, "c_pulse_bloodoxygen");
		//通过主键查询出添加的内容用于封装返回对象
		Map<String, Object> map = bloodOxygenDao.queryDetailById(result, "c_pulse_bloodoxygen");
		//格式化日期 yyyy-MM-dd HH:mm:ss转成yyyy-MM-dd 
		map.put("datetime", DateUtil.DateFormat(map.remove("create_date"), "yyyy-MM-dd"));
		infoJson.setData(map);
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: queryRecent
	 * @Description: app曲线图近期数据查询
	 *  @param user_id 
	 *  @return @throws
	 */
	public InfoJson queryRecent(long user_id, int days) {
		InfoJson infoJson = new InfoJson();
		if (bloodOxygenDao.queryRecent(user_id, 90).size() != 0) {
			infoJson.setCount(infoJson.getCount() + 1);
		}
		List<Map<String, Object>> list = bloodOxygenDao.queryRecent(user_id, days);
		for (Map<String, Object> map : list) {
			//处理血氧的小数点
			map.put("Oxygen", (NumberUtil.decimalFormat((float) map.remove("Oxygen"), 0)));
		}
		infoJson.setData(list);
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: pageQuery 
	 * @Description: app分页查询，按月分页查询  封装分页查询结果对象    monthList--monthMap(yearMonth,timeList----map) 
	 * @param user_id 
	 * @param year_month 
	 * @param pageno 
	 * @param pagesize 
	 * @return 
	 * @throws
	 *     
	 */
	public InfoJson pageQuery(long user_id, String year_month, int pageno, int pagesize) {
		InfoJson infoJson = new InfoJson();
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		List<Object> monthList = new ArrayList<Object>();
		//如果没有传年月，按照普通的查询
		if (!StringUtil.isEmpty(year_month)) {
			dataList = bloodOxygenDao.queryByMonth(user_id, year_month, pageno, pagesize);
			infoJson.setCount(bloodOxygenDao.pageNumberByMonth(user_id,year_month, pagesize));
		} else {
			dataList = bloodOxygenDao.pageQuery(user_id, pageno, pagesize);
			infoJson.setCount(bloodOxygenDao.pageNumber(user_id, pagesize));
		}
		if (dataList.size() > 0) {
			Iterator<Map<String, Object>> iterator = dataList.iterator();
			Map<String, Object> map = iterator.next();
			Date date = (Date) map.get("datetime");
			String yearMonth = DateUtil.dateFormat(date, "yyyy-MM");
			String monthDay = DateUtil.dateFormat(date, "MM-dd");
			String tempYearMonth = null;
			//分类可以用groupingBy代替      通过循环遍历的方式按照年月进行分组
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
					map.put("Oxygen", (NumberUtil.decimalFormat((float) map.remove("Oxygen"), 0)));
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
	 * @Title: periodDefine 
	 * @Description: 根据查询的时间 判断早中晚时间段，-1上午，0下午，1晚上 
	 * @param hour 
	 * @return 
	 * @throws
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

		infoJson.setData(bloodOxygenDao.queryMonth(user_id));
		infoJson.setStatus(1);

		return infoJson;

	}

}