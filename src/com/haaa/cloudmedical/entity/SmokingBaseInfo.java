package com.haaa.cloudmedical.entity;


public class SmokingBaseInfo {
	private Long user_id;
	private Integer user_age;
	private Integer user_sex;
	private Integer smoked_year;
	private Integer cigarettes_per_day;
	private String  smoked_last_time;
	private Integer no_smoke_plan;
	private Integer smoking_situation;
	private String create_date;
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Integer getUser_age() {
		return user_age;
	}
	public void setUser_age(Integer user_age) {
		this.user_age = user_age;
	}
	public Integer getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(Integer user_sex) {
		this.user_sex = user_sex;
	}
	public Integer getSmoked_year() {
		return smoked_year;
	}
	public void setSmoked_year(Integer smoked_year) {
		this.smoked_year = smoked_year;
	}
	public Integer getCigarettes_per_day() {
		return cigarettes_per_day;
	}
	public void setCigarettes_per_day(Integer cigarettes_per_day) {
		this.cigarettes_per_day = cigarettes_per_day;
	}
	public String getSmoked_last_time() {
		return smoked_last_time;
	}
	public void setSmoked_last_time(String smoked_last_time) {
		this.smoked_last_time = smoked_last_time;
	}
	public Integer getNo_smoke_plan() {
		return no_smoke_plan;
	}
	public void setNo_smoke_plan(Integer no_smoke_plan) {
		this.no_smoke_plan = no_smoke_plan;
	}
	public Integer getSmoking_situation() {
		return smoking_situation;
	}
	public void setSmoking_situation(Integer smoking_situation) {
		this.smoking_situation = smoking_situation;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

}
