# Java类加载与初始化（转载+整合）

> 这种层面的知识，目前都是从其他的博客或者是书中读来的，自己的认识理解还没有深入到这个层面，所以这篇文章也是【转载+整合】来得，转载的原始博文如下[http://www.cnblogs.com/zhguang/p/3154584.html](http://www.cnblogs.com/zhguang/p/3154584.html)
>
> **但是，转载也是要有自己的态度的，每个字都要自己手打，就是这（周）么（末）努（无）力（聊）！！！＜( ￣︿￣)︵θ︵θ︵θ︵θ︵☆**
>
> 刚好有一本《**深入理解Java虚拟机**》（虽然只看了一丢丢），相关的内容一块整理进来吧

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

**可以看出**：不实例化，只执行forName初始化时，仍然会执行static{}子句，但是不执行构造方法，因此输出的只有Initializing，没有Building。

---

> 关于初始化，@阿春阿晓 在评论中给出了很详细的场景：
>
>主要使用以下6种：
>
> 1. 创建类的实例；
> 2. 访问某个类或者接口的静态变量，或者对该静态变量赋值（如果访问静态编译常量（即编译时可以确定值的常量）不会导致类的初始化）；
> 3. 调用类的静态方法；
> 4. 反射（Class.forName(xxx.xxx.xxx)）；
> 5. 初始化一个类的子类（相当于对父类的主动使用），不过直接通过子类引用父类元素，不会引起子类的初始化（参见示例6）；
> 6. Java虚拟机被标明为启动类的类（包含main方法的）；

---

> 《深入理解Java虚拟机》【7.2 类加载的时机】
>
>对于初始化阶段，虚拟机规范是严格规定了5种情况必须立即对类进行“初始化”（而加载、验证、准备自然需要在此之前开始）：
>
> 1. 遇到new、getstatic、putstatic或invokestatic这4条字节码指令时，如果类没有进行过初始化，则需要先触发器初始化。生成这4条指令的最常见的Java代码场景是：使用new关键字实例化对象的时候、读取或设置一个类的静态字段（被final修饰、已在编译期把结果放入常量池的静态字段除外）的时候，以及调用一个类的静态方法的时候。
> 2. 使用java.lang.reflect包的方法对类进行反射调用的时候，如果类没有进行过初始化，则需要先触发其初始化。
> 3. 当初始化一个类的时候，如果发现一其父类还没有进行过初始化，则需要先触发其父类的初始化。**但是，对于接口来说，一个接口在初始化时，并不要求其父接口全部都完成初始化，只有在真正使用父接口的时候（例如引用接口中定义的常量）才会初始化**；
> 4. 当虚拟机启动时，用户需要制定一个要执行的主类（包含main()方法的那个类），虚拟机会先初始化这个类；
> 5. 当使用JDK 1.7的动态语言支持时，如果一个java.lang.invoke.MethodHandle实例最后的解析结果REF_getStatic、REF_putStatic、REF_invokeStatic的方法句柄，并且这个方法句柄所对应的类没有进行过初始化，则需要先触发其初始化。
>
> 这5中场景中的行为称为对一个类进行主动引用，初次之外的，其他引用类的方式都不会触发初始化，称为被动引用。
>
> 列举3中被动引用的场景：
> 1. 通过子类引用父类的静态字段，不会导致子类初始化。只有直接定义这个字段的类才会被初始化，因此父类会初始化，子类不会；至于是否会触发子类的加载，不同JVM实现不同，例如：HotSpot虚拟机可以通过【-XX:+TraceClassLoading】参数观察到此操作会导致子类的加载；
> 2. 通过数组定义来引用类，不会触发此类的初始化。但是会触发一个，由虚拟机自动生成的、直接继承java.lang.Object的子类，创建动作由字节码指令newarray触发；
> 3. 常量在编译阶段会存入调用类的常量池中，本质上没有直接引用到定义常量的类，因此也不会触发定义常量的类的初始化化。在编译阶段，通过常量传播优化，常量值会存储到引用类的常量池中，以后对常量的引用，都被转化为对自身常量池的引用。

#### 代码示例：

##### 1. 通过上面的讲解，将可以理解下面的程序（下面的程序部分来自于《Java编程思想》）：

```java
public class Toy {
    static {
        System.out.println("Initializing");//静态子句，只有类第一次被加载并初始化时执行一次，而且只执行一次
    }

    Toy() {
        System.out.println("Building");//构造方法，在每次声明新对象时加载
    }
}
```

对于上面的程序段，第一次调用Class.forName("Toy")，将执行static子句；如果之后执行new Toy()都只执行构造方法。

##### 2. 需要注意**newInstance()**方法

```java
Class cc = Class.forName("Toy");//获得类（注意，需要使用包含包名的全限定名）
Toy toy = (Toy)cc.newInstance();//相当于new一个对象，但是Toy类必须有默认的无参的构造方法
```

##### 3.用**类字段常量**.class和Class.forName都可以创建爱你对类的应用，但是不同点在于，用Toy.class创建Class对象的的应用时，不会自动初始化该Class对象（static子句不会执行）；

```java
package classloader;

/**
 * @Description 测试用类
 * @Author Muscleape
 * @Date 2018年5月20日
 */
public class Test {

    public static void main(String[] args) {

        // try {
        // // 执行完下面这一行，输出 Initializing
        // Class<?> c = Class.forName("classloader.Toy");
        // // 执行完下面这一行，输出 Building
        // c.newInstance();
        // } catch (InstantiationException | IllegalAccessException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (ClassNotFoundException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        Class c = Toy.class;// 不会输出任何值
    }

}
```

使用Toy.class是在编译期执行的，因此在编译期必须已经有了Toy的.class文件，不然会编译失败，这与Class.forName("Toy")不同，后者是运行时动态加载的。

但是，如果该main()方法直接写在Toy类中，那么调用Toy.class会引起初始化，并输出Initializing，原因并不是Toy.class引起的，而是该类中含有启动方法main，该方法会导致Toy的初始化。

##### 4.编译时常量。

回到完整的Toy类，如果直接输出：System.out.println(Toy.price)，会发现static子句和构造方法都没有被执行，这是因为Toy中，常量price被 static final限定，这样的常量叫做**编译时常量**，对于这种编译时常量必须满足3个条件：static的、final的、常量。

> 下面的几种**不是**编译时常量，对他们的应用，都会引起类的初始化：

```java
static int a;
final int b;
static final int c = ClassInitialization.rand.nextInt(100);
static final int d;
static {
    d=5;
}
```

##### 5.**static**块的本质

```java
package classloader;

public class StaticBlock {
    static final int c = 3;
    static final int d;
    static int e = 5;
    static {
        d = 5;
        e = 10;
        System.out.println("Initializing");
    }

    StaticBlock() {
        System.out.println("Building");
    }
}
```

```java
package classloader;

public class StaticBlockTest {
    public static void main(String[] args) {
        System.out.println(StaticBlock.c);
        System.out.println(StaticBlock.d);
        System.out.println(StaticBlock.e);
    }
}
```

上述代码执行结果为：

```shell
3
Initializing
5
10
```

> 原因分析：输出c时，由于c是编译时常量，不会引起类初始化，因此直接输出，输出d时，d不是编译时常量，所有会引起初始化操作，即static块的执行，于是d被赋值为5，e被赋值为10，然后输出Initializing，之后输出的为5，e为10。

JDK会自动为e的初始化创建一个static块，所有上面的diam等价于：

```java
class StaticBlock2 {
    static final int d;
    static int e;
    static {
        e = 5;
    }
    static {
        d = 5;
        e = 10;
        System.out.println("Initializing");
    }

    StaticBlock2() {
        System.out.println("Building");
    }
}
```

可见，**按顺序执行**，e先被初始化为5，再被初始化为10，于是输出10。

类似的，容易想到下面的代码：

```java
class StaticBlock3 {
    static {
        d = 5;
        e = 10;
        System.out.println("Initializing");
    }
    static final int d;
    static int e = 5;

    StaticBlock3() {
        System.out.println("Building");
    }
}
```

在这段代码中，对e的声明被放到static块后面，于是，e会被先初始化为10，再被初始化为5，所以这段代码中e会输出为5。

##### 6.《Java深度历险》第二章代码示例

当访问一个Java类或者接口的静态域时，只有真正声明这个域的类或接口才会被初始化。

```java
package classloader;

public class StaticBlock4 {
    public static void main(String[] args) {
        System.out.println(A.value);// 输出100
    }
}

class B {
    static int value = 100;
    static {
        System.out.println("Class B is initialized");// 输出
    }
}

class A extends B {
    static {
        System.out.println("Class A is initialized");// 不输出
    }
}
```

输出结果：

```java
Class B is initialized
100
```

在该例子中，虽然通过A来引用了value，但是value是在父类B中声明的，所以只会初始化B，而不会引起A的初始化。