package com.haaa.cloudmedical.platform.allinone.service;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haaa.cloudmedical.common.annotation.Log;
import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.service.UnicodeService;
import com.haaa.cloudmedical.common.util.BeanUtil;
import com.haaa.cloudmedical.common.util.DataTransfromUtil;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.common.util.IdcardInfoExtractor;
import com.haaa.cloudmedical.common.util.MD5Util;
import com.haaa.cloudmedical.common.util.StringUtil;
import com.haaa.cloudmedical.dao.AIODao;
import com.haaa.cloudmedical.dao.ChronicPlanDao;
import com.haaa.cloudmedical.dao.UserAppDao;
import com.haaa.cloudmedical.dao.equipment.BloodPressureDao;
import com.haaa.cloudmedical.dao.equipment.BloodSugarDao;
import com.haaa.cloudmedical.dao.equipment.BloodOxygenDao;
import com.haaa.cloudmedical.dao.equipment.EarThermometerDao;
import com.haaa.cloudmedical.dao.equipment.ElectrocardiographDao;
import com.haaa.cloudmedical.dao.equipment.EquipmentDao;
import com.haaa.cloudmedical.dao.equipment.LungCapacityDao;
import com.haaa.cloudmedical.dao.equipment.UrineTestDao;
import com.haaa.cloudmedical.entity.BloodOxygen;
import com.haaa.cloudmedical.entity.BloodPressure;
import com.haaa.cloudmedical.entity.BloodSugar;
import com.haaa.cloudmedical.entity.EarThermometer;
import com.haaa.cloudmedical.entity.Electrocardiograph;
import com.haaa.cloudmedical.entity.EquipmentUse;
import com.haaa.cloudmedical.entity.LungCapacity;
import com.haaa.cloudmedical.entity.Patient;
import com.haaa.cloudmedical.entity.UrineTest;
import com.haaa.cloudmedical.entity.User;
import com.haaa.cloudmedical.platform.user.controller.UserPlatFormController;

/**
 * 9900一体机，以及一体机数据查询
 * 
 * @author Bowen Fan
 *
 */
@Service
@Transactional
public class AIOService {

	@Resource
	private UserAppDao userAppDao;

	@Resource
	private AIODao aioDao;

	@Resource
	private UnicodeService unicodeService;

	@Resource
	private EquipmentDao equipmentDao;

	@Resource
	private BloodPressureDao bloodPressureDao;

	@Resource
	private BloodSugarDao bloodSugarDao;

	@Resource
	private EarThermometerDao earThermometerDao;

	@Resource
	private ElectrocardiographDao electrocardiographDao;

	@Resource
	private LungCapacityDao lungCapacityDao;

	@Resource
	private BloodOxygenDao bloodoxygenDao;

	@Resource
	private UrineTestDao urineTestDao;

	@Resource
	private UserPlatFormController userPlatFormController;
	
	//慢性病管理计划的dao
	@Autowired
	private ChronicPlanDao chronicPlanDao;

	private final String uploadPath = "upload/aio9900";

	private final String separator = "/";

	private static String baseDir = "";

	private static String httpURL = "";

	static {
		baseDir = BeanUtil.getProperty("dbconfig").getString("pictureuploaddir");
		httpURL = BeanUtil.getProperty("dbconfig").getString("pictureuploadhttp");

	}

	/**
	 * 
	 * @throws DocumentException
	 * @throws ParseException
	 * @Title: login @Description: 9900一体机登录 @param requestXml @return @throws
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> login(String requestXml) throws DocumentException {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Document doc = DocumentHelper.parseText(requestXml);// 将xml转为dom对象
		Element user = doc.getRootElement();// 获取根节点
		String authType = user.elementTextTrim("AuthType");
		Map<String, Object> userMap2 = null;
		if (authType.equals("08")) {
			Element authData = user.element("AuthData");
			String identityCard = authData.elementTextTrim("ProofNum");
			userMap2 = userAppDao.selectByIdCard(identityCard);
			if (userMap2 == null) {
				autoRegister(authData);
				userMap2 = userAppDao.selectByIdCard(identityCard);
			}
		} else if (authType.equals("07")) {
			Element authData = user.element("AuthData");
			String account = authData.elementTextTrim("Account");
			String pwd = authData.elementTextTrim("Pwd");
			if (account.length() > 12) {
				userMap2 = userAppDao.selectByIdCard(account);
			} else {
				userMap2 = userAppDao.selectByAccount(account);
			}
			pwd = MD5Util.encode(pwd);
			if (userMap2 == null) {
				responseMap.put("ResultCode", "9");
				responseMap.put("ResultMsg", "帐号不存在！！！");
				return responseMap;
			} else if (!pwd.equals(userMap2.get("user_pwd"))) {
				userMap2 = null;
			}
		}
		if (userMap2 != null) {
			responseMap.put("TimeStamp", DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
			String identityCard = (String) userMap2.get("user_card");
			if (identityCard == null) {
				responseMap.put("ResultCode", "9");
				responseMap.put("ResultMsg", "请先填写身份证号");
			} else {
				responseMap.put("ResultCode", "0");
				responseMap.put("ResultMsg", "");
				responseMap.put("ResultData", new HashMap<String, Object>());
				Map<String, Object> map = (Map<String, Object>) responseMap.get("ResultData");
				map.put("UserId", userMap2.get("user_id"));
				map.put("UserName", userMap2.get("user_name"));
				IdcardInfoExtractor idcardInfo = new IdcardInfoExtractor(identityCard);
				map.put("Birthday", DateUtil.dateFormat(idcardInfo.getBirthday(), "yyyy-MM-dd"));
				map.put("UserSex", idcardInfo.getGender());
				map.put("IdentityCard", identityCard);
			}
		} else {
			responseMap.put("TimeStamp", DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
			responseMap.put("ResultCode", "1");
			responseMap.put("ResultMsg", "用户密码错误！！！");
		}
		return responseMap;
	}

	private void autoRegister(Element authData) {
		User user = new User();
		Patient patient = new Patient();
		user.setUser_card(authData.elementTextTrim("ProofNum") + "");
		user.setUser_name(authData.elementTextTrim("Name") + "");
		user.setUser_address(authData.elementTextTrim("Address") + "");
		String nation="";
	    if (unicodeService.getIdByName(authData.elementTextTrim("Nation")).contains("族")) {
			nation = unicodeService.getIdByName(authData.elementTextTrim("Nation"));
		}else{
			nation =unicodeService.getIdByName(authData.elementTextTrim("Nation") + "族");
		}
		user.setUser_nation(nation);
		IdcardInfoExtractor idcardInfo = new IdcardInfoExtractor(authData.elementTextTrim("ProofNum") + "");
		user.setUser_birthday(DateUtil.dateFormat(idcardInfo.getBirthday(), "yyyy-MM-dd"));
		user.setUser_sex(idcardInfo.getGender().equals("男") ? Constant.MALE : Constant.FEMALE);
		user.setUser_pwd(MD5Util.encode(BeanUtil.getProperty("dbconfig").getString("initPassword")));
		Date date = new Date();
		user.setCreate_date(DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"));
		user.setUser_source(Constant.USER_SOURCE_MACHINE);
		Number user_id = aioDao.insertAndGetKey(user, "n_user");
		patient.setPatient_id(user_id.toString());
		patient.setPatient_name(user.getUser_name());
		patient.setCreate_date(DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"));
		aioDao.insertAndGetKey(patient, "d_patient");
	}

	/**
	 * 
	 * @throws Exception
	 * @Title: saveData @Description: 9900一体机数据保存 @param
	 *         requestData @return @throws
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> saveData(HttpServletRequest request) throws Exception {
		String requestData = request.getParameter("requestData");
		EquipmentUse equipmentUse = new EquipmentUse();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		/*
		 * Map<String, Object> dataMap = XMLUtil.xml2Map(requestData);
		 */ Document doc = DocumentHelper.parseText(requestData);// 将xml转为dom对象
		Element data = doc.getRootElement();// 获取根节点
		String use_id = "0";
		if (data.element("SenseData") != null && data.element("SenseData").elements().size() != 0) {
			equipmentUse.setCheck_data_source(Constant.BLUETOOTHUPLOAD);
			equipmentUse.setCreate_date(String.valueOf(data.elementTextTrim("TimeStamp")));
			equipmentUse.setEquipment_name("selfservice");
			equipmentUse.setEquipment_property_order_id(Constant.SELF_SERVICE);
			Map<String, Object> user = userAppDao.selectByIdCard((String) data.elementTextTrim("UserDiscernData"));
			equipmentUse.setUser_id(String.valueOf(user.get("user_id")));
			equipmentUse.setEquipment_number(((String) data.elementTextTrim("RoomId")));
			use_id = String.valueOf(equipmentDao.add(equipmentUse));
			request.getSession().setAttribute("use_id", use_id);
			Map<String, Object> map = new HashMap<String, Object>();
			Element senseData = data.element("SenseData");
			List<Element> item = senseData.elements("Item");
			String temp_use_id = use_id;
			item.forEach(s -> {
				try {
					save(map, s, temp_use_id,
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.elementTextTrim("TimeStamp")));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException();
				}
			});

			if (!map.isEmpty()) {
				map.put("equipment_use_order_id", use_id);
				map.put("create_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(String.valueOf(data.elementTextTrim("TimeStamp"))));

				aioDao.addCommon(map);
			}
			resultMap.put("TimeStamp", DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
			if (!use_id.equals("0")) {
				resultMap.put("ResultCode", "0");
				resultMap.put("ResultMsg", "数据同步成功");
				resultMap.put("Id", use_id);
			} else {
				resultMap.put("ResultCode", "1");
				resultMap.put("ResultMsg", "数据同步失败");
			}

		} else {
			resultMap.put("ResultCode", "1");
			resultMap.put("ResultMsg", "测量数据不能为空");
		}
		return resultMap;
	}

	/**
	 * @throws DocumentException
	 * 
	 * @Title: saveCardioData @Description: 保存心电数据 @param
	 * request @return @throws ParseException @throws
	 */
	@SuppressWarnings("unchecked")
	@Log(name = "保存心电数据")
	public Map<String, Object> saveCardioData(HttpServletRequest request) throws ParseException, DocumentException {
		String requestXml = request.getParameter("requestXml"); // 体检数据
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Document doc = DocumentHelper.parseText(requestXml);// 将xml转为dom对象
		Element data = doc.getRootElement();// 获取根节点
		String common_id = "0";
		if (data.element("EcgLeadList") != null && data.element("EcgLeadList").elements().size() != 0) {
			String use_id = (String) request.getSession().getAttribute("use_id"); // 拿取使用ID
			// 如果session没有拿到使用Id,从新生成一次使用记录
			if (use_id.equals("") || use_id == null) {
				EquipmentUse equipmentUse = new EquipmentUse();
				equipmentUse.setCheck_data_source(Constant.BLUETOOTHUPLOAD);
				equipmentUse.setCreate_date(
						data.elementTextTrim("TimeStamp"));
				equipmentUse.setEquipment_name("selfservice");
				equipmentUse.setEquipment_property_order_id(Constant.SELF_SERVICE);
				Map<String, Object> user = userAppDao.selectByIdCard(data.elementTextTrim("UserDiscernData"));
				equipmentUse.setUser_id(String.valueOf(user.get("user_id")));
				equipmentUse.setEquipment_number((data.elementTextTrim("RoomId")));
				use_id = String.valueOf(equipmentDao.add(equipmentUse));
			}
			Element ecgLeadList = data.element("EcgLeadList");
			List<Element> ecgLeadInfo = ecgLeadList.elements("EcgLeadInfo");
			Map<String, Object> map = aioDao.selectCommonByUseId(use_id);
			if (map == null) {
				map = new HashMap<String, Object>();
				map.put("create_date",
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.elementTextTrim("TimeStamp")));
				map.put("equipment_use_order_id", use_id);
				common_id = String.valueOf(aioDao.addCommon(map));
				map.put("order_id", common_id);
			} else {
				common_id = String.valueOf(map.get("order_id"));
			}
			Map<String, Object> common = map;
			List<Object> heartRate = new ArrayList<Object>(); // xml各个ecg信号会附带一个心率数据，取第一个数据保存
			String report_id = common_id;
			ecgLeadInfo.parallelStream().forEach(s -> {
				String type = s.elementTextTrim("LeadType");
				heartRate.add(s.elementTextTrim("HeartValue"));
				common.put("ecg_result", data.elementTextTrim("DetectionResult"));
				List<Integer> leadData = Arrays.asList((s.elementTextTrim("LeadData")).split(",")).stream()
						.map(a -> Integer.valueOf(a) - 2000).collect(Collectors.toList());
				String filename = "";
				// 取心电数据的最大最小值用于画图
				int max = leadData.stream().max((a, b) -> {
					if (a - b > 0) {
						return 1;
					} else if (a - b == 0) {
						return 0;
					} else {
						return -1;
					}
				}).get();
				int min = leadData.stream().min((a, b) -> {
					if (a - b > 0) {
						return 1;
					} else if (a - b == 0) {
						return 0;
					} else {
						return -1;
					}
				}).get();
				try {
					filename = saveChart(leadData, max, min, type);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				Map<String, Object> picture = new HashMap<String, Object>();
				// 保存心电图片到图片业务表内
				picture.put("parent_id", report_id);
				picture.put("pic_num", ecgLeadInfo.indexOf(s) + 1);
				picture.put("pic_type", Constant.PIC_TYPE_AIO9900REPORT);
				picture.put("medical_picture_upload", filename);
				picture.put("create_date", new Date());
				aioDao.addAioReportPic(picture);
				common.put(type, filename);
			});
			if (heartRate.size() > 0) {
				Electrocardiograph electrocardiograph = new Electrocardiograph(null, use_id,
						String.valueOf(heartRate.get(0)), null, null, null, null, null, null, DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"), null);
				electrocardiographDao.add(electrocardiograph);
			}
			common.put("equipment_use_order_id", use_id);
			common.put("create_date",
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.elementTextTrim("TimeStamp")));
			aioDao.updateCommon(common);

			resultMap.put("TimeStamp", DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));

			if (!common_id.equals("0")) {
				resultMap.put("ResultCode", "0");
				resultMap.put("ResultMsg", "数据同步成功");
			} else {
				resultMap.put("ResultCode", "1");
				resultMap.put("ResultMsg", "数据同步失败");
			}
		} else {
			resultMap.put("ResultCode", "0");
			resultMap.put("ResultMsg", "无心电数据");
		}
		return resultMap;
	}

	/**
	 * 
	 * @throws Exception
	 * @Title: fieldTrans @Description: 将一体机数据分别保存进七件套表格内 @param map @param
	 *         dataMap @param size @return @throws
	 */
	private void save(Map<String, Object> map, Element item, String use_id, Date date) throws Exception {
		String type = (String) item.elementTextTrim("ItemType");
		Element itemData = item.element("ItemData");
		switch (type) {
		case "bloodPress":
			BloodPressure bloodPressure = new BloodPressure();
			bloodPressure.setEquipment_use_order_id(use_id);
			bloodPressure.setCreate_date(DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm:ss"));
			bloodPressure.setHighPressure(itemData.elementTextTrim("SBP"));
			bloodPressure.setLowPressure(itemData.elementTextTrim("DBP"));
			bloodPressure.setPulseRate(itemData.elementTextTrim("PulseRate"));
			bloodPressureDao.add(bloodPressure);
			//当血压数据有值的时候,增加管理计划的iterm表记录   createBy设置的设备的服务号码
			insetBloodPressureIterm(use_id,DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm:ss"),Constant.SELF_SERVICE);
			break;
		case "bloodGlucose":
			BloodSugar bloodSugar = new BloodSugar();
			bloodSugar.setEquipment_use_order_id(use_id);
			bloodSugar.setCreate_date(DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm:ss"));
			bloodSugar.setBloodSugar(itemData.elementTextTrim("BloodGlucose"));
			bloodSugar.setMeasurement_period(String.valueOf(Constant.EMPTY_STOMACH));
			bloodSugarDao.add(bloodSugar);
			//管理向content里面插入数据计划  createBy设置的设备的服务号码
			insetBloodSugarIterm(use_id,DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm:ss"),bloodSugar.getMeasurement_period(),Constant.SELF_SERVICE);
			break;
		case "bloodOxygen":
			BloodOxygen bloodOxygen = new BloodOxygen();
			bloodOxygen.setEquipment_use_order_id(use_id);
			bloodOxygen.setCreate_date(DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm:ss"));
			bloodOxygen.setOxygen(itemData.elementTextTrim("BloodOxygen"));
			bloodOxygen.setPulseRate(itemData.elementTextTrim("PulseRate"));
			bloodoxygenDao.add(bloodOxygen);
			break;
		case "heightWeight":
			map.put("user_height", itemData.elementTextTrim("Height"));
			map.put("user_weight", itemData.elementTextTrim("Weight"));
			map.put("BMI", itemData.elementTextTrim("BMI"));
			break;
		case "temperature":
			EarThermometer earThermometer = new EarThermometer();
			earThermometer.setEquipment_use_order_id(use_id);
			earThermometer.setCreate_date(DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm:ss"));
			earThermometer.setTemperature(itemData.elementTextTrim("Thermometer"));
			earThermometerDao.add(earThermometer);
			break;
		case "electrocardio":

			break;
		case "lungFunction":
			LungCapacity lungCapacity = new LungCapacity();
			lungCapacity.setEquipment_use_order_id(use_id);
			lungCapacity.setCreate_date(DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm:ss"));
			lungCapacity.setFvc(itemData.elementTextTrim("FVC"));
			lungCapacity.setFev1(itemData.elementTextTrim("FevOne"));
			lungCapacity.setPef(itemData.elementTextTrim("PEF"));
			map.put("fef25", itemData.elementTextTrim("FiveOne"));
			map.put("fef75", itemData.elementTextTrim("FiveThree"));
			map.put("fef2575", itemData.elementTextTrim("FiveTwo"));
			lungCapacityDao.add(lungCapacity);
			break;
		case "urineRoutine":
			UrineTest urineTest = new UrineTest();
			urineTest.setEquipment_use_order_id(use_id);
			urineTest.setCreate_date(DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm:ss"));
			urineTest.setBil(itemData.elementTextTrim("LRY"));
			urineTest.setBld(itemData.elementTextTrim("BU"));
			urineTest.setGlu(itemData.elementTextTrim("GLU"));
			urineTest.setKet(itemData.elementTextTrim("KET"));
			urineTest.setLeu(itemData.elementTextTrim("LEU"));
			urineTest.setNit(itemData.elementTextTrim("NIT"));
			urineTest.setPh(itemData.elementTextTrim("PH"));
			urineTest.setPro(itemData.elementTextTrim("PRO"));
			urineTest.setSg(itemData.elementTextTrim("SG"));
			urineTest.setUro(itemData.elementTextTrim("UBG"));
			urineTest.setVc(itemData.elementTextTrim("VC"));
			DataTransfromUtil.urineTest9900Trans(urineTest);
			urineTestDao.add(urineTest);
			break;
		default:
			break;
		}
	}
	/**
	 * @description 管理向content里面插入数据计划 并且更新report表
	 * @param patient_id
	 * @param create_date
	 * @param create_by
	 */
	private void insetBloodPressureIterm(String patient_id,String create_date,String create_by) {
		//获取用户的管理计划
		List<Map<String, Object>> plan = bloodPressureDao.getPlan(patient_id);
		if (plan!=null) {
			for (Map<String, Object> map : plan) {
				map.put("create_date", create_date);
				map.put("create_by", create_by);
				map.put("check_time", create_date);
				long item_order_id = bloodPressureDao.insert(map, "p_plan_item");
				//调用存储过程更新report表
				chronicPlanDao.callStoreUpd(String.valueOf(item_order_id));
			}
		}		
	}
	
	/**
	 * @description 用于添加血糖记录的时候在管理计划表里面插入一条记录,并且调用存储过程更新report表
	 * @param patient_id
	 * @param create_date
	 * @param period
	 * @param create_by
	 */
	private void insetBloodSugarIterm(String patient_id, String create_date,String period, String create_by) {
		//获取用户的管理计划
		List<Map<String, Object>> plan = bloodSugarDao.getPlan(patient_id,period);
		if (plan!=null) {
			for (Map<String, Object> map : plan) {
				map.put("create_date", create_date);
				map.put("create_by", create_by);
				map.put("check_time", create_date);
				long item_order_id = bloodSugarDao.insert(map, "p_plan_item");
				//调用存储过程更新report表
				chronicPlanDao.callStoreUpd(String.valueOf(item_order_id));
			}
		}
	}
	
	
	
	

	/**
	 * 
	 * @Title: getTimeList @Description: 查询时间列表 @param user_id @param
	 *         datemin @param datemax @return @throws
	 */
	public InfoJson getTimeList(String user_id, String datemin, String datemax) {
		InfoJson infoJson = new InfoJson();
		List<Map<String, Object>> list = aioDao.getTimeList(user_id, datemin, datemax);
		infoJson.setData(list);

		return infoJson;
	}

	/**
	 * 
	 * @Title: getDetail @Description: 根据一体机数据order_id查询具体数据 @param
	 *         user_id @param order_id @return @throws
	 */
	@SuppressWarnings("serial")
	public InfoJson getDetail(String order_id) {
		InfoJson infoJson = new InfoJson();

		Map<String, Object> selfServiceCheck = aioDao.getDetail(order_id);
		selfServiceCheck.put("UserName", selfServiceCheck.remove("user_name"));
		selfServiceCheck.put("Hospital", selfServiceCheck.remove("hosp_name"));
		selfServiceCheck.put("date", selfServiceCheck.remove("create_date"));
		if (selfServiceCheck.get("s6500_pic_index") != null && !selfServiceCheck.get("s6500_pic_index").equals("")) {
			selfServiceCheck.put("report_type", "6500");
			selfServiceCheck.put("s6500_pic_index", httpURL + selfServiceCheck.get("s6500_pic_index"));
		} else {
			selfServiceCheck.put("report_type", "9900");
			selfServiceCheck.put("Temperature", selfServiceCheck.remove("temperature"));
			selfServiceCheck.put("BMI", selfServiceCheck.get("BMI"));
			if (selfServiceCheck.get("BMI") != null) {
				Float bmi = Float.valueOf(selfServiceCheck.get("BMI").toString());
				if (bmi < Constant.BMI1) {
					selfServiceCheck.put("BMIResult", "过轻");
				} else if (bmi < Constant.BMI2) {
					selfServiceCheck.put("BMIResult", "正常");
				} else if (bmi < Constant.BMI3) {
					selfServiceCheck.put("BMIResult", "过重");
				} else if (bmi < Constant.BMI4) {
					selfServiceCheck.put("BMIResult", "肥胖");
				} else {
					selfServiceCheck.put("BMIResult", "非常肥胖");
				}
			} else {
				selfServiceCheck.put("BMIResult", "");
			}

			/*
			 * if (selfServiceCheck.get("BLD") != null &&
			 * selfServiceCheck.get("BIL") != null &&
			 * selfServiceCheck.get("GLU") != null &&
			 * selfServiceCheck.get("KET") != null &&
			 * selfServiceCheck.get("LEU") != null &&
			 * selfServiceCheck.get("NIT") != null &&
			 * selfServiceCheck.get("PRO") != null &&
			 * selfServiceCheck.get("URO") != null && selfServiceCheck.get("SG")
			 * != null && selfServiceCheck.get("PH") != null) { if
			 * (!(String)(selfServiceCheck.get("BLD")).equals(Constant.
			 * URINEMINUS) ||
			 * !(String)(selfServiceCheck.get("BIL")).equals(Constant.
			 * URINEMINUS) ||
			 * !(String)(selfServiceCheck.get("GLU")).equals(Constant.
			 * URINEMINUS) ||
			 * !(String)(selfServiceCheck.get("KET")).equals(Constant.
			 * URINEMINUS) ||
			 * !(String)(selfServiceCheck.get("LEU")).equals(Constant.
			 * URINEMINUS) ||
			 * !(String)(selfServiceCheck.get("LEU")).equals(Constant.
			 * URINEMINUS) ||
			 * !(String)(selfServiceCheck.get("PRO")).equals(Constant.
			 * URINEMINUS) ||
			 * !(String)(selfServiceCheck.get("URO")).equals(Constant.
			 * URINEMINUS) || (float) selfServiceCheck.get("SG") <
			 * Constant.URINESG1 || (float) selfServiceCheck.get("SG") >
			 * Constant.URINESG2 || (float) selfServiceCheck.get("PH") <
			 * Constant.URINEPH1 || (float) selfServiceCheck.get("PH") >
			 * Constant.URINEPH2) { resultMap.put("UrineResult", "异常"); } else {
			 * resultMap.put("UrineResult", "正常"); } }
			 */
			List<Object> ecgList = new ArrayList<Object>();

			if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg1")))) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "I");
						put("image", httpURL + selfServiceCheck.remove("ecg1"));
					}
				});
			}
			if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg2")))) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "II");
						put("image", httpURL + selfServiceCheck.remove("ecg2"));
					}
				});
			}
			if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg2")))) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "III");
						put("image", httpURL + selfServiceCheck.remove("ecg3"));
					}
				});
			}
			if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg4")))) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "aVR");
						put("image", httpURL + selfServiceCheck.remove("ecg4"));
					}
				});
			}
			if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg5")))) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "aVL");
						put("image", httpURL + selfServiceCheck.remove("ecg5"));
					}
				});
			}
			if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg6")))) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "aVF");
						put("image", httpURL + selfServiceCheck.remove("ecg6"));
					}
				});
			}
			if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg7")))) {
				if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg8")))) {
					ecgList.add(new HashMap<String, Object>() {
						{
							put("name", "V1");
							put("image", httpURL + selfServiceCheck.remove("ecg7"));
						}
					});
					ecgList.add(new HashMap<String, Object>() {
						{
							put("name", "V2");
							put("image", httpURL + selfServiceCheck.remove("ecg8"));
						}
					});
					if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg9")))) {
						ecgList.add(new HashMap<String, Object>() {
							{
								put("name", "V3");
								put("image", httpURL + selfServiceCheck.remove("ecg9"));
							}
						});
					}
					if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg10")))) {
						ecgList.add(new HashMap<String, Object>() {
							{
								put("name", "V4");
								put("image", httpURL + selfServiceCheck.remove("ecg10"));
							}
						});
					}
					if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg11")))) {
						ecgList.add(new HashMap<String, Object>() {
							{
								put("name", "V5");
								put("image", httpURL + selfServiceCheck.remove("ecg11"));
							}
						});
					}
					if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg12")))) {
						ecgList.add(new HashMap<String, Object>() {
							{
								put("name", "V6");
								put("image", httpURL + selfServiceCheck.remove("ecg12"));
							}
						});
					}
				} else {
					ecgList.add(new HashMap<String, Object>() {
						{
							put("name", "V");
							put("image", httpURL + selfServiceCheck.remove("ecg7"));
						}
					});
				}
			}
			selfServiceCheck.put("ecgList", ecgList);
		}
		infoJson.setData(selfServiceCheck);
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: getDetailByTime @Description: 按时间查询一体机数据（包含具体数据） @param
	 *         user_id @param datemin @param datemax @return @throws
	 */
	@SuppressWarnings("serial")
	public InfoJson getDetailByTime(String user_id, String datemin, String datemax) {
		InfoJson infoJson = new InfoJson();
		List<Map<String, Object>> selfServiceCheckList = aioDao.getDetailByTime(user_id, datemin, datemax);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> selfServiceCheck : selfServiceCheckList) {
			selfServiceCheck.put("UserName", selfServiceCheck.remove("user_name"));
			selfServiceCheck.put("Hospital", selfServiceCheck.remove("hosp_name"));
			selfServiceCheck.put("date", selfServiceCheck.remove("create_date"));
			if (selfServiceCheck.get("s6500_pic_index") != null
					&& !selfServiceCheck.get("s6500_pic_index").equals("")) {
				selfServiceCheck.put("report_type", "6500");
				selfServiceCheck.put("s6500_pic_index", httpURL + selfServiceCheck.get("s6500_pic_index"));
			} else {
				selfServiceCheck.put("report_type", "9900");
				selfServiceCheck.put("OPulseRate", selfServiceCheck.get("pulseRate"));
				selfServiceCheck.put("PPulseRate", selfServiceCheck.get("pulseRate"));
				if (selfServiceCheck.get("BMI") != null) {
					Float bmi = Float.valueOf(selfServiceCheck.get("BMI").toString());
					if (bmi < Constant.BMI1) {
						selfServiceCheck.put("BMIResult", "过轻");
					} else if (bmi < Constant.BMI2) {
						selfServiceCheck.put("BMIResult", "正常");
					} else if (bmi < Constant.BMI3) {
						selfServiceCheck.put("BMIResult", "过重");
					} else if (bmi < Constant.BMI4) {
						selfServiceCheck.put("BMIResult", "肥胖");
					} else {
						selfServiceCheck.put("BMIResult", "非常肥胖");
					}
				} else {
					selfServiceCheck.put("BMIResult", "");
				}
				/*
				 * if (selfServiceCheck.get("BLD") != null &&
				 * selfServiceCheck.get("BIL") != null &&
				 * selfServiceCheck.get("GLU") != null &&
				 * selfServiceCheck.get("KET") != null &&
				 * selfServiceCheck.get("LEU") != null &&
				 * selfServiceCheck.get("NIT") != null &&
				 * selfServiceCheck.get("PRO") != null &&
				 * selfServiceCheck.get("URO") != null &&
				 * selfServiceCheck.get("SG") != null &&
				 * selfServiceCheck.get("PH") != null) { if
				 * (!(String)(selfServiceCheck.get("BLD")).equals(Constant
				 * .URINEMINUS) ||
				 * !(String)(selfServiceCheck.get("BIL")).equals(Constant.
				 * URINEMINUS) ||
				 * !(String)(selfServiceCheck.get("GLU")).equals(Constant.
				 * URINEMINUS) ||
				 * !(String)(selfServiceCheck.get("KET")).equals(Constant.
				 * URINEMINUS) ||
				 * !(String)(selfServiceCheck.get("LEU")).equals(Constant.
				 * URINEMINUS) ||
				 * !(String)(selfServiceCheck.get("LEU")).equals(Constant.
				 * URINEMINUS) ||
				 * !(String)(selfServiceCheck.get("PRO")).equals(Constant.
				 * URINEMINUS) ||
				 * !(String)(selfServiceCheck.get("URO")).equals(Constant.
				 * URINEMINUS) || (float) selfServiceCheck.get("SG") <
				 * Constant.URINESG1 || (float) selfServiceCheck.get("SG") >
				 * Constant.URINESG2 || (float) selfServiceCheck.get("PH") <
				 * Constant.URINEPH1 || (float) selfServiceCheck.get("PH") >
				 * Constant.URINEPH2) { resultMap.put("UrineResult", "异常"); }
				 * else { resultMap.put("UrineResult", "正常"); } }
				 */
				List<Object> ecgList = new ArrayList<Object>();

				if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg1")))) {
					ecgList.add(new HashMap<String, Object>() {
						{
							put("name", "I");
							put("image", httpURL + selfServiceCheck.remove("ecg1"));
						}
					});
				}
				if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg2")))) {
					ecgList.add(new HashMap<String, Object>() {
						{
							put("name", "II");
							put("image", httpURL + selfServiceCheck.remove("ecg2"));
						}
					});
				}
				if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg2")))) {
					ecgList.add(new HashMap<String, Object>() {
						{
							put("name", "III");
							put("image", httpURL + selfServiceCheck.remove("ecg3"));
						}
					});
				}
				if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg4")))) {
					ecgList.add(new HashMap<String, Object>() {
						{
							put("name", "aVR");
							put("image", httpURL + selfServiceCheck.remove("ecg4"));
						}
					});
				}
				if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg5")))) {
					ecgList.add(new HashMap<String, Object>() {
						{
							put("name", "aVL");
							put("image", httpURL + selfServiceCheck.remove("ecg5"));
						}
					});
				}
				if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg6")))) {
					ecgList.add(new HashMap<String, Object>() {
						{
							put("name", "aVF");
							put("image", httpURL + selfServiceCheck.remove("ecg6"));
						}
					});
				}
				if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg7")))) {
					if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg8")))) {
						ecgList.add(new HashMap<String, Object>() {
							{
								put("name", "V1");
								put("image", httpURL + selfServiceCheck.remove("ecg7"));
							}
						});
						ecgList.add(new HashMap<String, Object>() {
							{
								put("name", "V2");
								put("image", httpURL + selfServiceCheck.remove("ecg8"));
							}
						});
						if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg9")))) {
							ecgList.add(new HashMap<String, Object>() {
								{
									put("name", "V3");
									put("image", httpURL + selfServiceCheck.remove("ecg9"));
								}
							});
						}
						if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg10")))) {
							ecgList.add(new HashMap<String, Object>() {
								{
									put("name", "V4");
									put("image", httpURL + selfServiceCheck.remove("ecg10"));
								}
							});
						}
						if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg11")))) {
							ecgList.add(new HashMap<String, Object>() {
								{
									put("name", "V5");
									put("image", httpURL + selfServiceCheck.remove("ecg11"));
								}
							});
						}
						if (!StringUtil.isBlank((String)(selfServiceCheck.get("ecg12")))) {
							ecgList.add(new HashMap<String, Object>() {
								{
									put("name", "V6");
									put("image", httpURL + selfServiceCheck.remove("ecg12"));
								}
							});
						}
					} else {
						ecgList.add(new HashMap<String, Object>() {
							{
								put("name", "V");
								put("image", httpURL + selfServiceCheck.remove("ecg7"));
							}
						});
					}
				}
				selfServiceCheck.put("ecgList", ecgList);
			}
			resultList.add(selfServiceCheck);
		}
		infoJson.setData(resultList);
		infoJson.setStatus(1);
		return infoJson;
	}

//	public void saveTest() throws Exception {
//		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><TimeStamp>2017-06-10 12:11:32</TimeStamp><AccessCode>CONTEC</AccessCode><UserDiscernType>02</UserDiscernType><UserDiscernData>330501199107299411</UserDiscernData><PhoneNum>null</PhoneNum><SenseData><Item><ItemType>heightWeight</ItemType><ItemData><Height>174.5</Height><Weight>87</Weight><BMI>28.57</BMI><DetectionTime>2017-06-10 12:11:08</DetectionTime><DetectionResult></DetectionResult><EquCode></EquCode><Id></Id></ItemData></Item><Item><ItemType>temperature</ItemType><ItemData><Thermometer>36.9</Thermometer><TestPositon>01</TestPositon><DetectionTime>2017-06-10 12:11:08</DetectionTime><DetectionResult></DetectionResult><EquCode></EquCode><Id></Id></ItemData></Item></SenseData><RoomId>JM1410100034</RoomId></root>";
//		/*
//		 * System.out.println("------异常开始"); int result = 1/0;
//		 */
//	}

	/**
	 * 
	 * @Title: saveChart @Description: 将9900心电数据转化成图片文件进行保存 @param
	 * dataList @param max @param min @param type @return @throws
	 * IOException @throws
	 */
	public String saveChart(List<Integer> dataList, int max, int min, String type) throws IOException {

		XYSeries dataSeries = new XYSeries("1");

		for (int i = 0; i < dataList.size(); i++) {
			dataSeries.add(i, dataList.get(i));
		}

		XYSeriesCollection xyDataset = new XYSeriesCollection();

		xyDataset.addSeries(dataSeries);

		String filename = "";
		// Create the chart object

		ValueAxis xAxis = new NumberAxis("");
		// x轴坐标
		ValueAxis yAxis = new NumberAxis("");
		yAxis.setRange(min - 200D, max + 200D);
		// y轴坐标
		StandardXYItemRenderer renderer = new StandardXYItemRenderer(StandardXYItemRenderer.LINES);

		Font font = new Font("黑体", Font.TRUETYPE_FONT, 12);
		XYPlot plot = new XYPlot(xyDataset, xAxis, yAxis, renderer);
		JFreeChart chart = new JFreeChart("", font, plot, true);
		LegendTitle legendTitle = chart.getLegend();
		legendTitle.setVisible(false);
		chart.setBackgroundPaint(java.awt.Color.white);

		// Write the chart image to the temporary directory
		ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());

		String date = DateFormatUtils.format(new Date(), "yyyy" + separator + "MM" + separator + "dd");

		String dir = baseDir + separator + uploadPath + separator + date;
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		filename = UUID.randomUUID() + "_" + type + ".png";
		File file = new File(dir + separator + filename);
		String relativePath = uploadPath + separator + date + separator + filename;

		ChartUtilities.saveChartAsPNG(file, chart, 4500, 300);
		return relativePath;
	}
}
