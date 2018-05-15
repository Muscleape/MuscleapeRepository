package com.imooc.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TimeHandler implements InvocationHandler {

	// 被代理对象
	private Object target;

	public TimeHandler(Object target) {
		super();
		this.target = target;
	}

	@Override
	public void invoke(Object o, Method m) {
		try {
			System.out.println("自定义代理开始执行。。。");
			m.invoke(target);
			System.out.println("自定义代理执行完成。。。");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
