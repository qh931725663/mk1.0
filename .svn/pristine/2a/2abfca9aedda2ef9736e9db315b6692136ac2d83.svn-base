package com.haaa.cloudmedical.dao.equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.haaa.cloudmedical.common.util.BeanPropertyUtil;
import com.haaa.cloudmedical.entity.Electrocardiograph;

@Repository
public class ElectrocardiographDao extends EquipmentDao {

	public long add(Electrocardiograph electrocardiograph) {
		Map<String, Object> map = new HashMap<String, Object>();
		//通过自定义工具类将需要持久化的对象转换成map(通过反射方式拿到对象里面的字段)
		BeanPropertyUtil.toMapFromObject(map, electrocardiograph);
		return insert(map, "c_electrocardiograph");
	}

	/**
	 * 查询最近数据 AND A.create_date<NOW()
	 * 
	 * @param user_id
	 * @return
	 */
	public List<Map<String, Object>> queryRecent(long user_id, int recent) {
		String sql = "SELECT A.order_id,DATE_FORMAT(A.create_date,'%Y-%m-%d %H:%i') datetime,B.HeartRate,IF(B.result IS NULL, '',findName(B.result)) as result "
				+ " FROM c_equipment_use A,c_electrocardiograph B "
				+ " WHERE A.order_id=B.equipment_use_order_id AND A.user_id = ? AND "
				+ " DATE_SUB(NOW(),INTERVAL ? DAY) <A.create_date  ORDER BY A.create_date DESC";
		return jdbcTemplate.queryForList(sql, new Object[] { user_id, recent });
	}

	public List<Map<String, Object>> queryByMonth(long user_id, String year_month, int pageno, int pagesize) {
		String sql = "SELECT B.equipment_use_order_id,A.order_id,A.create_date datetime,B.HeartRate,B.result "
				+ " FROM c_equipment_use A,c_electrocardiograph B "
				+ " WHERE A.order_id=B.equipment_use_order_id AND A.user_id = ? AND "
				+ " DATE_FORMAT(A.create_date,'%Y-%m')=?  ORDER BY A.create_date DESC LIMIT ?,?";
		int start = (pageno - 1) * pagesize;
		return jdbcTemplate.queryForList(sql, user_id, year_month, start, pagesize);
	}

	public List<Map<String, Object>> pageQuery(long user_id, int pageno, int pagesize) {
		String sql = "SELECT B.equipment_use_order_id,A.order_id,A.create_date datetime,B.HeartRate,IF(B.result IS NULL, '',findName(B.result)) as result "
				+ " FROM c_equipment_use A,c_electrocardiograph B "
				+ " WHERE A.order_id=B.equipment_use_order_id AND A.user_id = ? ORDER BY A.create_date DESC LIMIT ?,?";
		int start = (pageno - 1) * pagesize;
		return jdbcTemplate.queryForList(sql, user_id, start, pagesize);
	}

	/*
	 * 查询一个年内有数据的月份
	 */
	public List<Map<String, Object>> queryMonth(long user_id) {
		String sql = "SELECT DISTINCT(DATE_FORMAT(A.create_date,'%Y-%m')) yearmonth "
				+ " FROM c_equipment_use A,c_electrocardiograph B WHERE "
				+ " A.order_id=B.equipment_use_order_id AND A.user_id = ? AND "
				+ " DATE_SUB(NOW(),INTERVAL 1 YEAR) <A.create_date ORDER BY A.create_date DESC ";
		return jdbcTemplate.queryForList(sql, user_id);
	}

	public Map<String, Object> getEcgByUserId(long user_id) {
		String sql = "SELECT DATE_FORMAT(A.create_date,'%Y-%m-%d %H:%i') datetime,B.HeartRate,findName(B.result) result,B.ecg1,B.ecg2,B.ecg3,B.ecg4,B.ecg5 FROM c_equipment_use A RIGHT JOIN c_electrocardiograph B ON A.order_id=B.equipment_use_order_id WHERE A.user_id = ?  ORDER BY A.create_date DESC limit 1";
		return jdbcTemplate.queryForMap(sql, user_id);
	}

	public Map<String, Object> getEcgByUseId(long use_id) {
		String sql = "SELECT DATE_FORMAT(create_date,'%Y-%m-%d %H:%i') datetime,HeartRate,findName(result) result,ecg1,ecg2,ecg3,ecg4,ecg5 FROM c_electrocardiograph WHERE equipment_use_order_id = ? ";
		return jdbcTemplate.queryForMap(sql, use_id);
	}

	public List<Map<String, Object>> getListByTime(long user_id, String datemin, String datemax) {
		List<Object> list = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("SELECT A.order_id,DATE_FORMAT(A.create_date,'%Y-%m-%d') datetime "
				+ " FROM c_equipment_use A,c_electrocardiograph B "
				+ " WHERE A.order_id=B.equipment_use_order_id AND A.user_id = ?  ");
		list.add(user_id);
		if (datemin != null && !datemin.equals("") && datemax != null && !datemax.equals("")) {
			sql.append(" AND DATE_FORMAT(A.create_date,'%Y-%m-%d')>=? AND DATE_FORMAT(A.create_date,'%Y-%m-%d') <=? ");
			list.add(datemin);
			list.add(datemax);
		} else if (datemin != null && !datemin.equals("")) {
			sql.append(" AND DATE_FORMAT(A.create_date,'%Y-%m-%d')>=? ");
			list.add(datemin);
		} else if (datemax != null && !datemax.equals("")) {
			sql.append(" AND DATE_FORMAT(A.create_date,'%Y-%m-%d')<=? ");
			list.add(datemax);
		} else {
			sql.append(" AND DATE_SUB(NOW(),INTERVAL ? DAY) <=A.create_date ");
			list.add(365);
		}
		sql.append(" ORDER BY A.create_date ASC ");
		return jdbcTemplate.queryForList(sql.toString(), list.toArray());
	}

	public int pageNumber(long user_id, int pagesize) {
		String sql = "SELECT COUNT(*) " + " FROM c_equipment_use A,c_electrocardiograph B "
				+ " WHERE A.order_id=B.equipment_use_order_id AND A.user_id = ? ";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, user_id);
		return count < 1 ? 0 : ((count - 1) / pagesize) + 1;
	}

	public int pageNumberByMonth(long user_id, String yearMonth, int pagesize) {
		String sql = "SELECT COUNT(*) " + " FROM c_equipment_use A,c_electrocardiograph B "
				+ " WHERE A.order_id=B.equipment_use_order_id AND A.user_id = ? AND DATE_FORMAT(A.create_date,'%Y-%m')=?  ";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, user_id, yearMonth);
		return count < 1 ? 0 : ((count - 1) / pagesize) + 1;
	}

	public Map<String, Object> getMostRecentData(long user_id) {
		String sql = "SELECT a.HeartRate,findName(a.result)Result,DATE_FORMAT(a.create_date,'%Y-%m-%d %H:%i')datetime FROM c_electrocardiograph a LEFT JOIN c_equipment_use b ON a.equipment_use_order_id = b.order_id WHERE b.user_id=? order by b.create_date desc limit 1";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, user_id);
		if (list != null && list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

}
