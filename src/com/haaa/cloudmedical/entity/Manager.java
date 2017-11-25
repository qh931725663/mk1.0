package com.haaa.cloudmedical.entity;

public class Manager {
	private String user_id;
	private String role_code;
	private String user_name;
	private String user_sex;
	private String user_age;
	private String user_birthday;
	private String user_phone;
	private String user_pwd;
	private String email;
	private String picture_head_order_id;
	private String user_head_pic_upload_index;
	private String picture_card_front_order_id;
	private String user_card_front_upload_index;
	private String picture_card_back_order_id;
	private String user_card_back_upload_index;
	private String create_date;
	private String update_date;
	private String user_flag;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getRole_code() {
		return role_code;
	}

	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}

	public String getUser_age() {
		return user_age;
	}

	public void setUser_age(String user_age) {
		this.user_age = user_age;
	}

	public String getUser_birthday() {
		return user_birthday;
	}

	public void setUser_birthday(String user_birthday) {
		this.user_birthday = user_birthday;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getUser_pwd() {
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture_head_order_id() {
		return picture_head_order_id;
	}

	public void setPicture_head_order_id(String picture_head_order_id) {
		this.picture_head_order_id = picture_head_order_id;
	}

	public String getUser_head_pic_upload_index() {
		return user_head_pic_upload_index;
	}

	public void setUser_head_pic_upload_index(String user_head_pic_upload_index) {
		this.user_head_pic_upload_index = user_head_pic_upload_index;
	}

	public String getPicture_card_front_order_id() {
		return picture_card_front_order_id;
	}

	public void setPicture_card_front_order_id(String picture_card_front_order_id) {
		this.picture_card_front_order_id = picture_card_front_order_id;
	}

	public String getUser_card_front_upload_index() {
		return user_card_front_upload_index;
	}

	public void setUser_card_front_upload_index(String user_card_front_upload_index) {
		this.user_card_front_upload_index = user_card_front_upload_index;
	}

	public String getPicture_card_back_order_id() {
		return picture_card_back_order_id;
	}

	public void setPicture_card_back_order_id(String picture_card_back_order_id) {
		this.picture_card_back_order_id = picture_card_back_order_id;
	}

	public String getUser_card_back_upload_index() {
		return user_card_back_upload_index;
	}

	public void setUser_card_back_upload_index(String user_card_back_upload_index) {
		this.user_card_back_upload_index = user_card_back_upload_index;
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

	public String getUser_flag() {
		return user_flag;
	}

	public void setUser_flag(String user_flag) {
		this.user_flag = user_flag;
	}

	@Override
	public String toString() {
		return "Manager [user_id=" + user_id + ", role_code=" + role_code + ", user_name=" + user_name + ", user_sex="
				+ user_sex + ", user_age=" + user_age + ", user_birthday=" + user_birthday + ", user_phone="
				+ user_phone + ", user_pwd=" + user_pwd + ", email=" + email + ", picture_head_order_id="
				+ picture_head_order_id + ", user_head_pic_upload_index=" + user_head_pic_upload_index
				+ ", picture_card_front_order_id=" + picture_card_front_order_id + ", user_card_front_upload_index="
				+ user_card_front_upload_index + ", picture_card_back_order_id=" + picture_card_back_order_id
				+ ", user_card_back_upload_index=" + user_card_back_upload_index + ", create_date=" + create_date
				+ ", update_date=" + update_date + ", user_flag=" + user_flag + "]";
	}

}
