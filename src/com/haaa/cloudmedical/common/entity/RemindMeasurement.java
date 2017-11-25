/**
 * 
 */
package com.haaa.cloudmedical.common.entity;

/**
 * @author haaa
 *
 */
public enum RemindMeasurement {
	bloodpressure("1400004","测量前需要安静休息五分钟，测量前30分钟内禁止吸烟或饮咖啡，排空膀胱"), // 血压
	bloodsugar("1400005","空腹血糖测量前需要安静休息5分钟，采血前不用降糖药、不吃早餐、不喝水、不运动；饭后血糖测量前需要安静休息5分钟，饭后2小时才能抽血检查"), // 血糖
	weight("1400006","测量前禁止剧烈运动，测量时双足平稳站在秤上，身体放松"), // 体重
	oxygen("1400007","测量前需要安静休息5分钟，保持手指干净和温暖"), // 血氧
	heartrate("1400008","测量前需要安静休息5分钟，保持手指干净和温暖"),// 心率
	vitalcapacity("1400009","测量前保持放松状态，进行一两次深呼吸动作"),// 肺活量
	temperature("1400010","测量时请正确使用耳温计，测量前30分钟内禁止剧烈运动"), // 体温
	urine("1400011","测量前多喝水，禁止服用药物，尿液标本保持新鲜、清洁");// 尿液
	
	private String value;
	private String message;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private RemindMeasurement(String value, String message) {
		this.value = value;
		this.message = message;
	}
}
