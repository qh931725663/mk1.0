package com.haaa.cloudmedical.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.common.entity.StdDTO;
import com.haaa.cloudmedical.common.util.BeanUtil;
import com.haaa.cloudmedical.dao.DoctorDao;
import com.haaa.cloudmedical.wechat.service.IWeixinNewsService;

@Service
public class WeixinNewsServiceImpl implements IWeixinNewsService{
	
	@Autowired
	private DoctorDao dao;
	
	@Override
	public StdDTO getNews(String section, int pageno, int pagesize) {
		String url = BeanUtil.getProperty("dbconfig").getString("pictureuploadhttp");
		StringBuilder sql = new StringBuilder();
		sql.append("select a.order_id,b.user_name,concat('" + url
				+ "',b.user_head_pic_upload_index) user_head_pic_upload_index," + "a.title,concat('" + url
				+ "',a.pic_index) pic_index,a.short_introduction,if(a.news_type=2,a.url_index,concat('" + url
				+ "',a.url_index)) url_index,"
				+ "date_format(a.update_date,'%Y-%m-%d %H:%i') update_date,a.news_type from b_banner_news a,n_manager b "
				+ "where a.user_id = b.user_id and a.is_push_to_doctor = 1 and a.section = ?");
		if (section.equals("7")) {
			section = "2";
			sql.append(" and a.is_push_to_frontpage = 1 ");
		} else if (section.equals("8")) {
			section = "4";
			sql.append(" and a.is_push_to_frontpage = 1 ");
		}
		sql.append(" order by a.update_date desc");
		Page page = dao.pageQuery(sql.toString(), new Object[] { section }, pageno, pagesize);
		return StdDTO.setSuccess(page);
	}

}
