package com.imooc.proxy;

public class Car3 implements Moveable {

	private Car car;

	public Car3(Car car) {
		super();
		this.car = car;
	}

	@Override
	public void move() {
		// 在车中添加功能，记录行驶时间，即记录move方法执行的开似乎时间和结束时间
		long startTime = System.currentTimeMillis();
		System.out.println("汽车开始行驶。。。");
		car.move();
		long endTime = System.currentTimeMillis();
		System.out.println("汽车结束行驶。。。汽车行驶时间：" + (endTime - startTime) + "毫秒");

	}

}
