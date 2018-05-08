### 1、JRE和JDK
- JRE（Java Runtime Environment），包含JVM和Java类库，以及一些模块等；
- JDK（Java Development Kit），JRE的超集，提供了更多的工具，例如：编译器、各种诊断工具等；

### 2、Java是解释执行的？

**说法不准确**

1. Java源代码->Javac编译->字节码（bytecode）；
2. 运行时：JVM内嵌的解释器将字节码->最终的机器码；
3. 常见的JVM（例如Hotspot）提供JIT(Just-In-Time)编译器（常说的动态编译器），JIT能在运行时将热点代码编译成机器码，这种情况下部分热点代码属于编译执行，不是解释执行；