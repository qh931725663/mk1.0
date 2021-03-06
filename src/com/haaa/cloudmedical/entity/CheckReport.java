package com.haaa.cloudmedical.entity;

public class CheckReport {
	private String order_id;
	private String report_no;
	private String user_id;
	private String user_name;
	private String check_time;
	private String hosp_order_id;
	private String hosp_name;
	private String create_date;
	private String update_date;
	private String department_order_id;
	private String department_name;
	private String report_doctor;
	private String report_upload_index;
	private String doctor_id;
	private String doctor_name;
	private String report_doctor_id;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getReport_no() {
		return report_no;
	}

	public void setReport_no(String report_no) {
		this.report_no = report_no;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getCheck_time() {
		return check_time;
	}

	public void setCheck_time(String check_time) {
		this.check_time = check_time;
	}

	public String getHosp_order_id() {
		return hosp_order_id;
	}

	public void setHosp_order_id(String hosp_order_id) {
		this.hosp_order_id = hosp_order_id;
	}

	public String getHosp_name() {
		return hosp_name;
	}

	public void setHosp_name(String hosp_name) {
		this.hosp_name = hosp_name;
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

	public String getDepartment_order_id() {
		return department_order_id;
	}

	public void setDepartment_order_id(String department_order_id) {
		this.department_order_id = department_order_id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getReport_doctor() {
		return report_doctor;
	}

	public void setReport_doctor(String report_doctor) {
		this.report_doctor = report_doctor;
	}

	public String getReport_upload_index() {
		return report_upload_index;
	}

	public void setReport_upload_index(String report_upload_index) {
		this.report_upload_index = report_upload_index;
	}

	public String getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getReport_doctor_id() {
		return report_doctor_id;
	}

	public void setReport_doctor_id(String report_doctor_id) {
		this.report_doctor_id = report_doctor_id;
	}

	@Override
	public String toString() {
		return "CheckReport [order_id=" + order_id + ", report_no=" + report_no + ", user_id=" + user_id
				+ ", user_name=" + user_name + ", check_time=" + check_time + ", hosp_order_id=" + hosp_order_id
				+ ", hosp_name=" + hosp_name + ", create_date=" + create_date + ", update_date=" + update_date
				+ ", department_order_id=" + department_order_id + ", department_name=" + department_name
				+ ", report_doctor=" + report_doctor + ", report_upload_index=" + report_upload_index + ", doctor_id="
				+ doctor_id + ", doctor_name=" + doctor_name + ", report_doctor_id=" + report_doctor_id + "]";
	}

}
