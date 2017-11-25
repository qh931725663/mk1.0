package com.haaa.cloudmedical.entity;

import java.util.List;
import java.util.Map;

public class Role {

    private String order_id;
    private String role_name;
    private String role_note;
    private String create_date;
    
    private List<Map<String,Object>> list;
    
    public List<Map<String,Object>> getList() {
        return list;
    }

    public void setList(List<Map<String,Object>> list) {
        this.list = list;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRole_note() {
        return role_note;
    }

    public void setRole_note(String role_note) {
        this.role_note = role_note;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

}
