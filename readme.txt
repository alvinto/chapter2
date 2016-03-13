本项目要点
1.使用jdbc，使用apache Commons DbUtils 简化jdbc使用;
2.使用数据库连接池（Apache DBCP） 解决频繁创建关闭数据库连接造成系统开销问题(创建关闭连接交给数据库连接池管理)
3.使用ThreadLocal保证线程安全
4.各种Util类
5.创建测试数据库，并记录备份insert语句，保证单元测试有数据可用
6.Properties文件读取
7.每个请求（查询列表、查询详情、编辑、新增...）都要一个对应的Controller,
如果请求多了，怎么办？整合到一个controller,这个就是接下来需要努力的方向