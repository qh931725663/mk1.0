package com.haaa.cloudmedical.common.util;

import java.text.DecimalFormat;
import java.text.Format;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdCard {

	// 省份编码
	private static final String province[] = { "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34",
			"35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64",
			"65", "71", "81", "82", "91" };

	// 每位加权因子
	private static final int weight[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	// 第18位校检码
	private static final String validate[] = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };

	private String card;
	private int year;
	private int month;
	private int day;
	private int age;
	private int sex;
	private String birthday;
	public final boolean flag;
	private String errmsg;

	public String getCard() {
		return card;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getAge() {
		return age;
	}

	public int getSex() {
		return sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getErrmsg() {
		return errmsg;
	}

	private IdCard(String card) {
		this.card = card;
		this.flag = validateIdCard(card);
	}
	
	public static IdCard of(String card){
		return new IdCard(card);
	}

	// 身份证格式校验
	private boolean validateFormat(String card) {
		boolean flag = false;
		if (card != null && card.length() > 0) {
			flag = card.toUpperCase().matches("\\d{15}|\\d{17}[\\dX]");
		} 
		if(!flag){
			this.errmsg="格式错误";
		}
		return flag;
	}

	// 省份编码校验
	private boolean validateProvince(String provinceCode) {
		boolean flag = false;
		if (Arrays.asList(province).contains(provinceCode)) {
			flag = true;
		} else {
			this.errmsg = "省份编码错误";
		}
		return flag;
	}

	// 年份校验
	private boolean validateYear(int year) {
		boolean flag = false;
		if (year <= LocalDate.now().getYear()) {
			flag = true;
		} else {
			this.errmsg = "年份错误";
		}
		return flag;
	}

	// 月份校验
	private boolean validateMonth(int month) {
		boolean flag = false;
		if (month >= 1 && month <= 12) {
			flag = true;
		} else {
			this.errmsg = "月份错误";
		}
		return flag;
	}

	// 是否是闰年
	private boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 100 == 0 && year % 400 == 0);
	}

	// 天数校验
	private boolean validateDay(int year, int month, int day) {
		int temp = 28;
		if (isLeapYear(year)) {
			temp = 29;
		}
		boolean flag = true;
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			flag = day <= 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			flag = day <= 30;
		}
		if (month == 2) {
			flag = day <= temp;
		}
		if (!flag) {
			this.errmsg = "天数错误";
		}
		return flag;

	}

	// 检验码校验
	private boolean validateCode(String card) {
		boolean flag = false;
		if (card.length() == 15)
			flag = true;
		if (card.length() == 18) {
			int sum = 0;
			String Code = card.substring(17, 18);
			String[] cardNum = card.substring(0, 17).split("");
			for (int i = 0; i < 17; i++) {
				sum += Integer.parseInt(cardNum[i]) * weight[i];
			}
			flag = validate[sum % 11].equals(Code);
		}
		if (!flag) {
			this.errmsg = "校验码错误";
		}
		return flag;
	}

	private boolean validateIdCard(String card) {
		String province = "";
		int year = 0;
		int month = 0;
		int day = 0;
		int sex = 0;
		boolean flag = validateFormat(card);
		if (flag) {
			if (card.length() == 15) {
				Pattern pattern = Pattern.compile("(\\d{2})\\d{4}(\\d{2})(\\d{2})(\\d{2})\\d{2}(\\d{1})");
				Matcher matcher = pattern.matcher(card);
				if (matcher.find()) {
					province = matcher.group(1);
					year = Integer.parseInt("19" + matcher.group(2));
					month = Integer.parseInt(matcher.group(3));
					day = Integer.parseInt(matcher.group(4));
					sex = Integer.parseInt(matcher.group(5)) % 2;
				}
			}
			if (card.length() == 18) {
				Pattern pattern = Pattern.compile("(\\d{2})\\d{4}(\\d{4})(\\d{2})(\\d{2})\\d{2}(\\d{1})([\\dX])");
				Matcher matcher = pattern.matcher(card);
				if (matcher.find()) {
					province = matcher.group(1);
					year = Integer.parseInt(matcher.group(2));
					month = Integer.parseInt(matcher.group(3));
					day = Integer.parseInt(matcher.group(4));
					sex = Integer.parseInt(matcher.group(5)) % 2;
				}
			}
		}
		if(flag){
			flag = validateProvince(province);
		}
		if (flag) {
			flag = validateYear(year);
		}
		if (flag) {
			flag = validateMonth(month);
		}
		if (flag) {
			flag = validateDay(year, month, day);
		}
		if (flag) {
			flag = validateCode(card);
		}
		if (flag) {
			Format format = new DecimalFormat("00");
			this.year = year;
			this.month = month;
			this.day = day;
			this.birthday = year + "-" + format.format(month) + "-" + format.format(day);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			this.age = (int) LocalDate.parse(birthday, formatter).until(LocalDate.now(), ChronoUnit.YEARS);
			this.sex = sex;
			this.errmsg = "success";
		}
		return flag;
	}

	@Override
	public String toString() {
		return "IdCard [card=" + card + ", year=" + year + ", month=" + month + ", day=" + day + ", age=" + age
				+ ", sex=" + sex + ", birthday=" + birthday + ", flag=" + flag + ", errmsg=" + errmsg + "]";
	}
}
