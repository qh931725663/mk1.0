package com.haaa.cloudmedical.entity;

/**
 * 
 * @author wp
 * @description 设备使用domain
 */
public class EquipmentUse {

	private String order_id;

	private String equipment_property_order_id;

	private String user_id;

	private String equipment_use_time;

	private String equipment_name;

	private String equipment_number;
	
	private String doctor_id;

	private String create_date;

	private String update_date;

	//区分手动和自动的
	private String check_data_source;
	


	/**
	 * 
	 */
	public EquipmentUse() {
		super();
	}

	/**
	 * @param order_id
	 * @param equipment_property_order_id
	 * @param user_id
	 * @param equipment_use_time
	 * @param equipment_name
	 * @param equipment_number
	 * @param doctor_id
	 * @param create_date
	 * @param update_date
	 * @param check_data_source
	 */
	public EquipmentUse(String order_id, String equipment_property_order_id, String user_id, String equipment_use_time,
			String equipment_name, String equipment_number, String doctor_id, String create_date, String update_date,
			String check_data_source) {
		super();
		this.order_id = order_id;
		this.equipment_property_order_id = equipment_property_order_id;
		this.user_id = user_id;
		this.equipment_use_time = equipment_use_time;
		this.equipment_name = equipment_name;
		this.equipment_number = equipment_number;
		this.doctor_id = doctor_id;
		this.create_date = create_date;
		this.update_date = update_date;
		this.check_data_source = check_data_source;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getEquipment_property_order_id() {
		return equipment_property_order_id;
	}

	public void setEquipment_property_order_id(String equipment_property_order_id) {
		this.equipment_property_order_id = equipment_property_order_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getEquipment_use_time() {
		return equipment_use_time;
	}

	public void setEquipment_use_time(String equipment_use_time) {
		this.equipment_use_time = equipment_use_time;
	}

	public String getEquipment_name() {
		return equipment_name;
	}

	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}

	public String getEquipment_number() {
		return equipment_number;
	}

	public void setEquipment_number(String equipment_number) {
		this.equipment_number = equipment_number;
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

	public String getCheck_data_source() {
		return check_data_source;
	}

	public void setCheck_data_source(String check_data_source) {
		this.check_data_source = check_data_source;
	}
	
	

	public String getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}

	@Override
	public String toString() {
		return "EquipmentUse [order_id=" + order_id + ", equipment_property_order_id=" + equipment_property_order_id
				+ ", user_id=" + user_id + ", equipment_use_time=" + equipment_use_time + ", equipment_name="
				+ equipment_name + ", equipment_number=" + equipment_number + ", doctor_id=" + doctor_id
				+ ", create_date=" + create_date + ", update_date=" + update_date + ", check_data_source="
				+ check_data_source + "]";
	}


}
