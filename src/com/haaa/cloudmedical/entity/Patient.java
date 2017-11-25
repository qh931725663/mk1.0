package com.haaa.cloudmedical.entity;

public class Patient {
	private String patient_id;
	private String patient_name;
	private String doctor_id;
	private String is_send;
	private String create_date;
	private String update_date;

	public String getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public String getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getIs_send() {
		return is_send;
	}

	public void setIs_send(String is_send) {
		this.is_send = is_send;
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
		return "Patient [patient_id=" + patient_id + ", patient_name=" + patient_name + ", doctor_id=" + doctor_id
				+ ", is_send=" + is_send + ", create_date=" + create_date + ", update_date=" + update_date + "]";
	}

}
