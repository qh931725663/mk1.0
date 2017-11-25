package com.haaa.cloudmedical.test.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.service.AhdiService;
import com.haaa.cloudmedical.test.dao.TestDao;

@Service
public class TestService {

	@Autowired
	private TestDao dao;

	@Autowired
	private AhdiService service;

	public Map<Long,String> updateHistoryAhdiAndMrs() {
		String sql = "select max(a.order_id) order_id,a.user_id from s_survey a,n_user b "
				+ "where a.user_id = b.user_id and b.user_type = '1500001' and b.user_card is not null group by b.user_id";
		List<Map<String, Object>> list = dao.select(sql)
				.stream().collect(Collectors.toList());
		Map<Long,String> errlog = new HashMap<>(list.size());
		for (Map<String, Object> map : list) {
			long order_id = Long.valueOf(map.get("order_id").toString()).longValue();
			try {
				int flag = service.getAhdi(order_id);
				if(flag==0){
					errlog.put(order_id, "计算失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return errlog;
	}

}
