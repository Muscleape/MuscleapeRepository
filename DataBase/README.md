# 数据库通用内容整理

## 1、字符串拼接

- MySQL
  - 函数 CONCAT()
- Oracle：
  - 函数 CONCAT()
  - 连接符 ||
- SQLServer
  - 函数 CONCAT()
  - 连接符 +

_**注意**_

1. SQLServer 2012版本开始支持CONCAT()函数；
2. MySQL和SQLServer中可以使用CONCAT()函数拼接多个字符串；
3. Oracle中CONCAT()函数只能拼接两个字符串，多个字符串只能使用"||"来拼接；