package com.haaa.cloudmedical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.haaa.cloudmedical.common.dao.BaseTemplateDao;

@Repository
public class RoleDao extends BaseTemplateDao {

	public List<Map<String, Object>> getRolesByCondition(String roleName, String authority, String downTime,
			String upTime, int pageno, int pagesize) {
		StringBuffer sql = new StringBuffer("SELECT a.order_id,DATE_FORMAT(a.create_date,'%Y-%m-%d %H:%i') date,a.role_name,a.role_note FROM n_role a ");
		List<Object> list = new ArrayList<Object>();
		if (authority != null && !authority.equals("")) {
			sql.append(" ,n_role_menu b WHERE a.order_id = b.role_code AND b.menu_code = ?");
			list.add(authority);
		}
		if (roleName != null && !roleName.equals("")) {
			if (sql.toString().contains("WHERE")) {
				sql.append(" AND a.role_name like ? ");
			} else {
				sql.append(" WHERE a.role_name like ? ");
			}
			list.add("%"+roleName.trim()+"%");
		}
		if (downTime != null && !downTime.equals("")) {
			if (sql.toString().contains("WHERE")) {
				sql.append(" AND a.create_date > ?");
			} else {
				sql.append(" WHERE a.create_date > ?");
			}
			list.add(downTime);
		}
		if (upTime != null && !upTime.equals("")) {
			if (sql.toString().contains("WHERE")) {
				sql.append(" AND a.create_date < ?");
			} else {
				sql.append(" WHERE a.create_date < ?");
			}
			list.add(upTime);
		}
		int start = (pageno - 1) * pagesize;
		list.add(start);
		list.add(pagesize);
		sql.append(" ORDER BY a.order_id ASC LIMIT ?,?");
		return jdbcTemplate.queryForList(sql.toString(), list.toArray());
	}

	public long addRole(Map<String, Object> data) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		Map<String, Object[]> property = this.getParamsAndValues(data);
		Object[] params = property.get("params");
		Object[] values = property.get("values");
		final String sql = "insert into n_role (" + StringUtils.join(params, ",") + ") values("
				+ StringUtils.repeat("?", ",", params.length) + ")";
		System.out.println(sql);
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, 1);
				for (int i = 0; i < values.length; i++) {
					ps.setObject(i + 1, values[i]);
				}
				return ps;
			}
		}, keyHolder);
		Long generatedId = keyHolder.getKey().longValue();
		return generatedId;
	}

	public Map<String, Object> getRoleById(String order_id) {
		String sql = "select * from n_role where order_id=?";
		return jdbcTemplate.queryForMap(sql, order_id);
	}

	public int deleteAuthortiesByRole(String role_code) {
		String sql = "delete n_role_menu where role_code=?";
		return jdbcTemplate.update(sql, role_code);
	}

	public int deleteRoleById(String order_id) {
		String sql = "delete n_role where order_id = ?";
		return jdbcTemplate.update(sql, order_id);
	}

	public int[] batchAddAuthorities(String[] strings, List<Object[]> list) {
		String sql = "insert into n_role_menu (" + StringUtils.join(strings, ",") + ") values ("
				+ StringUtils.repeat("?", ",", strings.length) + ")";
		return jdbcTemplate.batchUpdate(sql, list);

	}

	public long addAuthority(Map<String, Object> data) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		Map<String, Object[]> property = this.getParamsAndValues(data);
		Object[] params = property.get("params");
		Object[] values = property.get("values");
		final String sql = "insert into n_role_menu (" + StringUtils.join(params, ",") + ") values("
				+ StringUtils.repeat("?", ",", params.length) + ")";
		System.out.println(sql);
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, 1);
				for (int i = 0; i < values.length; i++) {
					ps.setObject(i + 1, values[i]);
				}
				return ps;
			}
		}, keyHolder);
		Long generatedId = keyHolder.getKey().longValue();
		return generatedId;

	}
}
