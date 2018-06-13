# MyBatis工作流程

## MyBatis的几个概念

- **Mapper配置**：可以使用基于XML的Mapper配置文件来实现，也可以使用基于Java注解的MyBatis注解来实现，甚至可以直接使用MyBatis提供的API来实现；
- **Mapper接口**：实质自定义的一个数据操作接口，类似于通常所说的DAO接口。早起的Mapper即可需要自定义实现，现在MyBatis会自动为Mapper接口创建**动态代理对象**，Mapper接口的方法通常与Mapper配置文件中的select、insert、update、delete等XML节点存在一一对应关系；
- **Executor**：MyBatis中所有的Mapper语句的执行都是通过Executor进行的，Executor是MyBatis的一个核心接口；
- **SqlSession**：MyBatis的关键对象，是执行持久化操作的对象，类似与JDBC中的Connection，SqlSession对象完全包含以数据库为背景的所有执行SQL操作的方法，底层封装了JDBC连接，可以用SqlSession实例来直接执行被映射的SQL语句；
- **SqlSessionFactory**：MyBatis的关键对象，是单个数据库映射关系经过编译后的内存镜像。SqlSessionFactory对象的实例可以通过SqlSessionFactoryBuilder对象类获得，而SqlSessionFactoryBuilder则可以从XML配置文件或一个预先定制的Configuration的实例构建出；

## MyBatis的工作流程

1. 加载Mapper配置的SQL映射文件，或者是注解的相关SQL内容;
2. 创建会话工厂（SqlSessionFactory）；
    - MyBatis通过读取配置文件的信息来构造出会话工厂；
3. 创建会话（SqlSession）；
    - 根据会话工厂，MyBatis就可以通过它来创建会话对象（SqlSession），会话对象是一个接口，该接口中包含了对数据库操作的增删改查方法；
4. 创建执行器（Executor）；
    - 会话对象本身不能直接操作数据库，所以使用了数据库执行器接口；
5. 封装SQL对象（MappedStatement）；
    - 执行器将待处理的SQL信息封装到一个对象中，其中包括SQL语句、输入的参数映射信息（Java简单类型、HashMap或POJO）和输出结果映射信息（Java简单类型、HashMap或POJO）；
6. 操作数据库；
    - 有了执行器和SQL信息封装对象就使用它们访问数据库，最后返回操作结果，结束流程；