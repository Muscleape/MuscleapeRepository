# Spring Boot （二）：Web综合开发

本篇文章接着上篇内容继续为大家介绍spring boot的其它特性（有些未必是spring boot体系桟的功能，但是是spring特别推荐的一些开源技术本文也会介绍），对了这里只是一个大概的介绍，特别详细的使用我们会在其它的文章中来展开说明。

## web开发

spring boot web开发非常的简单，其中包括常用的json输出、filters、property、log等；

## json接口开发

在以前的spring开发的时候需要我们提供json接口的时候需要做哪些配置呢？

1. 添加jackjson等相关jar包
2. 配置spring controller扫描
3. 对接的方法添加@ResponseBody

由于这些配置的问题，经常会出现配置错误，导致406错误等等，spring boot如果做呢？

只需要类添加@RestController即可，默认勒种的方法都会以json的格式返回

```java
@RestController
public class HelloWorldController {
    @RequestMapping("/getUser")
    public User getUser() {
        User user=new User();
        user.setUserName("小明");
        user.setPassWord("xxxx");
        return user;
    }
}
```

如果需要使用页面，只要只用@Controller注解即可，下面会结合模板来说明

## 自定义Filter