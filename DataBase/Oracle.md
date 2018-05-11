# Oracle内容整理

## 1、Oracle的JDBC连接方式：oci和thin

> 相同点：

- Oracle提供，Java访问Oracle数据库的方式；
- 驱动类别不同（SUN定义的四种JDBC驱动程序标准），但是在功能上没有差异；

> 不同点：

### 1. thin（for thin client）

- 一种瘦客户端连接方式，即不需要安装Oracle客户端，要求classpath中包含jdbc驱动的jar包
- 纯粹用Java写的Oracle数据库访问接口；
- 纯Java实现tcp/ip的c/s通讯；
- 是四类驱动；

### 2. oci(Oracle Call Interface)

- 一种胖客户端连接方式，即需要安装Oracle客户端（Oracle10.1.0开始单独提供OCI Instant Client，不用完整安装Client）；
- 通过native java method调用c library（OCI，Oracle call interface）访问服务端；
- 是二类驱动；

## 2、Oracle中thin有3中连接方式

### 1. Oracle JDBC Thin using a ServiceName

> Oracle推荐的格式，因为对于集群来说，每个节点的SID是不同的，但是SERVICE_NAME可以包含所有的节点

```java
jdbc:oracle:thin:@//<host>:<port>/<service_name>
// 例如：
jdbc:oracle:thin:@//192.168.1.1:1521/XE
```

### 2. Oracle JDBC Thin using an SID(SID是数据库实例的名字，每个实例各不相同)

> Support for SID is being phased out.Oracle recommends that users switch over to using service names;

```java
jdbc:oracle:thin:@<host>:<port>:<SID>
//例如：
jdbc:oracle:thin:@localhost:1521:oral
```

### 3. Oracle JDBC Thin using a TNSName

> Support for TNSName was added in the driver release 10.2.0.1

```java
jdbc:oracle:thin:@<TNSName>
//例如：
jdbc:oracle:thin:@GL
```