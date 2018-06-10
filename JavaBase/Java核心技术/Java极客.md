# Java核心技术36讲笔记

## 1、JRE和JDK

- JRE（Java Runtime Environment），包含JVM和Java类库，以及一些模块等（未来将退出历史舞台）；
- JDK（Java Development Kit），JRE的超集，提供了更多的工具，例如：编译器、各种诊断工具等；

## 2、Java是解释执行的？

> **说法不准确**

1. Java源代码->**Javac**编译变成->class字节码文件（bytecode）；
2. 运行时：
    1. 解析：JVM内嵌的解释器将字节码（class文件）->最终的机器码；
    2. 编译：JIT（Just In Time Compile即时编译器）把经常运行的代码作为“热点代码”编译为与本地平台相关的机器码，并进行各种层次的优化。
3. AOT编译器：Java9提供的直接将所有代码编译成机器码执行；

## 3、对于Java平台的理解

### 1.Java代码的编译过程：

> Java代码的编译分为两次，多次运行的前提是JVM；

  1. Java源代码被编译成.CLASS字节码文件（.class文件可以被任何平台的JVM识别的文件）；
  2. Class字节码文件被JVM编译成对应平台的机器代码；

### 2.Java特性

- 面向对象（封装、继承、多态）
- 平台无关性（JVM运行.class文件）
- 语言特性（包括泛型、Lambda等特性）
- 基础类库（集合、并发、安全、网络、IO/NIO等）
- 类加载大致过程：加载、验证、链接、初始化；
- 最常见的垃圾收集器，如SerialGC、ParallelGC、CMS、G1等；
- JRE(Java运行环境，包含JVM及类库)
- JDK(Java开发环境，包括JRE，运行时环境，安全工具，编译器，诊断和监控工具等)

### 3.代码执行类型

- **解释执行**：写个程序，直接执行字节码；
- **JIT**：写个程序，运行时把字节码动态翻译成机器码；
- **AOT**：写个程序，把java源代码直接翻译为机器码；
- **机器码**：造个CPU直接执行字节码，字节码就是机器码；

## 4、Exception和Error

### 1、介绍

> 都继承Throwable类，Java中占有Throwable类型的实例才可以被抛出（throw）或者捕获（catch）

- Exception
  - 程序正常运行中，可以预料的意外情况，可能并且应该被捕获，进行相应处理；
  - 分为可检查（checked）异常和不检查（unchecked）异常:
    - 可检查异常：代码中必须显示地进行捕获处理，是编译期检查的一部分；
    - 不检查异常：所谓的运行时异常，通常是通过编码避免的逻辑错误，根据需求判定是否需要捕获；例如：NullPointException、ArrayIndexOutOfBoundsException等；
- Error：正常情况下不大可能出现的情况，绝大部分的Error都会导致程序（比如JVM自身）出游非正常、不可恢复的状态。既然是非正常情况，所有不便于也不需要捕获；

### 2、处理

- 理解Throwable、Exception、Error的设计和分类；掌握那些应用最为广泛的子类，以及如何自定义异常等；
- 理解Jav语言中操作Throwable的元素和实践；掌握最基本的语法，如try-catch-finally块，throw、throws关键字等；

### 1.异常处理的2个基本原则

```java
try {
  // 业务代码
  // …
  Thread.sleep(1000L);
} catch (Exception e) {
  // Ignore it
}
```

1. 尽量不要捕获类似Exception这样的通用异常，而是应该捕获特定异常；例如上面例子中，Thread.sleep()抛出的InterruptedException;
2. 不要生吞（swallow）异常。即假设不可能发生异常，或者感觉忽略异常是无所谓的；
