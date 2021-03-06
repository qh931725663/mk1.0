package com.haaa.cloudmedical.wechat.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haaa.cloudmedical.common.entity.StdDTO;
import com.haaa.cloudmedical.wechat.service.IWeixinUserService;

@Controller
@RequestMapping("/wx/user")
public class WxUserController {
    
    @Autowired
    IWeixinUserService service;
    
    @RequestMapping(value = "/userInfo.action", method = RequestMethod.POST)
    @ResponseBody
    public Object userInfo(HttpServletRequest request) throws IOException {
        StdDTO dto = service.userInfo(request);
        return dto;
    }
    
    @RequestMapping(value = "/healthIndex.action", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserHealthIndex(HttpServletRequest request){
    	try {
			return service.getUserHealthIndex(request);
		} catch (Exception e) {
			return StdDTO.setFail();
		}
    }
}
