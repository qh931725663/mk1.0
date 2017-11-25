/**
 * 
 */
package com.haaa.cloudmedical.wechat.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.StdDTO;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.dao.UserAppDao;
import com.haaa.cloudmedical.dao.equipment.UrineTestDao;
import com.haaa.cloudmedical.wechat.service.IWeixinUrineTestService;

/**
 * @author Bowen Fan
 *
 */
@Service
public class WeixinUrineTestServiceImpl implements IWeixinUrineTestService {

	@Resource
	private UrineTestDao urineTestDao;

	@Resource
	private UserAppDao userAppDao;

	private NumberFormat nFormat = DecimalFormat.getInstance();

	@Override
	public StdDTO getPage(String open_id, int pageno, int pagesize) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> userMap = userAppDao.getUserByOpenId(open_id);
		long user_id = (long) userMap.get("user_id");
		List<Map<String, Object>> dataList = urineTestDao.pageQuery(user_id, pageno, pagesize);
		int pagecount = urineTestDao.pageNumber(user_id, pagesize);
		dataList.forEach(ss -> {
			ss.put("period", periodDefine(Integer.parseInt(DateUtil.dateFormat((Date) ss.get("datetime"), "HH"))));
			ss.put("datetime", DateUtil.dateFormat((Date) ss.get("datetime"), "yyyy-MM-dd HH:mm"));
			nFormat.setMinimumFractionDigits(3);
			ss.put("SG", nFormat.format(ss.get("SG")));
			nFormat.setMinimumFractionDigits(1);
			ss.put("PH", nFormat.format(ss.get("PH")));
			ss.remove("equipment_use_order_id");
		});
		retMap.put("datalist", dataList);
		retMap.put("pageno", pageno);
		retMap.put("pagecount", pagecount);
		retMap.put("pagesize", pagesize);
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
