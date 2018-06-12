# PHP项目图片验证码不显示

> 在PHP项目中图片及图片验证码不显示，是由于PHP没有安装gd扩展库，无法正常显示图片导致
>
> 下面出现的安装及配置命令中用到的路径，需要结合自己系统环境做调整

## 安装libpng

```shell
[root@z files]# wget http://jaist.dl.sourceforge.net/project/libpng/libpng16/1.6.34/libpng-1.6.34.tar.gz
[root@z files]# tar -xf libpng-1.6.34.tar.gz
[root@z files]# cd libpng-1.6.34
[root@z libpng-1.6.34]# CFLAGS="-O3 -fPIC" ./configure --prefix=/usr/local/libpng
[root@z libpng-1.6.34]# make && make install
```

## 安装jpegsrc

```shell
[root@z files]# wget http://www.ijg.org/files/jpegsrc.v9a.tar.gz
[root@z files]# tar -xf jpegsrc.v9a.tar.gz
[root@z files]# cd jpeg-9a/
[root@z jpeg-9a]# CFLAGS="-O3 -fPIC" ./configure --prefix=/usr/local/jpeg 
[root@z jpeg-9a]# make && make install
[root@z jpeg-9a]# mkdir -p /usr/local/jpeg/include
[root@z jpeg-9a]# mkdir -p /usr/local/jpeg/lib
[root@z jpeg-9a]# mkdir -p /usr/local/jpeg/bin
[root@z jpeg-9a]# mkdir -p /usr/local/jpeg/man/man1
```

## 安装freetype

```shell
[root@z files]# wget http://ftp.twaren.net/Unix/NonGNU/freetype/freetype-2.9.1.tar.gz
[root@z files]# tar -xf freetype-2.9.1.tar.gz
[root@z files]# cd freetype-2.9.1
[root@z freetype-2.9.1]# ./configure --prefix=/usr/local/freetype
[root@z freetype-2.9.1]# make && make install
```

## 进入PHP源码的GD目录

```shell
[root@z files]# cd /home/files/php-5.6.2/ext/gd
[root@z gd]# /usr/local/bin/phpize
[root@z gd]# ./configure --with-php-config=/usr/local/bin/php-config  --with-jpeg-dir=/usr/local/jpeg/  --with-png-dir=/usr/local/libpng/   --with-freetype-dir=/usr/local/freetype/
```

> 这里执行完后，需要注意输出内容中是否有提示错误信息
>
>比如我在执行时，最后有一个错误信息，经过确认是由于没有安装freetype-devel导致

```shell
.......省略其他信息........
If configure fails try --with-vpx-dir=<DIR>
checking for jpeg_read_header in -ljpeg... yes
checking for png_write_image in -lpng... yes
If configure fails try --with-xpm-dir=<DIR>
configure: error: freetype-config not found.
```

> 安装freetype-devel

```shell
[root@z gd]# yum install freetype-devel
```

重新执行配置命令 ./configure

编译并安装

```shell
[root@z gd]# make
[root@z gd]# make install
```

## 配置php

php.ini文件中添加扩展 extension=gd.so

```shell
[root@z gd]# vim /usr/local/lib/php.ini
```

## 重启web服务器和php-fpm

```shell
[root@z gd]# systemctl restart httpd
[root@z gd]# systemctl restart php-fpm
```