package com.haaa.cloudmedical.platform.healthRecords.contorller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.app.user.service.DoctorAppService;
import com.haaa.cloudmedical.app.util.CommonUserService;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.common.entity.ResponseDTO;
import com.haaa.cloudmedical.common.entity.StdDTO;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.entity.Hypertension;
import com.haaa.cloudmedical.entity.User;
import com.haaa.cloudmedical.platform.healthRecords.service.ChronicRecordsService;
import com.haaa.cloudmedical.platform.plan.model.PlanVo;

/**
 * 
 * @author Owen
 *
 */
@RestController
@RequestMapping("/chronicRecord")
public class ChronicRecordsPlatformController {
	@Autowired
    private ChronicRecordsService service;
	private static Logger logger = Logger.getLogger(DoctorAppService.class);	
	 /*
     * 分页数据
     */
    @RequestMapping("/pageData.action")
    public Object getPage(PlanVo model) {
    	 if (model.getPageno() == null || model.getPageno() == 0) {
             model.setPageno(1);
         }
         //查询分页
         Page page = null;
         try {
             page = service.getPage(model);
         } catch (Exception e) {
             e.printStackTrace();
             return new StdDTO(0, "系统异常");
         }
         return new StdDTO(1, page);
    }
 
	
}
