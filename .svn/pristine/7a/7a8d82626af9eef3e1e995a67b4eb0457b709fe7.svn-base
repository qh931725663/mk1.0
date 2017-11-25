package com.haaa.cloudmedical.wechat.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.haaa.cloudmedical.wechat.menu.Button;
import com.haaa.cloudmedical.wechat.menu.ClickButton;
import com.haaa.cloudmedical.wechat.menu.Menu;
import com.haaa.cloudmedical.wechat.menu.ViewButton;
import com.haaa.cloudmedical.wechat.model.AccessToken;
import com.haaa.cloudmedical.wechat.model.response.TemplateMessage;

import net.sf.json.JSONObject;

/**
 * 微信工具类
 *
 */
public class WeixinUtil {

    public static final String APPID             = "wxccaff4ba258520a3";
    public static final String APPSECRET         = "55f59592744b5ba94a0bbb5f03b3631c";

    public static final String ACCESS_TOKEN_URL  = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    public static final String UPLOAD_URL        = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    public static final String CREATE_MENU_URL   = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    public static final String QUERY_MENU_URL    = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

    public static final String DELETE_MENU_URL   = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    public final static String AUTH_URL          = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    public final static String AUTH_GET_OID      = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    public final static String SEND_TEMPLATE_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    /**
     * get请求
     * @param url
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static JSONObject doGetStr(String url) throws ParseException, IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        HttpResponse httpResponse = client.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            String result = EntityUtils.toString(entity, "UTF-8");
            jsonObject = JSONObject.fromObject(result);
        }
        return jsonObject;
    }

    /**
     * POST请求
     * @param url
     * @param outStr
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static JSONObject doPostStr(String url, String outStr) throws ParseException, IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);
        JSONObject jsonObject = null;
        httpost.setEntity(new StringEntity(outStr, "UTF-8"));
        HttpResponse response = client.execute(httpost);
        String result = EntityUtils.toString(response.getEntity(), "UTF-8");
        jsonObject = JSONObject.fromObject(result);
        return jsonObject;
    }

    /**
     * 文件上传
     * @param filePath
     * @param accessToken
     * @param type
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    public static String upload(String filePath, String accessToken, String type) throws IOException, NoSuchAlgorithmException,
                                                                                  NoSuchProviderException, KeyManagementException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }

        String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

        URL urlObj = new URL(url);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);

        //设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        //设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        //获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        //输出表头
        out.write(head);

        //文件正文部分
        //把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        //结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            //定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        JSONObject jsonObj = JSONObject.fromObject(result);
        System.out.println(jsonObj);
        String typeName = "media_id";
        if (!"image".equals(type)) {
            typeName = type + "_media_id";
        }
        String mediaId = jsonObj.getString(typeName);
        return mediaId;
    }

    /**
     * 获取accessToken
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static AccessToken getAccessToken() throws ParseException, IOException {
        AccessToken token = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        JSONObject jsonObject = doGetStr(url);
        if (jsonObject != null) {
            token.setToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        return token;
    }

    /**
     * 组装菜单
     * @return
     */
    public static Menu initMenu() {
        Menu menu = new Menu();

        ViewButton button11 = new ViewButton();
        button11.setName("综合健康指数");
        button11.setType("view");
        button11.setUrl("http://1752n45s01.iask.in/mkh1.0/system/view/wechat/login.html");

        ViewButton button12 = new ViewButton();
        button12.setName("设备检测报告");
        button12.setType("view");
        button12.setUrl("http://1752n45s01.iask.in/mkh1.0/system/view/wechat/login.html");

        ViewButton button13 = new ViewButton();
        button13.setName("健康档案");
        button13.setType("view");
        button13.setUrl("http://1752n45s01.iask.in/mkh1.0/system/view/wechat/myHealth/healthArchives/healthArchives.html");

        ViewButton button14 = new ViewButton();
        button14.setName("个人资料");
        button14.setType("view");
        button14.setUrl("http://1752n45s01.iask.in/mkh1.0/system/view/wechat/login.html");

        Button button1 = new Button();
        button1.setName("我的健康");
        button1.setSub_button(new Button[] { button11, button12, button13, button14 });

        ViewButton button21 = new ViewButton();
        button21.setName("健康科普");
        button21.setType("view");
        button21.setUrl("http://1752n45s01.iask.in/mkh1.0/system/view/wechat/login.html");

        Button button2 = new Button();
        button2.setName("健康科普");
        button2.setSub_button(new Button[] { button21 });

        ViewButton button31 = new ViewButton();
        button31.setName("免费APP下载");
        button31.setType("view");
        button31.setUrl("http://1752n45s01.iask.in/mkh1.0/system/view/wechat/login.html");

        ViewButton button32 = new ViewButton();
        button32.setName("健康设备");
        button32.setType("view");
        button32.setUrl("http://1752n45s01.iask.in/mkh1.0/system/view/wechat/login.html");

        ViewButton button33 = new ViewButton();
        button33.setName("公司简介");
        button33.setType("view");
        button33.setUrl("http://1752n45s01.iask.in/mkh1.0/system/view/wechat/login.html");

        ViewButton button34 = new ViewButton();
        button34.setName("权威专家");
        button34.setType("view");
        button34.setUrl("http://1752n45s01.iask.in/mkh1.0/system/view/wechat/aboutWe/expert/expert.html");

        ViewButton button35 = new ViewButton();
        button35.setName("联系我们");
        button35.setType("view");
        button35.setUrl("http://1752n45s01.iask.in/mkh1.0/system/view/wechat/aboutWe/contactUs/contactUs.html");

        Button button3 = new Button();
        button3.setName("关于我们");
        button3.setSub_button(new Button[] { button31, button32, button33, button34, button35 });

        menu.setButton(new Button[] { button1, button2, button3 });
        return menu;
    }

    public static int createMenu(String token, String menu) throws ParseException, IOException {
        int result = 0;
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doPostStr(url, menu);
        if (jsonObject != null) {
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    public static JSONObject queryMenu(String token) throws ParseException, IOException {
        String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doGetStr(url);
        return jsonObject;
    }

    public static int deleteMenu(String token) throws ParseException, IOException {
        String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doGetStr(url);
        int result = 0;
        if (jsonObject != null) {
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    public static boolean isWeixinFace(String content) {
        boolean result = false;

        // 判断QQ表情的正则表达式  
        String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
        Pattern p = Pattern.compile(qqfaceRegex);
        Matcher m = p.matcher(content);
        if (m.matches()) {
            result = true;
        }
        return result;
    }

    public static JSONObject sendTemplateMessage(String token, TemplateMessage template) throws ParseException, IOException {
        String url = SEND_TEMPLATE_MSG.replace("ACCESS_TOKEN", token);
        String msg = JSONObject.fromObject(template).toString();
        JSONObject jsonObject = doPostStr(url, msg);
        return jsonObject;
    }
}
