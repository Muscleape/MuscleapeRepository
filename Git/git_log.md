# git log 配置

## 原生log

git 原生的log内容看起来杂乱无章，其实每次提交之后，各个节点之间的关系（合并、新分支）等都不能通过log命令，清晰地看到，例如下面

```shell
$ git log
commit 1bf516532493c8ffbca317661adc084dbb148691 (HEAD -> master, origin/master, origin/HEAD)
Author: muscleape@muscleape.com <muscleape@muscleape.com>
Date:   Mon Nov 20 17:43:12 2017 +0800

    支付修改

commit 080ee4e1bb2b8254ee4218a86055f8c9c52e053b
Author: muscleape@muscleape.com <muscleape@muscleape.com>
Date:   Sun Nov 12 14:05:25 2017 +0800

    app微信支付修改

commit f0efd69869981664261a83aa80008a427c30e5d9
Author: muscleape@muscleape.com <muscleape@muscleape.com>
Date:   Sun Nov 12 14:00:04 2017 +0800

    修改交易

commit 808b7fb2626d2c8b3b8b9327953f55803dc566ee
Author: muscleape@muscleape.com <muscleape@muscleape.com>
Date:   Thu Nov 9 17:08:49 2017 +0800

    修改支付

```

## 修改git配置

打开git的配置文件（.gitconfig），添加以下配置信息:

```shell
[alias]
    lg1 = log --graph --abbrev-commit --decorate --date=relative --format=format:'%C(bold blue)%h%C(reset) - %C(bold green)(%ar)%C(reset) %C(white)%s%C(reset) %C(dim white)- %an%C(reset)%C(bold yellow)%d%C(reset)' --all
    lg2 = log --graph --abbrev-commit --decorate --format=format:'%C(bold blue)%h%C(reset) - %C(bold cyan)%aD%C(reset) %C(bold green)(%ar)%C(reset)%C(bold yellow)%d%C(reset)%n''          %C(white)%s%C(reset) %C(dim white)- %an%C(reset)' -10 --all
    lg = !"git lg1"
```

该配置信息添加了两种git log命令，分别是

- git lg1 #显示所有的日志信息
- git lg2 #只显示10行日志信息

## 修改后日志显示效果

左侧那个形似“水管”的优美线条，各个节点之间的关系一目了然，当前dev分支所在的节点当前master分支所在的节点，都一目了然，这对于我们解决merge等问题，简直高效了很多。

```shell
$ git lg2
*   c7100d7 - Fri, 10 Aug 2018 09:40:06 +0800 (40 minutes ago) (HEAD -> dev, origin/dev)
|\            Merge branch 'dev' of http://test into dev - Muscleape
| * 078b373 - Fri, 10 Aug 2018 08:58:01 +0800 (82 minutes ago)
| |           天气 - Muscleape
| * dbb1a1d - Thu, 9 Aug 2018 17:52:47 +0800 (16 hours ago)
| |           值班信息 - Muscleape
* | 339593b - Fri, 10 Aug 2018 09:35:00 +0800 (45 minutes ago)
|/            新增组织id - Muscleape
*   f8a0483 - Thu, 9 Aug 2018 17:19:54 +0800 (17 hours ago)
|\            Merge branch 'dev' of http://test into dev - Muscleape
| * a503048 - Thu, 9 Aug 2018 17:19:18 +0800 (17 hours ago)
| |           增加 调派接口 - Muscleape
| * d59df10 - Thu, 9 Aug 2018 17:18:29 +0800 (17 hours ago)
| |           增加 业务常量 - Muscleape
| * 4bcb110 - Thu, 9 Aug 2018 17:17:53 +0800 (17 hours ago)
| |           增加 业务字段 - Muscleape
| * 8ebd3a5 - Thu, 9 Aug 2018 17:16:05 +0800 (17 hours ago)
| |           新增 消息 - Muscleape
| * d3e3548 - Thu, 9 Aug 2018 17:14:12 +0800 (17 hours ago)
| |           增加 权限 - Muscleape

```
