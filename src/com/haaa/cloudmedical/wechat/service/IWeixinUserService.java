package com.haaa.cloudmedical.wechat.service;

import javax.servlet.http.HttpServletRequest;

import com.haaa.cloudmedical.common.entity.StdDTO;

public interface IWeixinUserService {
    
    public String queryOpenidByCode(String code);

    public StdDTO userInfo(HttpServletRequest request);
    
    public StdDTO getUserHealthIndex(HttpServletRequest request);
}
