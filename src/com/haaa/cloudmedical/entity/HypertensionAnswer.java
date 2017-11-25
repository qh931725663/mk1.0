package com.haaa.cloudmedical.entity;

public class HypertensionAnswer {

	private String order_id;
	private String hypertension_order_id;
	private String question_order_id;
	private String question_region;
	private String group_id;
	private String answer_code;
	private String answer_content;
	private String answer_text;
	private String create_date;
	private String update_date;


	public String getQuestion_region() {
		return question_region;
	}

	public void setQuestion_region(String question_region) {
		this.question_region = question_region;
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

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getAnswer_code() {
		return answer_code;
	}

	public void setAnswer_code(String answer_code) {
		this.answer_code = answer_code;
	}

	public String getAnswer_content() {
		return answer_content;
	}

	public void setAnswer_content(String answer_content) {
		this.answer_content = answer_content;
	}

	public String getAnswer_text() {
		return answer_text;
	}

	public void setAnswer_text(String answer_text) {
		this.answer_text = answer_text;
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
		return "HypertensionAnswer [order_id=" + order_id + ", hypertension_order_id=" + hypertension_order_id
				+ ", question_order_id=" + question_order_id + ", question_region=" + question_region + ", group_id="
				+ group_id + ", answer_code=" + answer_code + ", answer_content=" + answer_content + ", answer_text="
				+ answer_text + ", create_date=" + create_date + ", update_date=" + update_date + "]";
	}

}
