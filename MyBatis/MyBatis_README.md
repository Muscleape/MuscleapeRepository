# [内容整理在GitHub，地址https://github.com/Muscleape/TestDemoProjects](https://github.com/Muscleape/TestDemoProjects/tree/master/MyBatis/MyBatis_README.md)

### 1、关于JDBC
项目中使用了8.0.9版本的JDBC，在配置JDBC相关的数据库连接时，与老版本的有一些不同
- 包名改变了，添加了一个cj
```java
Class.forName("com.mysql.cj.jdbc.Driver");
```
- 创建数据库连接时需要添加一些信息，SSL、时区等
```java
Connection conn = DriverManager.getConnection(
"jdbc:mysql://127.0.0.1:3306/message?serverTimezone=GMT&autoReconnect=true&useSSL=true",
"root",
"root");
```

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

### 4、MyBatis的XML配置文件中配置信息使用OGNL语言，部分类似EL语言
- OGNL中特殊字符进行转义：例如：&  => \&quot\;

### 5、\<where>标签
- 当标签内的内容满足条件时，拼接where条件语句
- 后面多个条件时，自动删除最开始的and或or关键字，保证sql语句正确


### 6、\<sql>标签
 - 类似与Java中的常量定义
 - 作用：类似与字段名这种常用的，可以设置到一个sql中，用的时候直接应用该常量，不需要每个地方都写一遍所有的字段名

### 7、\<set>标签
- 代替SQL中的set关键字
- 满足条件时，拼接set关键字
- 多个字段时，自动删掉最后一个字段的“,”(逗号)

### 8、\<trim>标签
- 具有组合功能
- 可以代替\<where>标签或\<set>标签
- \<where>标签的等效表示\<trim prefix="where" prefixOverrides="and/or">
- \<set>标签的等效表示\<trim prefix="set" suffixOverrides=",">

### 9、\<choose>标签
- 作用类似java中的 if else结构或者是switch case结构

```xml
<choose>
    <when test=""></when>
    <when test=""></when>
    <when test=""></when>
    <when test=""></when>
    <otherwise></otherwise>
</choose>
```

### 10、主从表
- 主表中，存储子表的一个集合，使用\<collention>标签
- 子表中，有一个字段，关联到主表，使用\<association>标签

### 11、SQL中的增删改查对应的标签
- insert
- delete
- update
- select

### 12、\<resultMap>标签
- 配置java对象属性与查询结果集中列表对应关系

### 13、控制动态SQL拼接
- 循环 \<foreach>标签
- 条件 \<if>标签
- 多条件判断 \<choose>标签

### 14、格式化输出
- \<where>
- \<set>
- \<trim>

### 15、配置关联关系（一对多及多对一等）
- \<collection>
- \<association>

### 16、\<sql>标签
- 定义常量

### 17、\<include>标签
- 引用\<sql>标签定义的常量

### 18、resultMap和ResultType

 相同点：
- 都是指定结果集与Java对象之间关系的，处理查询结果集，映射为Java对象

 不同点：
resultMap | resultType
----------|-----------
适用于返回值类型是自定义的实体类的情况，实体类的属性在标签\<resultMap>中指定|适用于返回值类型是JAVA提供的类型的情况，例如"java.util.Map"、"long"、"java.lang.String"等

### 19、parameterMap和parameterType

相同点：
- 指定sql中查询查询参数
不同点：

parameterMap|parameterType
--------|------
已不推荐使用|直接将查询参数映射到对应的java对象上去

### 20、#{}和${}

#{} | ${}
----|----
被MyBatis解析成预编译的【?】，然后通过PreparedStatement赋值为对应的变量值|直接将变量值拼接在对应的位置，没有预编译效果，使用位置要加单引号

#{}的优势
- 有预编译的效果，多次执行同一个SQL（只是变量值不同）时，有性能上的优势；
- 可以防止SQL注入

${}的优势
- 因为原样输出，可以用作SQL中的字段，例如：order by ${字段名}

### 21、MyBatis处理自增主键字段
 - \<insert>标签中添加属性值【useGeneratedKeys="true" keyProperty="id"】
 - useGeneratedKeys说明需要生成自增主键
 - keyProperty指定了生成的主键存到参数对象的那个属性字段中

 ### 22、找不到namespace.id的异常效果

 - MyBatis的总的配置文件容易遗漏，没有引用对应属性对象的XML文件，查看总配置文件遗漏后的异常信息；
 - SQLSession中调用XML中的namespace时，拼写错写；

### 23、MyBatis中的接口式编程

MyBatis中通过SQLSession直接调用SQL执行的方式是：
```java
// 通过SQLSession执行SQl语句
messageList = sqlSession.selectList("Message.queryMessageList", message);
```
这种方式存在几个问题：
1. 参数1是namespace的名称及调用的xml中方法的名称，容易存在拼写错误的问题 
2. 参数二指定了参数对象的类型，需要与xml中指定的parameterType类型严格对应
3. 返回值的类型需要与xml中指定的resultMap严格对应
4. 通过查看SQLSession的selectList方法，如果以上三处在代码中写错，代码在编译时不会出现任何的问题，但是在执行时，因为类型的问题会导致报错

改进措施，使用接口式的编程，在接口中已方法的方式指定对应的参数及返回值类型；
- 在MyBatis与Spring结合使用的程序中，大量的配置文件均由Spring的配置实现，其中会有大量的接口；