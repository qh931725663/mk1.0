/**
 * 
 */
package com.haaa.cloudmedical.platform.allinone.controller;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.platform.allinone.service.AIOService;

/**
 * @author Bowen Fan
 *
 */
@RestController
@RequestMapping("/aioTest")
public class AioTest {
	
	@Resource
	private AIOService service;
	
	@RequestMapping("/transaction.action")
	public void test() {
		try {
			//service.saveTest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test1() {
		ClassPathXmlApplicationContext applicationContext= new ClassPathXmlApplicationContext("applicationContext.xml");
	    AIOService bean = applicationContext.getBean(AIOService.class);
	    try {
			///bean.saveTest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
