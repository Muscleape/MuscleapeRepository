# JDK动态代理代码示例

1. 业务接口
2. 实现了业务接口的业务类
3. 实现了InvocationHandler接口的handler代理类

> 1、业务接口

```java
package com.wzq.demo01;

/**
 * 业务接口
 *
 * @author Muscleape
 *
 */
public interface UserService {
	/**
	 * 增加一个用户
	 */
	public void addUser();

	/**
	 * 编辑账户
	 */
	public void editUser();
}

```

> 2、业务接口实现类

```java
package com.wzq.demo01;

/**
 * 业务接口实现类
 * 
 * @author Muscleape
 *
 */
public class UserServiceImpl implements UserService {

	@Override
	public void addUser() {
		System.out.println("增加一个用户。。。");
	}

	@Override
	public void editUser() {
		System.out.println("编辑一个用户。。。");
	}

}

```

> 3、实现了InvocationHandler接口的handler代理类

```java
package com.wzq.demo01;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ServiceInvocationHandler implements InvocationHandler {

	/**
	 * 目标对象
	 */
	private Object target;

	/**
	 * 构造函数
	 * 
	 * @param target
	 */
	public ServiceInvocationHandler(Object target) {
		super();
		this.target = target;
	}

	/**
	 * 实现InvocationHandler接口的方法
	 * 
	 * 执行目标对象的方法，并进行增强
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		Object result = null;
		System.out.println("代理类方法，进行增强。。。");
		System.out.println("事务开始。。。");
		// 执行目标方法对象
		result = method.invoke(target, args);
		System.out.println("事务结束。。。");
		return result;
	}

	/**
	 * 创建代理实例
	 * 
	 * @return
	 * @throws Throwable
	 */
	public Object getProxy() throws Throwable {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				this.target.getClass().getInterfaces(), this);
		// 这样写只返回了目标对象，没有生成代理对象
		// return null;
	}

}

```

> 4、测试类

```java
package com.wzq.demo01;

public class Test {
	/**
	 * jdk动态代理会生成一个动态代理类，生成相应的字节码，然后通过ClassLoader加载字节码；
	 * 
	 * 该实例继承了Proxy类，并实现了业务接口，在实现的方法里通过反射调用了InvocationHandler接口实现类的invoke()回调方法；
	 * 
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		UserService userService = new UserServiceImpl();
		ServiceInvocationHandler handler = new ServiceInvocationHandler(userService);

		// 根据目标生成代理对象
		UserService proxy = (UserService) handler.getProxy();
		proxy.addUser();
	}
}

```

> 5、测试结果

```java
代理类方法，进行增强。。。
事务开始。。。
增加一个用户。。。
事务结束。。。

```
