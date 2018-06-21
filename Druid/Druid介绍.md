# Druid 介绍

Druid 是阿里巴巴开源平台上的一个项目，整个项目由数据库连接池、插件框架和 SQL 解析器组成。该项目主要是为了扩展 JDBC 的一些限制，可以让程序员实现一些特殊的需求，比如向密钥服务请求凭证、统计 SQL 信息、SQL 性能收集、SQL 注入检查、SQL 翻译等，程序员可以通过定制来实现自己需要的功能。

Druid 首先是一个数据库连接池，但它不仅仅是一个数据库连接池，它还包含一个 ProxyDriver，一系列内置的 JDBC 组件库，一个 SQL Parser。在 Javad 的世界中 Druid 是目前最好的数据库连接池，在功能、性能、扩展性方面，都超过其他数据库连接池，包括 DBCP、C3P0、BoneCP、Proxool、JBoss DataSource。

## Druid 可以做什么

- 替换其他 Java 连接池，Druid 提供了一个高效、功能强大、可扩展性好的数据库连接池。
- 可以监控数据库访问性能，Druid 内置提供了一个功能强大的 StatFilter 插件，能够详细统计 SQL 的执行性能，这对于线上分析数据库访问性能有帮助。
- 数据库密码加密。直接把数据库密码写在配置文件中，这是不好的行为，容易导致安全问题。DruidDruiver 和 DruidDataSource 都支持 PasswordCallback。
- SQL 执行日志，Druid 提供了不同的 LogFilter，能够支持 Common-Logging、Log4j 和 JdkLog，可以按需要选择相应的 LogFilter，监控应用的数据库访问情况。
- 扩展 JDBC，如果你要对 JDBC 层有编程的需求，可以通过 Druid 提供的 Filter 机制，很方便编写 JDBC 层的扩展插件。

## 总结

Druid 是一款非常优秀的数据库连接池开源软件，使用 Druid 提供的druid-spring-boot-starter可以非常简单的对 Druid 进行集成。Druid 提供了很多预置的功能，非常方便对 SQL 进行监控、分析。在集成多数据源的时候，我们配置不同数据源，采用一层一层注入的形式，最终构建出对应的SqlSessionTemplate，并将其注入到对应的 Mapper 包中来使用。利用 Druid 属性继承的功能，可以简化多数据源的配置，MyBatis+Druid 的多数据源解决方案很简单高效。