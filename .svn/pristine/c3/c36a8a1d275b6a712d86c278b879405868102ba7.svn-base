/**
 * 
 */
package com.haaa.cloudmedical.app.equipment.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haaa.cloudmedical.common.annotation.Log;
import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.util.DataTransfromUtil;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.dao.equipment.BloodOxygenDao;
import com.haaa.cloudmedical.dao.equipment.ElectrocardiographDao;
import com.haaa.cloudmedical.dao.equipment.EquipmentDao;
import com.haaa.cloudmedical.entity.BloodOxygen;
import com.haaa.cloudmedical.entity.Electrocardiograph;
import com.haaa.cloudmedical.entity.EquipmentUse;

/**
 * @author Bowen Fan
 *
 */
@Service
@Log(name = "app腕表")
public class WristWatchService {

	@Resource
	private EquipmentDao equipmentDao;

	@Resource
	private ElectrocardiographDao electrocardiographDao;

	@Resource
	private BloodOxygenDao bloodOxygenDao;

	@Transactional
	@Log(name = "心电血氧记录增加")
	public InfoJson addAll(EquipmentUse equipmentUse, Electrocardiograph electrocardiograph, BloodOxygen bloodOxygen)
			throws Exception {
		InfoJson infoJson = new InfoJson();
		if (equipmentUse.getCreate_date()==null) {
			Date date = new Date();
			equipmentUse.setCreate_date(DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm:ss"));
		}	
		equipmentUse.setEquipment_name("wristwatch");
		equipmentUse.setEquipment_property_order_id(Constant.WRIST_WATCH);
		Long id = (Long) equipmentDao.insertAndGetKey(equipmentUse, "c_equipment_use");

		electrocardiograph.setCreate_date(equipmentUse.getCreate_date());
		electrocardiograph.setEquipment_use_order_id(String.valueOf(id));
		bloodOxygen.setCreate_date(equipmentUse.getCreate_date());
		bloodOxygen.setEquipment_use_order_id(String.valueOf(id));

		long result1 = 0;
		long result2 = 0;
		if (bloodOxygen.getOxygen() != null && !bloodOxygen.getOxygen().equals("") && bloodOxygen.getPulseRate() != null
				&& !bloodOxygen.getPulseRate().equals("")) {
			result1 = (Long) bloodOxygenDao.insertAndGetKey(bloodOxygen, "c_pulse_bloodoxygen");
		}
		if (electrocardiograph.getEcg1() != null && !electrocardiograph.getEcg1().equals("")
				&& electrocardiograph.getHeartRate() != null && !electrocardiograph.getHeartRate().equals("")
				&& electrocardiograph.getResult() != null && !electrocardiograph.getResult().equals("")) {
			DataTransfromUtil.electroTrans(electrocardiograph);
			result2 = (Long) electrocardiographDao.insertAndGetKey(electrocardiograph, "c_electrocardiograph");
		}

		// Map<String, Object> map1 = bloodOxygenDao.queryDetailById(result1,
		// "c_pulse_bloodoxygen");
		// Map<String, Object> map2 =
		// electrocardiographDao.queryDetailById(result2,
		// "c_electrocardiograph");
		//
		// map1.put("datetime", DateUtil.DateFormat(map1.remove("create_date"),
		// "yyyy-MM-dd"));
		// map2.put("datetime", DateUtil.DateFormat(map2.remove("create_date"),
		// "yyyy-MM-dd"));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("equipment_use_order_id", id);
		infoJson.setData(map);
		infoJson.setStatus(1);
		return infoJson;
	}

}
