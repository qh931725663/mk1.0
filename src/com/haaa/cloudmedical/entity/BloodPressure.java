package com.haaa.cloudmedical.entity;

import java.util.Date;

/**
 * 
 * @author haaa
 *
 */
public class BloodPressure {
	
	private String order_id;
	
	private String equipment_use_order_id;
	
	private String highPressure;
	
	private String lowPressure;
	
	private String meanPressure;
	
	private String pulseRate;
	
	private String create_date;
	
	private String update_date;

	
	
	/**
	 * 
	 */
	public BloodPressure() {
		super();
	}

	/**
	 * @param order_id
	 * @param equipment_use_order_id
	 * @param highPressure
	 * @param lowPressure
	 * @param meanPressure
	 * @param pulseRate
	 * @param create_date
	 * @param update_date
	 */
	public BloodPressure(String order_id, String equipment_use_order_id, String highPressure, String lowPressure,
			String meanPressure, String pulseRate, String create_date, String update_date) {
		super();
		this.order_id = order_id;
		this.equipment_use_order_id = equipment_use_order_id;
		this.highPressure = highPressure;
		this.lowPressure = lowPressure;
		this.meanPressure = meanPressure;
		this.pulseRate = pulseRate;
		this.create_date = create_date;
		this.update_date = update_date;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getEquipment_use_order_id() {
		return equipment_use_order_id;
	}

	public void setEquipment_use_order_id(String equipment_use_order_id) {
		this.equipment_use_order_id = equipment_use_order_id;
	}

	public String getHighPressure() {
		return highPressure;
	}

	public void setHighPressure(String highPressure) {
		this.highPressure = highPressure;
	}

	public String getLowPressure() {
		return lowPressure;
	}

	public void setLowPressure(String lowPressure) {
		this.lowPressure = lowPressure;
	}

	public String getMeanPressure() {
		return meanPressure;
	}

	public void setMeanPressure(String meanPressure) {
		this.meanPressure = meanPressure;
	}

	public String getPulseRate() {
		return pulseRate;
	}

	public void setPulseRate(String pulseRate) {
		this.pulseRate = pulseRate;
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
		return "BloodPressure [order_id=" + order_id + ", equipment_use_order_id=" + equipment_use_order_id
				+ ", highPressure=" + highPressure + ", lowPressure=" + lowPressure + ", meanPressure=" + meanPressure
				+ ", pulseRate=" + pulseRate + ", create_date=" + create_date + ", update_date=" + update_date + "]";
	}

	
	
	

}
