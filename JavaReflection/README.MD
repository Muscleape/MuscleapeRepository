# Java反射相关内容整理

## Class类

1. Java语言中，**普通数据类型类**、**静态的成员**不是对象；
2. 类也是对象，类是java.lang.Class类的实例对象；
3. Class类的构造函数是私有的，只能JVM调用；
4. 任何一个类都是Class类的实例对象，这种实例对象有3种表示方式：

```java
Foo foo1 = new Foo();
//1.任何一个类都有一个隐含的静态成员变量class：
Class c1 = Foo.class;
//2.已知该类的对象，通过getClass()方法
Class c2 = foo1.getClass();
/**
 * 其中c1、c2表示了Foo类的类类型（class type）;
 * 万事万物皆对象;
 * 类也是对象，是Class类的实例对象;
 */
//c1和c2都代表了Foo类的类类型，一个类只可能是Class类的一个实例对象，所以以下代码值为true;
System.out.println(c1 == c2);//true
//3.第三种方式
Class c3 = null;
c3 = Class.forName("com.wzq.Foo");
System.out.println(c2 == c3);//true
// 可以通过类的类类型创建该类的对象实例，即可以通过c1、c2、c3创建Foo的实例；
Foo foo = (Foo)c1.newInstance();
```

## Java动态加载类

> 1. Class.forName("类的全限定名")不仅表示了类的类类型，还代表了动态加载类；
>
> 2. 编译时刻加载类是静态加载类，运行时刻加载类是动态加载类；
>
> 3. new 创建对象是静态加载类，在编译时刻就需要加载所有可能使用到的类；
>

```java
//动态加载
Class<?> c = Class.forName(args[0]);
OfficeIntf oa = (OfficeIntf) c.newInstance();//接口
oa.start();
```

## Java方法的反射

1. 方法的名称和方法的参数列表唯一决定某个方法；
2. method.invoke(对象,参数列表);

## 通过反射了解集合泛型的本质

1. 编译之后集合的泛型是去泛型化的；
2. Java中集合的泛型，是防止错误输入的，只在编译阶段有效，绕过编译就无效了；

```java
ArrayList a1 = new ArrayList();
ArrayList<String> a2 = new ArrayList<String>();
Class c1 = a1.getClass();
Class c2 = a2.getClass();
System.out.println(a1==a2);//结果为true
```