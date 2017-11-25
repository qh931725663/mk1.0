package com.haaa.cloudmedical.wechat.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.haaa.cloudmedical.common.entity.StdDTO;
import com.haaa.cloudmedical.wechat.dao.WxUserDao;
import com.haaa.cloudmedical.wechat.service.IWeixinUserService;
import com.haaa.cloudmedical.wechat.util.WeixinUtil;

import net.sf.json.JSONObject;

@Service("weixinUserService")
public class IWeixinUserServiceImpl implements IWeixinUserService {

	@Autowired
	WxUserDao dao;

	@Override
	public String queryOpenidByCode(String code) {
		try {
			String url = WeixinUtil.AUTH_GET_OID;
			url = url.replace("APPID", WeixinUtil.APPID).replace("SECRET", WeixinUtil.APPSECRET).replace("CODE", code);
			JSONObject json = WeixinUtil.doGetStr(url);
			if (json.get("openid") != null) {
				return json.get("openid").toString();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public StdDTO userInfo(HttpServletRequest request) {
		//String openid = request.getSession().getAttribute("openid").toString();
		String openid = "osRqI0reTjY1x_qy57nj9C14hVC8";
		String sql = "select a.user_id,a.user_name,a.user_card,a.user_sex,a.user_age,a.user_height,a.user_weight,a.user_blood,a.user_medicare_card,a.user_marriage,"
				+ "a.user_medical_burden,a.user_work,c.doctor_name from n_user a left join d_patient b on a.user_id = b.patient_id left join "
				+ "d_doctor c on b.doctor_id = c.doctor_id where open_id =?";
		List<Map<String, Object>> list = dao.select(sql, openid);
		if (list != null && list.size() > 0) {
			Map<String, Object> m = list.get(0);
			return new StdDTO(1, m);
		}
		return new StdDTO(0, null);
	}

	@Override
	public StdDTO getUserHealthIndex(HttpServletRequest request) {
/*		String openid = request.getSession().getAttribute("openid").toString();
*/		String openid="zhangming";
		String sql = "select ahdi_value,ahdi_level,mrs_value,mrs_level from n_user where open_id=?";
		return StdDTO.setSuccess(dao.get(sql, new Object[] { openid })); 
	}

}
