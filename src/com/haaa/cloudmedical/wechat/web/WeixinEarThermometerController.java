/**
 * 
 */
package com.haaa.cloudmedical.wechat.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.StdDTO;
import com.haaa.cloudmedical.wechat.service.IWeixinEarThermometerService;

/**
 * @author Bowen Fan
 *
 */
@RestController
@RequestMapping("/wx/ear_thermometer")
public class WeixinEarThermometerController {

	@Resource
	private IWeixinEarThermometerService service;

	private Logger logger = Logger.getLogger(getClass());

	@RequestMapping("/graph.action")
	public StdDTO getGraph(HttpServletRequest request, String open_id, Integer days) {
		StdDTO stdDTO = new StdDTO();
		try {
	        open_id = request.getSession().getAttribute("openid").toString();
			if (days == null) {
				days = Constant.RECENT;
			}
			stdDTO = service.getGraph(open_id, days);
		} catch (Exception e) {
			stdDTO.setStatus(1);
			logger.error(e.getMessage(), e);
		}
		return stdDTO;
	};

	@RequestMapping("/page.action")
	public StdDTO getPage(HttpServletRequest request, String open_id, String yearmonth, Integer pageno,
			Integer pagesize) {
		StdDTO stdDTO = new StdDTO();
		try {
	        open_id = request.getSession().getAttribute("openid").toString();
			if (pagesize == null) {
				pagesize = Constant.DEFAULT_PAGESIZE;
			}
			if (pageno == null) {
				pageno = 1;
			}
			stdDTO = service.getPage(open_id, yearmonth, pageno, pagesize);
		} catch (Exception e) {
			stdDTO.setStatus(1);
			logger.error(e.getMessage(), e);
		}
		return stdDTO;
	};

	@RequestMapping("/months.action")
	public StdDTO getMonths(HttpServletRequest request, String open_id) {
		StdDTO stdDTO = new StdDTO();
		try {
	        open_id = request.getSession().getAttribute("openid").toString();
			stdDTO = service.getMonths(open_id);
		} catch (Exception e) {
			stdDTO.setStatus(1);
			logger.error(e.getMessage(), e);
		}
		return stdDTO;
	};
}
