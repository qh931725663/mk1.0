package com.haaa.cloudmedical.platform.plan.service;

import javax.servlet.http.HttpServletRequest;

import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.platform.plan.model.PlanVo;
import com.haaa.cloudmedical.platform.plan.model.TrackVo;

public interface IPlanManagerService {

    Page getPage(PlanVo model);

    Object getPlanList(Integer user_id);

    Object getPlanItem(Integer order_id);

    Object addTrackItem(TrackVo model, HttpServletRequest request);

    Object trackItemList(String plan_order_id, String dict_order_id);
    
}
