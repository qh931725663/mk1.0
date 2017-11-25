package com.haaa.cloudmedical.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.haaa.cloudmedical.common.entity.RetData;
import com.haaa.cloudmedical.common.util.StringUtil;
import com.haaa.cloudmedical.common.util.redis.RedisCacheUtil;
import com.haaa.cloudmedical.interceptor.util.AESUtil;
import com.haaa.cloudmedical.platform.user.service.UserPlatformService;

public class CommonInterceptor implements HandlerInterceptor {

    private static final String   SUCCESS_CODE = "10";
    private static final String   FAIL_CODE    = "20";
    private static final String   REPEAT_CODE  = "30";

    @Autowired
    public RedisCacheUtil<Object> redis;

    private Logger                log          = Logger.getLogger(UserPlatformService.class);

    /**  
     * 在业务处理器处理请求之前被调用  
     * 如果返回false  
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
     * 如果返回true  
     *    执行下一个拦截器,直到所有的拦截器都执行完毕  
     *    再执行被拦截的Controller  
     *    然后进入拦截器链,  
     *    从最后一个拦截器往回执行所有的postHandle()  
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()  
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        /*log.info("*******************************拦截器*******************************");*/
        //获取请求的URL  
        String url = request.getRequestURI();
        //是否登录
        if (url.indexOf("login.action") >= 0) {
            return true;
        }
        Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("user");
        if (user == null) {
            //token
            String token = request.getHeader("token");
            if (StringUtil.isBlank(token)) {
                //log.info("未登录");
                this.returnJson(response, "{\"flag\":\"fail\",\"msg\":\"未登录\",\"code\":\"20\"}");
                return false;
            } else {
                RetData<Object> retData = this.checkAPPToken(request);
                if (retData.isFlag()) {
                    /*this.returnJson(response, "{\"flag\":\"success\",\"msg\":\"" + retData.getError() + "\",\"code\":\"" + retData.getData() + "\"}");*/
                    return true;
                } else {
                    response.sendError(404, retData.getError());
                    /*this.returnJson(response, "{\"flag\":\"fail\",\"msg\":\"" + retData.getError() + "\",\"code\":\"" + retData.getData() + "\"}");*/
                    return false;
                }
            }
        }
        return true;
    }

    private RetData<Object> checkAPPToken(HttpServletRequest request) throws Exception {
        //随机数
        String new_original = request.getHeader("original");
        if (StringUtil.isBlank(new_original)) {
            return new RetData<Object>(false, FAIL_CODE, "original不能为空");
        }
        //验签
        String sign = request.getHeader("sign");
        if (StringUtil.isBlank(sign)) {
            return new RetData<Object>(false, FAIL_CODE, "sign不能为空");
        }
        //token
        String token = request.getHeader("token");
        //
        Map<String, Object> map = redis.getCacheMap(token);
        if (map != null) {
            Object obj = map.get("user");
            if (obj != null) {
                /*request.getSession().setAttribute("user", obj);*/
                //判断用户是否重复提交
                Object old_original = map.get("original");
                if (old_original == null) {
                    //更新随机数
                    map.put("original", new_original);
                    redis.setCacheMap(token, map);
                } else if (old_original.toString().equals(new_original)) {
                    return new RetData<Object>(false, REPEAT_CODE, "重复请求");
                } else {
                    //更新随机数
                    map.put("original", new_original);
                    redis.setCacheMap(token, map);
                }
                //验证
                String checkSign = null;
                try {
                    checkSign = AESUtil.decryptAES(sign);
                    if (new_original.equals(checkSign.substring(0, checkSign.indexOf(",")))) {
                        //
                        return new RetData<Object>(true, SUCCESS_CODE, "成功");
                    } else {
                        return new RetData<Object>(false, FAIL_CODE, "验签失败");
                    }
                } catch (Exception e) {
                    return new RetData<Object>(false, FAIL_CODE, "数据有误");
                }
            }
        }
        return new RetData<Object>(false, FAIL_CODE, "数据有误");
    }

    private void returnJson(HttpServletResponse response, String json) {
        PrintWriter writer = null;
        response.reset();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            writer = response.getWriter();
            writer.print(json);
            writer.flush();
        } catch (IOException e) {
            log.error("response error", e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e) throws Exception {
        //log.info("afterCompletion");
        //随机数
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView m) throws Exception {
        //log.info("postHandle");
    }

}
