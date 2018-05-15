package com.imooc.proxy;

/**
 * 测试类
 */
public class Client {
	// public static void main(String[] args) {
	// // Car car = new Car();
	// // car.move();
	// // 使用继承方式
	// // Moveable m = new Car2();
	// // m.move();
	// // 使用聚合方式
	// Car car = new Car();
	// Moveable m = new Car3(car);
	// m.move();
	// }

	// 模拟JDK动态代理实现
	public static void main(String[] args) {
		Proxy.newProxyInstance(Moveable.class);
	}
}
