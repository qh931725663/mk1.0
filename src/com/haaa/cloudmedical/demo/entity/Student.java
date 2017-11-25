package com.haaa.cloudmedical.demo.entity;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable{
	private int id;
	private String name;
	private String phone;
	private Date birthday;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPrimaryKey(){
		return "id";
	}
	public String getPrimaryValue(){
		return id+"";
	}
	
	public Student() {
		super();
	}
	public Student(int id, String name, String phone, Date birthday) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", phone=" + phone + ", birthday=" + birthday + "]";
	}
	
	

}
