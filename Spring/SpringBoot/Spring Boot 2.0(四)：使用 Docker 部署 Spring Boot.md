# Spring Boot 2.0(四)：使用 Docker 部署 Spring Boot

>本文章内容转载自大佬**纯洁的微笑**， 大佬的网站<http://www.mooooc.com/>

安装 wget

```shell
[root@localhost ~]# yum install wget
```

下载网易yum源（路径/etc/yum.repos.d）

```shell
[root@localhost yum.repos.d]# wget http://mirrors.163.com/.help/CentOS7-Base-163.repo
```

以下两条命令生成缓存

```shell
[root@localhost yum.repos.d]# yum clean all
[root@localhost yum.repos.d]# yum makecache
```

yum安装docker

```shell
[root@localhost ~]# yum install docker
```

安装完成提示信息

```shell
######################
##省略其他输出内容####
######################
Installed:
  docker.x86_64 2:1.13.1-63.git94f4240.el7.centos

Dependency Installed:
  audit-libs-python.x86_64 0:2.8.1-3.el7                                           checkpolicy.x86_64 0:2.5-6.el7
  container-selinux.noarch 2:2.55-1.el7                                            container-storage-setup.noarch 0:0.9.0-1.rhel75.gite0997c3.el7
  docker-client.x86_64 2:1.13.1-63.git94f4240.el7.centos                           docker-common.x86_64 2:1.13.1-63.git94f4240.el7.centos
  libcgroup.x86_64 0:0.41-15.el7                                                   libsemanage-python.x86_64 0:2.5-11.el7
  oci-register-machine.x86_64 1:0-6.git2b44233.el7                                 oci-systemd-hook.x86_64 1:0.1.15-2.gitc04483d.el7
  oci-umount.x86_64 2:2.3.3-3.gite3c9055.el7                                       policycoreutils-python.x86_64 0:2.5-22.el7
  python-IPy.noarch 0:0.75-6.el7                                                   setools-libs.x86_64 0:3.3.8-2.el7
  skopeo-containers.x86_64 1:0.1.29-3.dev.git7add6fc.el7.0                         yajl.x86_64 0:2.0.4-4.el7

Dependency Updated:
  audit.x86_64 0:2.8.1-3.el7                  audit-libs.x86_64 0:2.8.1-3.el7                      libselinux.x86_64 0:2.5-12.el7  libselinux-python.x86_64 0:2.5-12.el7
  libselinux-utils.x86_64 0:2.5-12.el7        libsemanage.x86_64 0:2.5-11.el7                      libsepol.x86_64 0:2.5-8.1.el7   policycoreutils.x86_64 0:2.5-22.el7
  selinux-policy.noarch 0:3.13.1-192.el7_5.3  selinux-policy-targeted.noarch 0:3.13.1-192.el7_5.3 

Complete!
```

启动docker服务并将其设置为开机启动

```shell
[root@localhost ~]# systemctl start docker.service
[root@localhost ~]# systemctl enable docker.service
Created symlink from /etc/systemd/system/multi-user.target.wants/docker.service to /usr/lib/systemd/system/docker.service.
```

查看docker服务状态

```shell
[root@localhost ~]# systemctl status docker.service
● docker.service - Docker Application Container Engine
   Loaded: loaded (/usr/lib/systemd/system/docker.service; enabled; vendor preset: disabled)
   Active: active (running) since Sun 2018-04-15 19:13:52 EDT; 9min ago
     Docs: http://docs.docker.com
 Main PID: 1963 (dockerd-current)
   CGroup: /system.slice/docker.service
           ├─1963 /usr/bin/dockerd-current --add-runtime docker-runc=/usr/libexec/docker/docker-runc-current --default-runtime=docker-runc --exec-opt native.cgroupdri...
           └─1968 /usr/bin/docker-containerd-current -l unix:///var/run/docker/libcontainerd/docker-containerd.sock --metrics-interval=0 --start-timeout 2m --state-di...

Apr 15 19:13:52 localhost.localdomain dockerd-current[1963]: time="2018-04-15T19:13:52.026558731-04:00" level=warning msg="Docker could not enable SELinux on ... system"
Apr 15 19:13:52 localhost.localdomain dockerd-current[1963]: time="2018-04-15T19:13:52.077174211-04:00" level=info msg="Graph migration to content-addressabil...seconds"
Apr 15 19:13:52 localhost.localdomain dockerd-current[1963]: time="2018-04-15T19:13:52.077845742-04:00" level=info msg="Loading containers: start."
Apr 15 19:13:52 localhost.localdomain dockerd-current[1963]: time="2018-04-15T19:13:52.123134000-04:00" level=info msg="Firewalld running: true"
Apr 15 19:13:52 localhost.localdomain dockerd-current[1963]: time="2018-04-15T19:13:52.436692335-04:00" level=info msg="Default bridge (docker0) is assigned w...address"
Apr 15 19:13:52 localhost.localdomain dockerd-current[1963]: time="2018-04-15T19:13:52.821399299-04:00" level=info msg="Loading containers: done."
Apr 15 19:13:52 localhost.localdomain dockerd-current[1963]: time="2018-04-15T19:13:52.847036643-04:00" level=info msg="Daemon has completed initialization"
Apr 15 19:13:52 localhost.localdomain dockerd-current[1963]: time="2018-04-15T19:13:52.847148353-04:00" level=info msg="Docker daemon" commit="94f4240/1.13.1"...n=1.13.1
Apr 15 19:13:52 localhost.localdomain dockerd-current[1963]: time="2018-04-15T19:13:52.884165204-04:00" level=info msg="API listen on /var/run/docker.sock"
Apr 15 19:13:52 localhost.localdomain systemd[1]: Started Docker Application Container Engine.
Hint: Some lines were ellipsized, use -l to show in full.
```
使用Docker中国加速器

```shell
[root@localhost ~]# cd /etc/docker/
[root@localhost docker]# vim daemon.json
```

添加内容

```shell
#添加后：
{
    "registry-mirrors": ["https://registry.docker-cn.com"],
    "live-restore": true
}
```