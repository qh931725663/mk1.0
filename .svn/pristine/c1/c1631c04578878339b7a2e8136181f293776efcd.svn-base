package com.haaa.cloudmedical.app.plan.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.app.plan.dao.ChronicPlanAppDao;
import com.haaa.cloudmedical.app.plan.service.ChronicPlanAppService;
import com.haaa.cloudmedical.common.annotation.Log;
import com.haaa.cloudmedical.common.entity.RetData;
import com.haaa.cloudmedical.common.exception.BizException;
import com.haaa.cloudmedical.common.util.BeanUtil;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.entity.ChronicManage;
import com.haaa.cloudmedical.entity.ChronicTrack;

@Service
@Log(name = "医生端计划管理")
public class ChronicPlanAppServiceImpl implements ChronicPlanAppService {

    @Autowired
    private ChronicPlanAppDao dao;

    private Logger            logger = Logger.getLogger(ChronicPlanAppServiceImpl.class);

    @Override
    public Object getList(String user_id, String type) {
        if (StringUtils.isBlank(user_id)) {
            return new RetData<Object>(false, "用户名不能为空");
        }
        if (StringUtils.isBlank(type)) {
            return new RetData<Object>(false, "类型不能为空");
        }
        String sql = "select * from m_chronic_manage where user_id=" + user_id + " and chronic_code=" + type;
        List<Map<String, Object>> list = dao.select(sql);
        return new RetData<List<Map<String, Object>>>(true, list);
    }

    @Override
    @Log(name = "添加计划")
    public Object addPlan(ChronicManage form) {
        try {
            String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
            form.setCreate_date(date);
            form.setCheck_num("0");
            form.setIs_end("102");
            return dao.insert(form, "m_chronic_manage");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizException("添加失败");
        }
    }

    @Override
    @Log(name = "追踪计划")
    public Object addTrackPlan(ChronicTrack form) {
        try {
            if (StringUtils.isBlank(form.getChronic_order_id())) {
                return new RetData<Object>(false, "计划ID不能为空");
            }
            //查询计划管理
            String sql = "select * from m_chronic_manage where order_id=" + form.getChronic_order_id();
            Map<String, Object> map = this.dao.select(sql).get(0);
            //历史追踪次数
            Integer check_num = Integer.valueOf(map.get("check_num").toString());
            Integer check_target = Integer.valueOf(map.get("check_target").toString());
            if (check_num == check_target) {
                return new RetData<Object>(false, "跟踪完成");
            }
            check_num += 1;//追踪次数
            if (check_num == check_target) {
                sql = "update m_chronic_manage set is_end = 101,check_num = check_num + 1 where check_target > check_num and order_id="
                      + form.getChronic_order_id();
            } else {
                sql = "update m_chronic_manage set check_num = check_num + 1 where check_target > check_num and order_id="
                      + form.getChronic_order_id();
            }
            //更新计划
            this.dao.execute(sql);
            //添加追踪
            String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
            form.setCreate_date(date);
            form.setCheck_num(check_num.toString());
            Long ret = dao.insert(form, "m_track_plan");
            return new RetData<Long>(true, ret);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizException("添加失败");
        }
    }

    @Override
    @Log(name = "更新计划")
    public Object updatePlan(ChronicManage form) {
        try {
            if (StringUtils.isBlank(form.getOrder_id())) {
                return new RetData<Object>(false, "ID不能为空");
            }
            //
            String sql = "select * from m_chronic_manage where order_id=" + form.getOrder_id();
            Map<String, Object> map = this.dao.select(sql).get(0);
            Integer check_num = Integer.valueOf(map.get("check_num").toString());
            Integer check_target = Integer.valueOf(form.getCheck_target());
            //
            if (check_target > check_num && "101".equals(map.get("is_end").toString())) {
                form.setIs_end("102");
            } else if (check_target <= check_num) {
                return new RetData<Object>(false, "检查次数必须大于已更新次数");
            }
            Integer ret = dao.update(form, "m_chronic_manage");
            return new RetData<Integer>(true, ret);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizException("更新失败");
        }
    }

    @Override
    public Object getTemplateList(String type) {
        if (StringUtils.isBlank(type)) {
            return new RetData<Object>(false, "类型不能为空");
        }
        String sql = "select * from m_plan_template where plan_type=" + type;
        List<Map<String, Object>> list = this.dao.select(sql);
        return new RetData<List<Map<String, Object>>>(true, list);
    }

    @Override
    @Log(name = "删除计划")
    public Object deletePlan(String order_id) {
        try {
            if (StringUtils.isBlank(order_id)) {
                return new RetData<Object>(false, "ID不能为空");
            }
            String sql = "delete from m_chronic_manage where order_id=" + order_id;
            Integer ret = this.dao.execute(sql);
            return new RetData<Integer>(true, ret);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizException("删除失败");
        }
    }

    @Override
    public Object getTrackPlanList(String order_id) {
        if (StringUtils.isBlank(order_id)) {
            return new RetData<Object>(false, "ID不能为空");
        }
        String sql = "select order_id,chronic_order_id,check_num,chronic_code,chronic_name,check_result,"
                     + "date_format(create_date,'%Y-%m-%d %H:%i') create_date from m_track_plan where chronic_order_id=" + order_id;
        List<Map<String, Object>> list = this.dao.select(sql);
        //图片路径
        String uploadUrl = BeanUtil.getProperty("dbconfig").getString("pictureuploadhttp");
        if (list != null && list.size() > 0) {
            list.stream().forEach(m -> {
                String track_id = m.get("order_id").toString();
                String pic_sql = "select order_id,parent_id,concat('" + uploadUrl
                                 + "',medical_picture_upload) medical_picture_upload,pic_type,pic_num from p_picture where pic_type = 500008 and parent_id=" + track_id;
                List<Map<String, Object>> piclist = this.dao.select(pic_sql);
                m.put("piclist", piclist);
            });
        }
        return new RetData<List<Map<String, Object>>>(true, list);
    }
}
