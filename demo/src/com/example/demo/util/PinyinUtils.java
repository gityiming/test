package com.example.demo.util;

import android.text.TextUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {

	public static String getPinyin(String str) {
		
		if(TextUtils.isEmpty(str)){
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		// 初始化拼音格式工具
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		
		// 得到字符数组 
		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			// 如果是空格，则不添加
			if(Character.isWhitespace(c)){
				continue;
			}
			// -127 -> 128
			if(c > 128){
				// 可能是汉字
				try {
					// c 得到多音字的第一种读法
					String pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format)[0];
					sb.append(pinyin);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
					sb.append(c);
				}
			}else {
				sb.append(c);
			}
		}
		
		return sb.toString();
	}

}
