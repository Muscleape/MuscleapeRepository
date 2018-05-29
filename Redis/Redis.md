# Redis

## Redis介绍

开源内存数据库，支持String、Hash、List、Set、Zset、Geo、Hyperloglogs等多数据结构。同事支持主从复制、Luau脚本、事务、数据持久化、高可用和集群化等；

## Redis特性

1. 高性能：Redis虽然是单线程的，但是它同样拥有这超高的性能。有测试显示每秒请求OPS能够达到10W左右；
2. 多样化数据结构：Redis支持String、Hash、List、Set、Zset、Geo等多数据结构；
3. 持久化：
    1. RDB持久化（快照式持久化方式），将内存中的全量数据dump到磁盘。它的优势是加载速度比AOF快，劣势是快照式的全量备份，没有增量数据，造成数据丢失；
    2. AOF持久化：AOF记录Redis执行的所有写操作命令。恢复数据的过程相当于回放这些写操作。使用AOF的持久化方式，优势是有灵活的刷盘策略，保证数据的安全性。劣势是AOF文件体积比RDB大，占用磁盘多，数据加载到内存的速度慢；
4. 多重数据删除策略：
    1. 惰性删除：当读/写一个已经过期的key时，会触发惰性删除策略，删除掉这个过期key；
    2. 定期删除：由于惰性删除策略无法保证冷数据被及时删掉，所有redis会定期主动淘汰一批已过期的key；
    3. 主动删除：当已用内存超过maxmemory限定时，触发主动清除策略，该策略由启动参数的配置决定，可配置参数及说明如下：
        - volatile-lru：从已设置过期时间的样本中根据LRU算法删除数据 （redis3.0之前的默认策略）
        - volatile-ttl：从已设置过期时间的样本中挑选过期时间最小的数据删除
        - volatile-random：从已设置过期时间的样本中随机选择数据删除
        - allkeys-lru：从样本中根据LRU算法删除数据
        - allkeys-random：从样本中任意选择删除数据
        - noenviction：禁止从内存中删除数据 （从redis3.0 开始默认策略）
5. 高可用：Redis自身带有哨兵（Sentinel）的组件，可以监控Redis主从的运行转态和自动的故障切换，实现Redis的高可用；

## Redis安装配置

### Redis安装

#### 1.Redis下载并解压（目前最新版本是4.0.9）

```shell
cd /usr/local/
wget http://download.redis.io/releases/redis-4.0.9.tar.gz
tar -zxzf redis-4.0.9.tar.gz
```

#### 2.编译并安装

> make install PREFIX=安装路径

```shell
cd /usr/local/redis-4.0.9
make install PREFIX=/usr/local/redis
```

> 可以复制redis相关命令到/usr/sbin目录下，这样就可以执行执行这些命令，不用谢全路径

```shell
cd /usr/local/redis/bin
cp redis-cli redis-server redis-sentinel /usr/sbin
```

#### 3.Redis配置（v4.0.9）

> redis的配置文件（redis.conf）拷贝备份
>
> **个人习惯，喜欢把最原始的配置文件做一个备份，便于后期对比或还原**
>
>redis配置文件主要参数解析参考

- **daemonize**

>配置Redis是否以守护进程的方式运行，默认为no

```shell
# By default Redis does not run as a daemon. Use 'yes' if you need it.
# Note that Redis will write a pid file in /var/run/redis.pid when daemonized.
daemonize no
```

- **pidfile**

>

```shell
# If a pid file is specified, Redis writes it where specified at startup
# and removes it at exit.
#
# When the server runs non daemonized, no pid file is created if none is
# specified in the configuration. When the server is daemonized, the pid file
# is used even if not specified, defaulting to "/var/run/redis.pid".
#
# Creating a pid file is best effort: if Redis is not able to create it
# nothing bad happens, the server will start and run normally.
pidfile /var/run/redis_6379.pid
```