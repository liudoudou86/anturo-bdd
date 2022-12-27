# anturo-bdd

#### 项目介绍

接口测试自动化框架，加持BDD

#### 项目结构

```
src -- 父工程
├── main
     └── java/com/qa/tools
                       ├── MysqlUtils --- jdbc封装工具类
                       └── TimeUtils --- 时间戳工具类
└── test -- 测试用例集
     ├── java/com/qa/demo
            ├── runner --- BDD启动器
            └── steps --- BDD用例集
     └── resources
            ├── features/demo --- 实例化需求
            ├── scripts --- sql脚本
            └── testng.xml --- testng配置文件
```

#### 技术选型

| 技术            | 说明      |
|---------------|---------|
| Java 1.8      | 编程语言    |
| Intellij IDEA | 开发工具    |
| Serenity BDD  | BDD集成   |
| RestAssured   | 接口请求工具  |
| Hamcrest      | 断言工具    |
| TestNG        | 测试框架    |
| Serenity      | 测试报告    |
| JDBC          | 数据库处理工具 |
| 部署方式          | Gitlab  |
