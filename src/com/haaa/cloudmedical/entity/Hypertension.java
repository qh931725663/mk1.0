package com.haaa.cloudmedical.entity;

public class Hypertension {
	// id
	private String order_id;

	// 患者id
	private String user_id;

	// 高血压风险评估级别
	private String risk;

	// 高血压管理级别
	private String level;

	// 基本信息完善标志
	private String flag1 = "0";

	// 辅助检查完成标志
	private String flag2 = "0";

	// 工作站工作完成标志
	private String flag3 = "0";

	// 档案完善标志
	private String flag  = "0";

	// 档案创建日期
	private String create_date;

	// 档案归档日期
	private String update_date;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getFlag1() {
		return flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public String getFlag2() {
		return flag2;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}

	public String getFlag3() {
		return flag3;
	}

	public void setFlag3(String flag3) {
		this.flag3 = flag3;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	@Override
	public String toString() {
		return "Hypertension [order_id=" + order_id + ", user_id=" + user_id + ", risk=" + risk + ", level=" + level
				+ ", flag1=" + flag1 + ", flag2=" + flag2 + ", flag3=" + flag3 + ", flag=" + flag + ", create_date="
				+ create_date + ", update_date=" + update_date + "]";
	}

}
