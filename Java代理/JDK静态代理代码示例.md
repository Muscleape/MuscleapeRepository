# JDK静态代理示例代码

 1. 业务接口
 2. 接口的实现类
 3. 代理类，实现接口，并扩展实现类的功能
 > 1、业务接口

 ```java
/**
 * 业务接口
 * @author pc
 *
 */
public interface UserService {
    // 增加一个用户
    public void addUser();
    // 编辑账户
    public void editUser();
}
 ```

 > 2、业务实现类

```java
 /**
 * 业务实现类
 * @author pc
 *
 */
public class UserServiceImpl implements UserService {

    public void addUser() {
        System.out.println("增加一个用户。。。");
    }

    public void editUser() {
        System.out.println("编辑一个用户。。。");
    }

}
 ```

 > 3、代理类（实现业务接口，并实例化业务实现类）

```java
 /**
 * 代理类
 *
 * @author pc
 *
 */
public class UserServiceProxy implements UserService {

    private UserServiceImpl userImpl;

    public UserServiceProxy(UserServiceImpl countImpl) {
        this.userImpl = countImpl;
    }

    public void addUser() {
        System.out.println("代理类方法，进行了增强。。。");
        System.out.println("事务开始。。。");
        // 调用委托类的方法;
        userImpl.addUser();
        System.out.println("处理结束。。。");
    }

    public void editUser() {
        System.out.println("代理类方法，进行了增强。。。");
        System.out.println("事务开始。。。");
        // 调用委托类的方法;
        userImpl.editUser();
        System.out.println("事务结束。。。");
    }

}
 ```

 > 4、测试类

```java
 public static void main(String[] args) {
    UserServiceImpl userImpl = new UserServiceImpl();
    UserServiceProxy proxy = new UserServiceProxy(userImpl);
    proxy.addUser();
    System.out.println("----------分割线----------");
    proxy.editUser();
}
 ```

 > 5、结果

```java
 代理类方法，进行了增强。。。
事务开始。。。
增加一个用户。。。
处理结束。。。
----------分割线----------
代理类方法，进行了增强。。。
事务开始。。。
编辑一个用户。。。
事务结束。。。
 ```