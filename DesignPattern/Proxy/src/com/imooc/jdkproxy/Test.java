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
		 */
		Moveable m = (Moveable) Proxy.newProxyInstance(arg0, arg1, arg2);
		m.move();
	}

}
