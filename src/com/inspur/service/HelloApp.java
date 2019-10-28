package com.inspur.service;

public class HelloApp {
	public HelloApp() {}
	/**
	 * 对外公开的服务方法
	 */
	public String sayHello(String name) {
		return "Hello," + name;
	}
}
