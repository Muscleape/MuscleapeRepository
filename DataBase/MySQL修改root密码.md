# MySQL 修改root密码

>试用环境说明：
>
>mysql：mysql  Ver 14.14 Distrib 5.7.20, for Linux (x86_64) using  EditLine wrapper
>
>OS:NAME="CentOS Linux" VERSION="7 (Core)"

## 注意：此方法是通过配置文件，使MySQL在登录时跳过验证，所以在改密码的过程中存在风险，生产环境建议防火墙中先禁止MySQL的端口

## 1、停MySQL服务

```shell
[root@z mysql]# systemctl stop mysqld;
[root@z mysql]# systemctl status mysqld;
● mysqld.service - MySQL Server
   Loaded: loaded (/usr/lib/systemd/system/mysqld.service; enabled; vendor preset: disabled)
   Active: inactive (dead) since Sun 2018-06-10 13:00:35 CST; 6s ago
     Docs: man:mysqld(8)
           http://dev.mysql.com/doc/refman/en/using-systemd.html
  Process: 7567 ExecStart=/usr/sbin/mysqld --daemonize --pid-file=/var/run/mysqld/mysqld.pid $MYSQLD_OPTS (code=exited, status=0/SUCCESS)
  Process: 7550 ExecStartPre=/usr/bin/mysqld_pre_systemd (code=exited, status=0/SUCCESS)
 Main PID: 7571 (code=exited, status=0/SUCCESS)

Jun 10 12:56:39 izuf6d9xxma2xa7sklzdrgz systemd[1]: Starting MySQL Server...
Jun 10 12:56:40 izuf6d9xxma2xa7sklzdrgz systemd[1]: Started MySQL Server.
Jun 10 13:00:33 izuf6d9xxma2xa7sklzdrgz systemd[1]: Stopping MySQL Server...
Jun 10 13:00:35 izuf6d9xxma2xa7sklzdrgz systemd[1]: Stopped MySQL Server.

```

## 2、修改MySQL配置文件

### 1、查找配置文件my.cnf文件，在mysqld配置节中添加以下配置

```shell
[mysqld]
#忘记密码时使用，该配置节会使登录时跳过密码验证
skip-grant-tables
```

### 2、登录数据库，密码不输入直接登录

```shell
[root@z mysql]# mysql -u root -p
Enter password:
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 5
Server version: 5.7.20 MySQL Community Server (GPL)

Copyright (c) 2000, 2017, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql>
```

### 3、进入数据库并修改密码

```shell
mysql> use mysql;
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed

mysql> update mysql.user set authentication_string=password('123456') where user='root';

mysql> flush privileges;
```

### 4、配置文件中去掉添加的配置，启动数据库