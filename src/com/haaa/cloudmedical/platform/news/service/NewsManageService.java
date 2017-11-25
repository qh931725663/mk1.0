package com.haaa.cloudmedical.platform.news.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.annotation.Log;
import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.common.entity.ResponseDTO;
import com.haaa.cloudmedical.common.util.BeanUtil;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.dao.NewsDao;
import com.haaa.cloudmedical.entity.BannerNews;

@Service
@Log(name = "新闻管理")
public class NewsManageService {

	@Resource
	private NewsDao dao;

	private Logger logger = Logger.getLogger(NewsManageService.class);
	
	private String separator = "/";

	// 条件分页查询
	public Page getNewsByCondition(String the_text, String user_id, String section, String startDate, String endDate,
			Integer pageno) {
		String url = BeanUtil.getProperty("dbconfig").getString("pictureuploadhttp");
		List<Object> list = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder("select a.order_id,a.title,a.is_push_to_doctor,concat('" + url
				+ "',a.pic_index) pic_index,date_format(a.create_date,'%Y-%m-%d %H:%i') create_date,"
				+ "a.short_introduction,b.user_name from b_banner_news a,n_manager b where a.user_id=b.user_id");
		if (the_text != null && !the_text.equals("")) {
			sql.append(" and a.title like ? ");
			list.add("%" + the_text.trim() + "%");
		}
		if (user_id != null && !user_id.equals("")) {
			sql.append(" and a.user_id = ? ");
			list.add(user_id);
		}
		if (section != null && !section.equals("")) {
			sql.append(" and a.section = ? ");
			list.add(section);
		}
		if (startDate != null && !startDate.equals("")) {
			sql.append(" and date_format(a.create_date,'%Y-%m-%d') >= ?");
			list.add(startDate);
		}
		if (endDate != null && !endDate.equals("")) {
			sql.append(" and date_format(a.create_date,'%Y-%m-%d') <= ?");
			list.add(endDate);
		}
		sql.append(" order by a.create_date desc");
		Page page = dao.pageQuery(sql.toString(), list.toArray(), pageno, 5);
		return page;
	}

	// 查询
	public ResponseDTO getNewsById(String order_id) {
		String url = BeanUtil.getProperty("dbconfig").getString("pictureuploadhttp");
		String sql = "select order_id,title,is_push_to_patient,ifnull(is_push_to_frontpage,0) is_push_to_frontpage,concat('"
				+ url + "',pic_index) pic_index,short_introduction,if(news_type=1,concat('" + url
				+ "',url_index),if(news_type=2,url_index,'')) url_index,"
				+ "the_text,news_type,section from b_banner_news where order_id=?";
		List<Map<String, Object>> list = dao.select(sql, order_id);
		ResponseDTO dto = new ResponseDTO();
		if (list.size() > 0) {
			dto.setData(list.get(0));
			dto.setFlag(true);
		}
		return dto;
	}

	// 获取人员ID
	public String getUserId(String user_phone) {
		String sql = "select user_id from n_manager where user_phone = ? ";
		String user_id = dao.getValue(sql, new Object[] { user_phone }, String.class);
		return user_id;
	}

	// 添加新闻
	@Log(name = "添加新闻")
	public ResponseDTO add(BannerNews news) {
		ResponseDTO dto = new ResponseDTO();
		long order_id = 0l;
		try {
			order_id = dao.insert(news, "b_banner_news");
			dto.setFlag(true);
			dto.setData(order_id);
			if (news.getNews_type().equals("1")) {
				news.setOrder_id(String.valueOf(order_id));
				createHtml(news);
			}
		} catch (Exception e) {
			logger.error("添加失败", e);
			e.printStackTrace();
		}
		return dto;
	}

	// 编辑新闻
	@Log(name = "编辑新闻")
	public boolean update(BannerNews news) {
		boolean flag = false;
		try {
			dao.update(news, "b_banner_news");
			if (news.getNews_type().equals("1")) {
				createHtml(news);
			}
			flag = true;
		} catch (Exception e) {
			logger.error("修改失败", e);
			e.printStackTrace();
		}
		return flag;
	}

	// 删除新闻
	@Log(name = "删除新闻")
	public boolean delete(String order_id) {
		boolean flag = false;
		try {
			dao.delete("b_banner_news", order_id);
			flag = true;
		} catch (Exception e) {
			logger.error("删除失败", e);
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean push(String order_id,String is_push_to_docotor){
		String sql = "update b_banner_news set is_push_to_doctor = ? where order_id = ? ";
		int rows = dao.execute(sql, is_push_to_docotor,order_id);
		if(rows==1){
			return true;
		}else{
			return false;
		}
	}

	// 获取所有的发布者
	public ResponseDTO getManagerList() {
		String sql = "select a.user_id,b.user_name from b_banner_news a,n_manager b where a.user_id=b.user_id group by a.user_id";
		List<Map<String, Object>> list = dao.select(sql);
		ResponseDTO dto = new ResponseDTO();
		dto.setData(list);
		dto.setFlag(true);
		return dto;
	}

	// 生成网页
	@Log(name = "生成网页")
	public void createHtml(BannerNews news) {
		String order_id = news.getOrder_id();
		String head = "<!doctype html>" + "<html>" + "<head>" + "<meta charset='utf-8'>" + "<title></title>"
				+ "<meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'/>"
				+ "</head>" + "<body>";
		String script = "<script src='http://cdn.static.runoob.com/libs/jquery/1.10.2/jquery.min.js'></script>"
				+ "<script>$(function(){$('video').each(function(){$(this).attr({'controls':'controls', 'preload':'auto' ,'autoplay':'autoplay' })"
				+ "});})</script>";
		String tail = "</body>" + "</html>";
		String name = StringUtils.join(new String[] { java.util.UUID.randomUUID().toString(), ".", "html" });
		String text = news.getThe_text();
		String dateDir = DateFormatUtils.format(new Date(), "yyyy"+separator+"MM"+separator+"dd");
		String uploadUrl = BeanUtil.getProperty("dbconfig").getString("pictureuploaddir");
		String uploadDir = "upload"+separator+"news"+separator+"html"+separator+dateDir+separator;
		if (!uploadUrl.endsWith(String.valueOf(separator))) {
			uploadUrl = uploadUrl + separator;
		}
		File dir = new File(uploadUrl + uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File html = new File(uploadUrl + uploadDir + name);
		if (!html.exists()) {
			try {
				html.createNewFile();
				String sql = "update b_banner_news a set a.url_index = ?,a.the_text = ? where a.order_id = ? ";
				dao.execute(sql, uploadDir + name, text, order_id);
				PrintWriter pw = new PrintWriter(html, "utf8");
				pw.println(head + text +script+ tail);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Log(name = "保存图片")
	public void savePicture(HttpServletRequest request, String path) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pic_type", request.getParameter("report_type"));
		map.put("parent_id", request.getParameter("order_id"));
		map.put("pic_num", 1);
		map.put("medical_picture_upload", path);
		String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		map.put("create_date", date);
		dao.insert(map, "p_picture");
	}

	public String getFilePath(HttpServletRequest request) {
		// 用户ID
		String user_id = request.getParameter("user_id");
		// 文件类型
		String report_type = request.getParameter("report_type");
		// 文件保存路径
		String dateDir = DateFormatUtils.format(new Date(), "yyyy"+separator+"MM"+separator+"dd");
		String uploadDir = "upload"+separator+"image"+ separator+ report_type + separator + dateDir + separator + user_id + separator;
		// 文件目录是否存在
		File dir = new File(BeanUtil.getProperty("dbconfig").getString("pictureuploaddir") + uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return uploadDir;
	}
}
