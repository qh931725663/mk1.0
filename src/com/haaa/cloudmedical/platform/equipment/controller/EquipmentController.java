package com.haaa.cloudmedical.platform.equipment.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.app.util.CommonUserService;
import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.entity.BloodOxygen;
import com.haaa.cloudmedical.entity.BloodPressure;
import com.haaa.cloudmedical.entity.BloodSugar;
import com.haaa.cloudmedical.entity.EarThermometer;
import com.haaa.cloudmedical.entity.Electrocardiograph;
import com.haaa.cloudmedical.entity.EquipmentUse;
import com.haaa.cloudmedical.entity.LungCapacity;
import com.haaa.cloudmedical.entity.UrineTest;
import com.haaa.cloudmedical.platform.equipment.service.EquipmentService;

@RestController
@RequestMapping({ "/equipment" })
public class EquipmentController {
	@Resource
	private EquipmentService service;
	@Resource
	private CommonUserService commonUserService;

	private Logger logger = Logger.getLogger(EquipmentController.class);

	/**
	 * 后台数据添加以后要提取出去
	 * 
	 * @param user_id
	 * @param bloodOxygen
	 * @param bloodPressure
	 * @param bloodSugar
	 * @param earThermometer
	 * @param electrocardiograph
	 * @param lungCapacity
	 * @param urineTest
	 * @return
	 */
	@RequestMapping(value = { "/addAll.action" }, method = { RequestMethod.POST })
	public InfoJson addAll(EquipmentUse equipmentUse, BloodOxygen bloodOxygen, BloodPressure bloodPressure,
			BloodSugar bloodSugar, EarThermometer earThermometer, Electrocardiograph electrocardiograph,
			LungCapacity lungCapacity, UrineTest urineTest, String bloodOxygen_time, String bloodPressure_time,
			String bloodSugar_time, String earThermometer_time, String electrocardiograph_time, String urineTest_time,
			String lungCapacity_time, Integer oPulseRate, Integer pPulseRate,String create_by) {
		InfoJson infoJson = new InfoJson();
		try {

			Date date = new Date();
			String dateStr = DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm:ss");
			bloodOxygen.setCreate_date(bloodOxygen_time != null ? bloodOxygen_time : dateStr);
			bloodPressure.setCreate_date(bloodPressure_time != null ? bloodPressure_time : dateStr);
			bloodSugar.setCreate_date(bloodSugar_time != null ? bloodSugar_time : dateStr);
			earThermometer.setCreate_date(earThermometer_time != null ? earThermometer_time : dateStr);
			electrocardiograph.setCreate_date(electrocardiograph_time != null ? electrocardiograph_time : dateStr);
			lungCapacity.setCreate_date(lungCapacity_time != null ? lungCapacity_time : dateStr);
			urineTest.setCreate_date(urineTest_time != null ? urineTest_time : dateStr);
			//血氧脉搏，血压脉搏设置
			if (oPulseRate != null) {
				bloodOxygen.setPulseRate(String.valueOf(oPulseRate));
			}
			if (pPulseRate != null) {
				bloodPressure.setPulseRate(String.valueOf(pPulseRate));
			}
			infoJson = service.addAll(equipmentUse, bloodOxygen, bloodPressure, bloodSugar, earThermometer,
					electrocardiograph, lungCapacity, urineTest,create_by);
		} catch (Exception e) {
			logger.error("", e);

		}
		return infoJson;
	}

	@RequestMapping("/bloodPressureChart.action")
	public InfoJson bloodPressureChart(String user_id, String datemin, String datemax) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = service.getBloodPressureChart(user_id, datemin, datemax);
		} catch (Exception e) {
			logger.error("", e);

		}
		return infoJson;
	}

	@RequestMapping("/bloodSugarChart.action")
	public InfoJson getBloodSugarChart(String user_id, Integer period, String datemin, String datemax) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = service.getBloodSugarChart(user_id, period, datemin, datemax);
		} catch (Exception e) {
			logger.error("", e);

		}
		return infoJson;
	}

	@RequestMapping("/bloodOxygenChart.action")
	public InfoJson getBloodOxygenChart(String user_id, String datemin, String datemax) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = service.getBloodOxygenChart(user_id, datemin, datemax);
		} catch (Exception e) {
			logger.error("", e);

		}
		return infoJson;
	}

	@RequestMapping("/earThermometerChart.action")
	public InfoJson getEarThermometerChart(String user_id, String datemin, String datemax) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = service.getEarThermometerChart(user_id, datemin, datemax);
		} catch (Exception e) {
			logger.error("", e);

		}
		return infoJson;
	}

	@RequestMapping("/lungCapacityChart.action")
	public InfoJson getLungCapacityChart(String user_id, String datemin, String datemax) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = service.getLungCapacityChart(user_id, datemin, datemax);
		} catch (Exception e) {
			logger.error("", e);

		}
		return infoJson;
	}

	@RequestMapping("/electrocardiographList.action")
	public InfoJson electrocardioList(String user_id, String datemin, String datemax) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = service.getElectrocardiographList(user_id, datemin, datemax);
		} catch (Exception e) {
			logger.error("", e);

		}
		return infoJson;
	}

	@RequestMapping("/electrocardiographChart.action")
	public InfoJson electrocardioChart(String order_id) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = service.getElectrocardiographChart(order_id);
		} catch (Exception e) {
			logger.error("", e);

		}
		return infoJson;
	}

	@RequestMapping("/bloodOxygenPage.action")
	public InfoJson getBloodOxygenPage(String user_id, String datemin, String datemax, Integer pageno,
			Integer pagesize) {
		InfoJson infoJson = new InfoJson();
		try {
			if (pageno == null) {
				pageno = 1;
			}
			if (pagesize == null) {
				pagesize = Constant.DEFAULT_PAGESIZE;
			}
			infoJson = service.getBloodOxygenPage(user_id, datemin, datemax, pageno, pagesize);
		} catch (Exception e) {
			logger.error("", e);

		}
		return infoJson;
	}

	@RequestMapping("/bloodPressurePage.action")
	public InfoJson getBloodPressurePage(String user_id, String datemin, String datemax, Integer pageno,
			Integer pagesize) {
		InfoJson infoJson = new InfoJson();
		try {
			if (pageno == null) {
				pageno = 1;
			}
			if (pagesize == null) {
				pagesize = Constant.DEFAULT_PAGESIZE;
			}
			infoJson = service.getBloodPressurePage(user_id, datemin, datemax, pageno, pagesize);
		} catch (Exception e) {
			logger.error("", e);

		}
		return infoJson;
	}

	@RequestMapping("/bloodSugarPage.action")
	public InfoJson getBloodSugarPage(String user_id, Integer period, String datemin, String datemax, Integer pageno,
			Integer pagesize) {
		InfoJson infoJson = new InfoJson();
		try {
			if (pageno == null) {
				pageno = 1;
			}
			if (pagesize == null) {
				pagesize = Constant.DEFAULT_PAGESIZE;
			}
			infoJson = service.getBloodSugarPage(user_id, period, datemin, datemax, pageno, pagesize);
		} catch (Exception e) {
			logger.error("", e);

		}
		return infoJson;
	}

	@RequestMapping("/earThermometerPage.action")
	public InfoJson getEarThermometer(String user_id, String datemin, String datemax, Integer pageno,
			Integer pagesize) {
		InfoJson infoJson = new InfoJson();
		try {
			if (pageno == null) {
				pageno = 1;
			}
			if (pagesize == null) {
				pagesize = Constant.DEFAULT_PAGESIZE;
			}
			infoJson = service.getEarThermometerPage(user_id, datemin, datemax, pageno, pagesize);
		} catch (Exception e) {
			logger.error("", e);

		}
		return infoJson;
	}

	@RequestMapping("/lungCapacityPage.action")
	public InfoJson getLungCapacityPage(String user_id, String datemin, String datemax, Integer pageno,
			Integer pagesize) {
		InfoJson infoJson = new InfoJson();
		try {
			if (pageno == null) {
				pageno = 1;
			}
			if (pagesize == null) {
				pagesize = Constant.DEFAULT_PAGESIZE;
			}
			infoJson = service.getLungCapacityPage(user_id, datemin, datemax, pageno, pagesize);
		} catch (Exception e) {
			logger.error("", e);

		}
		return infoJson;
	}

	@RequestMapping("/urineTestPage.action")
	public InfoJson getUrineTestPage(String user_id, String datemin, String datemax, Integer pageno, Integer pagesize) {
		InfoJson infoJson = new InfoJson();
		try {
			if (pageno == null) {
				pageno = 1;
			}
			if (pagesize == null) {
				pagesize = Constant.DEFAULT_PAGESIZE;
			}
			infoJson = service.getUrineTestPage(user_id, datemin, datemax, pageno, pagesize);
		} catch (Exception e) {
			logger.error("", e);

		}
		return infoJson;
	}

}
