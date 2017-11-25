package com.haaa.cloudmedical.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.common.util.redis.RedisCacheUtil;
import com.haaa.cloudmedical.demo.entity.User;

@RestController
@RequestMapping("/redis")
public class RedisTestController {

	/*@Resource
	private RedisCacheUtil redisCacheUtil;
	
	@RequestMapping("/write.action")
	public void write(){
	System.out.println("------write");	
	List list = new ArrayList();
	list.add("范搏文");
	redisCacheUtil.setCacheObject("name", "范博文");
	redisCacheUtil.setCacheList("list", list);
	}
	
	@RequestMapping("/read.action")
	public void read(){
		System.out.println("--------read");
		List list=redisCacheUtil.getCacheList("list");
		String name=(String) redisCacheUtil.getCacheObject("name");
		System.out.println(list);
		System.out.println(name);
	}
	
	
	@RequestMapping("/annotation.action")
	@Cacheable(value="common",key="'user'")
	public User annoataionTest(String name){
		System.out.println(name);
		User user = new User();
		user.setName(name);		
        return user;
	}*/
}
