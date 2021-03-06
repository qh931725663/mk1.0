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
import com.haaa.cloudmedical.dao.equipment.BloodSugarDao;
import com.haaa.cloudmedical.wechat.service.IWeixinBloodSugarService;

/**
 * @author Bowen Fan
 *
 */
@Service
public class WeixinBloodSugarServiceImpl implements IWeixinBloodSugarService {

	@Resource
	private BloodSugarDao bloodSugarDao;
	@Resource
	private UserAppDao userAppDao;

	private NumberFormat nFormat = DecimalFormat.getInstance();

	@Override
	public StdDTO getGraph(String open_id, int period, int days) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> userMap = userAppDao.getUserByOpenId(open_id);
		long user_id = (long) userMap.get("user_id");
		List<Map<String, Object>> list = bloodSugarDao.queryRecent(user_id, period, days);
		float bloodsugar0 = (float) list.get(0).get("BloodSugar");
		int period0 = (int) list.get(0).get("measurement_period");
		int high = 0;
		int low = 0;
		int normal = 0;
		int temp_period = 0;
		float bloodsugar = 0;
		List<Object> bloodSugarList = new LinkedList<Object>();
		List<Object> timeList = new LinkedList<Object>();
		nFormat.setMinimumFractionDigits(2);
		for (Map<String, Object> map : list) {
			temp_period = (int) map.get("measurement_period");
			bloodsugar = (float) map.get("BloodSugar");
			int temp = classifyBloodSugar(bloodsugar, temp_period);
			if (temp == -1) {
				low++;
			} else if (temp == 1) {
				high++;
			} else if (temp == 0) {
				normal++;
			}
			bloodSugarList.add(map.get("BloodSugar"));
			timeList.add(map.get("datetime"));
		}
		retMap.put("BloodSugarList", bloodSugarList);
		retMap.put("TimeList", timeList);
		retMap.put("BloodSugar", list.get(0).get("BloodSugar"));
		retMap.put("measurement_period", list.get(0).get("period"));
		retMap.put("measurement_period_value", list.get(0).get("measurement_period"));
		int temp = classifyBloodSugar(bloodsugar0, period0);
		if (temp == -1) {
			retMap.put("result", "不正常");
			retMap.put("result_icon", 0);
		} else if (temp == 1) {
			retMap.put("result", "不正常");
			retMap.put("result_icon", 0);
		} else if (temp == 0) {
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
			dataList = bloodSugarDao.pageQuery(user_id, pageno, pagesize);
			pagecount = bloodSugarDao.pageNumber(user_id, pagesize);
		} else {
			dataList = bloodSugarDao.queryByMonth(user_id, year_month, pageno, pagesize);
			pagecount = bloodSugarDao.pageNumberByMonth(user_id, year_month, pagesize);
		}
		nFormat.setMinimumFractionDigits(2);// 两位小数
		Object data = dataList.stream()
				.collect(Collectors.groupingBy(m -> DateUtil.dateFormat((Date) m.get("datetime"), "yyyy-MM")))
				.entrySet().stream().map(s -> new HashMap<String, Object>() {
					{
						put("yearmonth", s.getKey());
						s.getValue().forEach(ss -> {
							ss.put("BloodSugar", nFormat.format(ss.get("BloodSugar")));
							ss.put("measurement_period_value",ss.get("period"));
							ss.put("period", periodDefine(
									Integer.parseInt(DateUtil.dateFormat((Date) ss.get("datetime"), "HH"))));
							ss.put("datetime", DateUtil.dateFormat((Date) ss.get("datetime"), "yyyy-MM-dd HH:mm"));
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
		retMap.put("list", bloodSugarDao.queryMonth(user_id));
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

	private int classifyBloodSugar(float blood_sugar, int period) {
		if (period == Constant.EMPTY_STOMACH) {
			if (blood_sugar < Constant.LOW_FBG) {
				return -1;
			} else if (blood_sugar > Constant.HIGH_FBG) {
				return 1;
			} else {
				return 0;
			}
		}
		// 睡前
		else if (period == Constant.BEFORE_SLEEP) {
			if (blood_sugar < Constant.LOW_PRE_BG) {
				return -1;
			} else if (blood_sugar > Constant.HIGH_PRE_BG) {
				return 1;
			} else {
				return 0;
			}
		}
		// 饭后
		else if (period == Constant.AFTER_BREAKFAST || period == Constant.AFTER_DINNER
				|| period == Constant.AFTER_LUNCH) {
			if (blood_sugar < Constant.LOW_POST_BG) {
				return -1;
			} else if (blood_sugar > Constant.HIGH_POST_BG) {
				return 1;
			} else {
				return 0;
			}

		} else if (period == Constant.BEFORE_DINNER || period == Constant.BEFORE_LUNCH) {// 饭前
			if (blood_sugar < Constant.LOW_PRE_BG) {
				return -1;
			} else if (blood_sugar > Constant.HIGH_PRE_BG) {
				return 1;
			} else {
				return 0;
			}
		}
		return 0;
	}

	@Override
	public StdDTO classify(float blood_sugar, int period) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		String blood_sugar_result = "";
		String measurement_period = "";
		if (period == Constant.EMPTY_STOMACH) {
			measurement_period = "空腹";
			if (blood_sugar < Constant.BLOOD_SUGAR_LEVEL.get("fbg1")) {
				blood_sugar_result = "低血糖";
			} else if (blood_sugar < Constant.BLOOD_SUGAR_LEVEL.get("fbg2")) {
				blood_sugar_result = "正常";
			} else if (blood_sugar < Constant.BLOOD_SUGAR_LEVEL.get("fbg3")) {
				blood_sugar_result = "良好";
			} else if (blood_sugar < Constant.BLOOD_SUGAR_LEVEL.get("fbg4")) {
				blood_sugar_result = "一般";
			} else if (blood_sugar < Constant.BLOOD_SUGAR_LEVEL.get("fbg5")) {
				blood_sugar_result = "不良";
			} else {
				blood_sugar_result = "极其不良";
			}
		} else if (period == Constant.AFTER_BREAKFAST || period == Constant.AFTER_DINNER
				|| period == Constant.AFTER_LUNCH) {
			measurement_period = "餐后";
			if (blood_sugar < Constant.BLOOD_SUGAR_LEVEL.get("post1")) {
				blood_sugar_result = "低血糖";
			} else if (blood_sugar < Constant.BLOOD_SUGAR_LEVEL.get("post2")) {
				blood_sugar_result = "正常";
			} else if (blood_sugar < Constant.BLOOD_SUGAR_LEVEL.get("post3")) {
				blood_sugar_result = "良好";
			} else if (blood_sugar < Constant.BLOOD_SUGAR_LEVEL.get("post4")) {
				blood_sugar_result = "一般";
			} else if (blood_sugar < Constant.BLOOD_SUGAR_LEVEL.get("post5")) {
				blood_sugar_result = "不良";
			} else {
				blood_sugar_result = "极其不良";
			}
		} else if (period == Constant.BEFORE_DINNER || period == Constant.BEFORE_LUNCH) {
			measurement_period = "餐前";
			if (blood_sugar < Constant.BLOOD_SUGAR_LEVEL.get("pre1")) {
				blood_sugar_result = "低血糖";
			} else if (blood_sugar < Constant.BLOOD_SUGAR_LEVEL.get("pre2")) {
				blood_sugar_result = "正常";
			} else if (blood_sugar < Constant.BLOOD_SUGAR_LEVEL.get("pre3")) {
				blood_sugar_result = "良好";
			} else if (blood_sugar < Constant.BLOOD_SUGAR_LEVEL.get("pre4")) {
				blood_sugar_result = "一般";
			} else if (blood_sugar < Constant.BLOOD_SUGAR_LEVEL.get("pre5")) {
				blood_sugar_result = "不良";
			} else {
				blood_sugar_result = "极其不良";
			}
		}
		nFormat.setMinimumFractionDigits(2);// 两位小数
		retMap.put("blood_sugar", nFormat.format(blood_sugar));
		retMap.put("blood_sugar_result", blood_sugar_result);
		retMap.put("measurement_period", measurement_period);
		return StdDTO.setSuccess(retMap);

	}
}
