package com.haaa.cloudmedical.common.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.haaa.cloudmedical.common.util.BeanPropertyUtil;


public class BaseJdbcDao{
	
	@Autowired
	private DataSource dataSource;
	
	

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// insert with object
	public int insert(Object object, String tabName) throws SQLException, Exception {
		int rows = 0;
		String[] params = BeanPropertyUtil.getPropertyNameAndValue(object);
		if(params[1].length()>0){
			String sql = "insert into " + tabName + "(" + params[0] + ") values(" + params[1] + ")";
			Connection conn = dataSource.getConnection();
			rows = conn.prepareStatement(sql).executeUpdate();
		}
		return rows;
	}

	// insert with map
	public int insert(Map<String, Object> map, String tabName) throws SQLException, Exception {
		int rows = 0;
		if(map.size()>0){
			String[] params = BeanPropertyUtil.getPropertyNameAndValue(map);
			String sql = "insert into " + tabName + "(" + params[0] + ") values(" + params[1] + ")";
			System.out.println(sql);
			Connection conn = dataSource.getConnection();
			rows = conn.prepareStatement(sql).executeUpdate();
		}
		return rows;
	}

	// delete with object
	public int delete(Object object, String tabName) throws SQLException, Exception {
		int rows = 0;
		Map<String, Object> map = new HashMap<String,Object>();
		BeanPropertyUtil.toMapFromObject(map,object);
		if(map.size()>0){
			StringBuilder sb = new StringBuilder("delete from " + tabName + " where 1=1");
			Set<String> set = map.keySet();
			for (String key : set) {
				String value = map.get(key).toString();
				if (map.get(key).getClass() == Date.class) {
					value = DateFormat.getDateTimeInstance().format(map.get(key));
				}
				sb.append(" and " + key + "='" + value + "'");
			}
			Connection conn = dataSource.getConnection();
			rows = conn.prepareStatement(sb.toString()).executeUpdate();
		}
		return rows;
	}

	// delete with map
	public int delete(Map<String, Object> map, String tabName) throws SQLException, Exception {
		int rows = 0;
		if(map.size()>0){
			StringBuilder sb = new StringBuilder("delete from " + tabName + " where 1=1");
			Set<String> set = map.keySet();
			for (String key : set) {
				String value = map.get(key).toString();
				if (map.get(key).getClass() == Date.class) {
					value = DateFormat.getDateTimeInstance().format(map.get(key));
				}
				sb.append(" and " + key + "='" + value + "'");
			}
			Connection conn = dataSource.getConnection();			
			rows = conn.prepareStatement(sb.toString()).executeUpdate();
		}
		return rows;
	}

	// update with object
	public int update(Object object, String tabName) throws SQLException, Exception {
		int rows = 0;
		Map<String, Object> map = new HashMap<String,Object>();
		BeanPropertyUtil.toMapFromObject(map,object);
		if(map.size()>0){
			int count = 0;
			int size = map.size();
			StringBuilder sb = new StringBuilder("update " + tabName + " set ");
			Set<String> set = map.keySet();			
			for (String key : set) {
				Object obj = map.get(key);
				if (obj.getClass() == Date.class)
					obj = DateFormat.getDateTimeInstance().format(obj);
				if (count < size - 1)
					sb.append(key + "='" + obj.toString() + "',");
				else
					sb.append(key + "='" + obj.toString() + "'");
				count++;
			}
			Connection conn = dataSource.getConnection();
			ResultSet rs = conn.getMetaData().getPrimaryKeys(null, null, tabName);
			rs.next();
			String primaryKey = rs.getString("COLUMN_NAME").toLowerCase();
			sb.append(" where 1=1 and " + primaryKey + "='" + map.get(primaryKey).toString() + "'");
			rows = conn.prepareStatement(sb.toString()).executeUpdate();
			if (rs != null)
				rs.close();
		}
		return rows;
	}

	// update with map
	public int update(Map<String, Object> map, String tabName) throws SQLException, Exception {
		int rows=0;
		if(map.size()>0){
			int count = 0;
			int size = map.size();
			StringBuilder sb = new StringBuilder("update " + tabName + " set ");
			Set<String> set = map.keySet();
			for (String key : set) {
				Object obj = map.get(key);
				if (map.get(key).getClass() == Date.class)
					obj = DateFormat.getDateTimeInstance().format(obj);
				if (count < size - 1)
					sb.append(key + "='" + obj.toString() + "',");
				else
					sb.append(key + "='" + obj.toString() + "'");
				count++;
			}
			Connection conn = dataSource.getConnection();
			ResultSet rs = conn.getMetaData().getPrimaryKeys(null, null, tabName);
			rs.next();
			String primaryKey = rs.getString("COLUMN_NAME").toLowerCase();
			sb.append(" where 1=1 and " + primaryKey + "='" + map.get(primaryKey).toString() + "'");
			rows = conn.prepareStatement(sb.toString()).executeUpdate();
			if (rs != null)
				rs.close();
		}
		return rows;
	}

	// 防sql注入
	public List<?> query(String sql, String[] paramValue, Class<?> cla) throws SQLException, Exception {
		List<Object> list = new ArrayList<Object>();
		int count = BeanPropertyUtil.charCount(sql, '?');
		Field[] fields = cla.getDeclaredFields();
		Field.setAccessible(fields, true);
		Connection conn = dataSource.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		for (int i = 0; i < count; i++) {
			st.setString(i + 1, paramValue[i]);
		}
		ResultSet rs = st.executeQuery();
		List<HashMap<String, Object>> resultSetList = BeanPropertyUtil.toHashMap(rs);
		for (HashMap<String, Object> map : resultSetList) {
			Object obj = cla.newInstance();
			for (Field field : fields) {
				field.set(obj, map.get(field.getName()));
			}
			list.add(obj);
		}
		this.close(conn,st, rs);
		return list;
	}

	// 防sql注入
	public List<HashMap<String, Object>> query(String sql, String[] paramValue) throws SQLException, Exception {
		List<HashMap<String, Object>> list = null;
		Connection conn = dataSource.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		if(paramValue!=null&&paramValue.length>0){
			int count = BeanPropertyUtil.charCount(sql, '?');
			for (int i = 0; i < count; i++) {
				st.setString(i + 1, paramValue[i]);
			}
		}
		ResultSet rs = st.executeQuery();
		list = BeanPropertyUtil.toHashMap(rs);
		this.close(conn,st, rs);
		return list;
	}

	public void close(Connection conn,PreparedStatement st, ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (st != null)
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 try {  
             if(!conn.isClosed()){  
                 try {  
                     conn.close();  
                 } catch (SQLException e) {  
                     e.printStackTrace();  
                 }  
             }  
         } catch (SQLException e) {  
             e.printStackTrace();  
         }  
     }  
}
