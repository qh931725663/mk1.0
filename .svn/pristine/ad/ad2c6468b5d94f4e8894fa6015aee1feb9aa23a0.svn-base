package com.haaa.cloudmedical.common.util.redis;

import java.sql.SQLException;

import javax.annotation.Resource;


import com.haaa.cloudmedical.common.service.PositionService;
import com.haaa.cloudmedical.common.service.UnicodeService;


public class RedisCacheInitial {

	@Resource
	private UnicodeService unicodeService;

	@Resource
	private PositionService positionService;
	

	private int init=1;
		
	public int getInit() {
		return init;
	}

	public void setInit(int init) {
		this.init = init;
	}

	public void init() throws SQLException, Exception{
		System.out.println(init);
		if (init==1) {
		System.out.println("--------初始化redis");
		System.out.println("--------初始化unicode码表");
		unicodeService.init();//unicode码表初始化存储到redis数据库中
		positionService.positionInfoInit();       //三级联动初始化
		}
	}

	public void destroy() {

	}
}
