### 1、Oracle的JDBC连接方式：oci和thin

> 相同点：
- Oracle提供，Java访问Oracle数据库的方式；
- 驱动类别不同（SUN定义的四种JDBC驱动程序标准），但是在功能上没有差异；
> 不同点：
1. thin（for thin client）
- 一种瘦客户端连接方式，即不需要安装Oracle客户端，要求classpath中包含jdbc驱动的jar包
- 纯粹用Java写的Oracle数据库访问接口；
- 纯Java实现tcp/ip的c/s通讯；
- 是四类驱动；

2. oci(Oracle Call Interface)
- 一种胖客户端连接方式，即需要安装Oracle客户端（Oracle10.1.0开始单独提供OCI Instant Client，不用完整安装Client）；
- 通过native java method调用c library（OCI，Oracle call interface）访问服务端；
- 是二类驱动；
