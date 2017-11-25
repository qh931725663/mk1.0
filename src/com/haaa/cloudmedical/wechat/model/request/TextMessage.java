package com.haaa.cloudmedical.wechat.model.request;

import com.haaa.cloudmedical.wechat.model.BaseMessage;

public class TextMessage extends BaseMessage{
    
    private String MsgId;       //消息id，64位整型
	private String Content;//文本消息内容
	
	public String getContent() {
		return Content;
	}
	public String getMsgId() {
        return MsgId;
    }
    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
    public void setContent(String content) {
		Content = content;
	}
}
