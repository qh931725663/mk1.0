/**
 * 
 */
package com.haaa.cloudmedical.platform.maintenance.service;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.dao.MaintenanceDao;

/**
 * @author Bowen Fan
 *
 */
@Service
public class MaintenanceService {
	
	@Resource
	private MaintenanceDao maintenanceDao;
	
	
	public InfoJson maintenancePage(String datemin,String datemax,int pageno,int pagesize){
		return InfoJson.setSucc(maintenanceDao.getPage(datemin, datemax, pageno, pagesize));
	}

}
