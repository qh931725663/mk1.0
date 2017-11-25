package com.haaa.cloudmedical.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.common.entity.ResponseDTO;

@RestController
@RequestMapping("test")
public class TestController {

	@Autowired
	private TestService service;

	@RequestMapping("updateAhdiAndMrs.action")
	public Object updateAhdiAndMrsByHistory() {
		ResponseDTO dto = new ResponseDTO();
		try {
			Map<Long,String> map = service.updateHistoryAhdiAndMrs();
			dto.setData(map);
			dto.setFlag(true);
		} catch (Exception e) {
			dto.setErrmsg(e.getLocalizedMessage());
		} 
		return dto;
	}
	
	@RequestMapping("getFood.action")
	public Object getFood() {
		
		List<Food> foods = new ArrayList<>();
		
		Food stapleFood = new Food();
		stapleFood.setName("主食");
		stapleFood.setFoods(Arrays.asList("米饭","面条","馒头子"));
		foods.add(stapleFood);
		
		Food fruits = new Food();
		fruits.setName("水果");
		fruits.setFoods(Arrays.asList("苹果","香蕉","黄瓜","橙子"));
		foods.add(fruits);
		
		Food meat = new Food();
		meat.setName("肉类");
		meat.setFoods(Arrays.asList("猪肉","牛肉","羊肉","鸡肉","鱼肉"));
		foods.add(meat);
		
		ResponseDTO dto = new ResponseDTO();
		dto.setData(foods);
		dto.setFlag(true);
		
		return dto;
	}
	
	class Food{
		private String name;
		private List<String> foods;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<String> getFoods() {
			return foods;
		}
		public void setFoods(List<String> foods) {
			this.foods = foods;
		}
		@Override
		public String toString() {
			return "Food [name=" + name + ", foods=" + foods + "]";
		}
		
	}

}
