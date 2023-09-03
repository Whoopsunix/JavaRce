# JavaRceDemo

By. Whoopsunix

# 0x00 do what?

🚀 对照实战场景梳理较通用的 Java Rce 相关漏洞的利用方式

🚩 对于研究过的组件会针对可利用版本进行一个梳理 详情见代码

🚧 长期项目 不定期梳理后更新......

## 目录

- [命令执行](#0x01-command)
- [表达式注入](#0x02-expression-inject)
    - [OGNL](#ognl)
- [JDBC Attack](#0x03-jdbc-attack)
    - [Mysql](#mysql)
    - [PostgreSQL](#postgresql)
    - [H2database](#h2database)
    - [IBM DB2](#ibmdb2)
    - [ModeShape](#modeshape)
    - [Apache Derby](#apache-derby)
    - [Sqlite](#sqlite)
- [鸣谢](#Thanks)

目前涵盖：命令执行及输出、表达式及输出、JDBC

# 0x01 [Command](Command)

参考 [javaweb-sec](https://github.com/javaweb-sec/javaweb-sec) 有很详细的例子

## 执行Demo

- [x] Runtime
- [x] ProcessBuilder
- [x] ProcessImpl
- [x] ProcessImpl & UnixProcess
- [x] ProcessImpl & UnixProcess by unsafe + Native
- [x] Thread
- [x] ScriptEngineManager
- [x] jni

## 执行结果输出（InputStream 处理Demo）

- [x] java.lang.StringBuilder
- [x] java.io.ByteArrayOutputStream
- [x] java.util.Scanner
- [x] java.io.BufferedReader
- [x] java.io.InputStream.readNBytes > JDK 9
- [x] org.springframework:spring-core
- [x] org.apache.commons:commons-io

# 0x02 [Expression inject](Expression)

## OGNL

- [x] 普通执行demo：get、set
- [x] 有sout的回显 (Ps. 通过 Servlet 的回显移到 RceEcho 章节介绍)
    - 明文
    - 套一层base64加密

# 0x03 [JDBC Attack](JDBCAttack)

参考 [JDBC-Attack](https://github.com/su18/JDBC-Attack) 有很详细的例子

## [Mysql](JDBCAttack/MysqlAttack)

- [x] 文件读取
- [x] 反序列化
    - statementInterceptors
    - detectCustomCollations

## [PostgreSQL](JDBCAttack/PostgreSQLAttack)

- [x] CVE-2022-21724 RCE
  - AbstractXmlApplicationContext 实现类
- [x] 文件写入
  - loggerLevel / loggerFile
    - 原始方式写入 EL
    - 截断方式拓展更多写法 写入 jsp

## [H2database](JDBCAttack/H2Attack)

- [x] RUNSCRIPT 远程sql加载
- [x] 代码执行
  - INIT转义分号
  - TriggerJS
  - Groovy

## [IBMDB2](JDBCAttack/IBMDB2Attack)

- [x] JNDI

## [ModeShape](JDBCAttack/ModeShapeAttack)

- [x] JNDI

## [Apache Derby](JDBCAttack/DerbyAttack)

- [x] Serialize

## [Sqlite](JDBCAttack/SqliteAttack)

- [x] RCE

# Thanks

感谢师傅们的研究 带来了很大的帮助 :)

> https://github.com/javaweb-sec/javaweb-sec
>
> https://github.com/yzddmr6/Java-Js-Engine-Payloads
>
> https://github.com/su18/JDBC-Attack
>
> https://pyn3rd.github.io/
