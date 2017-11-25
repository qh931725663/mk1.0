package com.haaa.cloudmedical.entity;

public class Hospital {
	private String order_id;
	private String hosp_name;
	private String area_id;
	private String create_date;
	private String hosp_level;
	private String hosp_icon;
	private String hosp_property;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getHosp_name() {
		return hosp_name;
	}

	public void setHosp_name(String hosp_name) {
		this.hosp_name = hosp_name;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getHosp_level() {
		return hosp_level;
	}

	public void setHosp_level(String hosp_level) {
		this.hosp_level = hosp_level;
	}

	public String getHosp_icon() {
		return hosp_icon;
	}

	public void setHosp_icon(String hosp_icon) {
		this.hosp_icon = hosp_icon;
	}

	public String getHosp_property() {
		return hosp_property;
	}

	public void setHosp_property(String hosp_property) {
		this.hosp_property = hosp_property;
	}

	@Override
	public String toString() {
		return "Hospital [order_id=" + order_id + ", hosp_name=" + hosp_name + ", area_id=" + area_id + ", create_date="
				+ create_date + ", hosp_level=" + hosp_level + ", hosp_icon=" + hosp_icon + ", hosp_property="
				+ hosp_property + "]";
	}

}
