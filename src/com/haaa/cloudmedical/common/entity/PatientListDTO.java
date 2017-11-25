package com.haaa.cloudmedical.common.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PatientListDTO implements Serializable{
	private String type;
	private List<Map<String, Object>> list;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "PatientListDTO [type=" + type + ", list=" + list + "]";
	}

}
