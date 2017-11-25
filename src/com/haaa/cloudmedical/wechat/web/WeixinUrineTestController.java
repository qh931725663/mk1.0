/**
 * 
 */
package com.haaa.cloudmedical.wechat.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.common.entity.StdDTO;
import com.haaa.cloudmedical.wechat.service.IWeixinUrineTestService;

/**
 * @author Bowen Fan
 *
 */
@RestController
@RequestMapping("/wx/urine_test")
public class WeixinUrineTestController {

	@Resource
	private IWeixinUrineTestService service;

	private Logger logger = Logger.getLogger(getClass());

	@RequestMapping("/page.action")
	public StdDTO getPage(HttpServletRequest request, String open_id, String year_month, Integer pageno,
			Integer pagesize) {
		StdDTO stdDTO = new StdDTO();
		try {
	        open_id = request.getSession().getAttribute("openid").toString();
			if (pagesize == null) {
				pagesize = 1;
			}
			if (pageno == null) {
				pageno = 1;
			}
			stdDTO = service.getPage(open_id, pageno, pagesize);

		} catch (Exception e) {
			stdDTO.setStatus(1);
			logger.error(e.getMessage(), e);
		}
		return stdDTO;
	};
}
