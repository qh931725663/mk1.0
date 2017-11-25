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
import com.haaa.cloudmedical.common.util.IdcardInfoExtractor;
import com.haaa.cloudmedical.common.util.StringUtil;
import com.haaa.cloudmedical.dao.UserAppDao;
import com.haaa.cloudmedical.dao.equipment.LungCapacityDao;
import com.haaa.cloudmedical.wechat.service.IWeixinLungCapacityService;

/**
 * @author Bowen Fan
 *
 */
@Service
public class WeixinLungCapacityServiceImpl implements IWeixinLungCapacityService {

	@Resource
	private LungCapacityDao lungCapacityDao;

	@Resource
	private UserAppDao userAppDao;

	private NumberFormat nFormat0 = DecimalFormat.getInstance();

	private NumberFormat nFormat2 = DecimalFormat.getInstance();

	@Override
	public StdDTO getGraph(String open_id, int days) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> userMap = userAppDao.getUserByOpenId(open_id);
		long user_id = (long) userMap.get("user_id");
		List<Map<String, Object>> list = lungCapacityDao.queryRecent(user_id, "all", days);
		List<Object> fvcList = new LinkedList<Object>();
		List<Object> pefList = new LinkedList<Object>();
		List<Object> fev1List = new LinkedList<Object>();
		List<Object> fvcRateList = new LinkedList<Object>();
		List<Object> pefRateList = new LinkedList<Object>();
		List<Object> fev1RateList = new LinkedList<Object>();
		List<Object> timeList = new LinkedList<Object>();
		nFormat2.setMinimumFractionDigits(2);
		nFormat0.setMinimumFractionDigits(0);
		list.stream().forEach(s -> {
			fvcList.add(s.get("fvc"));
			pefList.add(s.get("pef"));
			fev1List.add(s.get("fev1"));
			fvcRateList.add(s.get("fvc"));
			pefRateList.add(s.get("pef_rate"));
			fev1RateList.add(s.get("fev1_rate"));
			timeList.add(s.get("datetime"));
		});
		retMap.put("fvcList", fvcList);
		retMap.put("pefList", pefList);
		retMap.put("fev1List", fev1List);
		retMap.put("fvcRateList", fvcRateList);
		retMap.put("pefRateList", pefRateList);
		retMap.put("fev1RateList", fev1RateList);
		retMap.put("TimeLiest", timeList);
		retMap.put("fvc", nFormat2.format(list.get(0).get("fvc")));
		retMap.put("fev1", nFormat2.format(list.get(0).get("fev1")));
		retMap.put("pef", nFormat2.format(list.get(0).get("pef")));
		retMap.put("fvc_rate", nFormat0.format(list.get(0).get("fvc_rate")));
		retMap.put("fev1_rate", nFormat0.format(list.get(0).get("fev1_rate")));
		retMap.put("pef_rate", nFormat0.format(list.get(0).get("pef_rate")));
		retMap.put("datetime", list.get(0).get("datetime"));
		String user_card = String.valueOf(userMap.get("user_card"));
		String user_sex = String.valueOf(userMap.get("user_sex"));
		String user_height = String.valueOf(userMap.get("user_height"));
		Date user_birthday = (Date) userMap.get("user_birthday");
		int user_age = 0;
		if (!StringUtil.isEmpty(user_card)) {
			IdcardInfoExtractor idcardInfo = new IdcardInfoExtractor(user_card);
			if (idcardInfo.getGender().equals("男")) {
				user_sex = Constant.MALE;
			} else {
				user_sex = Constant.FEMALE;
			}
			user_age = idcardInfo.getAge();
		} else {
			user_age = DateUtil.getAge(user_birthday);
		}
		retMap.put("normal_pef", calculatePEF(Integer.parseInt(user_sex), user_age, Float.parseFloat(user_height)));
		return StdDTO.setSuccess(retMap);
	}

	@Override
	public StdDTO getPage(String open_id, String year_month, int pageno, int pagesize) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> userMap = userAppDao.getUserByOpenId(open_id);
		long user_id = (long) userMap.get("user_id");
		List<Map<String, Object>> dataList;
		int pagecount = 0;
		if (StringUtil.isEmpty(year_month)) {
			dataList = lungCapacityDao.pageQuery(user_id, pageno, pagesize);
			pagecount = lungCapacityDao.pageNumber(user_id, pagesize);
		} else {
			dataList = lungCapacityDao.queryByMonth(user_id, year_month, pageno, pagesize);
			pagecount = lungCapacityDao.pageNumberByMonth(user_id, year_month, pagesize);
		}
		nFormat2.setMinimumFractionDigits(2);
		nFormat0.setMinimumFractionDigits(0);
		@SuppressWarnings("serial")
		Object data = dataList.stream()
				.collect(Collectors.groupingBy(m -> DateUtil.dateFormat((Date) m.get("datetime"), "yyyy-MM")))
				.entrySet().stream().map(s -> new HashMap<String, Object>() {
					{
						put("yearmonth", s.getKey());
						s.getValue().forEach(ss -> {
							ss.put("period", periodDefine(
									Integer.parseInt(DateUtil.dateFormat((Date) ss.get("datetime"), "HH"))));
							ss.put("datetime", DateUtil.dateFormat((Date) ss.get("datetime"), "yyyy-MM-dd HH:mm"));
							ss.put("fvc", nFormat2.format(ss.get("fvc")));
							ss.put("fev1", nFormat2.format(ss.get("fev1")));
							ss.put("pef", nFormat2.format(ss.get("pef")));
							ss.put("fvc_rate", nFormat0.format(ss.get("fvc_rate")));
							ss.put("fev1_rate", nFormat0.format(ss.get("fev1_rate")));
							ss.put("pef_rate", nFormat0.format(ss.get("pef_rate")));
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
		retMap.put("list", lungCapacityDao.queryMonth(user_id));
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

	/**
	 * 计算正常PEF
	 * 
	 * @param sex
	 * @param age
	 * @param height
	 * @return
	 */
	private double calculatePEF(int sex, int age, double height) {
		double pef = 0;
		if (sex % 2 == 1) { // 男
			pef = (75.6 + 20.4 * age - 0.41 * Math.pow(age, 2) + 0.002 * Math.pow(age, 3) + 1.19 * height) / 100;
		} else { // 女
			pef = (282.0 + 1.79 * age - 0.046 * Math.pow(age, 2) + 0.68) / 100;
		}
		return pef;
	}
}
