# CentOS7中PHP环境搭建

> MySQL数据库的安装这里不再赘述，只记录PHP及Nginx服务器的安装及配置过程
>
>涉及到的配置文件
>vim /usr/local/etc/php-fpm.conf
>
>vim /usr/local/nginx/conf/nginx.conf
>

## 1、安装并配置MySQL（或其他）数据库

>下面使用到的数据库是MySQL5.7版本

### **注意**

#### 1.1、安装MySQL时需要安装MySQL头文件，通过yum安装mysql-devel，安装完成后可以找到一个mysql.h文件即可；

## 2、安装PHP

### 2.1、安装php编译时依赖的包

```shell
yum -y install gcc gcc-c++ libxml2 libxml2-devel
```

### 2.2、下载php安装包

```shell
wget http://mirrors.sohu.com/php/php-5.6.2.tar.gz
```

### 2.3、解压下载的php安装包

```shell
tar -xf php-5.6.2.tar.gz
```

### 2.4、PHP安装前的配置

进入解压后路径，对即将安装的软件进行配置，检查当前环境是否满足要安装软件的依赖关系；

#### 2.4.1.进入解压文件

```shell
cd php-5.6.2
```

#### 2.4.2.查找mysql.h文件路径

```shell
[root@z etc]# find / -name mysql.h
/usr/include/mysql/mysql.h
```

#### 2.4.3.配置php

其中第二个参数为mysql.h文件的路径

```shell
[root@z php-5.6.2]# ./configure --enable-fpm --enable-mbstring --with-mysql=/usr --enable-pdo --with-pdo-mysql
```

>--enable-fpm的作用是开启php的fastcgi功能即开启php-fpm功能;
>
>--with-mysql=/usr/local/mysql是启用php支持mysql的功能，/usr/local/mysql是mysql数据库的安装路径，找到mysql.h文件的路径即可;
>
>--enable-mbstring表示启用mbstring模块,mbstring模块的主要作用在于检测和转换编码，提供对应的多字节操作的字符串函数。目前php内部的编码只支持ISO-8859-*、EUC-JP、UTF-8，其他的编码的语言是没办法在php程序上正确显示的，所以我们要启用mbstring模块。
>
>同时我们也只是简单的开启和扩展php的一部分功能，其他需要的功能，请自行添加;

----------

> **注意**执行该命令，最后的提示信息中是否有错误，如果有错误要处理错误再继续往下执行
>
>.configure命令执行完成后输出一下内容，才能够开始后面的操作

```shell
.........省略其他的内容........
+--------------------------------------------------------------------+
| License:                                                           |
| This software is subject to the PHP License, available in this     |
| distribution in the file LICENSE.  By continuing this installation |
| process, you are bound by the terms of this license agreement.     |
| If you do not agree with the terms of this license, you must abort |
| the installation process at this point.                            |
+--------------------------------------------------------------------+

Thank you for using PHP.

config.status: creating php5.spec
config.status: creating main/build-defs.h
config.status: creating scripts/phpize
config.status: creating scripts/man1/phpize.1
config.status: creating scripts/php-config
config.status: creating scripts/man1/php-config.1
config.status: creating sapi/cli/php.1
config.status: creating sapi/fpm/php-fpm.conf
config.status: creating sapi/fpm/init.d.php-fpm
config.status: creating sapi/fpm/php-fpm.service
config.status: creating sapi/fpm/php-fpm.8
config.status: creating sapi/fpm/status.html
config.status: creating sapi/cgi/php-cgi.1
config.status: creating ext/phar/phar.1
config.status: creating ext/phar/phar.phar.1
config.status: creating main/php_config.h
config.status: executing default commands
```

- 问题1：遇到一个错误信息，在/usr下找不到libmysqlclient

```shell
......省略其他的输出......
configure: error: Cannot find libmysqlclient under /usr.
Note that the MySQL client library is not bundled anymore!
```

- 问题1原因：经过查找，在64位的机器中libmysqlclient相关文件存放在【/usr/lib64/mysql/】下，但是php默认的搜索路径是【/usr/lib/】
- 问题1解决办法：在赋值相应的文件到【/usr/lib/mysql】目录下

```shell
[root@gz lib]# cd /usr/lib64
[root@z lib64]# cd mysql/
[root@z mysql]# ll
total 39960
-rw-r--r-- 1 root root 21389906 Mar  4 22:01 libmysqlclient.a
lrwxrwxrwx 1 root root       20 Dec  3  2017 libmysqlclient_r.so.18 -> libmysqlclient.so.18
lrwxrwxrwx 1 root root       24 Dec  3  2017 libmysqlclient_r.so.18.1.0 -> libmysqlclient.so.18.1.0
lrwxrwxrwx 1 root root       20 Jun 10 13:43 libmysqlclient.so -> libmysqlclient.so.20
lrwxrwxrwx 1 root root       24 Dec  3  2017 libmysqlclient.so.18 -> libmysqlclient.so.18.1.0
-rwxr-xr-x 1 root root  9580608 Sep 14  2017 libmysqlclient.so.18.1.0
lrwxrwxrwx 1 root root       24 Dec  3  2017 libmysqlclient.so.20 -> libmysqlclient.so.20.3.7
-rwxr-xr-x 1 root root  9884704 Sep 14  2017 libmysqlclient.so.20.3.7
-rw-r--r-- 1 root root    44126 Mar  4 22:00 libmysqlservices.a
drwxr-xr-x 4 root root     4096 Dec  3  2017 mecab
drwxr-xr-x 3 root root     4096 Dec  3  2017 plugin
[root@z mysql]# cp -rp libmysqlclient.* /usr/lib/mysql
```

#### 2.4.4、开始编译PHP

>根据机器的配置可能需要10~20分钟左右

```shell
[root@z php-5.6.2]# make
```

编译完成后输出内容

```shell
Generating phar.php
Generating phar.phar
PEAR package PHP_Archive not installed: generated phar will require PHP's phar extension be enabled.
clicommand.inc
directorytreeiterator.inc
pharcommand.inc
directorygraphiterator.inc
invertedregexiterator.inc
phar.inc

Build complete.
Don't forget to run 'make test'.
```

#### 2.4.5、安装php

```shell
[root@z php-5.6.2]# make install
```

安装完成查看php版本，确认是否安装成功

```shell
[root@z php-5.6.2]# php -v
PHP 5.6.2 (cli) (built: Jun 10 2018 14:45:55) 
Copyright (c) 1997-2014 The PHP Group
Zend Engine v2.6.0, Copyright (c) 1998-2014 Zend Technologies
```

#### 2.4.6、php.ini配置

安装完后，在/usr/local/lib目录下没有php.ini文件。这里可以先将php安装文件提供的模板复制到这里：

```shell
[root@z php-5.6.2]# cp /home/files/php-5.6.2/php.ini-production /usr/local/lib/php.ini
```

#### 2.4.7、php-fpm配置

- LNMP环境中的nginx是不支持php的，需要通过fastcgi来处理有关php的请求。而php需要php-fpm这个组件来支持；
- 在php5.3.3以前的版本php-fpm是以一个补丁包的形式存在的，而php5.3.3以后的php-fpm只需要在安装php-fpm开启这个功能即可。这个也就是前边，我们在配置php使用到的那个命令--enable-fpm。
- php-fpm功能开启后，我们还需要配置php-fpm。其实php-fpm的配置文件在安装php时，已经为我们提供了一个配置文件的模版。该模版为/usr/local/etc/php-fpm.conf.default

```shell
[root@z php-5.6.2]# cp /usr/local/etc/php-fpm.conf.default /usr/local/etc/php-fpm.conf
```

- 为了让php-fpm以服务的形式启动，需要复制php安装目录下的/sapi/fpm/init.d.php-fpm文件

```shell
[root@z php-5.6.2]# cp ./sapi/fpm/init.d.php-fpm /etc/init.d/php-fpm
```

- php-fpm文件添加执行权限，并启动php-fpm

```shell
[root@z php-5.6.2]# ll  /etc/init.d/php-fpm
-rw-r--r-- 1 root root 2350 Jun 10 15:16 /etc/init.d/php-fpm
[root@z php-5.6.2]# chmod a+x /etc/init.d/php-fpm
[root@z php-5.6.2]# ll  /etc/init.d/php-fpm
-rwxr-xr-x 1 root root 2350 Jun 10 15:16 /etc/init.d/php-fpm
```

```shell
[root@z php-5.6.2]# netstat -tunlp |grep 9000
[root@z php-5.6.2]# /etc/init.d/php-fpm start
Starting php-fpm  done
[root@z php-5.6.2]# netstat -tunlp |grep 9000
tcp        0      0 127.0.0.1:9000          0.0.0.0:*               LISTEN      22385/php-fpm: mast
```

## 3、Nginx服务器的安装和配置

### 3.1、安装依赖库

>如果已确认安装过可以跳过，不确认的话可以执行以下安装命令

- pcre: 用来作地址重写的功能；
- zlib：nginx 的gzip模块,传输数据打包，省流量（但消耗资源）；
- openssl：提供ssl加密协议；

```shell
[root@z php-5.6.2]# yum install -y gcc gcc-c++ autoconf automake
[root@z php-5.6.2]# yum install -y zlib zlib-devel openssl openssl-devel pcre-devel
```

### 3.2、下载nginx并安装

#### 3.2.1、下载

>wget下载速度很慢，我是直接从官网下载后上传到CentOS服务器上的

```shell
[root@z files]# wget http://nginx.org/download/nginx-1.14.0.tar.gz
```

#### 3.2.2、解压并编译安装

```shell
[root@z files]# tar -zxvf nginx-1.14.0.tar.gz
[root@z files]# cd nginx-1.14.0
[root@z nginx-1.14.0]# ./configure
.....省略部分输出内容...没有出现错误信息......
Configuration summary
  + using system PCRE library
  + OpenSSL library is not used
  + using system zlib library

  nginx path prefix: "/usr/local/nginx"
  nginx binary file: "/usr/local/nginx/sbin/nginx"
  nginx modules path: "/usr/local/nginx/modules"
  nginx configuration prefix: "/usr/local/nginx/conf"
  nginx configuration file: "/usr/local/nginx/conf/nginx.conf"
  nginx pid file: "/usr/local/nginx/logs/nginx.pid"
  nginx error log file: "/usr/local/nginx/logs/error.log"
  nginx http access log file: "/usr/local/nginx/logs/access.log"
  nginx http client request body temporary files: "client_body_temp"
  nginx http proxy temporary files: "proxy_temp"
  nginx http fastcgi temporary files: "fastcgi_temp"
  nginx http uwsgi temporary files: "uwsgi_temp"
  nginx http scgi temporary files: "scgi_temp"

[root@z nginx-1.14.0]# make
[root@z nginx-1.14.0]# make install
```

#### 3.2.3、创建nginx服务

>nginx默认安装在/usr/local/nginx/

```shell
[root@z nginx-1.14.0]# vim /etc/init.d/nginx
```

上面会创建一个nginx文件，在其中输入下面的内容

```shell
#!/bin/bash
# chkconfig:235 85 15
# description: Nginx is an HTTP server
. /etc/rc.d/init.d/functions
start() {
        echo "Start..."
        /usr/local/nginx/sbin/nginx &> /dev/null
        if [ $? -eq 0 ];then
                echo "Start successful!"
        else
                echo "Start failed!"
        fi
}
stop() {
        if killproc nginx -QUIT ;then
                echo "Stopping..."
        fi
}
restart() {
        stop
        sleep 1
        start
}
reload() {
        killproc nginx -HUP
        echo "Reloading..."
}
configtest() {
        /usr/local/nginx/sbin/nginx -t
}
case $1 in
start)
        start ;;
stop)
        stop ;;
restart)
        restart ;;
reload)
        reload ;;
configtest)
        configtest ;;
*)
        echo "Usage: nginx {start|stop|restart|reload|configtest}"
        ;;
esac
```

给该文件添加可执行权限

```shell
[root@z nginx-1.14.0]# ll /etc/init.d/nginx
-rw-r--r-- 1 root root 838 Jun 10 15:47 /etc/init.d/nginx
[root@z nginx-1.14.0]# chmod  +x  /etc/init.d/nginx
[root@z nginx-1.14.0]# ll /etc/init.d/nginx
-rwxr-xr-x 1 root root 838 Jun 10 15:47 /etc/init.d/nginx
```

启动nginx服务

```shell
[root@z nginx-1.14.0]# service nginx start
Starting nginx (via systemctl):        [  OK  ]
```

#### 3.2.4、配置nginx，使其能支持PHP

```shell
[root@z nginx-1.14.0]# vim /usr/local/nginx/conf/nginx.conf
```

```shell
location ~ \.php/?.*{
        root            /usr/local/nginx/html;
        fastcgi_pass   127.0.0.1:9000;
        fastcgi_index  index.php;
        fastcgi_param  SCRIPT_FILENAME  $document_root$fastcgi_script_name;
        include        fastcgi_params;
}
```

启动nginx服务

```shell
[root@z nginx-1.14.0]# service nginx start
Starting nginx (via systemctl):          [  OK  ]
```

#### 3.2.5、测试php文件

在nginx网站根目录下面新建index.php文件

```php
<?php phpinfo();?>
```