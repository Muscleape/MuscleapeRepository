# Java类加载与初始化

> 这种层面的知识，目前都是从其他的文章中读来的，自己的认识还没有深入到这个层面，所有这篇文章也是【转载来的】[http://www.cnblogs.com/zhguang/p/3154584.html](http://www.cnblogs.com/zhguang/p/3154584.html)
>
> **但是，转载也是要有自己的态度的，每个字都要自己手打，就是这（周）么（末）努（无）力（聊）！！！＜( ￣︿￣)︵θ︵θ︵θ︵θ︵☆**

## 先说一下目录

1. 类加载器
2. 动态加载
3. 链接
4. 初始化
5. 示例
6. 哈哈，XX的评论也得找几个噻

### 1、类加载器

在了解Java的机制之前，需要先了解一下类在JVM（Java虚拟机）中如何加载的，这对后面理解java其他机制将有重要的作用。

每个类编译后都会产生一个Class对象,存储在.class文件里，JVM使用类加载器（Class Loader）来加载类的字节码文件（.class），类加载器实质上是一条类加载器链，一般的，我们只会用到一个原生的类加载器，它只加载Java API等可信类，通常只是在本地磁盘中加载，这些类一般就够我们使用了。如果我们需要从远程网络或数据库中下载.class字节码文件，那就需要我们来挂载额外的类加载器。

一般来说，类加载器是按照树形的层次结构组织的，每个类加载器都有一个符类加载器。另外，每个类加载器都支持代理模式，即可以自己完成Java类的加载工作，也可以代理给其它类加载器。

类加载器的加载顺序有两种，一种是父类优先加载，一种是自己优先加载；

- 父类优先策略是比较一般的情况（如JDK采用的就是这种方式），在这种策略下，类在加载某个Java类之前，会尝试代理给其父类加载器，只有当父类加载器找不到时，才尝试自己去加载。
- 自己优先的策略与父类优先相反，它会首先尝试自己加载，找不到的时候才要父类加载器去加载，这种在web容器（例如tomcat）中比较常见。

### 2、动态加载

不管使用什么样的类加载器，类，都是在第一次被用到时，动态加载到JVM的。这句话有两层含义：

1. Java程序在运行时并不一定被完整加载，只有当发现该类还没有加载时，才去本地或者远程查找类的.class文件并验证和加载；
2. 当程序创建了第一个对类的静态成员的引用（例如类的静态变量、静态方法、构造方法——构造方法也是静态的）时，才会加载该类。Java的这个特性叫做：**动态加载**。

需要区分加载和初始化的区别，加载一个类的.class文件，并不意味着该Class对象被初始化，事实上，一个类的初始化包括了3个步骤：

1. **加载（Loading）**，有类加载器执行，查找字节码，并创建一个Class对象（**只是创建**）；
2. **链接（Linking）**，验证字节码，为静态域分配存储空间（只是分配，并**不初始化**该存储空间），解析该类创建所需要的对其他类的引用；
3. **初始化（Initialization）**，首先执行静态初始化块static{}，初始化静态变量，执行静态方法（如构造方法）；

### 3、链接

Java在加载了类之后，需要进行链接的步骤，链接简单地说，就是将已经加载的java二进制代码组合到JVM运行状态中去。它包括3个步骤：

1. **验证（Verification）**，验证就是保证二进制字节码在结构上的正确性，具体来说，工作包括检测类型正确性，接入属性正确性（public、private），检查final class没有被继承，检查静态变量的正确性等；
2. **准备（Preparation）**，准备阶段主要是创建静态域，分配空间，给这些域设置默认值，需要注意的是两点：
    1. 在准备阶段不会执行任何代码，仅仅是设置默认值；
    2. 这些默认值是这样分配的，原生类型全部设为0，如：float:0f、int 0、long 0L、boolean 0（布尔类型也是0），其他引用类型为null；
3. **解析（Resolution）**，解析的过程就是对类中的接口、类、方法、变量的符号引用进行解析并定位，解析成直接引用（符号引用就是编码用字符串表示某个变量、接口的位置，直接引用就是根据符号引用翻译出来的地址），并保证这些类被正确的找到。解析的过程可能导致其它的类被加载。**需要注意的是**，根据不同的解析策略，这一步不一定是必须的，有些解析策略在解析时递归把所有引用解析，这是**early resolution**，要求所有引用都必须存在；还有一种策略是**late resolution**，这也是Oracle的JDK所采用的策略，即在类只是被引用了，还没有被真正用到时，并不进行解析，只有当真正用到了，才去加载和解析这个类。

### 4、初始化

> **注意：**在《Java编程思想》中，说static{}子句是在类第一次加载时执行且执行一次（可能是笔误或者翻译错误，因为此书的例子显示static是在第一次初始化时执行的），《Java深度历险》中说static{}是在第一次实例化时执行且执行一次，这两种应该都是错误的，static{}是在第一次初始化时执行，且只执行一次，用下面的例子可以判定出来：

```java
package classloader;

/**
 * @Description 测试类加载时机
 * @Author Muscleape
 * @Date 2018年5月20日
 */
public class Toy {

    private static String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Toy.name = name;
    }

    public static final int price = 10;
    static {
        System.out.println("Initializing");
    }

    Toy() {
        System.out.println("Building");
    }

    Toy(String name) {
        this.setName(name);
    }

    public static String playToy(String player) {
        String msg = buildMsg(player);
        System.out.println(msg);
        return msg;
    }

    private static String buildMsg(String player) {
        String msg = player + " plays " + name;
        return msg;
    }
}
```

```java
package classloader;

/**
 * @Description 测试用类
 * @Author Muscleape
 * @Date 2018年5月20日
 */
public class Test {

    public static void main(String[] args) {

        try {
            //执行完下面这一行，输出  Initializing
            Class<?> c = Class.forName("classloader.Toy");
            //执行完下面这一行，输出  Building
            c.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
```

> **可以看出:**不实例化，只执行forName初始化时，仍然会执行static{}子句，但是不执行构造方法，因此输出的只有Initializing，没有Building。

关于初始化，@阿春阿晓 在评论中给出了很详细的场景：

