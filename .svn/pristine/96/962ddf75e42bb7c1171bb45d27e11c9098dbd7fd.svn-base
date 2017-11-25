package com.haaa.cloudmedical.wechat.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.wechat.service.IWeixinService;
import com.haaa.cloudmedical.wechat.util.MessageUtil;
import com.haaa.cloudmedical.wechat.util.WeixinUtil;

@Service
public class WeixinServiceImpl implements IWeixinService{
    
    @Override
    public String handlerMsg(Map<String, String> msgMap) throws IOException {
        String message = "";
        String msgType = msgMap.get("MsgType");
        String fromUserName = msgMap.get("FromUserName");
        String toUserName = msgMap.get("ToUserName");
        String content = msgMap.get("Content");
        //事件
        if (MessageUtil.MESSAGE_EVNET.equals(msgType)) {
            String eventType = msgMap.get("Event");
            if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
                message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.subscribeText());
            } else if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {
                message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
            }
        }else if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
            //
            if ("1".equals(content)) {
                message = MessageUtil.initText(toUserName, fromUserName, "美康惠介绍");
            } else if ("2".equals(content)) {
                message = MessageUtil.initText(toUserName, fromUserName, "美康惠产品");
            } else if (content != null && WeixinUtil.isWeixinFace(content)) {
                message = MessageUtil.initText(toUserName, fromUserName, content);
            } else if ("3".equals(content)) {
                message = MessageUtil.initNewsText(toUserName, fromUserName);
            }else if("4".equals(content)){
                message = MessageUtil.initImageText(toUserName, fromUserName);
            }
            else if("?".equals(content)){
                message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
            }
        }
        return message;
    }
}
