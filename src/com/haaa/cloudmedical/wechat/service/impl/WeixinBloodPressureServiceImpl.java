/**
 * 
 */
package com.haaa.cloudmedical.wechat.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.StdDTO;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.common.util.StringUtil;
import com.haaa.cloudmedical.dao.UserAppDao;
import com.haaa.cloudmedical.dao.equipment.BloodPressureDao;
import com.haaa.cloudmedical.wechat.service.IWeixinBloodPressureService;

/**
 * @author Bowen Fan
 *
 */
@Service
public class WeixinBloodPressureServiceImpl implements IWeixinBloodPressureService {

	@Resource
	private BloodPressureDao bloodPressureDao;

	@Resource
	private UserAppDao userAppDao;

	private NumberFormat nFormat = DecimalFormat.getInstance();

	@Override
	public StdDTO getGraph(String open_id, int days) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> userMap = userAppDao.getUserByOpenId(open_id);
		long user_id = (long) userMap.get("user_id");
		List<Map<String, Object>> list = bloodPressureDao.queryRecent(user_id, days);
		int high_pressure0 = (int) list.get(0).get("HighPressure");
		int low_pressure0 = (int) list.get(0).get("LowPressure");
		int high = 0;
		int low = 0;
		int normal = 0;
		nFormat.setMaximumFractionDigits(0);
		List<Object> highPressureList = new LinkedList<Object>();
		List<Object> lowPressureList = new LinkedList<Object>();
		List<Object> timeList = new LinkedList<Object>();
		for (Map<String, Object> map : list) {
			int highPressure = (int) map.get("HighPressure");
			int lowPressure = (int) map.get("LowPressure");
			if (highPressure > Constant.HIGH_PRESSURE_HIGH || lowPressure > Constant.LOW_PRESSURE_HIGH) {
				high++;
			} else if (lowPressure < Constant.LOW_PRESSURE_LOW || highPressure < Constant.HIGH_PRESSURE_LOW) {
				low++;
			} else {
				normal++;
			}
			highPressureList.add(map.get("HighPressure"));
			lowPressureList.add(map.get("LowPressure"));
			timeList.add(map.get("datetime"));
		}
		retMap.put("HighPressureList", highPressureList);
		retMap.put("LowPressureList", lowPressureList);
		retMap.put("TimeList", timeList);
		retMap.put("HighPressure", nFormat.format(list.get(0).get("HighPressure")));
		retMap.put("LowPressure", nFormat.format(list.get(0).get("LowPressure")));
		retMap.put("PulseRate", nFormat.format(list.get(0).get("PulseRate")));
		if (high_pressure0 > Constant.HIGH_PRESSURE_HIGH || low_pressure0 > Constant.LOW_PRESSURE_HIGH) {
			retMap.put("result", "不正常");
			retMap.put("result_icon", 0);
		} else if (low_pressure0 < Constant.LOW_PRESSURE_LOW || high_pressure0 < Constant.HIGH_PRESSURE_LOW) {
			retMap.put("result", "不正常");
			retMap.put("result_icon", 0);
		} else {
			retMap.put("result", "正常");
			retMap.put("result_icon", 1);
		}
		retMap.put("datetime", list.get(0).get("datetime"));
		retMap.put("high", high);
		retMap.put("low", low);
		retMap.put("normal", normal);
		return StdDTO.setSuccess(retMap);
	}

	@SuppressWarnings("serial")
	@Override
	public StdDTO getPage(String open_id, String year_month, int pageno, int pagesize) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> userMap = userAppDao.getUserByOpenId(open_id);
		long user_id = (long) userMap.get("user_id");
		List<Map<String, Object>> dataList;
		int pagecount = 0;
		if (StringUtil.isEmpty(year_month)) {
			dataList = bloodPressureDao.pageQuery(user_id, pageno, pagesize);
			pagecount = bloodPressureDao.pageNumber(user_id, pagesize);
		} else {
			dataList = bloodPressureDao.queryByMonth(user_id, year_month, pageno, pagesize);
			pagecount = bloodPressureDao.pageNumberByMonth(user_id, year_month, pagesize);
		}
		nFormat.setMaximumFractionDigits(0);
		Object data = dataList.stream()
				.collect(Collectors.groupingBy(m -> DateUtil.dateFormat((Date) m.get("datetime"), "yyyy-MM")))
				.entrySet().stream().map(s -> new HashMap<String, Object>() {
					{
						put("yearmonth", s.getKey());
						s.getValue().forEach(ss -> {
							ss.put("period", periodDefine(
									Integer.parseInt(DateUtil.dateFormat((Date) ss.get("datetime"), "HH"))));
							ss.put("datetime", DateUtil.dateFormat((Date) ss.get("datetime"), "yyyy-MM-dd HH:mm"));
							ss.put("HighPressure", nFormat.format(ss.get("HighPressure")));
							ss.put("LowPressure", nFormat.format(ss.get("LowPressure")));
							ss.put("PulseRate", nFormat.format(ss.get("PulseRate")));
							ss.remove("equipment_use_order_id");
						});
						put("datalist", s.getValue());
					}
				}).sorted((s1, s2) -> {
					return String.valueOf(s2.get("yearmonth")).compareTo(String.valueOf(s1.get("yearmonth")));
				}).collect(Collectors.toList());
		retMap.put("monthlist", data);
		retMap.put("pageno", pageno);
		retMap.put("pagecount", pagecount);
		retMap.put("pagesize", pagesize);
		return StdDTO.setSuccess(retMap);
	}

	@Override
	public StdDTO getMonths(String open_id) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> userMap = userAppDao.getUserByOpenId(open_id);
		long user_id = (long) userMap.get("user_id");
		retMap.put("list", bloodPressureDao.queryMonth(user_id));
		return StdDTO.setSuccess(retMap);
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

	public StdDTO classify(int high_pressure, int low_pressure) {
		Map<String, Object> retMap = new HashMap<String, Object>();
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
		nFormat.setMaximumFractionDigits(0);
		retMap.put("high_pressure", nFormat.format(high_pressure));
		retMap.put("low_pressure", nFormat.format(low_pressure));
		retMap.put("high_pressure_result", high_pressure_result);
		retMap.put("low_pressure_result", low_pressure_result);
		return StdDTO.setSuccess(retMap);

	}
}
