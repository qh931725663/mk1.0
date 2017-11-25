package com.haaa.cloudmedical.demo.controller;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.common.util.SqlUtil;


@Controller
@RequestMapping("/query")
public class StudentController {
	
	@RequestMapping("/testjson.action")	
	public @ResponseBody Page test(int pageNo) throws SQLException, Exception{		
		String sql = "select * from n_quit_smoking_plan";
		Page page=SqlUtil.pageQuery(sql, pageNo);
		return page;
	}
	


}