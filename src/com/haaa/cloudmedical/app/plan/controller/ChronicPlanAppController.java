package com.haaa.cloudmedical.app.plan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haaa.cloudmedical.app.plan.service.ChronicPlanAppService;
import com.haaa.cloudmedical.common.entity.RetData;
import com.haaa.cloudmedical.common.exception.BizException;
import com.haaa.cloudmedical.entity.ChronicManage;
import com.haaa.cloudmedical.entity.ChronicTrack;

@Controller
@RequestMapping("/plan-app")
public class ChronicPlanAppController {

    @Autowired
    private ChronicPlanAppService service;

    /**
     * 查询用户计划列表
     * @Description: 
     * @author 吴琪
     * @param user_id
     * @return 
     * @date: 2017年6月19日 下午2:32:46
     */
    @RequestMapping(value = "/getList.action", method = RequestMethod.POST)
    @ResponseBody
    public Object getList(String user_id, String type) {
        Object ret = service.getList(user_id, type);
        return ret;
    }

    /**
     * 添加计划管理
     * @Description: 
     * @author 吴琪
     * @param model
     * @return 
     * @date: 2017年6月19日 下午2:56:55
     */
    @RequestMapping(value = "/addPlan.action", method = RequestMethod.POST)
    @ResponseBody
    public Object addPlan(ChronicManage form) {
        try {
            Object order_id = service.addPlan(form);
            return new RetData<Object>(true, order_id);
        } catch (BizException e) {
            return new RetData<Object>(false, e.getMessage());
        }
    }

    /**
     * 修改计划
     * @Description: 
     * @author 吴琪
     * @param model
     * @return 
     * @date: 2017年6月19日 下午2:59:47
     */
    @RequestMapping(value = "/updatePlan.action", method = RequestMethod.POST)
    @ResponseBody
    public Object updatePlan(ChronicManage form) {
        try {
            Object ret = service.updatePlan(form);
            return ret;
        } catch (BizException e) {
            return new RetData<Object>(false, e.getMessage());
        }
    }

    /**
     * 跟踪计划
     * @Description: 
     * @author 吴琪
     * @return 
     * @date: 2017年6月19日 下午3:01:23
     */
    @RequestMapping(value = "/trackPlan.action", method = RequestMethod.POST)
    @ResponseBody
    public Object trackPlan(ChronicTrack form) {
        try {
            Object ret = service.addTrackPlan(form);
            return ret;
        } catch (BizException e) {
            return new RetData<Object>(false, e.getMessage());
        }
    }

    /**
     * 获取模板列表
     * @Description: 
     * @author 吴琪
     * @return 
     * @date: 2017年6月23日 上午9:26:59
     */
    @RequestMapping(value = "/getTemplateList.action", method = RequestMethod.POST)
    @ResponseBody
    public Object getTemplateList(String type) {
        return service.getTemplateList(type);
    }

    /**
     * 删除计划
     * @Description: 
     * @author 吴琪
     * @param order_id
     * @return 
     * @date: 2017年6月23日 下午2:41:54
     */
    @RequestMapping(value = "/deletePlan.action", method = RequestMethod.POST)
    @ResponseBody
    public Object deletePlan(String order_id) {
        try {
            Object ret = service.deletePlan(order_id);
            return ret;
        } catch (Exception e) {
            return new RetData<Object>(false, e.getMessage());
        }
    }
    
    /**
     * 查询计划跟踪列表
     * @Description: 
     * @author 吴琪
     * @return 
     * @date: 2017年6月23日 下午3:34:17
     */
    @RequestMapping(value = "/getTrackPlanList.action", method = RequestMethod.POST)
    @ResponseBody
    public Object getTrackPlanList(String order_id){
        Object ret = service.getTrackPlanList(order_id);
        return ret;
    }
}
