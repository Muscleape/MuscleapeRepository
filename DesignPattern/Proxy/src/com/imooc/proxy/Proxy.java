package com.imooc.proxy;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Proxy {
	@SuppressWarnings({ "unused", "deprecation" })
	public static Object newProxyInstance() {
		//1、声明一段源码，动态产生代理
		//windows系统中的回车换行符\r\n
		String rt = "\r\n";
		String str=
		"package com.imooc.proxy;"+rt+
		"public class $Proxy0 implements Moveable {"+rt+
		"	private Moveable m;"+rt+
		"	public Proxy0(Moveable m) {"+rt+
		"		super();"+rt+
		"		this.m = m;"+rt+
		"	}"+rt+
		"	@Override"+rt+
		"	public void move() {"+rt+
		"		System.out.println(\"日志开始。。。\");"+rt+
		"		m.move();"+rt+
		"		System.out.println(\"日志结束。。。\");"+rt+
		"	}"+rt+
		"}";
		//2、编译源码（JDK Compiler API），产生新的类（代理类）
		String filename = System.getProperty("user.dir")+"/bin/com/imooc/proxy/$Proxy.java";
		File file = new File(filename);
		try {
			FileUtils.writeStringToFile(file, str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
