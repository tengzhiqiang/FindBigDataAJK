package com.example.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	
	public static void main(String[] args) {
		System.out.println(getInteger("1s1s1sfd汉中"));
	}

	/**
	 * 返回数字，没有小数或者符号
	 * @param content
	 * @return
	 */
	public static String getInteger(String content) {
		Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(content);
		if (isNum.find()) {
			return isNum.group(0);
		}
		
		return null;
	}
}
