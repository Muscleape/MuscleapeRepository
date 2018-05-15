# Java代理内容整理

## 1、JDK静态代理

 1. 业务接口
 2. 接口的实现类
 3. 代理类，实现接口，并扩展实现类的功能

 > 原理

 一般是代理类对象直接包装被代理对象

 > 缺点

 只能为一个被代理类服务，如果需要代理多个类，会产生过多的代理类；

> 优点

 在编译时产生class文件，运行时无需产生，可以直接使用，效率好；

## 2、JDK动态代理

1. 业务接口
2. 实现了业务接口的业务类
3. 实现了InvocationHandler接口的handler代理类

> 原理

1. 是接口代理；
2. 被代理类需要实现业务接口；
3. 业务代理类需要实现InvocationHandler接口；
4. **JDK动态代理会根据被代理对象生成一个继承Proxy类，并实现该业务接口的JDK代理类，该类的字节码会被传进去的ClassLoader加载，创建JDK代理对象实例**；
5. JDK代理对象实例创建
    1. 创建业务代理对象实例；
    2. 1中的实例赋值给Proxy类；
    3. 通过2，JDK代理对象实例也就有了业务代理对象实例；
    4. JDK代理对象实例通过反射，根据被代理类的业务方法创建相应的Method对象（**可能有多个**）；
    5. JDK代理对象实例调用业务方法时，会先把Method对象作为参数传递给invoke()方法（invoke方法的第二个参数）；
    6. 调用JDK代理对象实例的invoke()回调方法，在invoke方法里通过反射来调用代理对象的方法；

> 缺点

必须实现接口；通过反射动态代理方法，消耗系统性能；

> 优点

无需产生过多的代理类，避免重复代码，系统更加灵活；

## 3、CGLIB动态代理

> 不需要定义业务接口

1. 业务类（不需要实现接口）
2. cglib代理类（实现接口MethodInterceptor）

> 原理

1. 是继承代理；
2. 因为是继承，所有类和方法都不能用final修饰符；

> 缺点

1. CGLIB会继承被代理类；
2. CGLIB会重写被代理方法；
3. 代理类和代理方法不能使用final修饰符；

> 优点

1. 无需实现接口，通过子类字节码来实现；
2. 比反射速度快，没有性能问题；

## 4、JDK动态代理和CGLIB动态代理

JDK|CGLIB
---|-----
只能代理实现了接口的类；没有实现接口的类不能实现JDK动态代理|针对类来实现代理；对指定目标类产生一个子类，通过**方法拦截技术**拦截所有父类方法的调用

## JDK动态代理实现原理思路：

### 1. 声明一段源码，这段源码动态生成我们的动态代理；

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

### 2. 把源码生成Java文件；

```java
//产生代理类的Java文件
String filename = System.getProperty("user.dir")+"/bin/com/imooc/proxy/$Proxy0.java";
File file = new File(filename);
FileUtils.writeStringToFile(file, str);
```

### 3. 获取系统的Java编译器（JavaCompiler类似与javac）；

```java
JavaCompiler comproiler = ToolProvider.getSystemJavaCompiler();
```

### 4. 获取文件管理器StandardJavaFileManager；

```java
StandardJavaFileManager fileManager = comproiler.getStandardFileManager(null, null, null);
```

### 5. 获取需要编译的java文件对象（Iterable）；

```java
Iterable units = fileManager.getJavaFileObjects(file);
```

### 6. 获取编译的任务（）；

```java
CompilationTask task = comproiler.getTask(null, fileManager, null, null, null, units);
```

### 7. 进行编译；

```java
task.call();
```

### 8. 关闭文件管理器

```java
fileManager.close();
```

### 9. 编译完成后会生成class文件；

### 10. 把class文件加载到内存中；

```java
//因为生成的文件在bin目录下，可以直接使用ClassLoader进行加载
ClassLoader cl = ClassLoader.getSystemClassLoader();
//默认生成的代理类名称均为 $Proxy0
Class c = cl.loadClass("com.imooc.proxy.$Proxy0");
```

### 11. 产生一个代理类的对象，并返回该对象；

```java
//获取类的构造函数，创建类的实例
Constructor ctr = c.getConstructor(InvocationHandler.class);
return ctr.newInstance(h);
```

### 12. 代理类的调用

> 创建一个InvocationHandler（专门做事务处理）

```java
Car car = new Car();
InvocationHandler h = new TimeHandler(car);
Moveable m = (Moveable) Proxy.newProxyInstance(Moveable.class,h);
m.move();
```