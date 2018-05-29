# Redis

>后面的配置基于4.0.9版本=>>>不指定版本信息的配置说明都是耍流氓
>
>比如在4.0.9中没有vm相关的及glueoutputbuf的配置信息

**部分常用配置节（后面有详细说明）：**

- daemonize
- pidfile
- port
- bind
- timeout
- loglevel
- logfile
- databases
- save
- rdbcompression
- dbfilename
- dir
- slaveof
- masterauth
- requirepass
- maxclients
- maxmemory
- maxmemory-policy
- appendonly
- appendfilename
- appendfsync
- hash-max-zipmap-entries
- hash-max-zipmap-value
- activerehashing
- include

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

>当Redis以守护进程方式运行时，Redis默认会把pid写入/var/run/redis.pid文件，可以通过pidfile指定

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

- **port**

>指定Redis的监听端口，端口默认6379（来源一个女歌手的名字）

```shell
# Accept connections on the specified port, default is 6379 (IANA #815344).
# If port 0 is specified Redis will not listen on a TCP socket.
port 6379
```

- **bind**

>绑定的主机地址

```shell
# By default, if no "bind" configuration directive is specified, Redis listens
# for connections from all the network interfaces available on the server.
# It is possible to listen to just one or multiple selected interfaces using
# the "bind" configuration directive, followed by one or more IP addresses.
#
# Examples:
#
# bind 192.168.1.100 10.0.0.1
# bind 127.0.0.1 ::1
#
# ~~~ WARNING ~~~ If the computer running Redis is directly exposed to the
# internet, binding to all the interfaces is dangerous and will expose the
# instance to everybody on the internet. So by default we uncomment the
# following bind directive, that will force Redis to listen only into
# the IPv4 lookback interface address (this means Redis will be able to
# accept connections only from clients running into the same computer it
# is running).
#
# IF YOU ARE SURE YOU WANT YOUR INSTANCE TO LISTEN TO ALL THE INTERFACES
# JUST COMMENT THE FOLLOWING LINE.
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
bind 127.0.0.1
```

- **timeout**

> 当客户端闲置多长时间后关闭连接，如果指定为0，表示**关闭该功能**

```shell
# Close the connection after a client is idle for N seconds (0 to disable)
timeout 0
```

- **loglevel**

>指定日志记录级别，Redis共支持4个级别：debug、verbose、notice、warning，默认为notice

```shell
# Specify the server verbosity level.
# This can be one of:
# debug (a lot of information, useful for development/testing)
# verbose (many rarely useful info, but not a mess like the debug level)
# notice (moderately verbose, what you want in production probably)
# warning (only very important / critical messages are logged)
loglevel notice
```

- **logfile**

>日志记录方式，默认为标准输出，如果配置Redis为守护进程方式运行，而这里又配置为日志记录方式为标准输出，则将日志发送给/dev/null

```shell
# Specify the log file name. Also the empty string can be used to force
# Redis to log on the standard output. Note that if you use standard
# output for logging but daemonize, logs will be sent to /dev/null
logfile ""
```

- **databases**

>设置数据库的数量，默认数据库为0，可以使用SELECT \<dbid> 命令在连接上指定数据库id

```shell
# Set the number of databases. The default database is DB 0, you can select
# a different one on a per-connection basis using SELECT <dbid> where
# dbid is a number between 0 and 'databases'-1
databases 16
```

- **save**

>指定在多长时间内，有多少次更新操作，就将数据同步到数据文件，可以多个条件配合
>
>默认的

```shell
# Save the DB on disk:
#
#   save <seconds> <changes>
#
#   Will save the DB if both the given number of seconds and the given
#   number of write operations against the DB occurred.
#
#   In the example below the behaviour will be to save:
#   after 900 sec (15 min) if at least 1 key changed
#   after 300 sec (5 min) if at least 10 keys changed
#   after 60 sec if at least 10000 keys changed
#
#   Note: you can disable saving completely by commenting out all "save" lines.
#
#   It is also possible to remove all the previously configured save
#   points by adding a save directive with a single empty string argument
#   like in the following example:
#
#   save ""

save 900 1
save 300 10
save 60 10000
```

- **rdbcompression**

>指定存储至本地数据库时是否压缩数据，默认为yes，Redis采用LZF（Apache开源算法）压缩，如果为了节省CPU时间，可以关闭该选项，但会导致数据库文件变的巨大

```shell
# Compress string objects using LZF when dump .rdb databases?
# For default that's set to 'yes' as it's almost always a win.
# If you want to save some CPU in the saving child set it to 'no' but
# the dataset will likely be bigger if you have compressible values or keys.
rdbcompression yes
```

- **dbfilename**

>指定本地数据库文件名，默认为dump.rdb

```shell
# The filename where to dump the DB
dbfilename dump.rdb
```

- **dir**

>指定本地数据库存放目录

```shell
# The working directory.
#
# The DB will be written inside this directory, with the filename specified
# above using the 'dbfilename' configuration directive.
#
# The Append Only File will also be created inside this directory.
#
# Note that you must specify a directory here, not a file name.
dir ./
```

- **slaveof**

>设置当本机为slave服务时，设置master服务的ip地址及端口，Redis启动时，会自动从master进行数据同步

```shell
# Master-Slave replication. Use slaveof to make a Redis instance a copy of
# another Redis server. A few things to understand ASAP about Redis replication.
#
# 1) Redis replication is asynchronous, but you can configure a master to
#    stop accepting writes if it appears to be not connected with at least
#    a given number of slaves.
# 2) Redis slaves are able to perform a partial resynchronization with the
#    master if the replication link is lost for a relatively small amount of
#    time. You may want to configure the replication backlog size (see the next
#    sections of this file) with a sensible value depending on your needs.
# 3) Replication is automatic and does not need user intervention. After a
#    network partition slaves automatically try to reconnect to masters
#    and resynchronize with them.
#
# slaveof <masterip> <masterport>
```

- **masterauth**

>当master服务设置了密码保护时，salve服务连接master的密码

```shell
# If the master is password protected (using the "requirepass" configuration
# directive below) it is possible to tell the slave to authenticate before
# starting the replication synchronization process, otherwise the master will
# refuse the slave request.
#
# masterauth <master-password>
```

- **requirepass**

>设置Redis的连接密码，如果配置了连接密码，客户端在连接Redis时，需要通过AUTH \<password>命令提供密码，默认关闭

```shell
# Require clients to issue AUTH <PASSWORD> before processing any other
# commands.  This might be useful in environments in which you do not trust
# others with access to the host running redis-server.
#
# This should stay commented out for backward compatibility and because most
# people do not need auth (e.g. they run their own servers).
#
# Warning: since Redis is pretty fast an outside user can try up to
# 150k passwords per second against a good box. This means that you should
# use a very strong password otherwise it will be very easy to break.
#
# requirepass foobared
```

- **maxclients**

>设置同一时间最大客户端连接数，默认无限制，Redis可以同时打开的客户端连接数为Redis进程可以打开的最大描述文件符数，如果设置maxclients 0，表示不限制。当客户端连接数达到限制时，Redis会关闭新的连接，并想客户端返回max number of clients reached错误信息

```shell
# Set the max number of connected clients at the same time. By default
# this limit is set to 10000 clients, however if the Redis server is not
# able to configure the process file limit to allow for the specified limit
# the max number of allowed clients is set to the current file limit
# minus 32 (as Redis reserves a few file descriptors for internal uses).
#
# Once the limit is reached Redis will close all the new connections sending
# an error 'max number of clients reached'.
#
# maxclients 10000
```

- **maxmemory**

>指定Redis最大内存限制，Redis在启动时会把数据加载到内存中，达到最大内存后，Redis将会无法再进行写入操作，但任然可以进行读取操作。内存清理使用下面的内存清理策略进行。Redis新的vm机制，会把Key存放内存，Value存放在swap区

```shell
# Set a memory usage limit to the specified amount of bytes.
# When the memory limit is reached Redis will try to remove keys
# according to the eviction policy selected (see maxmemory-policy).
#
# If Redis can't remove keys according to the policy, or if the policy is
# set to 'noeviction', Redis will start to reply with errors to commands
# that would use more memory, like SET, LPUSH, and so on, and will continue
# to reply to read-only commands like GET.
#
# This option is usually useful when using Redis as an LRU or LFU cache, or to
# set a hard memory limit for an instance (using the 'noeviction' policy).
#
# WARNING: If you have slaves attached to an instance with maxmemory on,
# the size of the output buffers needed to feed the slaves are subtracted
# from the used memory count, so that network problems / resyncs will
# not trigger a loop where keys are evicted, and in turn the output
# buffer of slaves is full with DELs of keys evicted triggering the deletion
# of more keys, and so forth until the database is completely emptied.
#
# In short... if you have slaves attached it is suggested that you set a lower
# limit for maxmemory so that there is some free RAM on the system for slave
# output buffers (but this is not needed if the policy is 'noeviction').
#
# maxmemory <bytes>
```

- **maxmemory-policy**

>内存达到设置的最大内存后，将要使用哪种策略进行内存清理

```shell
# MAXMEMORY POLICY: how Redis will select what to remove when maxmemory
# is reached. You can select among five behaviors:
#
# volatile-lru -> Evict using approximated LRU among the keys with an expire set.
# allkeys-lru -> Evict any key using approximated LRU.
# volatile-lfu -> Evict using approximated LFU among the keys with an expire set.
# allkeys-lfu -> Evict any key using approximated LFU.
# volatile-random -> Remove a random key among the ones with an expire set.
# allkeys-random -> Remove a random key, any key.
# volatile-ttl -> Remove the key with the nearest expire time (minor TTL)
# noeviction -> Don't evict anything, just return an error on write operations.
#
# LRU means Least Recently Used
# LFU means Least Frequently Used
#
# Both LRU, LFU and volatile-ttl are implemented using approximated
# randomized algorithms.
#
# Note: with any of the above policies, Redis will return an error on write
#       operations, when there are no suitable keys for eviction.
#
#       At the date of writing these commands are: set setnx setex append
#       incr decr rpush lpush rpushx lpushx linsert lset rpoplpush sadd
#       sinter sinterstore sunion sunionstore sdiff sdiffstore zadd zincrby
#       zunionstore zinterstore hset hsetnx hmset hincrby incrby decrby
#       getset mset msetnx exec sort
#
# The default is:
#
# maxmemory-policy noeviction
```

- **appendonly**

>指定是否在每次更新操作后进行日志记录，Redis默认情况下是异步的把数据写入磁盘，如果不开启，可能会在断电时导致一段时间内的数据丢失。因为Redis本身同步数据文件是按上面save条件来同步的，所以有的数据会在一段时间内只存在于内存中。默认为no

```shell
# By default Redis asynchronously dumps the dataset on disk. This mode is
# good enough in many applications, but an issue with the Redis process or
# a power outage may result into a few minutes of writes lost (depending on
# the configured save points).
#
# The Append Only File is an alternative persistence mode that provides
# much better durability. For instance using the default data fsync policy
# (see later in the config file) Redis can lose just one second of writes in a
# dramatic event like a server power outage, or a single write if something
# wrong with the Redis process itself happens, but the operating system is
# still running correctly.
#
# AOF and RDB persistence can be enabled at the same time without problems.
# If the AOF is enabled on startup Redis will load the AOF, that is the file
# with the better durability guarantees.
#
# Please check http://redis.io/topics/persistence for more information.

appendonly no
```

- **appendfilename**

>指定更新日志文件名

```shell
# The name of the append only file (default: "appendonly.aof")

appendfilename "appendonly.aof"
```

- **appendfsync**

>指定更新日志条件，共有3个可选值

```shell
# The fsync() call tells the Operating System to actually write data on disk
# instead of waiting for more data in the output buffer. Some OS will really flush
# data on disk, some other OS will just try to do it ASAP.
#
# Redis supports three different modes:
#
# no: don't fsync, just let the OS flush the data when it wants. Faster.
# always: fsync after every write to the append only log. Slow, Safest.
# everysec: fsync only one time every second. Compromise.
#
# The default is "everysec", as that's usually the right compromise between
# speed and data safety. It's up to you to understand if you can relax this to
# "no" that will let the operating system flush the output buffer when
# it wants, for better performances (but if you can live with the idea of
# some data loss consider the default persistence mode that's snapshotting),
# or on the contrary, use "always" that's very slow but a bit safer than
# everysec.
#
# More details please check the following article:
# http://antirez.com/post/redis-persistence-demystified.html
#
# If unsure, use "everysec".

# appendfsync always
appendfsync everysec
# appendfsync no
```

- **hash-max-zipmap-entries**、**hash-max-zipmap-value**

>指定在超过一定的数量或者最大的元素超过某个临界值时，采用一种特殊的哈希算法

```shell
# Hashes are encoded using a memory efficient data structure when they have a
# small number of entries, and the biggest entry does not exceed a given
# threshold. These thresholds can be configured using the following directives.
hash-max-ziplist-entries 512
hash-max-ziplist-value 64
```

- **activerehashing**

>指定是否激活重置哈希，默认为开启

```shell
# Active rehashing uses 1 millisecond every 100 milliseconds of CPU time in
# order to help rehashing the main Redis hash table (the one mapping top-level
# keys to values). The hash table implementation Redis uses (see dict.c)
# performs a lazy rehashing: the more operation you run into a hash table
# that is rehashing, the more rehashing "steps" are performed, so if the
# server is idle the rehashing is never complete and some more memory is used
# by the hash table.
#
# The default is to use this millisecond 10 times every second in order to
# actively rehash the main dictionaries, freeing memory when possible.
#
# If unsure:
# use "activerehashing no" if you have hard latency requirements and it is
# not a good thing in your environment that Redis can reply from time to time
# to queries with 2 milliseconds delay.
#
# use "activerehashing yes" if you don't have such hard requirements but
# want to free memory asap when possible.
activerehashing yes
```

- **include**

>指定包含其它的配置文件，可以在同一个主机上多个Redis实例之间使用同一份配置文件，而同时各个实例又拥有自己的特定配置文件

```shell
# Include one or more other config files here.  This is useful if you
# have a standard template that goes to all Redis servers but also need
# to customize a few per-server settings.  Include files can include
# other files, so use this wisely.
#
# Notice option "include" won't be rewritten by command "CONFIG REWRITE"
# from admin or Redis Sentinel. Since Redis always uses the last processed
# line as value of a configuration directive, you'd better put includes
# at the beginning of this file to avoid overwriting config change at runtime.
#
# If instead you are interested in using includes to override configuration
# options, it is better to use include as the last line.
#
# include /path/to/local.conf
# include /path/to/other.conf
```