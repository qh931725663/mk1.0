package com.haaa.cloudmedical.common.entity;

public class RemindSendView {
	private String user_id;
	private String remind_title;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getRemind_title() {
		return remind_title;
	}

	public void setRemind_title(String remind_title) {
		this.remind_title = remind_title;
	}

	@Override
	public String toString() {
		return "RemindSendView [ user_id=" + user_id + ", remind_title=" + remind_title + "]";
	}

}
