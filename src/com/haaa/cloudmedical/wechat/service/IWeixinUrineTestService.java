/**
 * 
 */
package com.haaa.cloudmedical.wechat.service;

import com.haaa.cloudmedical.common.entity.StdDTO;

/**
 * @author Bowen Fan
 *
 */
public interface IWeixinUrineTestService {
	
	public StdDTO getPage(String open_id,int pageno,int pagesize);

}
