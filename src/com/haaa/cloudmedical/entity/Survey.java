package com.haaa.cloudmedical.entity;

public class Survey {
	private String order_id;
	private String user_id;
	private String create_date;
	private String update_date;
	private String email;
	private String post_code;
	private String user_address;
	private String user_height;
	private String user_weight;
	private String user_waist;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPost_code() {
		return post_code;
	}

	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	public String getUser_height() {
		return user_height;
	}

	public void setUser_height(String user_height) {
		this.user_height = user_height;
	}

	public String getUser_weight() {
		return user_weight;
	}

	public void setUser_weight(String user_weight) {
		this.user_weight = user_weight;
	}

	public String getUser_waist() {
		return user_waist;
	}

	public void setUser_waist(String user_waist) {
		this.user_waist = user_waist;
	}

	@Override
	public String toString() {
		return "Survey [order_id=" + order_id + ", user_id=" + user_id + ", create_date=" + create_date
				+ ", update_date=" + update_date + ", email=" + email + ", post_code=" + post_code + ", user_address="
				+ user_address + ", user_height=" + user_height + ", user_weight=" + user_weight + ", user_waist="
				+ user_waist + "]";
	}

}
