package com.haaa.cloudmedical.common.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.util.redis.RedisCacheUtil;

/**
 * 
 * @author haaa
 *
 */
@Service
public class PositionService {
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private RedisCacheUtil redisCacheUtil;


	/**
	 * 初始化常量数据：省、市、医院、部门
	 */
	public void positionInfoInit() {
		List<Map<String, Object>> provinceList = null;
		String sql = "SELECT areacounty_id,areacounty_name FROM k_areacounty_all where parent_id = 100000";
		provinceList = jdbcTemplate.queryForList(sql);
		redisCacheUtil.setCacheList("province", provinceList);
		List<Map<String, Object>> cityList = null;
		for (Map<String, Object> map : provinceList) {
			String prov_id = String.valueOf(map.get("areacounty_id"));
			if (prov_id.equals("330000")) {
				sql = "SELECT areacounty_id,areacounty_name FROM k_areacounty_all where parent_id=" + prov_id;
				cityList = jdbcTemplate.queryForList(sql);
				redisCacheUtil.setCacheList("province_city_" + prov_id, cityList);
				List<Map<String, Object>> areaList = null;
				for (Map<String, Object> map2 : cityList) {
					String city_id = String.valueOf(map2.get("areacounty_id"));
					sql = "SELECT areacounty_id,areacounty_name FROM k_areacounty_all where parent_id=" + city_id;
					areaList = jdbcTemplate.queryForList(sql);
					redisCacheUtil.setCacheList("city_area_" + city_id, areaList);
					List<Map<String, Object>> hospList = null;
					for (Map<String, Object> map3 : areaList) {
						String area_id = String.valueOf(map3.get("areacounty_id"));
						sql = "SELECT order_id,hosp_name FROM k_hosp where area_id=" + area_id;
						hospList = jdbcTemplate.queryForList(sql);
						redisCacheUtil.setCacheList("area_hosp_" + area_id, hospList);
						List<Map<String, Object>> departList = null;
						for (Map<String, Object> map4 : hospList) {
							String hosp_id = String.valueOf(map4.get("order_id"));
							sql = "SELECT order_id,department_name FROM k_department where hosp_order_id=" + hosp_id;
							departList = jdbcTemplate.queryForList(sql);
							redisCacheUtil.setCacheList("hosp_department_" + hosp_id, departList);
							/*
							 * List<Map<String, Object>> doctorList = null; for
							 * (Map<String, Object> map5 : departList) { String
							 * department_id =
							 * String.valueOf(map5.get("order_id"));
							 * System.out.println(department_id); sql =
							 * "SELECT A.doctor_id,A.doctor_name,A.doctor_title,B.user_head_pic_upload_index FROM d_doctor A,n_user B where A.doctor_id=B.user_id AND A.department_order_id="
							 * + department_id; doctorList =
							 * jdbcTemplate.queryForList(sql);
							 * redisCacheUtil.setCacheList("department_doctor_"
							 * + department_id, doctorList); }
							 */
						}
					}
				}
			}

		}
		System.out.println("地区初始化结束");
	}

	/*
	 * 初始化医生信息到redis
	 */
	// public void doctorsInit() {
	// String sql = "select
	// A.doctor_id,A.doctor_name,A.doctor_title,A.department_order_id,B.user_head_pic_upload_index
	// FROM d_doctor A,n_user B where A.doctor_id=B.user_id";
	// List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
	// for (Map<String, Object> map : list) {
	// redisCacheUtil.setCacheObject("doctor_" + map.get("doctor_id"), map);
	// }
	// }

	/*
	 * 根据医生ID查询医生信息
	 */
	public InfoJson getDoctorById(Long doctor_id) {
		InfoJson infoJson = new InfoJson();
		String sql = "select A.doctor_id,A.doctor_name,A.doctor_title,A.department_order_id,B.user_head_pic_upload_index FROM d_doctor A,n_user B where A.doctor_id=B.user_id AND A.doctor_id = ?";
		infoJson.setData(jdbcTemplate.queryForList(sql, doctor_id).get(0));
		infoJson.setStatus(1);
		return infoJson;
	}

	// /**
	// * 根据条件查询地点
	// *
	// * @param type
	// * 查询类型
	// * @param parent_id
	// * 父ID
	// * @return
	// */
	// public List<Map<String, Object>> getPositionList(String type, String
	// parent_id) {
	// if (parent_id == null || parent_id.equals("")) {
	// return (List<Map<String, Object>>) redisCacheUtil.getCacheList(type);
	// }
	// System.out.println(type);
	// System.out.println(parent_id);
	// return (List<Map<String, Object>>) redisCacheUtil.getCacheList(type + "_"
	// + parent_id);
	// }
	//
	// /**
	// * 模糊查询从数据库内查询医院数据并把医院科室信息返回
	// * condition内容为空返回null
	// * @param condition
	// * @return
	// */
	// public List<Map<String, Object>> getHospitals(String condition) {
	// if (condition != null && !condition.equals("")) {
	// condition = "%"+condition+"%";
	// String sql = "SELECT order_id,hosp_name FROM k_hosp where hosp_name like
	// ?";
	// return jdbcTemplate.queryForList(sql, condition);
	// }
	// return null;
	// }

	/**
	 * 根据条件查询地点
	 * 
	 * @param type
	 *            查询类型
	 * @param parent_id
	 *            父ID
	 * @return
	 */
	public InfoJson getPositionList(String type, String parent_id) {
		InfoJson infoJson = new InfoJson();
		if (parent_id == null || parent_id.equals("")) {
			infoJson.setData(redisCacheUtil.getCacheList(type));
			infoJson.setStatus(1);
		} else {
			if (type.equals("department_doctor")) {
				String sql = "SELECT A.doctor_id,A.doctor_name,A.doctor_title,B.user_head_pic_upload_index FROM d_doctor A,n_user B where A.doctor_id=B.user_id AND A.department_order_id= ? AND B.user_type=1500002";
				infoJson.setData(jdbcTemplate.queryForList(sql, parent_id));
				infoJson.setStatus(1);
			} else if (type.equals("area_hosp")) {
				String sql = "SELECT order_id,hosp_name FROM k_hosp where area_id=?";
				infoJson.setData(jdbcTemplate.queryForList(sql, parent_id));
				infoJson.setStatus(1);
			} else if (type.equals("hosp_department")) {
				String sql = "SELECT order_id,department_name FROM k_department where hosp_order_id=?";
				infoJson.setData(jdbcTemplate.queryForList(sql, parent_id));
				infoJson.setStatus(1);
			} else {
				infoJson.setData(redisCacheUtil.getCacheList(type + "_" + parent_id));
				infoJson.setStatus(1);
			}
		}
		return infoJson;
	}

	public <T> void setPositionList(String type, String parent_id, List<T> list) {
		redisCacheUtil.setCacheList(type + "_" + parent_id, list);
	}

	/**
	 * 模糊查询从数据库内查询医院数据并把医院科室信息返回 condition内容为空返回null
	 * 
	 * @param condition
	 * @return
	 */
	public InfoJson getHospitals(String condition) {
		InfoJson infoJson = new InfoJson();
		String sql = "SELECT order_id,hosp_name,hosp_level,hosp_icon,hosp_property FROM k_hosp";
		if (condition != null && !condition.equals("")) {
			condition = "%" + condition + "%";
			sql += " where hosp_name like ?";
			infoJson.setData(jdbcTemplate.queryForList(sql, condition));
			infoJson.setStatus(1);
		} else {
			infoJson.setData(jdbcTemplate.queryForList(sql));
			infoJson.setStatus(1);
		}
		return infoJson;
	}

	public InfoJson getDoctors(String condition) {
		InfoJson infoJson = new InfoJson();
		if (condition != null && !condition.equals("")) {
			condition = "%" + condition + "%";
			String sql = "SELECT doctor_id,doctor_name FROM d_doctor a,n_user b where a.doctor_id=b.user_id and a.doctor_name like ?";
			infoJson.setData(jdbcTemplate.queryForList(sql, condition));
			infoJson.setStatus(1);
		} else {
			String sql = "SELECT doctor_id,doctor_name FROM d_doctor a,n_user b where a.doctor_id=b.user_id  ";
			infoJson.setData(jdbcTemplate.queryForList(sql));
			infoJson.setStatus(1);
		}
		return infoJson;
	}

	public InfoJson getHospitalsByArea(String name) {
		InfoJson infoJson = new InfoJson();
		String sql = "SELECT A.order_id,A.hosp_name,A.hosp_level,A.hosp_icon,A.hosp_property FROM k_hosp A,k_areacounty_all B WHERE B.areacounty_id = A.area_id AND B.areacounty_name = ? ";
		infoJson.setData(jdbcTemplate.queryForList(sql, name));
		return infoJson;
	}

	public List<Map<String, Object>> getProvinceList() {
		String sql = " select a.areacounty_id,a.areacounty_name,a.parent_id from k_areacounty_all a where a.parent_id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, 100000);
		return list;
	}

	public List<Map<String, Object>> getPrefectureCity(String parent_id) {
		String sql = " select a.areacounty_id,a.areacounty_name,a.parent_id from k_areacounty_all a where a.parent_id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, parent_id);
		return list;
	}

	public List<Map<String, Object>> getCountyCity(String parent_id) {
		String sql = " select a.areacounty_id,a.areacounty_name,a.parent_id from k_areacounty_all a where a.parent_id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, parent_id);
		return list;
	}

	public List<Map<String, Object>> getHospitalList(String area_id) {
		String sql = " select a.order_id,a.hosp_name,a.area_id,a.hosp_level,a.hosp_icon,a.hosp_property from k_hosp a where a.area_id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, area_id);
		return list;
	}

	public List<Map<String, Object>> getDepartmentList(String hosp_order_id) {
		String sql = " select a.order_id,a.department_name,a.hosp_order_id from k_department a where a.hosp_order_id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, hosp_order_id);
		return list;
	}

	public List<Map<String, Object>> getDoctorList(String department_order_id) {
		String sql = " select a.doctor_id,a.doctor_name,a.department_order_id,a.doctor_title from d_doctor a where a.department_order_id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, department_order_id);
		return list;
	}
}
