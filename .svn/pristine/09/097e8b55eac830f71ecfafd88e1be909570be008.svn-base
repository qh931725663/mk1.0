package com.haaa.cloudmedical.app.util;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.haaa.cloudmedical.common.entity.Constant;

public class DoctorAppPushUtils {

    //定义常量, appId、appKey、masterSecret  在"个推控制台"中获得的应用配置
    // 由IGetui管理页面生成，是您的应用与SDK通信的标识之一，每个应用都对应一个唯一的AppID
    private static String appId        = Constant.doctor_AppId;
    // 预先分配的第三方应用对应的Key，是您的应用与SDK通信的标识之一。
    private static String appKey       = Constant.doctor_AppKey;
    // 个推服务端API鉴权码，用于验证调用方合法性。在调用个推服务端API时需要提供。（请妥善保管，避免通道被盗用）
    private static String masterSecret = Constant.doctor_MasterSecret;

    // 设置通知消息模板
    /*
     * 1. appId
     * 2. appKey
     * 3. 要传送到客户端的 msg
     * 3.1 标题栏：key = title, 
     * 3.2 通知栏内容： key = titleText,
     * 3.3 穿透内容：key = transText 
     */
    private static TransmissionTemplate getTemplate(String appId, String appKey, String msg) {
        // 在通知栏显示一条含图标、标题等的通知，用户点击后激活您的应用
        TransmissionTemplate template = new TransmissionTemplate();
        // 设置appid，appkey
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 穿透消息设置为，1 强制启动应用
        template.setTransmissionType(1);
        // 设置穿透内容
        template.setTransmissionContent(msg);
        //苹果消息
        APNPayload payload = new APNPayload();
        // 在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("+1");
        payload.setContentAvailable(1);
        payload.setSound("default");
        payload.setCategory("$由客户端定义");
        // 简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg(msg));
        
        template.setAPNInfo(payload);
        
        return template;
    }

    // 对单个用户推送消息
    /*
     * 1. cid
     * 2. 要传到客户端的 msg
     * 2.1 标题栏：key = title, 
     * 2.2 通知栏内容： key = titleText,
     * 2.3 穿透内容：key = transText 
     */
    public static IPushResult pushMsgToSingleByCid(String cid, String msg) {
        // 代表在个推注册的一个 app，调用该类实例的方法来执行对个推的请求
        IGtPush push = new IGtPush(appKey, masterSecret);
        // 创建信息模板
        TransmissionTemplate template = getTemplate(appId, appKey, msg);
        //定义消息推送方式为，单推
        SingleMessage message = new SingleMessage();
        //离线消息
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        // 设置推送消息的内容
        message.setData(template);
        // 设置推送目标
        Target target = new Target();
        target.setAppId(appId);
        // 设置cid
        //target.setClientId(cid);
        // 获得推送结果
        IPushResult result = push.pushMessageToSingle(message, target);
        /*
         * 1. 失败：{result=sign_error}
         * 2. 成功：{result=ok, taskId=OSS-0212_1b7578259b74972b2bba556bb12a9f9a, status=successed_online}
         * 3. 异常
         */
        System.out.println(result.getResponse().toString());
        return result;
    }

    // 对单个用户推送消息
    /*
     * 1. alias
     * 2. 要传到客户端的 msg
     * 2.1 标题栏：key = title, 
     * 2.2 通知栏内容： key = titleText,
     * 2.3 穿透内容：key = transText 
     */
    public static IPushResult pushMsgToSingleByAlias(String alias,String msg) {
        // 代表在个推注册的一个 app，调用该类实例的方法来执行对个推的请求
        IGtPush push = new IGtPush(appKey, masterSecret);
        // 创建信息模板
        TransmissionTemplate template = getTemplate(appId, appKey, msg);
        //定义消息推送方式为，单推
        SingleMessage message = new SingleMessage();     
        
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        // 设置推送消息的内容
        message.setData(template);
        // 设置推送目标
        Target target = new Target();
        target.setAppId(appId);
        // 设置cid
        target.setAlias(alias);
        // 获得推送结果
        IPushResult result = push.pushMessageToSingle(message, target);
        /*
         * 1. 失败：{result=sign_error}
         * 2. 成功：{result=ok, taskId=OSS-0212_1b7578259b74972b2bba556bb12a9f9a, status=successed_online}
         * 3. 异常
         */
        System.out.println(result.getResponse().toString());
        return result;
    }
}