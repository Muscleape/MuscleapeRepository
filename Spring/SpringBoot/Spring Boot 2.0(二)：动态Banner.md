# Spring Boot 2.0(二)：动态Banner

>该文章内容主要演示一下动态Banner的效果，并记录一个超好玩的网站，可以将文字转换成字符串，还有一个网站可以将gif动图转换成字符串；
>
>该系列文章涉及到的项目代码地址：<https://github.com/Muscleape/MuscleapeRepository/tree/master/Spring/SpringBoot/springBootDemo>

## 依赖配置

使用 Spring Boot 2.0 首先需要将项目依赖包替换为刚刚发布的 2.0 RELEASE，现在网站<https://start.spring.io/>也将 Spring Boot 2.0 设置为默认版本。

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.0.RELEASE</version>
</parent>
```

设置完毕后，dependencies中没有指明版本的依赖包，将自动使用2.0.0.RELEASE依赖的版本。

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
</dependencies>
```

## SpringBoot更换Banner

>项目的启动 Banner 有什么用呢，在一些大的组织或者公司中，可以利用这个特性定制自己专属的启动画面，增加团队对品牌的认同感。

1. 到SpringBoot官网使用项目初始化生成工具生成一个SpringBoot项目
2. 到网站<http://www.network-science.de/ascii/>将想要输出到Banner中的文字转化成字符串；或者是可以到网站<http://www.degraeve.com/img2txt.php>将图片转换成字符串；
3. 在项目目录src/main/resources下新建一个banner.txt文件，并将生成的字符串内容放进去；
4. 然后启动项目后就会在控制到打印出特定的Banner内容，例如如下所示MuscleApe：

```shell
ooo        ooooo                                oooo                  .o.
`88.       .888'                                `888                 .888.
 888b     d'888  oooo  oooo   .oooo.o  .ooooo.   888   .ooooo.      .8"888.     oo.ooooo.   .ooooo.
 8 Y88. .P  888  `888  `888  d88(  "8 d88' `"Y8  888  d88' `88b    .8' `888.     888' `88b d88' `88b
 8  `888'   888   888   888  `"Y88b.  888        888  888ooo888   .88ooo8888.    888   888 888ooo888
 8    Y     888   888   888  o.  )88b 888   .o8  888  888    .o  .8'     `888.   888   888 888    .o
o8o        o888o  `V88V"V8P' 8""888P' `Y8bod8P' o888o `Y8bod8P' o88o     o8888o  888bod8P' `Y8bod8P'
                                                                                 888
                                                                                o888o
2018-05-31 18:18:53.379  INFO 13152 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2018-05-31 18:18:53.381  INFO 13152 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2018-05-31 18:18:53.791  INFO 13152 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2018-05-31 18:18:53.853  INFO 13152 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2018-05-31 18:18:53.859  INFO 13152 --- [           main] c.m.s.SpringBootDemoApplication          : Started SpringBootDemoApplication in 5.839 seconds (JVM running for 6.606)
```

>如果目录src/main/resources下同时存在banner.txt和banner.gif，项目会先将banner.gif每一个画面打印完毕之后，再打印banner.txt中的内容。