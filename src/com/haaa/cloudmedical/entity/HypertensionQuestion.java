package com.haaa.cloudmedical.entity;

import java.util.List;

public class HypertensionQuestion {

	// id
	private String order_id;

	// 高血压档案id
	private String hypertension_order_id;

	// 高血压问题编码
	private String question_order_id;

	// 高血压问题内容
	private String question_content;
	
	private String parent_id;

	// 高血压问题区域
	private String question_region;

	// 问题提出日期
	private String create_date;

	// 问题更改日期
	private String update_date;
	
	private List<HypertensionAnswer> answers;

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getHypertension_order_id() {
		return hypertension_order_id;
	}

	public void setHypertension_order_id(String hypertension_order_id) {
		this.hypertension_order_id = hypertension_order_id;
	}

	public String getQuestion_order_id() {
		return question_order_id;
	}

	public void setQuestion_order_id(String question_order_id) {
		this.question_order_id = question_order_id;
	}

	public String getQuestion_content() {
		return question_content;
	}

	public void setQuestion_content(String question_content) {
		this.question_content = question_content;
	}

	public String getQuestion_region() {
		return question_region;
	}

	public void setQuestion_region(String question_region) {
		this.question_region = question_region;
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

	public List<HypertensionAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<HypertensionAnswer> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "HypertensionQuestion [order_id=" + order_id + ", hypertension_order_id=" + hypertension_order_id
				+ ", question_order_id=" + question_order_id + ", question_content=" + question_content + ", parent_id="
				+ parent_id + ", question_region=" + question_region + ", create_date=" + create_date + ", update_date="
				+ update_date + ", answers=" + answers + "]";
	}
}
