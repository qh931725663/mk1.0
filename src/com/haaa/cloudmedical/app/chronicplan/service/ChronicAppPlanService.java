package com.haaa.cloudmedical.app.chronicplan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.dao.ChronicPlanDao;
import com.haaa.cloudmedical.entity.Item;
import com.haaa.cloudmedical.entity.Plan;

@Service
public class ChronicAppPlanService {

	@Autowired
	private ChronicPlanDao dao;

	/**
	 * 管理计划列表
	 * 
	 * @param patient_id
	 * @param type
	 * @return
	 */
	public List<Map<String, String>> getChronicPlanList(String patient_id, String type) {
		return dao.getChronicPlanList(patient_id, type).stream()
				.sorted((m1, m2) -> m2.get("plan_begin").compareTo(m1.get("plan_begin"))).collect(Collectors.toList());
	}
	
	public int existsPlan(String patient_id, String type){
		return dao.existsPlan(patient_id, type);
	}

	/**
	 * 管理计划检查项的状态
	 * 
	 * @param order_id
	 * @return
	 */
	public List<Item> getChroncPlanItem(String order_id) {

		// 获取计划详情
		List<Map<String, String>> detail = dao.getChronicPlanDetail(order_id)
				.stream()
				.filter(m->m.get("plan_item_type")!=null)
				.filter(m->!m.get("plan_item_type").equals("8"))
				.collect(Collectors.toList());

		// 获取计划大项的编码和名称
		Map<String, String> type_name = detail.stream().collect(HashMap::new,
				(m1, m2) -> m1.put(m2.get("plan_item_type").toString(), m2.get("plan_item_name")),
				HashMap::putAll);
		Map<String, String> level = dao.getChronicPlanInfo(order_id);
		// 按照大项小项分组
		List<Item> items = new ArrayList<>(type_name.size());
		type_name.forEach((type, name) -> {
			Item item = new Item();
			item.setPlan_level(level.get("plan_level"));
			item.setLevel_name(level.get("level_name"));
			item.setItem_type(type);
			item.setItem_name(name);
			List<Plan> plans = detail.stream().filter(map -> map.get("plan_item_type").equals(type))
					.collect(ArrayList::new, (list, map) -> {
				Plan plan = new Plan();
				plan.setPlan_order_id(map.get("plan_order_id"));
				plan.setPlan_dict_order_id(map.get("plan_dict_order_id"));
				plan.setPlan_name(map.get("plan_name"));
				plan.setPlan_num(map.get("plan_num") == null ? null : map.get("plan_num"));
				plan.setReal_num(map.get("real_num") == null ? null : map.get("real_num"));
				plan.setValid_num(map.get("valid_num") == null ? null : map.get("valid_num"));
				plan.setPlan_rate(map.get("plan_rate") == null ? null : map.get("plan_rate"));
				plan.setPlan_content(map.get("plan_content") == null ? null : map.get("plan_content"));
				if (plan.getPlan_rate() == null || Double.parseDouble(plan.getPlan_rate()) < 100) {
					plan.setFlag(false);
					item.setFlag(false);
				} else {
					plan.setFlag(true);
					item.setFlag(true);
				}
				plan.setIs_require(map.get("is_require"));
				list.add(plan);
			} , ArrayList::addAll);
			item.setPlans(plans);
			items.add(item);
		});

		// 项目按照从小到大排序
		return items.stream().sorted((t1, t2) -> t1.getItem_type().compareTo(t2.getItem_type()))
				.collect(Collectors.toList());
	}

	/**
	 * 管理计划具体详情
	 * 
	 * @param order_id
	 * @return
	 */
	public List<Item> getChronicPlanDetail(String order_id) {

		// 获取计划详情
		List<Map<String, String>> detail = dao.getChronicPlanDetail(order_id).stream().filter(m->m.get("plan_item_type")!=null).collect(Collectors.toList());
		// 获取计划大项的编码和名称
		Map<String, String> type_name = detail.stream().collect(HashMap::new,
				(m1, m2) -> m1.put(m2.get("plan_item_type"), m2.get("plan_item_name")), HashMap::putAll);
		

		// 按照大项小项分组
		List<Item> items = new ArrayList<>(type_name.size());		
		type_name.forEach((type, name) -> {
			Item item = new Item();
			item.setItem_type(type);
			item.setItem_name(name);
			List<Plan> plans = detail.stream().filter(map -> map.get("plan_item_type").equals(type))
					.collect(ArrayList::new, (list, map) -> {
				Plan plan = new Plan();
				plan.setPlan_dict_order_id(map.get("plan_dict_order_id"));
				plan.setItem_name(name);
				plan.setPlan_name(map.get("plan_name"));
				plan.setPlan_num(map.get("plan_num") == null ? null : map.get("plan_num"));
				plan.setReal_num(map.get("real_num") == null ? null : map.get("real_num"));
				plan.setValid_num(map.get("valid_num") == null ? null : map.get("valid_num"));
				plan.setPlan_rate(map.get("plan_rate") == null ? null : map.get("plan_rate"));
				plan.setUp2std_num(map.get("up2std_num")==null?null:map.get("up2std_num"));
				plan.setUp2std_rate(map.get("up2std_rate")==null?null:map.get("up2std_rate"));
				plan.setAvg_value(map.get("avg_value")==null?null:map.get("avg_value"));
				list.add(plan);
			} , ArrayList::addAll);
			item.setPlans(plans);
			items.add(item);
		});
		
		return items.stream().sorted((t1, t2) -> t1.getItem_type().compareTo(t2.getItem_type()))
				.collect(Collectors.toList());
	}
	
	/**
	 * 管理计划具体详情
	 * 
	 * @param order_id
	 * @return
	 */
	public List<Map<String,String>> getChronicPlanDetail2(String order_id) {
		// 获取计划详情
		List<Map<String, String>> detail = dao.getChronicPlanDetail(order_id).stream().filter(m->m.get("plan_item_type")!=null).collect(Collectors.toList());
		return detail;
	}

}
