# Java基础面试题

## 1、 一个“.java”原文件中可以有几个类（不是内部类）？有什么限制？

可以有多个类，但只能有一个public的类，并且public的类名必须与文件名相一致。

## 2、有没有用过goto语句？

java中的保留字，现在没有在java中使用。

## 3、说说&和&&的区别

&和&&都可以用作逻辑与的运算符，表示逻辑与（and），当运算符两边的表达式的结果都为true时，整个运算结果才为true，否则，只要有一方为false，则结果为false。

&&还具有短路的功能，即如果第一个表达式为false，则不再计算第二个表达式，例如，对于if(str != null && !str.equals(“”))表达式，当str为null时，后面的表达式不会执行，所以不会出现NullPointerException如果将&&改为&，则会抛出NullPointerException异常。If(x==33 & ++y>0) y会增长，If(x==33 && ++y>0)不会增长。

&还可以用作位运算符，当&操作符两边的表达式不是boolean类型时，&表示按位与操作，我们通常使用0x0f来与一个整数进行&运算，来获取该整数的最低4个bit位，例如，0x31 & 0x0f的结果为0x01。

## 4、在JAVA中如何跳出当前的多重嵌套循环？

在Java中，要想跳出多重循环，可以在外面的循环语句前定义一个标号，然后在里层循环体的代码中使用带有标号的break 语句，即可跳出外层循环。例如：

```java
label:  
    for(int i=0;i<10;i++)  
    {  
        for(int j=0;j<10;j++)  
        {  
            System.out.println(“i=” + i + “,j=” + j);  
            if(j == 5) break label;  
        }  
    }
```

另外，我个人通常并不使用标号这种方式，而是让外层的循环条件表达式的结果可以受到里层循环体代码的控制，例如，要在二维数组中查找到某个数字。

```java
int arr[][] = {{1,2,3},{4,5,6,7},{9}};  
boolean found = false;  
for(int i=0;i<arr.length && !found;i++)  
    {  
        for(int j=0;j<arr[i].length;j++)  
        {  
            System.out.println(“i=” + i + “,j=” + j);  
            if(arr[i][j]  == 5)
            {  
                found = true;  
                break;  
            }
        }
```

## 5、switch语句能否作用在byte上，能否作用在long上，能否作用在String上？

在switch（expr1）中，expr1只能是一个整数表达式或者枚举常量，整数表达式可以是int基本类型或Integer包装类型，由于，byte,short,char都可以隐含转换为int，所以，这些类型以及这些类型的包装类型也是可以的。显然，long和String类型都不符合switch的语法规定，并且不能被隐式转换成int类型，所以，它们不能作用于swtich语句中。

## 6、short s1 = 1; s1 = s1 + 1;有什么错? short s1 = 1; s1 += 1;有什么错？

对于short s1 = 1; s1 = s1 + 1; 由于s1+1运算时会自动提升表达式的类型，所以结果是int型，再赋值给short类型s1时，编译器将报告需要强制转换类型的错误。

对于short s1 = 1; s1 += 1;由于+=是java语言规定的运算符，java编译器会对它进行特殊处理，因此可以正确编译。

## 7、char型变量中能不能存贮一个中文汉字？为什么？

char型变量是用来存储Unicode编码的字符的，unicode编码字符集中包含了汉字，所以，char型变量中当然可以存储汉字啦。不过，如果某个特殊的汉字没有被包含在unicode编码字符集中，那么，这个char型变量中就不能存储这个特殊汉字。补充说明：unicode编码占用两个字节，所以，char类型的变量也是占用两个字节。

## 8、用最有效率的方法算出2乘以8等於几？

2 << 3

因为将一个数左移n位，就相当于乘以了2的n次方，那么，一个数乘以8只要将其左移3位即可，而位运算cpu直接支持的，效率最高，所以，2乘以8等於几的最效率的方法是2 << 3。

## 9、请设计一个一百亿的计算器

首先要明白这道题目的考查点是什么，一是大家首先要对计算机原理的底层细节要清楚、要知道加减法的位运算原理和知道计算机中的算术运算会发生越界的情况，二是要具备一定的面向对象的设计思想。

首先，计算机中用固定数量的几个字节来存储的数值，所以计算机中能够表示的数值是有一定的范围的，为了便于讲解和理解，我们先以byte 类型的整数为例，它用1个字节进行存储，表示的最大数值范围为-128到+127。-1在内存中对应的二进制数据为11111111，如果两个-1相加，不考虑Java运算时的类型提升，运算后会产生进位，二进制结果为1,11111110，由于进位后超过了byte类型的存储空间，所以进位部分被舍弃，即最终的结果为11111110，也就是-2，这正好利用溢位的方式实现了负数的运算。-128在内存中对应的二进制数据为10000000，如果两个-128相加，不考虑Java运算时的类型提升，运算后会产生进位，二进制结果为1,00000000，由于进位后超过了byte类型的存储空间，所以进位部分被舍弃，即最终的结果为00000000，也就是0，这样的结果显然不是我们期望的，这说明计算机中的算术运算是会发生越界情况的，两个数值的运算结果不能超过计算机中的该类型的数值范围。由于Java中涉及表达式运算时的类型自动提升，我们无法用byte类型来做演示这种问题和现象的实验，大家可以用下面一个使用整数做实验的例子程序体验一下：

```java
int a = Integer.MAX_VALUE;
int b = Integer.MAX_VALUE;
int sum = a + b;
System.out.println(“a=”+a+”,b=”+b+”,sum=”+sum);
```

先不考虑long类型，由于int的正数范围为2的31次方，表示的最大数值约等于2*1000*1000*1000，也就是20亿的大小，所以，要实现一个一百亿的计算器，我们得自己设计一个类可以用于表示很大的整数，并且提供了与另外一个整数进行加减乘除的功能，大概功能如下：

- 这个类内部有两个成员变量，一个表示符号，另一个用字节数组表示数值的二进制数
- 有一个构造方法，把一个包含有多位数值的字符串转换到内部的符号和字节数组中
- 提供加减乘除的功能

```java
public class BigInteger  
{  
        int sign;  
        byte[] val;  
        public Biginteger(String val)  
        {  
            sign = ;  
            val = ;  
        }  
        public BigInteger add(BigInteger other)  
        {  

        }  
        public BigInteger subtract(BigInteger other)  
        {  

        }  
        public BigInteger multiply(BigInteger other)  
        {  

        }  
        public BigInteger divide(BigInteger other)  
        {  

        }  
  
}
```

备注：要想写出这个类的完整代码，是非常复杂的，如果有兴趣的话，可以参看jdk中自带的java.math.BigInteger类的源码。面试的人也知道谁都不可能在短时间内写出这个类的完整代码的，他要的是你是否有这方面的概念和意识，他最重要的还是考查你的能力，所以，你不要因为自己无法写出完整的最终结果就放弃答这道题，你要做的就是你比别人写得多，证明你比别人强，你有这方面的思想意识就可以了，毕竟别人可能连题目的意思都看不懂，什么都没写，你要敢于答这道题，即使只答了一部分，那也与那些什么都不懂的人区别出来，拉开了距离，算是矮子中的高个，机会当然就属于你了。另外，答案中的框架代码也很重要，体现了一些面向对象设计的功底，特别是其中的方法命名很专业，用的英文单词很精准，这也是能力、经验、专业性、英语水平等多个方面的体现，会给人留下很好的印象，在编程能力和其他方面条件差不多的情况下，英语好除了可以使你获得更多机会外，薪水可以高出一千元。

## 10、使用final关键字修饰一个变量时，是引用不能变，还是引用的对象不能变？

使用final关键字修饰一个变量时，是指引用变量不能变，引用变量所指向的对象中的内容还是可以改变的。例如，对于如下语句：

```java
final StringBuffer a=new StringBuffer("immutable");
//执行如下语句将报告编译期错误：
a=new StringBuffer("");
//但是，执行如下语句则可以通过编译：
a.append("broken!");
//有人在定义方法的参数时，可能想采用如下形式来阻止方法内部修改传进来的参数对象：
public void method(final  StringBuffer  param)
{

}
//实际上，这是办不到的，在该方法内部仍然可以增加如下代码来修改参数对象：
param.append("a");
```

## 11、"=="和equals方法究竟有什么区别？

> 单独把一个东西说清楚，然后再说清楚另一个，这样，它们的区别自然就出来了，混在一起说，则很难说清楚

==操作符专门用来比较两个变量的值是否相等，也就是用于比较变量所对应的内存中所存储的数值是否相同，要比较两个基本类型的数据或两个引用变量是否相等，只能用==操作符。

equals方法是用于比较两个独立对象的内容是否相同，就好比去比较两个人的长相是否相同，它比较的两个对象是独立的。例如，对于下面的代码：

```java
String a=new String("foo");
String b=new String("foo");
```

两条new语句创建了两个对象，然后用a,b这两个变量分别指向了其中一个对象，这是两个不同的对象，它们的首地址是不同的，即a和b中存储的数值是不相同的，所以，表达式a==b将返回false，而这两个对象中的内容是相同的，所以，表达式a.equals(b)将返回true。

在实际开发中，我们经常要比较传递进行来的字符串内容是否等，例如，String input = …;input.equals(“quit”)，许多人稍不注意就使用==进行比较了，这是错误的，随便从网上找几个项目实战的教学视频看看，里面就有大量这样的错误。记住，字符串的比较基本上都是使用equals方法。

如果一个类没有自己定义equals方法，那么它将继承Object类的equals方法，Object类的equals方法的实现代码如下：

```java
boolean equals(Object o){
　　return this==o;
}
```

这说明，如果一个类没有自己定义equals方法，它默认的equals方法（从Object 类继承的）就是使用==操作符，也是在比较两个变量指向的对象是否是同一对象，这时候使用equals和使用==会得到同样的结果，如果比较的是两个独立的对象则总返回false。如果你编写的类希望能够比较该类创建的两个实例对象的内容是否相同，那么你必须覆盖equals方法，由你自己写代码来决定在什么情况即可认为两个对象的内容是相同的。

## 12、静态变量和实例变量的区别？

- 语法定义上的区别：静态变量前要加static关键字，而实例变量前则不加。
- 在程序运行时的区别：实例变量属于某个对象的属性，必须创建了实例对象，其中的实例变量才会被分配空间，才能使用这个实例变量。静态变量不属于某个实例对象，而是属于类，所以也称为类变量，只要程序加载了类的字节码，不用创建任何实例对象，静态变量就会被分配空间，静态变量就可以被使用了。总之，实例变量必须创建对象后才可以通过这个对象来使用，静态变量则可以直接使用类名来引用。

例如，对于下面的程序，无论创建多少个实例对象，永远都只分配了一个staticVar变量，并且每创建一个实例对象，这个staticVar就会加1；但是，每创建一个实例对象，就会分配一个instanceVar，即可能分配多个instanceVar，并且每个instanceVar的值都只自加了1次。

```java
public class VariantTest  
{  
    public static int staticVar = 0;
    public int instanceVar = 0;
    public VariantTest()  
    {  
        staticVar++;  
        instanceVar++;  
        System.out.println(“staticVar=” + staticVar + ”,instanceVar=” + instanceVar);  
    }  
}  
```

## 13、static方法中怎么调用非static方法（是否可以从一个static方法内部发出对非static方法的调用）？

不可以。因为非static方法是要与对象关联在一起的，必须创建一个对象后，才可以在该对象上进行方法调用，而static方法调用时不需要创建对象，可以直接调用。也就是说，当一个static方法被调用时，可能还没有创建任何实例对象，如果从一个static方法中发出对非static方法的调用，那个非static方法是关联到哪个对象上的呢？这个逻辑无法成立，所以，一个static方法内部不可发出对非static方法的调用。

## 14、Integer与int的区别

- int是java提供的8种原始数据类型之一。Java为每个原始类型提供了封装类，Integer是java为int提供的封装类。
- int的默认值为0，而Integer的默认值为null，即Integer可以区分出未赋值和值为0的区别，int则无法表达出未赋值的情况，例如，要想表达出没有参加考试和考试成绩为0的区别，则只能使用Integer。
- 在JSP开发中，Integer的默认为null，所以用el表达式在文本框中显示时，值为空白字符串，而int默认的默认值为0，所以用el表达式在文本框中显示时，结果为0，所以，**int不适合作为web层的表单数据的类型**。
- 在Hibernate中，如果将OID定义为Integer类型，那么Hibernate就可以根据其值是否为null而判断一个对象是否是临时的，如果将OID定义为了int类型，还需要在hbm映射文件中设置其unsaved-value属性为0。
- Integer提供了多个与整数相关的操作方法，例如，将一个字符串转换成整数，Integer中还定义了表示整数的最大值和最小值的常量。

## 15、Math.round(11.5)等於多少? Math.round(-11.5)等於多少？

Math类中提供了三个与取整有关的方法：ceil、floor、round，这些方法的作用与它们的英文名称的含义相对应。例如：

- ceil的英文意义是天花板，该方法就表示向上取整，所以，Math.ceil(11.3)的结果为12,Math.ceil(-11.3)的结果是-11；
- floor的英文意义是地板，该方法就表示向下取整，所以，Math.floor(11.6)的结果为11,Math.floor(-11.6)的结果是-12；
- round方法，它表示“四舍五入”，算法为Math.floor(x+0.5)，即**将原来的数字加上0.5后再向下取整**，所以，Math.round(11.5)的结果为12，Math.round(-11.5)的结果为-11。

## 16、请说出作用域public、private、protected以及不写时的区别？

> 如果在修饰的元素上面没有写任何访问修饰符，则表示friendly。
>
> 只要记住了有4种访问权限，4个访问范围，然后将全选和范围在水平和垂直方向上分别按排从小到大或从大到小的顺序排列，就很容易画出该图了。

作用域 | 当前类 | 同一package | 子孙类 | 其他package
-------|-------|-------------|-------|--------
public | O | O | O | O
protected|O|O|O|X
friendly|O|O|X|X
private|O|X|X|X

## 17、Overload和Override的区别。Overloaded的方法是否可以改变返回值的类型？

Overload是重载的意思，Override是覆盖的意思，也就是重写。

重载Overload表示同一个类中可以有多个名称相同的方法，但这些方法的参数列表各不相同（即参数个数或类型不同）。

重写Override表示子类中的方法可以与父类中的某个方法的名称和参数完全相同，通过子类创建的实例对象调用这个方法时，将调用子类中的定义方法，这相当于把父类中定义的那个完全相同的方法给覆盖了，这也是面向对象编程的多态性的一种表现。子类覆盖父类的方法时，只能比父类抛出更少的异常，或者是抛出父类抛出的异常的子异常，因为子类可以解决父类的一些问题，不能比父类有更多的问题。子类方法的访问权限只能比父类的更大，不能更小。如果父类的方法是private类型，那么，子类则不存在覆盖的限制，相当于子类中增加了一个全新的方法。

## 18、java中实现多态的机制是什么？

靠的是父类或接口定义的引用变量可以指向子类或具体实现类的实例对象，而程序调用的方法在运行期才动态绑定，就是引用变量所指向的具体实例对象的方法，也就是内存里正在运行的那个对象的方法，而不是引用变量的类型中定义的方法。

例如，下面代码中的UserDao是一个接口，它定义引用变量userDao指向的实例对象由daofactory.getDao()在执行的时候返回，有时候指向的是UserJdbcDao这个实现，有时候指向的是UserHibernateDao这个实现，这样，不用修改源代码，就可以改变userDao指向的具体类实现，从而导致userDao.insertUser()方法调用的具体代码也随之改变，即有时候调用的是UserJdbcDao的insertUser方法，有时候调用的是UserHibernateDao的insertUser方法。

```java
UserDao userDao = daofactory.getDao();
userDao.insertUser(user);
```

## 19、abstract class和interface有什么区别？

### 抽象类

- 含有abstract修饰符的class即为抽象类；
- abstract类不能创建实例对象；
- 含有abstract方法的类必须定义为抽象类；
- 抽象类中的方法不必是抽象的；
- 抽象类中定义的抽象方法必须在具体子类中实现，非抽象方法可以不实现，所以不能有抽象构造方法或抽象静态方法；
- 如果子类没有实现抽象父类中的所有抽象方法，那么子类也必须定义为抽象的；

### 接口

- 接口是抽象类的一种特例；
- 接口中所有的方法都必须是抽象的；
- 接口中的方法，默认为public abstract类型，接口中的成员变量类型默认为public static final;

### 两者的区别

1. 抽象类可以有构造方法，接口中不能有构造方法；
2. 抽象类中可以有普通成员变量，接口中没有普通成员变量；
3. 抽象类中可以包含非抽象的普通方法，接口中所有方法都必须是抽象的，不能有非抽象的普通方法；
4. 抽象类中的抽象方法的访问类型可以是public、protected，但接口中的抽象方法只能是public类型的，并且默认是public abstract类型的；
5. 抽象类中可以包含静态方法，接口中不能包含静态方法；
6. 抽象类和接口中都可以包含静态成员变量，抽象类中的静态成员变量的访问类型可以任意，但接口中定义的变量只能是public static final类型；
7. 一个了只能继承一个抽象类，但是可以实现多个接口；

### 在应用上的区别

1. 接口更多的是在系统架构设计方面发挥作用，主要用于定义模块之间的通信契约；
2. 抽象类在代码实现方面发挥作用，可以实现代码的重用；

例如：模板方法设计模式是抽象类的一个典型应用，假设某个项目的所有Servlet类都要用相同的方法进行权限判断、记录访问日志和处理异常，那么就可以定义一个抽象的基类，让所有的Servlet都继承这个抽象基类，在抽象基类的service方法中完成权限判断、记录访问日志和处理异常的代码，伪代码如下：

```java
public abstract class BaseServlet extends HttpServlet  
{  
    public final void service(HttpServletRequest request,HttpServletResponse response) throws IOExcetionServletException  
    {  
        //记录访问日志  
        //进行权限判断  
        if(具有权限)  
        {  
            try  
            {  
                doService(request,response);  
            }catch(Excetpion e)  
            {  
                //记录异常信息  
            }  
        }  
    }
    protected abstract void doService(HttpServletRequest request, HttpServletResponse response) throws IOExcetion,ServletException;
    //注意访问权限定义成protected，显得既专业，又严谨，因为它是专门给子类用的  
}  
  
public class MyServlet1 extends BaseServlet  
{  
protected void doService(HttpServletRequest request, HttpServletResponse response) throws IOExcetion,ServletException  
    {  
        //本Servlet只处理的具体业务逻辑代码  
    }
}  
```

> 父类方法中间的某段代码不确定，留给子类干，就用模板方法设计模式

## 20、abstract的method是否可以同时是static,是否可以同时是native？

abstract的method不可以是static的，因为抽象的方法是要被子类实现的，而static与子类扯不上关系！

native方法表示该方法要用另外一种依赖平台的编程语言实现的，不存在着被子类实现的问题，所以，它也不能是抽象的，不能与abstract混用。

## 21、举一个例子说明一下是怎么继承String类的（是否可以继承String类）？

String类是final类故不可以继承；

## 22、String 和StringBuffer的区别

它们可以储存和操作字符串，即包含多个字符的字符数据。String类表示内容不可改变的字符串。而StringBuffer类表示内容可以被修改的字符串。当你知道字符数据要改变的时候你就可以使用StringBuffer。典型地，你可以使用StringBuffers来动态构造字符数据。另外，String实现了equals方法，new String(“abc”).equals(new String(“abc”)的结果为true,而StringBuffer没有实现equals方法，所以，new StringBuffer(“abc”).equals(new StringBuffer(“abc”)的结果为false。

```java
StringBuffer sbf = new StringBuffer();
for(int i=0;i<100;i++)  
{  
    sbf.append(i);  
}
```

> 上面的代码效率很高，因为只创建了一个StringBuffer对象，而下面的代码效率很低，因为创建了101个对象。String拼接字符串时底层实现是创建StringBuilder对象来实现的；

```java
String str = new String();
for(int i=0;i<100;i++)  
{  
    str = str + i;  
}
```

## 23、StringBuffer与StringBuilder的区别

StringBuffer和StringBuilder类都表示内容可以被修改的字符串，StringBuilder是线程不安全的，运行效率高，如果一个字符串变量是在方法里面定义，这种情况只可能有一个线程访问它，不存在不安全的因素了，则用StringBuilder。如果要在类里面定义成员变量，并且这个类的实例对象会在多线程环境下使用，那么最好用StringBuffer。

## 24、Spring 、Spring Boot 和 Spring Cloud 的关系

Spring 最初最核心的两大核心功能 Spring Ioc 和 Spring Aop 成就了 Spring，Spring 在这两大核心的功能上不断的发展，才有了 Spring 事务、Spirng Mvc 等一系列伟大的产品，最终成就了 Spring 帝国，到了后期 Spring 几乎可以解决企业开发中的所有问题。

Spring Boot 是在强大的 Spring 帝国生态基础上面发展而来，发明 Spring Boot 不是为了取代 Spring ,是为了让人们更容易的使用 Spring 。所以说没有 Spring 强大的功能和生态，就不会有后期的 Spring Boot 火热, Spring Boot 使用约定优于配置的理念，重新重构了 Spring 的使用，让 Spring 后续的发展更有生命力。

Spring Cloud 是一系列框架的有序集合。它利用 Spring Boot 的开发便利性巧妙地简化了分布式系统基础设施的开发，如服务发现注册、配置中心、消息总线、负载均衡、断路器、数据监控等，都可以用 Spring Boot 的开发风格做到一键启动和部署。

Spring 并没有重复制造轮子，它只是将目前各家公司开发的比较成熟、经得起实际考验的服务框架组合起来，通过 Spring Boot 风格进行再封装屏蔽掉了复杂的配置和实现原理，最终给开发者留出了一套简单易懂、易部署和易维护的分布式系统开发工具包。

根据上面的说明我们可以看出来，Spring Cloud 是为了解决微服务架构中服务治理而提供的一系列功能的开发框架，并且 Spring Cloud 是完全基于 Spring Boot 而开发，Spring Cloud 利用 Spring Boot 特性整合了开源行业中优秀的组件，整体对外提供了一套在微服务架构中服务治理的解决方案。

综上我们可以这样来理解，正是由于 Spring Ioc 和 Spring Aop 两个强大的功能才有了 Spring ，Spring 生态不断的发展才有了 Spring Boot ，使用 Spring Boot 让 Spring 更易用更有生命力，Spring Cloud 是基于 Spring Boot 开发的一套微服务架构下的服务治理方案。

## 25、solr

### solr 名称来源

Search On Lucene Replication

### solr 基本概况

Apache Solr (读音: SOLer) 是一个开源的搜索服务器。Solr 使用 Java 语言开发，主要基于 HTTP 和 Apache Lucene 实现。Apache Solr 中存储的资源是以 Document 为对象进行存储的。每个文档由一系列的 Field 构成，每个 Field 表示资源的一个属性。Solr 中的每个 Document 需要有能唯一标识其自身的属性，默认情况下这个属性的名字是 id，在 Schema 配置文件中使用：id进行描述。

Solr是一个高性能，采用Java开发，基于Lucene的全文搜索服务器。文档通过Http利用XML加到一个搜索集合中。查询该集合也是通过 http收到一个XML/JSON响应来实现。它的主要特性包括：高效、灵活的缓存功能，垂直搜索功能，高亮显示搜索结果，通过索引复制来提高可用性，提供一套强大Data Schema来定义字段，类型和设置文本分析，提供基于Web的管理界面等。