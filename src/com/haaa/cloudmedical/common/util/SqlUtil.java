package com.haaa.cloudmedical.common.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.haaa.cloudmedical.common.entity.Page;


public class SqlUtil {
	private static List<HashMap<String, Object>> getQuery(String sql, int start, int limit) {
		ResultSet rs = null;
		Connection conn = null;
		ResultSetMetaData rsmd = null;
		HashMap<String, Object> map = null;
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		StringBuffer sb = new StringBuffer(sql);
		sb.append(" limit " + start + "," + limit);
		try {
			conn = DataSourceUtil.getDataSource().getConnection();
			rs = conn.prepareStatement(sb.toString()).executeQuery();
			rsmd = rs.getMetaData();
			int size = rsmd.getColumnCount();
			String[] key = new String[size];
			for (int i = 1; i <= size; i++) {
				key[i - 1] = rsmd.getColumnLabel(i);
			}
			while (rs.next()) {
				map = new HashMap<String, Object>();
				for (int i = 0; i < size; i++) {
					map.put(key[i], rs.getObject(key[i]));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	private static int getRecordCount(String sql) {
		int size = 0;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from ( " + sql + " ) t ");

		try {
			conn = DataSourceUtil.getDataSource().getConnection();
			rs = conn.prepareStatement(sb.toString()).executeQuery();
			rs.next();
			size = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return size;
	}
	
	/**
	 * 分页查询
	 * @param sql
	 * @param pageNo
	 * @return
	 */
	public static Page pageQuery(String sql, int pageNo) {
		Page page = new Page();
		int start = (pageNo - 1) * page.getPageSize();
		int limit = page.getPageSize();
		int recordCount = getRecordCount(sql);
		List<HashMap<String, Object>> data = getQuery(sql, start, limit);
		int pageCount = recordCount % limit == 0 ? (recordCount / limit) : (recordCount / limit + 1);
		page.setPageCount(pageCount);
		page.setPageNo(pageNo);
		page.setRecordCount(recordCount);
		page.setData(data);
		return page;
	}
	
	/**
	 * 分页查询
	 * @param sql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static Page pageQuery(String sql, int pageNo, int pageSize) {
		Page page = new Page();
		page.setPageSize(pageSize);
		int start = (pageNo - 1) * page.getPageSize();
		int limit = page.getPageSize();
		int recordCount = getRecordCount(sql);
		List<HashMap<String, Object>> data = getQuery(sql, start, limit);
		int pageCount = recordCount % limit == 0 ? (recordCount / limit) : (recordCount / limit + 1);
		page.setPageCount(pageCount);
		page.setPageNo(pageNo);
		page.setRecordCount(recordCount);
		page.setData(data);
		return page;
	}

	public static List<HashMap<String, Object>> createQuery(String sql) throws SQLException, Exception {
		Connection conn = DataSourceUtil.getDataSource().getConnection();
		ResultSet rs = conn.prepareStatement(sql).executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int size = rsmd.getColumnCount();
		String[] columnName = new String[size];
		for (int i = 0; i < size; i++) {
			columnName[i] = rsmd.getColumnLabel(i + 1);
		}
		HashMap<String, Object> map = null;
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		while (rs.next()) {
			map = new HashMap<String, Object>();
			for (String str : columnName) {
				map.put(str.toLowerCase(), rs.getObject(str));
			}
			list.add(map);
		}
		return list;
	}
	
	public static boolean execute(String sql) throws SQLException, Exception {
		Connection conn = DataSourceUtil.getDataSource().getConnection();
		return conn.prepareStatement(sql).execute();
	}
	
}
