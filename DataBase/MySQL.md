# [内容整理在GitHub，地址https://github.com/Muscleape/TestDemoProjects](https://github.com/Muscleape/TestDemoProjects/tree/master/DataBase/MySQL.md)
### MySQL分页查询

> limit的用法

- limit子句可以被用于强制select语句返回指定的记录数；
- 接受1个或2个整数常量做参数，第一个是返回记录行的**偏移量**，第二个是返回记录行的**最大数目**
```
-- 检索记录行6~15；
mysql> SELECT * FROM table LIMIT 5,10;
-- 从某个偏移量到记录集的最后，可以指定第二个参数为-1；
mysql> SELECT * FROM table LIMIT 95,-1;
-- 如果只给定一个参数，表示返回的作答记录行数目;
mysql> SELECT * FROM table LIMIT 5; 
-- LIMIT n 等价于 LIMIT 0,n;
```
> MySQL分页查询语句的性能分析

- 确保使用索引，下列语句中在ta_id和id两列上建立索引
```
mysql> SELECT * FROM ta WHERE ta_id = 111 ORDER BY id LIMIT 50,10;
```

- **使用子查询**，随着数据量增加，越往后LIMIT语句的偏移量就会越大，速度会明显变慢；
```
-- 使用子查询之前，偏移量为10000;
mysql> SELECT * FROM ta WHERE ta_id = 123 ORDER BY id LIMIT 10000, 10;
-- 使用子查询之后;
mysql>
SELECT * FROM ta WHERE id >=
(SELECT id FROM ta WHERE ta_id = 111 ORDER BY id LIMIT 10000,1)
LIMIT 10;
```

> 大数据量的MySQL表，LIMIT分页存在严重的性能问题：

优化方式：
- 使用子查询；
- 利用类似策略模式处理分页，判断100页以内使用基本的分页方式，100页以上使用子查询的分页方式；
- 使用有索引的列或主键列进行order by操作；
- 记录上次返回的主键，下次查询时使用主键过滤；
- 为了保证index索引列连续，可以为每个表增加一个自增的字段，并且加上索引；