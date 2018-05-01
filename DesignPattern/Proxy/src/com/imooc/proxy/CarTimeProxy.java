package com.imooc.proxy;

public class CarTimeProxy implements Moveable {

	private Moveable m;

	public CarTimeProxy(Moveable m) {
		super();
		this.m = m;
	}

	@Override
	public void move() {
		// 在车中添加功能，记录行驶时间，即记录move方法执行的开似乎时间和结束时间
		System.out.println("日志开始。。。");
		m.move();
		System.out.println("日志结束。。。");

	}

}
