package com.haaa.cloudmedical.interceptor;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.haaa.cloudmedical.interceptor.util.AESUtil;

public class PropertyPlaceholderConfigurerProp extends PropertyPlaceholderConfigurer {

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
        String password = props.getProperty("mkh.password");
        if (password != null) {
            //解密jdbc.password属性值，并重新设置 
            try {
                props.setProperty("mkh.password", AESUtil.decryptAES(password));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.processProperties(beanFactory, props);

    }
}
