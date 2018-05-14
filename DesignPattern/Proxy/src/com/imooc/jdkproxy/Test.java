package com.imooc.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.imooc.proxy.Car;
import com.imooc.proxy.Moveable;

public class Test {

	public static void main(String[] args) {
		Car car = new Car();
		
		InvocationHandler arg2 = new TimeHandler(car);
		
		Class<?> cls = car.getClass();
		ClassLoader arg0 = cls.getClassLoader();
		Class<?>[] arg1 = cls.getInterfaces();

		/**
		 * 参数1：类加载器
		 * 
		 * 参数2：实现接口
		 * 
		 * 参数3：InvocationHandler
		 * 
		 * 动态代理实现思路：
		 * 实现功能：通过Proxy的newProxyInstance返回代理对象
		 * 1、声明一段源码（动态产生代理）
		 * 2、编译源码（JDK Compiler API），产生新的类（代理类）
		 * 3、将这个类load到内存当中，产生一个新的对象（代理对象）
		 * 4、return代理对象
		 */
		Moveable m = (Moveable) Proxy.newProxyInstance(arg0, arg1, arg2);
		m.move();
	}

}
