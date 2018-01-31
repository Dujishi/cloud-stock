package com.xiaoka.cloud.stock.client.app.bridge.demo;

import org.apache.commons.lang3.StringUtils;

public class BridgeDemoDto {
	private String name;
	private int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return StringUtils.join(this.name, " - ", String.valueOf(age));
	}
}
