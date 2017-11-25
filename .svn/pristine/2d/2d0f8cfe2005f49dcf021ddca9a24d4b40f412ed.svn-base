package com.haaa.cloudmedical.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.sun.glass.ui.Pixels.Format;

public class NumberUtil {

	
	public static BigDecimal decimalFormat(double number,int digit){
		BigDecimal b = new BigDecimal(number);
		return b.setScale(digit, BigDecimal.ROUND_HALF_UP);
	}
	
	public static String decimalFormat(float number,int digit){
		String format = "#0";
		if (digit!=0) {
			format+=".";
		}
		for (int i = 0; i < digit; i++) {
			format+="0";
		}
		DecimalFormat fnum = new DecimalFormat(format);   
		return fnum.format(number);  
	}
	
}
