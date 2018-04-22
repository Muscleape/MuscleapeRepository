### 1、关于JDBC
项目中使用了8.0.9版本的JDBC，在配置JDBC相关的数据库连接时，与老版本的有一些不同
- 包名改变了
- 创建数据库连接时需要添加一些信息，SSL、时区等

### 2、MyBatis的配置信息
配置信息一般可以在下载源码中包括，路径为：
> mybatis-3-mybatis-3.4.6\src\test\java\org\apache\ibatis\submitted\complex_property
- Configuration.xml  MyBatis链接相关配置信息
- User.xml 实体类的配置信息

### 3、log4j配置文件

```
# 日志输出的级别及位置 
# debug,info,warn,error（级别低到高）
log4j.rootLogger=DEBUG,Console
log4j.appender.Console=org.apache.log4j.ConsoleAppender
log4j.appender.Console.layout=org.apache.log4j.PatternLayout
# %d日志时间
# %t日志所处线程的线程名称
# %p输出日志的级别（debug,info,warn,error） 5p输出的字符占5位 -5p不足5位的在右侧补空位
# %c输出日志时所处类的全名，包括包名
# %m输出日志时自己添加的附件信息
# %n换行
log4j.appender.Console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%n
# org.apache包名，为当前包设置特殊的日志输出属性
log4j.logger.org.apache=INFO
```
