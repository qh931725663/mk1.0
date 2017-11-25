package com.haaa.cloudmedical.common.util;

import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.entity.Electrocardiograph;
import com.haaa.cloudmedical.entity.UrineTest;

/**
 * 康泰7件套部分码表转换乘美康慧数据库码表值
 * 
 * @author Bowen Fan
 *
 */
public class DataTransfromUtil {

	private static String string2UnicodeUrineTest(String value) {
		switch (value) {
		case "Norm":
			value = Constant.URINEMINUS;
			break;
		case "-":
			value = Constant.URINEMINUS;
			break;
		case "1+":
			value = Constant.URINEPLUS;
			break;
		case "2+":
			value = Constant.URINETWOPLUS;
			break;
		case "3+":
			value = Constant.URINETHREEPLUS;
			break;
		case ">=3+":
			value = Constant.URINETHREEPLUS;
			break;
		case "4+":
			value = Constant.URINEFOURPLUS;
			break;
		case "+-":
			value = Constant.URINEPLUSMINUS;
			break;
		case "<=1.005":
			value = "1.005";
			break;
		case ">=1.030":
			value = "1.030";
			break;
		}
		return value;
	}

	public static void urineTest9900Trans(UrineTest urineTest) {
		urineTest.setBil(string2UnicodeUrineTest(urineTest.getBil()));
		urineTest.setBld(string2UnicodeUrineTest(urineTest.getBld()));
		urineTest.setGlu(string2UnicodeUrineTest(urineTest.getGlu()));
		urineTest.setKet(string2UnicodeUrineTest(urineTest.getKet()));
		urineTest.setLeu(string2UnicodeUrineTest(urineTest.getLeu()));
		urineTest.setNit(string2UnicodeUrineTest(urineTest.getNit()));
		urineTest.setPh(string2UnicodeUrineTest(urineTest.getPh()));
		urineTest.setSg(string2UnicodeUrineTest(urineTest.getSg()));
		urineTest.setPro(string2UnicodeUrineTest(urineTest.getPro()));
		urineTest.setUro(string2UnicodeUrineTest(urineTest.getUro()));
		urineTest.setVc(string2UnicodeUrineTest(urineTest.getVc()));
	}

	public static void urineTestTrans(UrineTest urineTest, String check_data_source) throws Exception {

		System.out.println(urineTest);
		if (Integer.valueOf(urineTest.getUro()) < 0 || Integer.valueOf(urineTest.getUro()) > 3) {
			throw new Exception("uro输入越界");
		}
		switch (urineTest.getUro()) {
		case "0":
			urineTest.setUro(Constant.URINEMINUS);
			break;
		case "1":
			urineTest.setUro(Constant.URINEPLUS);
			break;
		case "2":
			urineTest.setUro(Constant.URINETWOPLUS);
			break;
		case "3":
			urineTest.setUro(Constant.URINETHREEPLUS);
			break;
		default:
			break;
		}

		if (Integer.valueOf(urineTest.getBld()) < 0 || Integer.valueOf(urineTest.getBld()) > 4) {
			throw new Exception("bld输入越界");
		}

		switch (urineTest.getBld()) {
		case "0":
			urineTest.setBld(Constant.URINEMINUS);
			break;
		case "1":
			urineTest.setBld(Constant.URINEPLUSMINUS);
			break;
		case "2":
			urineTest.setBld(Constant.URINEPLUS);
			break;
		case "3":
			urineTest.setBld(Constant.URINETWOPLUS);
			break;
		case "4":
			urineTest.setBld(Constant.URINETHREEPLUS);
			break;
		default:
			break;
		}

		if (Integer.valueOf(urineTest.getBil()) < 0 || Integer.valueOf(urineTest.getBil()) > 3) {
			throw new Exception("bil输入越界");
		}
		switch (urineTest.getBil()) {
		case "0":
			urineTest.setBil(Constant.URINEMINUS);
			break;
		case "1":
			urineTest.setBil(Constant.URINEPLUS);
			break;
		case "2":
			urineTest.setBil(Constant.URINETWOPLUS);
			break;
		case "3":
			urineTest.setBil(Constant.URINETHREEPLUS);
			break;
		default:
			break;
		}

		if (Integer.valueOf(urineTest.getKet()) < 0 || Integer.valueOf(urineTest.getKet()) > 3) {
			throw new Exception("ket输入越界");
		}
		switch (urineTest.getKet()) {
		case "0":
			urineTest.setKet(Constant.URINEMINUS);
			break;
		case "1":
			urineTest.setKet(Constant.URINEPLUS);
			break;
		case "2":
			urineTest.setKet(Constant.URINETWOPLUS);
			break;
		case "3":
			urineTest.setKet(Constant.URINETHREEPLUS);
			break;
		default:
			break;
		}

		if (Integer.valueOf(urineTest.getGlu()) < 0 || Integer.valueOf(urineTest.getGlu()) > 5) {
			throw new Exception("glu输入越界");
		}
		switch (urineTest.getGlu()) {
		case "0":
			urineTest.setGlu(Constant.URINEMINUS);
			break;
		case "1":
			urineTest.setGlu(Constant.URINEPLUSMINUS);
			break;
		case "2":
			urineTest.setGlu(Constant.URINEPLUS);
			break;
		case "3":
			urineTest.setGlu(Constant.URINETWOPLUS);
			break;
		case "4":
			urineTest.setGlu(Constant.URINETHREEPLUS);
			break;
		case "5":
			urineTest.setGlu(Constant.URINEFOURPLUS);
		default:
			break;
		}

		if (Integer.valueOf(urineTest.getPro()) < 0 || Integer.valueOf(urineTest.getPro()) > 4) {
			throw new Exception("pro输入越界");

		}
		switch (urineTest.getPro()) {
		case "0":
			urineTest.setPro(Constant.URINEMINUS);
			break;
		case "1":
			urineTest.setPro(Constant.URINEPLUSMINUS);
			break;
		case "2":
			urineTest.setPro(Constant.URINEPLUS);
			break;
		case "3":
			urineTest.setPro(Constant.URINETWOPLUS);
			break;
		case "4":
			urineTest.setPro(Constant.URINETHREEPLUS);
			break;
		default:
			break;
		}

		if (check_data_source.equals(Constant.MANUALUPLOAD)) {
			if (Float.valueOf(urineTest.getPh()) < 0 && Float.valueOf(urineTest.getPh()) > 14) {
				throw new Exception("ph输入越界");
			}
		} else {
			if (Integer.valueOf(urineTest.getPh()) < 0 || Integer.valueOf(urineTest.getPh()) > 4) {
				throw new Exception("ph输入越界");
			}
			switch (urineTest.getPh()) {
			case "0":
				urineTest.setPh("5");
				break;
			case "1":
				urineTest.setPh("6");
				break;
			case "2":
				urineTest.setPh("7");
				break;
			case "3":
				urineTest.setPh("8");
				break;
			case "4":
				urineTest.setPh("9");
				break;
			default:
				break;
			}
		}

		if (Integer.valueOf(urineTest.getLeu()) < 0 || Integer.valueOf(urineTest.getLeu()) > 4) {
			throw new Exception("leu输入越界");
		}
		switch (urineTest.getLeu()) {
		case "0":
			urineTest.setLeu(Constant.URINEMINUS);
			break;
		case "1":
			urineTest.setLeu(Constant.URINEPLUSMINUS);
			break;
		case "2":
			urineTest.setLeu(Constant.URINEPLUS);
			break;
		case "3":
			urineTest.setLeu(Constant.URINETWOPLUS);
			break;
		case "4":
			urineTest.setLeu(Constant.URINETHREEPLUS);
			break;
		default:
			break;
		}

		if (Integer.valueOf(urineTest.getVc()) < 0 || Integer.valueOf(urineTest.getVc()) > 4) {
			throw new Exception("leu输入越界");
		}
		switch (urineTest.getVc()) {
		case "0":
			urineTest.setVc(Constant.URINEMINUS);
			break;
		case "1":
			urineTest.setVc(Constant.URINEPLUSMINUS);
			break;
		case "2":
			urineTest.setVc(Constant.URINEPLUS);
			break;
		case "3":
			urineTest.setVc(Constant.URINETWOPLUS);
			break;
		case "4":
			urineTest.setVc(Constant.URINETHREEPLUS);
		default:
			break;
		}

		if (Integer.valueOf(urineTest.getNit()) < 0 || Integer.valueOf(urineTest.getNit()) > 1) {
			throw new Exception("nit输入越界");
		}
		switch (urineTest.getNit()) {
		case "0":
			urineTest.setNit(Constant.URINEMINUS);
			break;
		case "1":
			urineTest.setNit(Constant.URINEPLUS);
			break;
		default:
			break;
		}

		if (check_data_source.equals(Constant.MANUALUPLOAD)) {
			if (Float.valueOf(urineTest.getSg()) < 0 || Float.valueOf(urineTest.getSg()) > 3) {

			}
		} else {
			if (Integer.valueOf(urineTest.getSg()) < 0 || Integer.valueOf(urineTest.getSg()) > 5) {
				throw new Exception("leu输入越界");
			}
			switch (urineTest.getSg()) {
			case "0":
				urineTest.setSg(Constant.URINESG[0]);
				break;
			case "1":
				urineTest.setSg(Constant.URINESG[1]);
				break;
			case "2":
				urineTest.setSg(Constant.URINESG[2]);
				break;
			case "3":
				urineTest.setSg(Constant.URINESG[3]);
				break;
			case "4":
				urineTest.setSg(Constant.URINESG[4]);
				break;
			case "5":
				urineTest.setSg(Constant.URINESG[5]);
				break;
			default:
				break;
			}
		}

	}

	public static void electroTrans(Electrocardiograph electrocardiograph) throws Exception {
		System.out.println(electrocardiograph);
		if (Integer.valueOf(electrocardiograph.getResult()) < 0
				|| Integer.valueOf(electrocardiograph.getResult()) > 13) {
			throw new Exception("输入越界");
		}
		switch (electrocardiograph.getResult()) {
		case "0":
			electrocardiograph.setResult(Constant.HEARTRESULT[0]);
			break;
		case "1":
			electrocardiograph.setResult(Constant.HEARTRESULT[1]);
			break;
		case "2":
			electrocardiograph.setResult(Constant.HEARTRESULT[2]);
			break;
		case "3":
			electrocardiograph.setResult(Constant.HEARTRESULT[3]);
			break;
		case "4":
			electrocardiograph.setResult(Constant.HEARTRESULT[4]);
			break;
		case "5":
			electrocardiograph.setResult(Constant.HEARTRESULT[5]);
			break;
		case "6":
			electrocardiograph.setResult(Constant.HEARTRESULT[6]);
			break;
		case "7":
			electrocardiograph.setResult(Constant.HEARTRESULT[7]);
			break;
		case "8":
			electrocardiograph.setResult(Constant.HEARTRESULT[8]);
			break;
		case "9":
			electrocardiograph.setResult(Constant.HEARTRESULT[9]);
			break;
		case "10":
			electrocardiograph.setResult(Constant.HEARTRESULT[10]);
			break;
		case "11":
			electrocardiograph.setResult(Constant.HEARTRESULT[11]);
			break;
		case "12":
			electrocardiograph.setResult(Constant.HEARTRESULT[12]);
			break;
		case "13":
			electrocardiograph.setResult(Constant.HEARTRESULT[13]);
			break;
		default:
			break;
		}

	}

	public static int classifyBloodSugar(float blood_sugar, int period) {
		if (period == Constant.EMPTY_STOMACH) {
			if (blood_sugar < Constant.LOW_FBG) {
				return -1;
			} else if (blood_sugar > Constant.HIGH_FBG) {
				return 1;
			} else {
				return 0;
			}
		}
		// 睡前
		else if (period == Constant.BEFORE_SLEEP) {
			if (blood_sugar < Constant.LOW_PRE_BG) {
				return -1;
			} else if (blood_sugar > Constant.HIGH_PRE_BG) {
				return 1;
			} else {
				return 0;
			}
		}
		// 饭后
		else if (period == Constant.AFTER_BREAKFAST || period == Constant.AFTER_DINNER
				|| period == Constant.AFTER_LUNCH) {
			if (blood_sugar < Constant.LOW_POST_BG) {
				return -1;
			} else if (blood_sugar > Constant.HIGH_POST_BG) {
				return 1;
			} else {
				return 0;
			}

		} else if (period == Constant.BEFORE_DINNER || period == Constant.BEFORE_LUNCH) {// 饭前
			if (blood_sugar < Constant.LOW_PRE_BG) {
				return -1;
			} else if (blood_sugar > Constant.HIGH_PRE_BG) {
				return 1;
			} else {
				return 0;
			}
		}
		return 0;
	}

	public static int classifyBloodPressure(int highPressure,int lowPressure) {
		if (highPressure > Constant.HIGH_PRESSURE_HIGH
				|| lowPressure > Constant.LOW_PRESSURE_HIGH) {
			return 1;
		} else if (highPressure < Constant.HIGH_PRESSURE_LOW
				|| lowPressure < Constant.LOW_PRESSURE_LOW) {
			return -1;
		} else {
			return 0;
		}
	}

}
