package com.game.yangtechplatform.utils;

import java.text.NumberFormat;

public class NumberUtils {
	public static String numberfat(int number, int maxLength) {
		// 待测试数据
		// 得到一个NumberFormat的实例
		NumberFormat nf = NumberFormat.getInstance();
		// 设置是否使用分组
		nf.setGroupingUsed(false);
		// 设置最大整数位数
		nf.setMaximumIntegerDigits(maxLength);
		// 设置最小整数位数
		nf.setMinimumIntegerDigits(maxLength);
		// 输出测试语句
		return nf.format(number);
	}

	public static String numberfamat2(String number, int mxLength) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < mxLength; i++) {
			builder.append("0");
		}
		String builderStr = builder.toString();

		if (number.length() < mxLength) {
			number = builderStr.substring(0, mxLength - number.length())
					+ number;
		}
		if (number.length() > mxLength) {
			number = number.substring(number.length() - mxLength);
		} 
		return number;
	}
}
