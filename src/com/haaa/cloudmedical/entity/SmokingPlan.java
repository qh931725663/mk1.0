package com.haaa.cloudmedical.entity;


public class SmokingPlan {
	private Long order_id;
	private Long user_id;
	private String create_date;
	private Integer quit_smoking_plan_target;
	private Integer quit_smoking_plan_day;
	private Integer quit_smoking_plan_number;
	private Integer quit_smoking_plan_end;
	private String quit_smoking_plan_name;
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public Integer getQuit_smoking_plan_target() {
		return quit_smoking_plan_target;
	}
	public void setQuit_smoking_plan_target(Integer quit_smoking_plan_target) {
		this.quit_smoking_plan_target = quit_smoking_plan_target;
	}
	public Integer getQuit_smoking_plan_day() {
		return quit_smoking_plan_day;
	}
	public void setQuit_smoking_plan_day(Integer quit_smoking_plan_day) {
		this.quit_smoking_plan_day = quit_smoking_plan_day;
	}
	public Integer getQuit_smoking_plan_number() {
		return quit_smoking_plan_number;
	}
	public void setQuit_smoking_plan_number(Integer quit_smoking_plan_number) {
		this.quit_smoking_plan_number = quit_smoking_plan_number;
	}
	public Integer getQuit_smoking_plan_end() {
		return quit_smoking_plan_end;
	}
	public void setQuit_smoking_plan_end(Integer quit_smoking_plan_end) {
		this.quit_smoking_plan_end = quit_smoking_plan_end;
	}
	public String getQuit_smoking_plan_name() {
		return quit_smoking_plan_name;
	}
	public void setQuit_smoking_plan_name(String quit_smoking_plan_name) {
		this.quit_smoking_plan_name = quit_smoking_plan_name;
	}
	


}
