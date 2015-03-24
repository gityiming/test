package com.example.demo.bean;

import com.example.demo.util.PinyinUtils;

public class Friend implements Comparable<Friend> {

	private String name;
	private String pinyin;

	public Friend(String name) {
		super();
		this.name = name;
		pinyin = PinyinUtils.getPinyin(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Override
	public int compareTo(Friend another) {
		return this.pinyin.compareTo(another.pinyin);
	}

}
