package com.haaa.cloudmedical.entity;

public class MeasurementRemind {
	private String order_id;
	private String user_id;
	private String measurement_remind_title;
	private String measurement_remind_content;
	private String measurement_remind_state;
	private String measrtrment_remind_type;
	private String equipment_order_id;
	private String monday;
	private String tuesday;
	private String wednesday;
	private String thursday;
	private String friday;
	private String saturday;
	private String sunday;
	private String interval_day;
	private String time1;
	private String time2;
	private String time3;
	private String time4;
	private String begin_day;
	private String create_date;
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

	public String getMeasurement_remind_title() {
		return measurement_remind_title;
	}

	public void setMeasurement_remind_title(String measurement_remind_title) {
		this.measurement_remind_title = measurement_remind_title;
	}

	public String getMeasurement_remind_content() {
		return measurement_remind_content;
	}

	public void setMeasurement_remind_content(String measurement_remind_content) {
		this.measurement_remind_content = measurement_remind_content;
	}

	public String getMeasurement_remind_state() {
		return measurement_remind_state;
	}

	public void setMeasurement_remind_state(String measurement_remind_state) {
		this.measurement_remind_state = measurement_remind_state;
	}

	public String getMeasrtrment_remind_type() {
		return measrtrment_remind_type;
	}

	public void setMeasrtrment_remind_type(String measrtrment_remind_type) {
		this.measrtrment_remind_type = measrtrment_remind_type;
	}

	public String getEquipment_order_id() {
		return equipment_order_id;
	}

	public void setEquipment_order_id(String equipment_order_id) {
		this.equipment_order_id = equipment_order_id;
	}

	public String getMonday() {
		return monday;
	}

	public void setMonday(String monday) {
		this.monday = monday;
	}

	public String getTuesday() {
		return tuesday;
	}

	public void setTuesday(String tuesday) {
		this.tuesday = tuesday;
	}

	public String getWednesday() {
		return wednesday;
	}

	public void setWednesday(String wednesday) {
		this.wednesday = wednesday;
	}

	public String getThursday() {
		return thursday;
	}

	public void setThursday(String thursday) {
		this.thursday = thursday;
	}

	public String getFriday() {
		return friday;
	}

	public void setFriday(String friday) {
		this.friday = friday;
	}

	public String getSaturday() {
		return saturday;
	}

	public void setSaturday(String saturday) {
		this.saturday = saturday;
	}

	public String getSunday() {
		return sunday;
	}

	public void setSunday(String sunday) {
		this.sunday = sunday;
	}

	public String getInterval_day() {
		return interval_day;
	}

	public void setInterval_day(String interval_day) {
		this.interval_day = interval_day;
	}

	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public String getTime3() {
		return time3;
	}

	public void setTime3(String time3) {
		this.time3 = time3;
	}

	public String getTime4() {
		return time4;
	}

	public void setTime4(String time4) {
		this.time4 = time4;
	}

	public String getBegin_day() {
		return begin_day;
	}

	public void setBegin_day(String begin_day) {
		this.begin_day = begin_day;
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
		return "MeasurementRemind [order_id=" + order_id + ", user_id=" + user_id + ", measurement_remind_title="
				+ measurement_remind_title + ", measurement_remind_content=" + measurement_remind_content
				+ ", measurement_remind_state=" + measurement_remind_state + ", measrtrment_remind_type="
				+ measrtrment_remind_type + ", equipment_order_id=" + equipment_order_id + ", monday=" + monday
				+ ", tuesday=" + tuesday + ", wednesday=" + wednesday + ", thursday=" + thursday + ", friday=" + friday
				+ ", saturday=" + saturday + ", sunday=" + sunday + ", interval_day=" + interval_day + ", time1="
				+ time1 + ", time2=" + time2 + ", time3=" + time3 + ", time4=" + time4 + ", begin_day=" + begin_day
				+ ", create_date=" + create_date + ", update_date=" + update_date + "]";
	}

}
