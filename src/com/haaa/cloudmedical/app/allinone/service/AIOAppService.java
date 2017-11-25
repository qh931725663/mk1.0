/**
 * 
 */
package com.haaa.cloudmedical.app.allinone.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.util.BeanUtil;
import com.haaa.cloudmedical.dao.AIODao;

/**
 * @author Bowen Fan
 *
 */
@Service
public class AIOAppService {

	@Resource
	private AIODao aioDao;

	private static String httpURL = "";

	// 加载图片服务器路径
	static {
		httpURL = BeanUtil.getProperty("dbconfig").getString("pictureuploadhttp");
	}

	/**
	 * 
	 * @Title: getTimeList 
	 * @Description: app端一体机数据历史列表查询 
	 * @param user_id 
	 * @return 
	 * @throws
	 */
	public InfoJson getTimeList(String user_id) {
		InfoJson infoJson = new InfoJson();
		List<Map<String, Object>> list = aioDao.getTimeList(user_id);
		infoJson.setData(list);
		infoJson.setCount(list.size());
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: getDetail 
	 * @Description:android端一体机数据详情查询，
	 * 如果传order_id，根据order_id查询明确的数据记录;如果只传递user_id，根据user_id查询最近一条记录 
	 * @param user_id 
	 * @param order_id 
	 * @return 
	 * @throws
	 */
	public InfoJson getDetail(String user_id, String order_id) {
		InfoJson infoJson = new InfoJson();
		Map<String, Object> map;
		if (order_id == null || order_id.equals("")) {
			map = aioDao.getAppLatestDetail(user_id);
		} else {
			map = aioDao.getAppDetailById(order_id);
		}
		if (map != null) {
			
			map.forEach((k, v) -> {
				if (v == null) {
					map.put(k, "");
				}
			});
			//判断是哪种一体机
			if (String.valueOf(map.get("equipment_property_order_id")).equals(Constant.SELFSERVICE_6500)) {
				infoJson.setInfo("6500");
			} else {
				infoJson.setInfo("9900");
			}
			//6500一体机时存的图片  拼接图片的路径  通过资源文件获取httpURL
			map.put("s6500_pic_index",
					map.get("s6500_pic_index").equals("") ? "" : httpURL + map.get("s6500_pic_index"));
			
			
			//封装ecgList到返回数据里
			List<Object> ecgList = new ArrayList<Object>();
			map.put("ecgList", ecgList);
			//9900一体机是存放的数值
			if (!map.get("ecg1").equals("")) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "I");
						put("image", httpURL + map.get("ecg1"));
					}
				});
			}
			if (!map.get("ecg2").equals("")) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "II");
						put("image", httpURL + map.get("ecg2"));
					}
				});
			}
			if (!map.get("ecg3").equals("")) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "III");
						put("image", httpURL + map.get("ecg3"));
					}
				});
			}
			if (!map.get("ecg4").equals("")) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "aVR");
						put("image", httpURL + map.get("ecg4"));
					}
				});
			}
			if (!map.get("ecg5").equals("")) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "aVL");
						put("image", httpURL + map.get("ecg5"));
					}
				});
			}
			if (!map.get("ecg6").equals("")) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "aVF");
						put("image", httpURL + map.get("ecg6"));
					}
				});
			}
			if (!map.get("ecg7").equals("")) {
				if (!map.get("ecg8").equals("")) {
					ecgList.add(new HashMap<String, Object>() {
						{
							put("name", "V1");
							put("image", httpURL + map.get("ecg7"));
						}
					});
					ecgList.add(new HashMap<String, Object>() {
						{
							put("name", "V2");
							put("image", httpURL + map.get("ecg8"));
						}
					});
					if (!map.get("ecg9").equals("")) {
						ecgList.add(new HashMap<String, Object>() {
							{
								put("name", "V3");
								put("image", httpURL + map.get("ecg9"));
							}
						});
					}
					if (!map.get("ecg10").equals("")) {
						ecgList.add(new HashMap<String, Object>() {
							{
								put("name", "V4");
								put("image", httpURL + map.get("ecg10"));
							}
						});
					}
					if (!map.get("ecg11").equals("")) {
						ecgList.add(new HashMap<String, Object>() {
							{
								put("name", "V5");
								put("image", httpURL + map.get("ecg11"));
							}
						});
					}
					if (!map.get("ecg12").equals("")) {
						ecgList.add(new HashMap<String, Object>() {
							{
								put("name", "V6");
								put("image", httpURL + map.get("ecg12"));
							}
						});
					}
				} else {
					ecgList.add(new HashMap<String, Object>() {
						{
							put("name", "V");
							put("image", httpURL + map.get("ecg7"));
						}
					});
				}	
			}
			//封装好后原始数据就不用了
			map.remove("ecg1");
			map.remove("ecg2");
			map.remove("ecg3");
			map.remove("ecg4");
			map.remove("ecg5");
			map.remove("ecg6");
			map.remove("ecg7");
			map.remove("ecg8");
			map.remove("ecg9");
			map.remove("ecg10");
			map.remove("ecg11");
			map.remove("ecg12");	
			infoJson.setData(map);
			infoJson.setCount(1);
		} else {
		}
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: getIOSDetail 
	 * @Description:ios端一体机数据详情查询，如果传order_id，根据order_id查询明确的数据记录如果只传递user_id，
	 *         根据user_id查询最近一条记录，并根据页面返回数据结构方便 
	 * @param user_id 
	 * @param order_id 
	 * @return 
	 * @throws
	 */
	@SuppressWarnings("serial")
	public InfoJson getIOSDetail(String user_id, String order_id) {
		InfoJson infoJson = new InfoJson();
		Map<String, Object> map;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (order_id == null || order_id.equals("")) {
			map = aioDao.getAppLatestDetail(user_id);
		} else {
			map = aioDao.getAppDetailById(order_id);
		}

		if (map != null) {
			map.forEach((k, v) -> {
				if (v == null) {
					map.put(k, "");
				}
			});
			if (String.valueOf(map.get("equipment_property_order_id")).equals(Constant.SELFSERVICE_6500)) {
				infoJson.setInfo("6500");
			} else {
				infoJson.setInfo("9900");
			}

			resultMap.put("date", map.get("date"));
			resultMap.put("report_6500", map.put("s6500_pic_index",
					map.get("s6500_pic_index").equals("") ? "" : httpURL + map.get("s6500_pic_index")));
			resultMap.put("date", map.get("date"));
			resultMap.put("report_6500", map.put("s6500_pic_index",
					map.get("s6500_pic_index").equals("") ? "" : httpURL + map.get("s6500_pic_index")));
			//list集合用于封装一般检查/肺活量/尿检/心电的数据       每项都为一个map集合
			List<Object> list = new ArrayList<Object>();
			resultMap.put("list", list);
			Map<String, Object> generalMap = new HashMap<String, Object>();
			generalMap.put("title", "一般检查");
			List<Object> generalList = new ArrayList<Object>();
			//普通检查的map 里面还含有存放多个map的list集合
			generalMap.put("datalist", generalList);
			Map<String, Object> lungMap = new HashMap<String, Object>();
			lungMap.put("title", "肺功能检查");
			List<Object> lungList = new ArrayList<Object>();
			lungMap.put("datalist", lungList);
			Map<String, Object> urineMap = new HashMap<String, Object>();
			urineMap.put("title", "尿常规检查");
			List<Object> urineList = new ArrayList<Object>();
			urineMap.put("datalist", urineList);

			Map<String, Object> ecgMap = new HashMap<String, Object>();
			ecgMap.put("title", "心电图");
			List<Object> ecgList = new ArrayList<Object>();
			ecgMap.put("datalist", ecgList);

			list.add(generalMap);
			list.add(lungMap);
			list.add(urineMap);
			list.add(ecgMap);
			
			//一般检查里面的list存放多个map集合
			generalList.add(new HashMap<String, Object>() {
				{
					put("name", "心率");
					put("value", map.get("HeartRate") + "bpm");
				}
			});
			generalList.add(new HashMap<String, Object>() {
				{
					put("name", "血氧");
					put("value", map.get("Oxygen") + "/" + map.get("PulseRate") + "bpm");
				}
			});
			generalList.add(new HashMap<String, Object>() {
				{
					put("name", "血压");
					put("value", map.get("HighPressure") + "/" + map.get("LowPressure") + "mmHg");
				}
			});
			generalList.add(new HashMap<String, Object>() {
				{
					put("name", "血糖");
					put("value", map.get("BloodSugar") + "mmol/L");
				}
			});
			generalList.add(new HashMap<String, Object>() {
				{
					put("name", "身高");
					put("value", map.get("user_height") + "cm");
				}
			});
			generalList.add(new HashMap<String, Object>() {
				{
					put("name", "体重");
					put("value", map.get("user_weight") + "Kg");
				}
			});
			generalList.add(new HashMap<String, Object>() {
				{
					put("name", "BMI");
					put("value", map.get("BMI") + "指数");
				}
			});
			generalList.add(new HashMap<String, Object>() {
				{
					put("name", "体温");
					put("value", map.get("temperature") + "℃");
				}
			});
			lungList.add(new HashMap<String, Object>() {
				{
					put("name", "FVC");
					put("value", map.get("fvc") + "L");
				}
			});
			lungList.add(new HashMap<String, Object>() {
				{
					put("name", "PEF");
					put("value", map.get("pef") + "L/S");
				}
			});
			lungList.add(new HashMap<String, Object>() {
				{
					put("name", "FEV1");
					put("value", map.get("fev1") + "L");
				}
			});
			urineList.add(new HashMap<String, Object>() {
				{
					put("name", "PH");
					put("value", map.get("PH"));
				}
			});
			urineList.add(new HashMap<String, Object>() {
				{
					put("name", "SG（比重）");
					put("value", map.get("SG"));
				}
			});
			urineList.add(new HashMap<String, Object>() {
				{
					put("name", "BLD（潜血）");
					put("value", map.get("BLD"));
				}
			});
			urineList.add(new HashMap<String, Object>() {
				{
					put("name", "KET（酮体）");
					put("value", map.get("KET"));
				}
			});
			urineList.add(new HashMap<String, Object>() {
				{
					put("name", "BIL（胆红素）");
					put("value", map.get("BIL"));
				}
			});
			urineList.add(new HashMap<String, Object>() {
				{
					put("name", "PRO（蛋白质）");
					put("value", map.get("PRO"));
				}
			});
			urineList.add(new HashMap<String, Object>() {
				{
					put("name", "URO（尿胆原）");
					put("value", map.get("URO"));
				}
			});
			urineList.add(new HashMap<String, Object>() {
				{
					put("name", "LEU（白细胞）");
					put("value", map.get("LEU"));
				}
			});
			urineList.add(new HashMap<String, Object>() {
				{
					put("name", "VC（抗坏血酸）");
					put("value", map.get("VC"));
				}
			});
			urineList.add(new HashMap<String, Object>() {
				{
					put("name", "NIT（亚硝酸盐）");
					put("value", map.get("NIT"));
				}
			});

			if (!map.get("ecg1").equals("")) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "I");
						put("image", httpURL + map.get("ecg1"));
					}
				});
			}
			if (!map.get("ecg2").equals("")) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "II");
						put("image", httpURL + map.get("ecg2"));
					}
				});
			}
			if (!map.get("ecg3").equals("")) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "III");
						put("image", httpURL + map.get("ecg3"));
					}
				});
			}
			if (!map.get("ecg4").equals("")) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "aVR");
						put("image", httpURL + map.get("ecg4"));
					}
				});
			}
			if (!map.get("ecg5").equals("")) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "aVL");
						put("image", httpURL + map.get("ecg5"));
					}
				});
			}
			if (!map.get("ecg6").equals("")) {
				ecgList.add(new HashMap<String, Object>() {
					{
						put("name", "aVF");
						put("image", httpURL + map.get("ecg6"));
					}
				});
			}
			if (!map.get("ecg7").equals("")) {
				if (!map.get("ecg8").equals("")) {
					ecgList.add(new HashMap<String, Object>() {
						{
							put("name", "V1");
							put("image", httpURL + map.get("ecg7"));
						}
					});
					ecgList.add(new HashMap<String, Object>() {
						{
							put("name", "V2");
							put("image", httpURL + map.get("ecg8"));
						}
					});
					if (!map.get("ecg9").equals("")) {
						ecgList.add(new HashMap<String, Object>() {
							{
								put("name", "V3");
								put("image", httpURL + map.get("ecg9"));
							}
						});
					}
					if (!map.get("ecg10").equals("")) {
						ecgList.add(new HashMap<String, Object>() {
							{
								put("name", "V4");
								put("image", httpURL + map.get("ecg10"));
							}
						});
					}
					if (!map.get("ecg11").equals("")) {
						ecgList.add(new HashMap<String, Object>() {
							{
								put("name", "V5");
								put("image", httpURL + map.get("ecg11"));
							}
						});
					}
					if (!map.get("ecg12").equals("")) {
						ecgList.add(new HashMap<String, Object>() {
							{
								put("name", "V6");
								put("image", httpURL + map.get("ecg12"));
							}
						});
					}
				} else {
					ecgList.add(new HashMap<String, Object>() {
						{
							put("name", "V");
							put("image", httpURL + map.get("ecg7"));
						}
					});
				}
			}
			infoJson.setCount(1);
			infoJson.setData(resultMap);
		} else {
		}
		infoJson.setStatus(1);
		return infoJson;
	}
}
