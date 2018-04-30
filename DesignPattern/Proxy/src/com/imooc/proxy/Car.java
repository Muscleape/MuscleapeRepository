package com.imooc.proxy;

import java.util.Random;

public class Car implements Moveable {

	@Override
	public void move() {
		// 在车中添加功能，记录行驶时间，即记录move方法执行的开似乎时间和结束时间
		long startTime = System.currentTimeMillis();
		System.out.println("汽车开始行驶。。。");
		// 实现开车
		try {
			Thread.sleep(new Random().nextInt(1000));
			System.out.println("汽车行驶中。。。");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("汽车结束行驶。。。汽车行驶时间：" + (endTime - startTime) + "毫秒");
	}

}
