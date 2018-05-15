package com.imooc.proxy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;

public class Proxy {
	@SuppressWarnings({ "unused", "deprecation" })
	public static Object newProxyInstance(Class<?> interf) {
		//1、声明一段源码，动态产生代理
		//windows系统中的回车换行符\r\n
		String rt = "\r\n";
		
		String methodStr="";
		for(Method m :interf.getMethods()) {
			methodStr+="	@Override"+rt+
			"	public void "+m.getName()+"() {"+rt+
			"		System.out.println(\"日志开始。。。\");"+rt+
			"		m."+m.getName()+"();"+rt+
			"		System.out.println(\"日志结束。。。\");"+rt+
			"	}";
		}
		
		String str=
		"package com.imooc.proxy;"+rt+
		"public class $Proxy0 implements "+interf.getName()+ "{"+rt+
		"	private "+interf.getName()+ " m;"+rt+
		"	public $Proxy0("+interf.getName()+ " m) {"+rt+
		"		super();"+rt+
		"		this.m = m;"+rt+
		"	}"+rt+
		methodStr+rt+
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
