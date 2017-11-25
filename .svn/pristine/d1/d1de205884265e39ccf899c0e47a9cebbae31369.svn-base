package com.haaa.cloudmedical.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haaa.cloudmedical.entity.User;

public class WeChatAuthFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        String url = httpReq.getRequestURI();
        /*if (url.contains("wechat")) {
            if (url.contains("login")|| url.contains("aboutWe")) {
                chain.doFilter(httpReq, httpResp);
                return;
            } else {
                User user = (User) httpReq.getSession().getAttribute("user");
                if (user == null) {
                    httpResp.sendRedirect(httpReq.getContextPath() + "/system/view/wechat/login.html");
                    return;
                }
                chain.doFilter(httpReq, httpResp);
            }
        }*/
        chain.doFilter(httpReq, httpResp);
    }

    @Override
    public void init(FilterConfig cfg) throws ServletException {

    }

}
