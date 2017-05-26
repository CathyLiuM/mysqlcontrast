# mysqlcontrast
SpringBoot实现正式库测试库差异比较

由于测试库的table、function、procedure作了修改，同时正式库也需要和测试库保持一致，因此需要比较它们之间的差异。
本项目使用Spring Boot，配置简单，使用JdbcTemplate，把配置为Bean的DataSource作为参数传进JdbcTemplate的构造函数中，简化JDBC的使用。
导出一个Excel分为3个sheet“存储过程”，“表”，“函数”。
