# Java基础

> [Java面试通关要点](http://mp.weixin.qq.com/s?__biz=MzIwMzY1OTU1NQ==&mid=2247484070&idx=1&sn=6e00b058c02920dc2c4e9aebf69c242f&chksm=96cd42eaa1bacbfc0243deba7d4f2f7841368cc116407a41e68131201d0b97d10b433a8c9afd&mpshare=1&scene=23&srcid=0417QP2lBB1mzcpgtaAPk3U1#rd)

## 1.面向对象的特征

- 封装
- 继承
- 多态

> 封装
> 继承
> 多态

## 2.final,finally,finalize的区别

### final

- 可以应用于类、方法、变量
- 声明的引用及变量不能被改变
- final变量是只读的
- final方法不可以被子类方法重写
- final方法更快 : 因为在编译时已经静态绑定，不需要再运行时再动态绑定
- final类不能被继承，所以其功能一般是完整的。Java中String、Integer一级其他包装类都是final类型的
- final变量经常和static关键字一起使用，作为常量

## 3.为什么String类是不可变的

- String类使用final修饰符
- java在运行时保存了一个字符串池（String pool）
- String pool中不会保存多个相同的字符串
- 因为不可变，所以是多线程安全的。同一个字符串实例可以被多个线程共享
- 类加载器要用到字符串，不可变性提供了安全性：例如要加载java.sql.Connection类，如果这个值被改变成\*\*.\*\*.\*\*.Connection，容易被攻击
- 在创建是hashcode就被缓存，不需要重新计算。适合做Map中的键，字符串的处理速度要快过其他的键对象

## 4.try-with-resources结构
> Java7中新增加的异常处理机制；

```java
private static void printFileJava7() throws IOException {
    try(FileInputStream input = new FileInputStream("file.txt")) {
        int data = input.read();
        while(data != -1){
            System.out.print((char) data);
            data = input.read();
        }
    }
}
```

1. try语句块执行结束时，try()中括号里的FileInputStream会被自动关闭，因为其实现了java中的**java.lang.AutoCloseable**接口；
2. try(){}结构中，{}中代码抛出一个异常（业务中需要捕获的异常），()中资源被关闭时也抛出异常，则()中的异常会被抑制；

## 5.multiple catch

如果用一个catch块处理多个异常，可以用管道符（|）将它们分开，在这种情况下异常参数变量（ex）是定义为final的，所以不能被修改。这一特性将生成更少的字节码并减少代码冗余。

```java
catch(IOException | SQLException | Exception ex){
     logger.error(ex);
     throw new MyException(ex.getMessage());
}
```
