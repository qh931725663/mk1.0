package com.haaa.cloudmedical.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.haaa.cloudmedical.wechat.model.request.TextMessage;
import com.haaa.cloudmedical.wechat.model.response.Article;
import com.haaa.cloudmedical.wechat.model.response.Image;
import com.haaa.cloudmedical.wechat.model.response.ImageMessage;
import com.haaa.cloudmedical.wechat.model.response.NewsMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {

    public static final String MESSAGE_TEXT        = "text";
    public static final String MESSAGE_NEWS        = "news";
    public static final String MESSAGE_IMAGE       = "image";
    public static final String MESSAGE_VOICE       = "voice";
    public static final String MESSAGE_MUSIC       = "music";
    public static final String MESSAGE_VIDEO       = "video";
    public static final String MESSAGE_LINK        = "link";
    public static final String MESSAGE_LOCATION    = "location";
    public static final String MESSAGE_EVNET       = "event";
    public static final String MESSAGE_SUBSCRIBE   = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK       = "CLICK";
    public static final String MESSAGE_VIEW        = "VIEW";
    public static final String MESSAGE_SCANCODE    = "scancode_push";

    @SuppressWarnings("unchecked")
    public static Map<String, String> reqMsg2Map(HttpServletRequest req) throws IOException {
        String xml = req2xml(req);
        try {
            Map<String, String> maps = new HashMap<String, String>();
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();
            List<Element> eles = root.elements();
            for (Element e : eles) {
                maps.put(e.getName(), e.getTextTrim());
            }
            return maps;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String req2xml(HttpServletRequest req) throws IOException {
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String str = null;
        StringBuffer sb = new StringBuffer();
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        return sb.toString();
    }


    public static String initText(String toUserName, String fromUserName, String content) {
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        return textMessageToXml(text);
    }
    
    public static String initImageText(String toUserName, String fromUserName) {
        String mediaId ="1hhnSzfJAkXS64C3PV4R68kG0P_eDTTR27LyDldoEgay6Xvb-PZeWPjGdog_iFlO";
        ImageMessage image = new ImageMessage();
        image.setFromUserName(toUserName);
        image.setToUserName(fromUserName);
        image.setMsgType(MessageUtil.MESSAGE_IMAGE);
        image.setCreateTime(new Date().getTime());
        
        Image i = new Image();
        i.setMediaId(mediaId);
        
        image.setImage(i);
        
        return imageMessageToXml(image);
    }
    
    public static String initNewsText(String toUserName, String fromUserName) {
        NewsMessage news = new NewsMessage();
        news.setFromUserName(toUserName);
        news.setToUserName(fromUserName);
        news.setMsgType(MessageUtil.MESSAGE_NEWS);
        news.setCreateTime(new Date().getTime());
        news.setArticleCount(1);

        Article item = new Article();
        item.setTitle("这是一张美女图片");
        item.setDescription("这是一张美女图片这是一张美女图片这是一张美女图片这是一张美女图片这是一张美女图片这是一张美女图片");
        item.setPicUrl("http://1752n45s01.iask.in/mkh1.0/images/image-1.jpg");
        item.setUrl("http://1752n45s01.iask.in/mkh1.0/images/image-1.jpg");
        
        List<Article> items = new ArrayList<Article>();
        items.add(item);
        /*items.add(item1);*/
        news.setArticles(items);
        return newsMessageToXml(news);
    }

    public static String textMessageToXml(TextMessage textMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }
    
    public static String imageMessageToXml(ImageMessage imageMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", imageMessage.getClass());
        xstream.alias("Image", Image.class);
        return xstream.toXML(imageMessage);
    }
    
    public static String newsMessageToXml(NewsMessage newsMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", Article.class);
        return xstream.toXML(newsMessage);
    }

    public static String menuText() {
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
        sb.append("1、美康惠介绍\n");
        sb.append("2、产品介绍\n");
        sb.append("3、美女图文/:gift\n");
        sb.append("4、美女图片/:gift\n\n");
        sb.append("回复？调出此菜单。");
        return sb.toString();
    }
    
    public static String subscribeText() {
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎您关注美康管家！\n");
        sb.append("登录美康管家账号，获取专属健康服务！<a href='http://www.haaa365.com/mkh1.0/system/login.html'>点我登录</a>");
        return sb.toString();
    }
}
