package com.haaa.cloudmedical.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restful")
public class RestfulTestController {
	
	@RequestMapping(value="/post.action",method=RequestMethod.POST)
	public String post(){
		System.out.println("post");
		return "post请求";
	}
	
	@RequestMapping(value="/produce",method=RequestMethod.GET)
	public String get(){
		System.out.println("get");
		return "get请求";

	}
	
	@RequestMapping(value="/delete.action",method=RequestMethod.DELETE)
	public String delete(){
		System.out.println("delete");
		return "delete请求";

	}
	
	@RequestMapping(value="/put.action",method=RequestMethod.PUT)
	public String put(){
		System.out.println("put");
		return "put请求";
	}
}
