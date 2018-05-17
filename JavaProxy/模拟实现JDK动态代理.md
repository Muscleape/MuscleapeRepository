# JDK动态代理实现

## JDK动态代理实现原理思路：

## 1. 声明一段源码，这段源码动态生成我们的动态代理；

```java
//1、声明一段源码，动态产生代理
//windows系统中的回车换行符\r\n
String rt = "\r\n";
String methodStr="";
for(Method m : infce.getMethods()) {
    methodStr += "    @Override"+rt+
    " public void " + m.getName() +"() {"++
    " try{"+rt+
    " Method md = "+infce.getName+".class.getMethod(\""+m.getName()+"\""+rt+
    " h.invoke(this,md);"+rt+
    " }catch(Exception e){e.printStackTra();}"+rt+
"}";
}

String str=
"package com.imooc.proxy;"+rt+
"import java.lang.reflect.Method;"+rt+
"import com.imooc.proxy.InvocationHandler+rt+
"public class $Proxy0 implements+infce.getName()+ " {"+rt+
"    public $Proxy0(InvocationHandler h) +rt+
"    this.h = h;"+rt+
"}"+rt+
" private InvocationHandler h;"+rt+
methodStr+rt+
"}";
```

## 2. 把源码生成Java文件；

```java
//产生代理类的Java文件
String filename = System.getProperty("user.dir")+"/bin/com/imooc/proxy/$Proxy0.java";
File file = new File(filename);
FileUtils.writeStringToFile(file, str);
```

## 3. 获取系统的Java编译器（JavaCompiler类似与javac）；

```java
JavaCompiler comproiler = ToolProvider.getSystemJavaCompiler();
```

## 4. 获取文件管理器StandardJavaFileManager；

```java
StandardJavaFileManager fileManager = comproiler.getStandardFileManager(null, null, null);
```

## 5. 获取需要编译的java文件对象（Iterable）；

```java
Iterable units = fileManager.getJavaFileObjects(file);
```

## 6. 获取编译的任务（）；

```java
CompilationTask task = comproiler.getTask(null, fileManager, null, null, null, units);
```

## 7. 进行编译；

```java
task.call();
```

## 8. 关闭文件管理器

```java
fileManager.close();
```

## 9. 编译完成后会生成class文件；

## 10. 把class文件加载到内存中；

```java
//因为生成的文件在bin目录下，可以直接使用ClassLoader进行加载
ClassLoader cl = ClassLoader.getSystemClassLoader();
//默认生成的代理类名称均为 $Proxy0
Class c = cl.loadClass("com.imooc.proxy.$Proxy0");
```

## 11. 产生一个代理类的对象，并返回该对象；

```java
//获取类的构造函数，创建类的实例
Constructor ctr = c.getConstructor(InvocationHandler.class);
return ctr.newInstance(h);
```

## 12. 代理类的调用

> 创建一个InvocationHandler（专门做事务处理）

```java
Car car = new Car();
InvocationHandler h = new TimeHandler(car);
Moveable m = (Moveable) Proxy.newProxyInstance(Moveable.class,h);
m.move();
```