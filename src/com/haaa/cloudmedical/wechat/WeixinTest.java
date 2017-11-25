package com.haaa.cloudmedical.wechat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.ParseException;
import org.junit.Test;

import com.haaa.cloudmedical.wechat.model.AccessToken;
import com.haaa.cloudmedical.wechat.model.response.TemplateItem;
import com.haaa.cloudmedical.wechat.model.response.TemplateMessage;
import com.haaa.cloudmedical.wechat.util.WeixinUtil;

import net.sf.json.JSONObject;

public class WeixinTest {
    
	
	@Test
	public void test() throws ParseException, IOException{
	    AccessToken token = WeixinUtil.getAccessToken();
        System.out.println(token.getToken());
        //System.out.println("票据"+token.getToken());
        //System.out.println("有效时间"+token.getExpiresIn());
        
        /*String path = "C:\\Users\\WUQI\\Desktop\\image-1.jpg";
        String mediaId = WeixinUtil.upload(path, token.getToken(), "image");
        System.out.println(mediaId);*/
        //String tokenstr = "rBlQbaE0G2iWlUt5mnTalkQ8tlKg6WJnWNq4lIf1d8qjCOpr0AmL2JuE50WJqGf2vlvik5I63z7gGOhAJt3zGfTRKvZfx_RKAZDpMNQADM0QLHgAJABWS";
        String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
        System.out.println(menu);
        int createMenu = WeixinUtil.createMenu(token.getToken(), menu);
        System.out.println(createMenu);
	}
	
	@Test
	public void testGetToken() throws ParseException, IOException{
	    AccessToken token = WeixinUtil.getAccessToken();
        System.out.println(token.getToken());
	}
	
	@Test
	public void testCreateMenu() throws ParseException, IOException{
	    String token ="PTRGq2GYz-sjFElhH9SfLwkhg03oNfbpShWrtzzYJ6RJ3rI9kQAVlOp1O5WodKr_kdnoxDGNaiZ5eKQr1byF9s-1dNAGVxfHUNdpWH7ZCzIOAOdADATFT";
	    String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
        System.out.println(menu);
        int createMenu = WeixinUtil.createMenu(token, menu);
        System.out.println(createMenu);
	}
	
	@Test
	public void testTemplate() throws ParseException, IOException{
	    String token ="NKY3Ps12yko5WSUkSwt3Ay3oE_NLcWi4DZGdBqL-SYb5GtFaLs1V4mJFaKA565aysagIALxHqeKxHpJOS9eMRdVtsElhlAKiSeRxG60frLGezrgSNAEkw1Ay6RGqdZnqCZXdAHAFCA";
        TemplateMessage t = new TemplateMessage();
        t.setTouser("osRqI0juEV1M5YyLZjySppABGLSc");
        t.setTemplate_id("N8gDA59Bd8fCrJYK6mB5eRAdqtk1HbePOo1aAi7UHz0");
        Map<String,Object> data = new HashMap<String,Object>();
        TemplateItem item = new TemplateItem();
        item.setColor("#173177");
        item.setValue("血压测量时间到了，健康生活每一天~");
        data.put("type", item);
        TemplateItem item1 = new TemplateItem();
        item1.setColor("#173177");
        item1.setValue("2017-8-29");
        data.put("time", item1);
        TemplateItem item2 = new TemplateItem();
        item2.setColor("#173177");
        item2.setValue("血压测量前需要安静休息五分钟，半小时内不要吸烟，不要喝咖啡。坐姿测量，血压计、肘部与心脏处于同一高度。");
        data.put("remark", item2);
        t.setData(data);
        String msg = JSONObject.fromObject(t).toString();
        System.out.println(msg);
        JSONObject jsonObject = WeixinUtil.sendTemplateMessage(token, t);
        System.out.println(jsonObject);
	}
	
}
