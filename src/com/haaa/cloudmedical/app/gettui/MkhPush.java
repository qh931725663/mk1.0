package com.haaa.cloudmedical.app.gettui;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.haaa.cloudmedical.common.entity.Constant;

public class MkhPush {
	
	/**
	 * 
	 * @param user_id
	 * @param title		通知栏标题
	 * @param text		通知栏内容
	 * @param content	透传消息
	 */

	// 单个推送
	public void pushToSingle(String user_id,String content) {
		PushMessage pm = new PushMessage();
		IGtPush push = pm.getIGtpush();
		TransmissionTemplate template = pm.getTransmissionTemplate(content);
		SingleMessage message = pm.getSingleMessage(template);
		Target target = pm.getTarget(user_id);
		IPushResult ret = push.pushMessageToSingle(message, target);		
		System.out.println(ret.getResponse());

	}

	// 批量推送
	public void pushToList(List<String> user_id_list,String content) {
		PushMessage pm = new PushMessage();
		IGtPush push = pm.getIGtpush();
		TransmissionTemplate template = pm.getTransmissionTemplate(content);
		ListMessage message = pm.getListMessage(template);
		List<Target> targets = pm.getTarget(user_id_list);
		String taskId = push.getContentId(message);
		IPushResult ret = push.pushMessageToList(taskId, targets);
		System.out.println(ret.getResponse().toString());
	}

	// 全应用推送
	public void pushToApp(String content) {
		PushMessage pm = new PushMessage();
		IGtPush push = pm.getIGtpush();
		List<String> appIdList = new ArrayList<String>();
		appIdList.add(Constant.AppId);
		TransmissionTemplate template = pm.getTransmissionTemplate(content);		
		AppMessage message = pm.getAppMessage(template);
		message.setAppIdList(appIdList);
		IPushResult ret = push.pushMessageToApp(message);
		System.out.println(ret.getResponse().toString());
	}
	public static void main(String[] args) {
		MkhPush push = new MkhPush();
		push.pushToSingle("345", "your name");
	}

}
