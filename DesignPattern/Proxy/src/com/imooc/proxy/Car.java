package com.imooc.proxy;

import java.util.Random;

public class Car implements Moveable {

	@Override
	public void move() {
		// 实现开车
		try {
			Thread.sleep(new Random().nextInt(1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
