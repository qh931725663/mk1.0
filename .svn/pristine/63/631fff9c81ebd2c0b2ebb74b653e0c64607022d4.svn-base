package com.haaa.cloudmedical.wechat.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haaa.cloudmedical.wechat.model.WeixinContext;
import com.haaa.cloudmedical.wechat.service.IWeixinService;
import com.haaa.cloudmedical.wechat.util.MessageUtil;
import com.haaa.cloudmedical.wechat.util.SecurityUtil;

@Controller
@RequestMapping("/wx")
public class WeixinController {
    
    @Autowired
    IWeixinService service;
    
    public static final String TOKEN = "fanbowen13013";

    @RequestMapping(value = "/wget.action", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String init(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        String[] arrs = { WeixinController.TOKEN, nonce, timestamp };
        Arrays.sort(arrs);
        StringBuffer sb = new StringBuffer();
        for (String a : arrs) {
            sb.append(a);
        }
        String sha1 = SecurityUtil.sha1(sb.toString());
        if (sha1.equals(signature)) {
            return echostr;
        }
        return null;
    }

    @RequestMapping(value = "/wget.action", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String> msgMap = MessageUtil.reqMsg2Map(req);
        String ret = service.handlerMsg(msgMap);
        return ret;
    }

    @RequestMapping("/at")
    public void testAccessToken(HttpServletResponse resp) throws IOException {
        resp.getWriter().println(WeixinContext.getAccessToken());
    }
}
