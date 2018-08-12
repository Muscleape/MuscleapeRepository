# SpringBoot 常用注解

## @SpringBootApplication

- @SpringBootApplication 注解等价于已默认属性使用@Configuration、@EnableAutoConfiguration、@ComponentScan。通常使用在有main方法中类的注解

```java
package com.muscleape.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

## @Bean

- 产生一个bean的方法，并且交给Spring容器管理；
- 可以理解为用Spring的时候xml里面的标签；
- 该注解是用在方法上的，就是用来产生一个Bean；
- 该注解分为两类：
  - 1、使用Bean，即是把已经在xml文件中配置好的Bean哪里用，完成属性、方法的组装；例如：@Autowired和@Resource，可以通过byTYPE（@Autowired）、byNAME（@Resource）的方式获取Bean；
  - 2、注册Bean，@Componenet,@Repository,@Controller,@Service，@Configuration这些注解都是把要实例化的对象转化成一个Bean，放在IOC容器中，等需要使用的时候，会和上面的@Autowired、@Resource配合到一起，把对象、属性、方法完美组装；

  ```java
  @Bean(name = "clientbootstrap")
  public Bootstrap clientBootSrap() throws Exception {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(clientGroup())
              .channel(NioSocketChannel.class)
              .option(ChannelOption.TCP_NODELAY, true)
              .option(ChannelOption.SO_KEEPALIVE, true)
              .handler(clientInitializer);
      return bootstrap;
  }
  ```

## @ComponentScan

- 组件扫描。相当于 **context:component-scan**，如果扫描到有@Component、@Controller、@Service等这些注解的类，就把这些类注册为bean;

## @ControllerAdvice

- 该注解定义全局异常处理类，通常结合@ExceptionHandler使用；

## @ExceptionHandler

- 该注解声明异常处理方法，通常结合@ControllerAdvice使用；
- handleException()就会处理所有Controller层抛出的Exception及其子类的异常；

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    String handleException(){
        return "Exception Deal!";
    }
}
```

## @ResponseBody

- 表示该方法的返回结果直接写入HTTP response body中；
- 一般在异步获取数据时使用；
- 在使用@RequestMapping之后，返回值通常解析为跳转路径，加上@ResponseBody后返回结果不会被解析为跳转路径，而是直接写入HTTP Response Body中；

## @Qualifier

- 当有多个同一类型的Bean时，可以使用@Qualifier("name")来指定；
- 与@Autowired配合使用；

```java
@Autowired
@Qualifier(value = “demoInfoService”)
private DemoInfoService demoInfoService;
```

## 注入（@Autowired和@Resource）

- @Autowired与@Resource都可以用来装配bean. 都可以写在字段上,或写在setter方法上；
- @Autowired默认按类型装配（这个注解是属业spring的），默认情况下必须要求依赖对象必须存在，如果要允许null值，可以设置它的required属性为false，如：@Autowired(required=false) ，如果我们想使用名称装配可以结合@Qualifier注解进行使用，如下：

```java
@Autowired
@Qualifier(value = “demoInfoService”)
private DemoInfoService demoInfoService;
```

- @Resource 是JDK1.6支持的注解，默认按照名称进行装配，名称可以通过name属性进行指定;@Resource的装配顺序：
  - 1、如果同时指定了name和type，则从Spring上下文中找到唯一匹配的bean进行装配，找不到则抛出异常；
  - 2、如果指定了name，则从上下文中查找名称(id)匹配的bean进行装配，找不到则抛出异常；
  - 3、如果指定了type，则从上下文中找到类型匹配的唯一bean进行装配，找不到或者找到多个，都会抛出异常
  - 4、如果既没有指定name，又没有指定type，则自动按照byName方式进行装配；如果没有匹配，则回退为一个原始类型进行匹配，如果匹配则自动装配；