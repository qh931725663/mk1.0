package com.haaa.cloudmedical.wechat.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haaa.cloudmedical.common.entity.StdDTO;
import com.haaa.cloudmedical.wechat.service.IWxLoginService;

@Controller
@RequestMapping("/wx/login")
public class WxLoginController {
    
    @Autowired
    IWxLoginService service;
    
    @RequestMapping(value = "/doLogin.action", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request) throws IOException {
        StdDTO dto = service.doLogin(request);
        return dto;
    }
    
    
}
