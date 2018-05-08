### 1、JDK静态代理
 1. 业务接口
 2. 接口的实现类
 3. 代理类，实现接口，并扩展实现类的功能

 ### 2、JDK动态代理
1. 业务接口
2. 实现了业务接口的业务类
3. 实现了InvocationHandler接口的handler代理类

### 3、CGLIB动态代理
> 不需要定义业务接口
1. 业务类（不需要实现接口）
2. cglib代理类（实现接口MethodInterceptor）