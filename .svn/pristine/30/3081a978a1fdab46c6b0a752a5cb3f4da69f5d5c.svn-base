package com.haaa.cloudmedical.app.gettui;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Message;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.AbstractTemplate;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.NotyPopLoadTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.AbstractNotifyStyle;
import com.gexin.rp.sdk.template.style.Style0;
import com.haaa.cloudmedical.common.entity.Constant;

public class PushMessage {

	public void setMessage(Message message) {
		message.setOffline(true);
		message.setOfflineExpireTime(24 * 3600 * 1000); // 一天
		message.setPushNetWorkType(0); // 0:不限网络环境;1:仅wife下推送
	}

	public void setTemplate(AbstractTemplate template) {
		template.setAppId(Constant.AppId);
		template.setAppkey(Constant.AppKey);
	}

	// Message
	// 单个用户
	public SingleMessage getSingleMessage(ITemplate template) {
		SingleMessage message = new SingleMessage();
		setMessage(message);
		message.setData(template);
		return message;
	}

	// 对一组用户
	public ListMessage getListMessage(ITemplate template) {
		ListMessage message = new ListMessage();
		setMessage(message);
		message.setData(template);
		return message;
	}

	// 对应用
	public AppMessage getAppMessage(ITemplate template) {
		AppMessage message = new AppMessage();
		setMessage(message);
		message.setData(template);
		return message;
	}

	// 应用模板
	public NotificationTemplate getNotificationTemplate(String title, String text, String content) {
		NotificationTemplate template = new NotificationTemplate();
		setTemplate(template);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(2);
		template.setTransmissionContent(content); // "请输入您要透传的内容"
		template.setStyle(getStyle(title, text));
		template.setAPNInfo(getAPNPayload(content));
		return template;
	}

	// 网页模板
	public LinkTemplate getLinkTemplate(String title,String text,String url) {
		LinkTemplate template = new LinkTemplate();
		setTemplate(template);
		template.setUrl(url);		//打开的网页地址
		template.setStyle(getStyle(title, text));
		template.setAPNInfo(getAPNPayload(text));
		return template;

	}

	// 下载模板
	public NotyPopLoadTemplate getNotyPopLoadTemplate(String popTitle,String popContent,String loadTitle,String url) {
		NotyPopLoadTemplate template = new NotyPopLoadTemplate();
		setTemplate(template);
		template.setPopTitle(popTitle);		//"弹框标题"
		template.setPopContent(popContent);		//"弹框内容"
		// 设置弹框显示的图片
		template.setPopImage("");
		template.setPopButton1("下载");
		template.setPopButton2("取消");
		// 设置下载标题
		template.setLoadTitle(loadTitle);		//"下载标题"
		template.setLoadIcon("file://icon.png");
		// 设置下载地址
		template.setLoadUrl(url);				//下载地址
		template.setAPNInfo(getAPNPayload(popContent));
		return template;
	}

	// 透传模板
	public TransmissionTemplate getTransmissionTemplate(String content) {
		TransmissionTemplate template = new TransmissionTemplate();
		setTemplate(template);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(2);
		template.setTransmissionContent(content); //"请输入需要透传的内容"
		template.setAPNInfo(getAPNPayload(content));
		return template;
	}

	// 单个用户
	public Target getTarget(String user_id) {
		Target target = new Target();
		target.setAppId(Constant.AppId);
		target.setAlias(user_id);
		return target;
	}

	// 一批用户
	public List<Target> getTarget(List<String> user_id_list) {
		List<Target> targetList = new ArrayList<Target>();
		for (String user_id : user_id_list) {
			Target target = new Target();
			target.setAppId(Constant.AppId);
			target.setAlias(user_id);
			targetList.add(target);
		}
		return targetList;
	}

	// 样式
	public AbstractNotifyStyle getStyle(String title, String text) {
		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle(title); // "请输入通知栏标题"
		style.setText(text); // "请输入通知栏内容"
		// 配置通知栏图标
		style.setLogo("icon.png");
		// 配置通知栏网络图标
		style.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		return style;
	}

	public APNPayload getAPNPayload(String content) {
		APNPayload payload = new APNPayload();
		// 在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
		payload.setAutoBadge("+1");
		payload.setContentAvailable(1);
		payload.setSound("default");
		payload.setCategory("$由客户端定义");
		// 简单模式APNPayload.SimpleMsg
		payload.setAlertMsg(new APNPayload.SimpleAlertMsg(content));

		// 字典模式使用APNPayload.DictionaryAlertMsg
		/*payload.setAlertMsg(getDictionaryAlertMsg());*/

		// 添加多媒体资源
		/*payload.addMultiMedia(new MultiMedia().setResType(MultiMedia.MediaType.video)
				.setResUrl("http://ol5mrj259.bkt.clouddn.com/test2.mp4")
				.setOnlyWifi(true));*/
		return payload;
	}
	
	public APNPayload.DictionaryAlertMsg getDictionaryAlertMsg() {
		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		alertMsg.setBody("body");
		alertMsg.setActionLocKey("ActionLockey");
		alertMsg.setLocKey("LocKey");
		alertMsg.addLocArg("loc-args");
		alertMsg.setLaunchImage("launch-image");
		// iOS8.2以上版本支持
		alertMsg.setTitle("Title");
		alertMsg.setTitleLocKey("TitleLocKey");
		alertMsg.addTitleLocArg("TitleLocArg");
		return alertMsg;
	}
	
	public IGtPush getIGtpush(){
		IGtPush push = new IGtPush(Constant.URL,Constant.AppKey, Constant.MasterSecret);
		return push;		
	}

}
