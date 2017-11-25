package com.haaa.cloudmedical.entity;

public class Plan {

	private String plan_order_id;

	// 监测项字典id
	private String plan_dict_order_id;

	// 监测类别名称
	private String item_name;

	// 监测项目名称
	private String plan_name;

	// 监测次数说明
	private String plan_content;

	// 目标监测次数
	private String plan_num;

	// 实际监测次数
	private String real_num;

	// 有效监测次数
	private String valid_num;

	// 计划完成率
	private String plan_rate;

	// 达标次数
	private String up2std_num;

	// 达标率
	private String up2std_rate;

	// 监测数值(平均)
	private String avg_value;

	// 监测完成状态
	private boolean flag;

	// 必填标志
	private String is_require;

	public String getPlan_order_id() {
		return plan_order_id;
	}

	public void setPlan_order_id(String plan_order_id) {
		this.plan_order_id = plan_order_id;
	}

	public String getPlan_dict_order_id() {
		return plan_dict_order_id;
	}

	public void setPlan_dict_order_id(String plan_dict_order_id) {
		this.plan_dict_order_id = plan_dict_order_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getPlan_name() {
		return plan_name;
	}

	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}

	public String getPlan_content() {
		return plan_content;
	}

	public void setPlan_content(String plan_content) {
		this.plan_content = plan_content;
	}

	public String getPlan_num() {
		return plan_num;
	}

	public void setPlan_num(String plan_num) {
		this.plan_num = plan_num;
	}

	public String getReal_num() {
		return real_num;
	}

	public void setReal_num(String real_num) {
		this.real_num = real_num;
	}

	public String getValid_num() {
		return valid_num;
	}

	public void setValid_num(String valid_num) {
		this.valid_num = valid_num;
	}

	public String getPlan_rate() {
		return plan_rate;
	}

	public void setPlan_rate(String plan_rate) {
		this.plan_rate = plan_rate;
	}

	public String getUp2std_num() {
		return up2std_num;
	}

	public void setUp2std_num(String up2std_num) {
		this.up2std_num = up2std_num;
	}

	public String getUp2std_rate() {
		return up2std_rate;
	}

	public void setUp2std_rate(String up2std_rate) {
		this.up2std_rate = up2std_rate;
	}

	public String getAvg_value() {
		return avg_value;
	}

	public void setAvg_value(String avg_value) {
		this.avg_value = avg_value;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getIs_require() {
		return is_require;
	}

	public void setIs_require(String is_require) {
		this.is_require = is_require;
	}

	@Override
	public String toString() {
		return "Plan [plan_order_id=" + plan_order_id + ", plan_dict_order_id=" + plan_dict_order_id + ", item_name="
				+ item_name + ", plan_name=" + plan_name + ", plan_content=" + plan_content + ", plan_num=" + plan_num
				+ ", real_num=" + real_num + ", valid_num=" + valid_num + ", plan_rate=" + plan_rate + ", up2std_num="
				+ up2std_num + ", up2std_rate=" + up2std_rate + ", avg_value=" + avg_value + ", flag=" + flag
				+ ", is_require=" + is_require + "]";
	}

}