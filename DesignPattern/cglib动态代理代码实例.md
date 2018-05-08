# cglib动态代理代码示例

- 引用包cglib-xxx.jar
- 非Maven项目还需要手动引用包asm-xxx.jar

1. 业务类（不需要定义接口）
2. cglib代理类（实现接口MethodInterceptor）

> 1、业务类

```java
package com.wzq.demo02;

/**
 * 业务类
 * 
 * 没有实现接口
 * 
 * 如果类是final的，则无法生成代理对象，报错
 * 
 * 如果方法是final的，代理无效
 * 
 * @author Muscleape
 *
 */
public class UserServiceImpl {
	public void addUser() {
		System.out.println("增加一个用户。。。");
	}

	public void editUser() {
		System.out.println("编辑一个用户。。。");
	}
}

```

> 2、cglib代理类，需要实现接口MethodInterceptor

```java
package com.wzq.demo02;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class UserServiceCglib implements MethodInterceptor {

	private Object target;

	public Object getInstance(Object target) {
		this.target = target;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.target.getClass());
		// 设置回调方法
		enhancer.setCallback(this);
		// 创建代理对象
		return enhancer.create();
	}

	/**
	 * 实现MethodInterceptor接口中重写的方法
	 * 
	 * 回调方法
	 */
	@Override
	public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("事务开始。。。");
		Object result = proxy.invokeSuper(object, args);
		System.out.println("事务结束。。。");
		return result;
	}

}

```

> 3、测试类

```java
package com.wzq.demo02;

public class TestCglib {
	public static void main(String[] args) {
		UserServiceCglib cglib = new UserServiceCglib();
		UserServiceImpl bookFacedImpl = (UserServiceImpl) cglib.getInstance(new UserServiceImpl());
		bookFacedImpl.addUser();
	}
}

```

> 4、测试结果

```java
事务开始。。。
增加一个用户。。。
事务结束。。。

```

> 5、异常信息(项目只引用了cglib包，没有引用asm包)
```java
Exception in thread "main" java.lang.NoClassDefFoundError: org/objectweb/asm/Type
	at net.sf.cglib.core.TypeUtils.parseType(TypeUtils.java:184)
	at net.sf.cglib.core.KeyFactory.<clinit>(KeyFactory.java:72)
	at net.sf.cglib.proxy.Enhancer.<clinit>(Enhancer.java:72)
	at com.wzq.demo02.UserServiceCglib.getInstance(UserServiceCglib.java:15)
	at com.wzq.demo02.TestCglib.main(TestCglib.java:6)
Caused by: java.lang.ClassNotFoundException: org.objectweb.asm.Type
	at java.net.URLClassLoader.findClass(Unknown Source)
	at java.lang.ClassLoader.loadClass(Unknown Source)
	at sun.misc.Launcher$AppClassLoader.loadClass(Unknown Source)
	at java.lang.ClassLoader.loadClass(Unknown Source)
	... 5 more
FATAL ERROR in native method: JDWP on checking for an array class, jvmtiError=JVMTI_ERROR_WRONG_PHASE(112)
JDWP exit error JVMTI_ERROR_WRONG_PHASE(112): on checking for an array class [util.c:1299]

```