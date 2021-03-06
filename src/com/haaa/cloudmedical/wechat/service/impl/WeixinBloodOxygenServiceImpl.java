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
import com.haaa.cloudmedical.dao.equipment.BloodOxygenDao;
import com.haaa.cloudmedical.wechat.service.IWeixinBloodOxygenService;

/**
 * @author Bowen Fan
 *
 */
@Service
public class WeixinBloodOxygenServiceImpl implements IWeixinBloodOxygenService {

	@Resource
	private BloodOxygenDao bloodOxygenDao;

	@Resource
	private UserAppDao userAppDao;

	private NumberFormat nFormat = DecimalFormat.getInstance();

	@Override
	public StdDTO getGraph(String open_id, int days) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> userMap = userAppDao.getUserByOpenId(open_id);
		long user_id = (long) userMap.get("user_id");
		List<Map<String, Object>> list = bloodOxygenDao.queryRecent(user_id, days);
		nFormat.setMaximumFractionDigits(0);
		List<Object> oxygenList = new LinkedList<Object>();
		List<Object> timeList = new LinkedList<Object>();
		list.forEach(s -> {
			oxygenList.add(s.get("Oxygen"));
			timeList.add(s.get("datetime"));
		});
		retMap.put("OxygenList", oxygenList);
		retMap.put("TimeList", timeList);
		retMap.put("Oxygen", nFormat.format(list.get(0).get("Oxygen")));
		retMap.put("PulseRate", nFormat.format(list.get(0).get("PulseRate")));
		retMap.put("datetime", list.get(0).get("datetime"));
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
			dataList = bloodOxygenDao.pageQuery(user_id, pageno, pagesize);
			pagecount = bloodOxygenDao.pageNumber(user_id , pagesize);
		} else {
			dataList = bloodOxygenDao.queryByMonth(user_id, year_month, pageno, pagesize);
			pagecount = bloodOxygenDao.pageNumberByMonth(user_id, year_month, pagesize);
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
							ss.put("Oxygen", nFormat.format(ss.get("Oxygen")));
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
		retMap.put("list", bloodOxygenDao.queryMonth(user_id));
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
}
