package com.imooc.proxy;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.commons.io.FileUtils;

public class Proxy {
	@SuppressWarnings({ "deprecation" })
	public static Object newProxyInstance(Class infce) throws Exception {
		//1、声明一段源码，动态产生代理
		//windows系统中的回车换行符\r\n
		String rt = "\r\n";
		String methodStr="";
		for(Method m : infce.getMethods()) {
			methodStr += "	@Override"+rt+
			"	public void " + m.getName() +"() {"+rt+
			"		System.out.println(\"日志开始。。。\");"+rt+
			"		m." + m.getName() + "();"+rt+
			"		System.out.println(\"日志结束。。。\");"+rt+
			"	}";
		}
		
		String str=
		"package com.imooc.proxy;"+rt+
		"public class $Proxy0 implements "+infce.getName()+ " {"+rt+
		"	public $Proxy0("+infce.getName()+ " m) {"+rt+
		"		super();"+rt+
		"		this.m = m;"+rt+
		"	}"+rt+
		"	private "+infce.getName()+ " m;"+rt+
		methodStr+rt+
		"}";
		//2、编译源码（JDK Compiler API），

		//产生代理类的Java文件
		String filename = System.getProperty("user.dir")+"/bin/com/imooc/proxy/$Proxy0.java";
		File file = new File(filename);
		FileUtils.writeStringToFile(file, str);
		
		//获取当前系统的java编译器
		JavaCompiler comproiler = ToolProvider.getSystemJavaCompiler();
		//文件管理者
		StandardJavaFileManager fileManager = comproiler.getStandardFileManager(null, null, null);
		//获取文件Java文件对象
		Iterable units = fileManager.getJavaFileObjects(file);
		//获取编译任务
		CompilationTask task = comproiler.getTask(null, fileManager, null, null, null, units);
		//进行编译
		task.call();
		//关闭资源
		fileManager.close();
		
		//编译好的文件load到内存当中
		//因为生成的文件在bin目录下，可以直接使用ClassLoader进行加载
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		Class c = cl.loadClass("com.imooc.proxy.$Proxy0");
		//检测加载到的类c
		System.out.println(c.getName());
		
		//获取类的构造函数，创建类的实例
		Constructor ctr = c.getConstructor(infce);
		return ctr.newInstance(new Car());
	}
}
