/**
 * 
 */
package com.haaa.cloudmedical.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.haaa.cloudmedical.common.dao.BaseTemplateDao;
import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.Page;

/**
 * @author Bowen Fan
 *
 */
@Repository
public class MaintenanceDao extends BaseTemplateDao {

	public Page getPage(String datemin, String datemax, int pageno, int pagesize) {
		List<Object> list = new ArrayList<Object>();
		String sql = "SELECT DATE_FORMAT(create_date,'%Y-%m-%d %H:%i') datetime,backup_index,findName(backup_is_success) backup_is_success,back_host_ip FROM n_database_backup where 1=1";
		if (datemin != null && !datemin.equals("") && datemax != null && !datemax.equals("")) {
			sql += " AND create_date>? AND create_date <? ";
			list.add(datemin);
			list.add(datemax);
		} else if (datemin != null && !datemin.equals("")) {
			sql += " AND create_date>? ";
			list.add(datemin);
		} else if (datemax != null && !datemax.equals("")) {
			sql += " AND create_date<? ";
			list.add(datemax);
		} else {
			sql += " AND DATE_SUB(NOW(),INTERVAL ? DAY) <create_date ";
			list.add(Constant.RECENT);
		}
		sql += " ORDER BY create_date DESC ";
		return super.pageQuery(sql, list.toArray(), pageno, pagesize);
	}

}
