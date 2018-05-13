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

> 使用场景

- 不允许扩展的类，定义类的时候添加final修饰符；
- 类中的方法使用final修饰符时，子类就不能覆盖这个方法（final类中的所有方法自动成为final方法）；
- 将方法或类声明为final的目的：确保他们不会在子类中改变语义；

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

## 6、super和this

super不是一个对象的引用，不能将super赋给另一个对象变量，super只是一个指示编译器调用超累方法的特殊关键字。

## 7、方法签名

方法的名字和参数列表成为方法的签名，返回类型不是签名的一部分。

## 8、String

```java
//直接量字符串对象
String s="hello world";
```

- 由String声明的字符串长度不可变；
- 上面代码创建时，JVM的栈内存中产生一个s变量，堆内存中产生hello world字符串对象。s指向hello world的地址；
- 上面代码属于直接量字符串对象，JVM处理时，会进行缓存，产生时放入字符串池，再次使用时不会重新创建，而是直接指向已存在的字符串；

```java
String s2  = "hello world";
```

```java
//s2与s都是直接量字符串对象“hello world”，s与s2都指向同一个字符串
System.out.println( s == s2);
//上述代码返回值为“true”，即s和s2指向同一个地址
```

- 当一个String对象完成创建后，该对象的内容就固定下来了

```java
//下面代码中str只是个引用变量，str指向的对象在不断变化，最初的对象没有改变
String str = "I like";
str = str + "java";
```

- String类提供了16中构造方法，常用的5种：

```java
//1、String()----初始化一个String对象，表示一个空字符序列
String s = new String();
//2、String(String value)----利用一个直接量创建一个新串
String s1 = new String("hello");
//3、String(char[] value)----利用一个字符数组创建
char[] c = {'h','e','l','l','o'};
String s2 = new String(c);
//4、String(char[] value,int offset,int count)----截取字符数组，从offset开始count个字符创建
String s3 = new String(c,1,3);
//5、String(StringBuffer buffer)----利用StringBuffer创建
```

- 以下代码在内存中产生几个字符串对象

```java
String s = "123" + "456";
```

**解答**：由于Java字符串的缓存机制，编译器在编译的时候会进行优化，在编译的过程中两个字符串会被合并成一个“123456”。如果在缓存池中目前没有123456这个对象，会产生一个，且栈中产生一个引用s指向它。如果缓存池中已存在“123456”，将产生0个对象，直接用s指向它。
